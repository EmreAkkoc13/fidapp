package com.example.fidap;

public class OrnekKayitb {
    String id;
    String ad;
    String resim;
    String tur ;
    String aciklama;

    public OrnekKayitb(String id, String ad , String resim, String tur ,String aciklama){
        this.id = id;
        this.ad = ad ;
        this.resim = resim;
        this.tur = tur;
        this.aciklama = aciklama;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getAd() { return ad; }

    public void setAd(String ad) { this.ad = ad;}

    public String getResim() { return resim; }

    public void setResim(String resim) { this.resim = resim; }

    public  String getTur() { return tur; }

    public void setTur(String tur) {this.tur = tur; }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }



}
