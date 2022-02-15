package com.example.oopproject.models.Credits;

public class Kredi {

    private int id,miktar,taksitSayisi;
    private String ownerNo;
    private double aylikTaksit,kalanMiktar;

    public Kredi(int id, int miktar, int taksitSayisi, String ownerNo, double aylikTaksit, double kalanMiktar) {
        this.id = id;
        this.miktar = miktar;
        this.taksitSayisi = taksitSayisi;
        this.ownerNo = ownerNo;
        this.aylikTaksit = aylikTaksit;
        this.kalanMiktar = kalanMiktar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMiktar() {
        return miktar;
    }

    public void setMiktar(int miktar) {
        this.miktar = miktar;
    }

    public int getTaksitSayisi() {
        return taksitSayisi;
    }

    public void setTaksitSayisi(int taksitSayisi) {
        this.taksitSayisi = taksitSayisi;
    }

    public String getOwnerNo() {
        return ownerNo;
    }

    public void setOwnerNo(String ownerNo) {
        this.ownerNo = ownerNo;
    }

    public double getAylikTaksit() {
        return aylikTaksit;
    }

    public void setAylikTaksit(double aylikTaksit) {
        this.aylikTaksit = aylikTaksit;
    }

    public double getKalanMiktar() {
        return kalanMiktar;
    }

    public void setKalanMiktar(double kalanMiktar) {
        this.kalanMiktar = kalanMiktar;
    }
}
