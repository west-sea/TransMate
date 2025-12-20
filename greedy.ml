module F = Format

let greedy model prompt n =
  let select_max logits =
    match logits with
    | [] -> ""
    | (tok0, p0) :: rest ->
        let best_tok, _best_p =
          List.fold_left
            (fun (bt, bp) (t, p) -> if p > bp then (t, p) else (bt, bp))
            (tok0, p0) rest
        in
        best_tok
  in
  let rec loop i acc =
    if i <= 0 then acc
    else
      let out = Model.generate model acc in
      let tok = select_max out.logits in
      loop (i - 1) (acc ^ tok)
  in
  loop n prompt

module Make (Checker : GrammarChecker.CHECKER) = struct
  let constrained language model prompt n =
    let select_max logits =
      match logits with
      | [] -> ""
      | (tok0, p0) :: rest ->
          let best_tok, _best_p =
            List.fold_left
              (fun (bt, bp) (t, p) -> if p > bp then (t, p) else (bt, bp))
              (tok0, p0) rest
          in
          best_tok
    in

    let open_tag = "```" ^ language ^ "\n" in
    let close_tag = "\n```\n" in

    let must_start_with_object = language = "json" in

    (* backtick 포함 토큰 제거 *)
    let contains_backtick s = String.contains s '`' in

    (* 현재 코드가 완성된 상태인지 체크 *)
    let is_complete code =
      try
        let lexbuf = Checker.lexbuf_of_string code in
        let rec step_checkpoint cp =
          match cp with
          | Checker.Parser.InputNeeded _ -> (
              match Checker.Lexer.read lexbuf with
              | exception Checker.Lexer.LexerError _ -> false
              | token when Checker.Parser.is_eof token ->
                  (* EOF 도달 시 Accepted 상태인지 확인 *)
                  (match Checker.Parser.resume cp with
                  | Checker.Parser.Accepted _ -> true
                  | _ -> false)
              | token -> (
                  match Checker.step cp token with
                  | exception Checker.ParseError _ -> false
                  | cp' -> step_checkpoint cp'))
          | Checker.Parser.Shifting _ ->
              step_checkpoint (Checker.Parser.resume cp)
          | Checker.Parser.AboutToReduce _ ->
              step_checkpoint (Checker.Parser.resume cp)
          | Checker.Parser.Accepted _ -> true
          | Checker.Parser.HandlingError _ | Checker.Parser.Rejected -> false
        in
        step_checkpoint (Checker.start Lexing.dummy_pos)
      with _ -> false
    in

    (* prefix로서 valid한지 체크 *)
    let is_valid_prefix code =
      try
        let lexbuf = Checker.lexbuf_of_string code in
        let rec step_checkpoint cp =
          match cp with
          | Checker.Parser.InputNeeded _ -> (
              match Checker.Lexer.read lexbuf with
              | exception Checker.Lexer.LexerError _ ->
                  (* lexer 에러지만 입력이 끝났으면 부분 토큰일 수 있음 *)
                  lexbuf.lex_curr_pos >= lexbuf.lex_buffer_len
              | token when Checker.Parser.is_eof token -> true
              | token -> (
                  match Checker.step cp token with
                  | exception Checker.ParseError _ -> false
                  | cp' -> step_checkpoint cp'))
          | Checker.Parser.Shifting _ ->
              step_checkpoint (Checker.Parser.resume cp)
          | Checker.Parser.AboutToReduce _ ->
              step_checkpoint (Checker.Parser.resume cp)
          | Checker.Parser.Accepted _ -> true
          | Checker.Parser.HandlingError _ | Checker.Parser.Rejected -> false
        in
        step_checkpoint (Checker.start Lexing.dummy_pos)
      with _ -> false
    in

    let model_input code = prompt ^ open_tag ^ code in

    (* JSON 첫 토큰은 '{' 강제 *)
    let choose_first_token logits =
      if must_start_with_object then
        let rec find_lbrace = function
          | [] -> None
          | (t, p) :: rest ->
              if String.trim t = "{" || String.contains t '{' then Some (t, p)
              else find_lbrace rest
        in
        match find_lbrace logits with
        | Some (t, _) -> Some t
        | None -> None
      else None
    in

    (* valid한 토큰 중 확률 최대값 선택 *)
    let choose_token logits code_so_far =
      let logits =
        List.filter (fun (tok, _) -> not (contains_backtick tok)) logits
      in

      if String.length code_so_far = 0 then
        match choose_first_token logits with
        | Some tok -> tok
        | None -> select_max logits
      else
        let valid_tokens =
          List.filter
            (fun (tok, _) -> is_valid_prefix (code_so_far ^ tok))
            logits
        in
        match valid_tokens with
        | [] -> select_max logits
        | (tok0, p0) :: rest ->
            let best_tok, _ =
              List.fold_left
                (fun (bt, bp) (t, p) -> if p > bp then (t, p) else (bt, bp))
                (tok0, p0) rest
            in
            best_tok
    in

    (* 마지막 콤마를 제거하는 함수 *)
    let remove_trailing_comma code =
      let len = String.length code in
      (* 역순으로 스캔하여 마지막 콤마 찾기 *)
      let rec find_last_comma i =
        if i < 0 then -1
        else match code.[i] with
        | ',' -> i
        | ' ' | '\n' | '\t' | '\r' -> find_last_comma (i - 1)
        | _ -> -1
      in
      let comma_pos = find_last_comma (len - 1) in
      if comma_pos >= 0 then
        (* 콤마 이전 부분 + 콤마 이후 부분(공백 포함) *)
        (String.sub code 0 comma_pos) ^ (String.sub code (comma_pos + 1) (len - comma_pos - 1))
      else
        code
    in

    (* 생성 루프 - 완성될 때까지 계속 생성 *)
    let rec loop i code =
      (* 이미 완성되었으면 종료 *)
      if is_complete code then code
      else if i <= 0 then
        (* n번 다 썼는데 완성 안됨 - 강제로 닫기 시도 *)
        let rec try_complete curr =
          if is_complete curr then curr
          else
            (* 1. 먼저 } 추가만 시도 *)
            let with_brace = curr ^ "\n}" in
            if is_complete with_brace then with_brace
            else
              (* 2. 마지막 콤마 제거 후 } 추가 *)
              let without_comma = remove_trailing_comma curr in
              let with_brace = without_comma ^ "\n}" in
              if is_complete with_brace then with_brace
              else
                (* 3. 여러 개의 } 추가 시도 (중첩된 구조의 경우) *)
                let with_multiple_braces = without_comma ^ "\n}\n}" in
                if is_complete with_multiple_braces then with_multiple_braces
                else without_comma
        in
        try_complete code
      else
        let out = Model.generate model (model_input code) in
        let tok = choose_token out.logits code in
        loop (i - 1) (code ^ tok)
    in

    let code = loop n "" in
    open_tag ^ code ^ close_tag
end
