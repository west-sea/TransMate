package com.example.madcampw2.matelist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.madcampw2.MyGlobals;
import com.example.madcampw2.R;

public class TwoFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two, container, false);

        TextView name2 = view.findViewById(R.id.name2);
        TextView nickname2 = view.findViewById(R.id.nickname2);
        TextView score2 = view.findViewById(R.id.score2);
        TextView school2 = view.findViewById(R.id.school2);
        TextView major2 = view.findViewById(R.id.major2);
        TextView stdid2 = view.findViewById(R.id.stdid2);
        TextView phonenum2 = view.findViewById(R.id.phonenum2);
        TextView email2 = view.findViewById(R.id.email2);
        TextView state2 = view.findViewById(R.id.state2);
        TextView sk12 = view.findViewById(R.id.sk12);
        TextView sk22 = view.findViewById(R.id.sk22);
        TextView sk32 = view.findViewById(R.id.sk32);
        TextView sk42 = view.findViewById(R.id.sk42);
        TextView sk52 = view.findViewById(R.id.sk52);
        TextView sk62 = view.findViewById(R.id.sk62);
        TextView sk72 = view.findViewById(R.id.sk72);
        TextView sk82 = view.findViewById(R.id.sk82);
        TextView sk92 = view.findViewById(R.id.sk92);
        TextView sk102 = view.findViewById(R.id.sk102);
        TextView sk112 = view.findViewById(R.id.sk112);
        ImageView imageView2 = view.findViewById(R.id.imageView2);


        Log.v("twofrag", "twofrag: "+MyGlobals.getInstance().getName());
        name2.setText(MyGlobals.getInstance().getName());
        nickname2.setText(MyGlobals.getInstance().getNickname());
        //score2.setText(MyGlobals.getInstance().getEval());
        school2.setText(MyGlobals.getInstance().getSchool());
        major2.setText(MyGlobals.getInstance().getMajor());
        stdid2.setText(MyGlobals.getInstance().getNumber());
        phonenum2.setText(MyGlobals.getInstance().getPhone());
        email2.setText(MyGlobals.getInstance().getMail());
        state2.setText(MyGlobals.getInstance().getMate());

        if(MyGlobals.getInstance().getImage() != null) {
            String combinedBase64 = MyGlobals.getInstance().getImage();
            byte[] decodedString = android.util.Base64.decode(combinedBase64, android.util.Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageView2.setImageBitmap(decodedByte);
        }


        String evaluation = MyGlobals.getInstance().getEval();
        if(evaluation != null) {
            if (evaluation.charAt(0) == '1') {
                sk12.setPressed(true);
            }
            if (evaluation.charAt(1) == '1') {
                sk22.setPressed(true);
            }
            if (evaluation.charAt(2) == '1') {
                sk32.setPressed(true);
            }
            if (evaluation.charAt(3) == '1') {
                sk42.setPressed(true);
            }
            if (evaluation.charAt(4) == '1') {
                sk52.setPressed(true);
            }
            if (evaluation.charAt(5) == '1') {
                sk62.setPressed(true);
            }
            if (evaluation.charAt(6) == '1') {
                sk72.setPressed(true);
            }
            if (evaluation.charAt(7) == '1') {
                sk82.setPressed(true);
            }
            if (evaluation.charAt(8) == '1') {
                sk92.setPressed(true);
            }
            if (evaluation.charAt(9) == '1') {
                sk102.setPressed(true);
            }
            if (evaluation.charAt(10) == '1') {
                sk112.setPressed(true);
            }
        }

        return view;
    }


}
