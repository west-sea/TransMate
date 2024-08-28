package com.example.madcampw2.model

import org.json.JSONException
import org.json.JSONObject

class User {
    private val id: String? = null

    /*public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }*/  var name: String? = null
    var nickname: String? = null
    var school: String? = null
    var major: String? = null
    var number: String? = null
    var phone: String? = null
    var mail: String? = null

    @set:Throws(JSONException::class)
    var mate: String? = null
        set(mate) {
            var jsonObject: JSONObject? = null
            jsonObject = JSONObject(mate)
            var index = 1 // 초기 mate 인덱스
            var text = "" // 결과를 저장할 문자열
            while (true) {
                val mateKey = "mate$index"

                // jsonObject에서 mateKey를 키로 갖는 값 추출
                val mateValue = jsonObject.getString(mateKey)

                // mateValue가 null이면 더 이상 mate 정보가 없는 것으로 가정하고 루프 종료
                if (mateValue == null || mateValue.isEmpty()) {
                    break
                }

                // mateValue를 결과 문자열에 추가 (여기에서 원하는 형식으로 조정 가능)
                text += "$mateValue " // 여기에서 " "를 사용하여 mate 정보 사이에 공백을 추가
                index++ // 다음 mate 인덱스로 이동
            }
            field = text
        }

    var profile: String? = null
    var profile2: String? = null

    /*public String getLogId() {
        return logid;
    }

    public void setLogId(String logid) {
        this.logid = logid;
    }

    public String getLogPw() {
        return logpw;
    }

    public void setLogPw(String logpw) {
        this.logpw = logpw;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getTecList() {
        return teclist;
    }

    public void setTecList(String teclist) {
        this.teclist = teclist;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }*/  var eval: String? = null
    private val img: String? = null
    private val imgM: String? = null
    private val logid: String? = null
    private val logpw: String? = null
    private val period: String? = null
    private val teclist: String? = null
    private val gender: String? = null
}