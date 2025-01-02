package com.example.fidap;

public class OrnekKayit {
    String id;
    String ad;
    String resim;
    String tur;
    String aciklama;
    String yas;

    // Constructor'ı güncelle
    public OrnekKayit(String id, String ad, String resim, String tur, String aciklama, String yas) {
        this.id = id; // id burada doğru bir şekilde atanmalı
        this.ad = ad;
        this.resim = resim;
        this.tur = tur;
        this.aciklama = aciklama;
        this.yas = yas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id; // id'yi doğru güncelle
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getResim() {
        return resim;
    }

    public void setResim(String resim) {
        this.resim = resim;
    }

    public String getTur() {
        return tur;
    }

    public void setTur(String tur) {
        this.tur = tur;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getYas() {
        return yas;
    }

    public void setYas(String yas) {
        this.yas = yas;
    }
}
