package com.example.oopproject.models.Admins;

public class Admin {
    String ad,pw;
    int id;

    public Admin(String ad, String pw) {
        this.ad = ad;
        this.pw = pw;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
