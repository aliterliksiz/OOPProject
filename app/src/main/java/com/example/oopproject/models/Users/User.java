package com.example.oopproject.models.Users;

import android.content.Context;

import com.example.oopproject.MyDatabaseHelper;

public class User {
    private int id,bakiye;
    private String ad,soyad,tc,pw,hesapNo;

    MyDatabaseHelper myDB;

    public User(int bakiye, String ad, String soyad, String tc, String pw, String hesapNo) {
        this.bakiye = bakiye;
        this.ad = ad;
        this.soyad = soyad;
        this.tc = tc;
        this.pw = pw;
        this.hesapNo = hesapNo;
    }

    public User(int bakiye, String ad, String soyad, String tc, String pw, String hesapNo, Context context) {
        this.bakiye = bakiye;
        this.ad = ad;
        this.soyad = soyad;
        this.tc = tc;
        this.pw = pw;
        this.hesapNo = hesapNo;
        myDB = new MyDatabaseHelper(context);
    }

    public void displayDB()
    {
        myDB.displayDB();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBakiye() {
        return bakiye;
    }

    public void setBakiye(int bakiye) {
        this.bakiye = bakiye;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getHesapNo() {
        return hesapNo;
    }

    public void setHesapNo(String hesapNo) {
        this.hesapNo = hesapNo;
    }
}
