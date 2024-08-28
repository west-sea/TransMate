package com.example.madcampw2;

import android.graphics.Bitmap;
import android.util.Log;

public class MyGlobals {
    private String name;
    private String nickname;
    private String school;
    private String major;
    private String number;
    private String phone;
    private String mail;
    private String mate;
    private String eval;
    private String namen;
    private String nicknamen;
    private String schooln;
    private String majorn;
    private String numbern;
    private String phonen;
    private String mailn;
    private String maten;
    private String evaln;
    private String image;
    private String imagen;
    private Bitmap bitImg;
    private String me;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        Log.v("Global", "Global: " + name);
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMate() {
        return mate;
    }

    public void setMate(String mate) {
        this.mate = mate;
    }

    public String getEval() {
        return eval;
    }

    public void setEval(String eval) {
        this.eval = eval;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }



    //now
    public String getNamen() {
        return namen;
    }

    public void setNamen(String namen) {
        this.namen = namen;
        Log.v("Global", "Global: " + namen);
    }

    public String getNicknamen() {
        return nicknamen;
    }

    public void setNicknamen(String nicknamen) {
        this.nicknamen = nicknamen;
    }

    public String getSchooln() {
        return schooln;
    }

    public void setSchooln(String schooln) {
        this.schooln = schooln;
    }

    public String getMajorn() {
        return majorn;
    }

    public void setMajorn(String majorn) {
        this.majorn = majorn;
    }

    public String getNumbern() {
        return numbern;
    }

    public void setNumbern(String numbern) {
        this.numbern = numbern;
    }

    public String getPhonen() {
        return phonen;
    }

    public void setPhonen(String phonen) {
        this.phonen = phonen;
    }

    public String getMailn() {
        return mailn;
    }

    public void setMailn(String mailn) {
        this.mailn = mailn;
    }

    public String getMaten() {
        return maten;
    }

    public void setMaten(String maten) {
        this.maten = maten;
    }

    public String getEvaln() {
        return evaln;
    }

    public void setEvaln(String evaln) {
        this.evaln = evaln;
    }

    public Bitmap getBitImg() {
        return bitImg;
    }

    public void setBitImg(Bitmap bitImg) {
        this.bitImg = bitImg;
        // You can log or perform any necessary actions here
        Log.v("Global", "Global Bitmap Image is set");
    }

    public String getMe() {
        return me;
    }

    public void setMe(String me) {
        this.me = me;
    }

    private static MyGlobals instance = null;



    public static synchronized MyGlobals getInstance() {
        if (null == instance) {
            instance = new MyGlobals();
        }
        return instance;
    }



}
