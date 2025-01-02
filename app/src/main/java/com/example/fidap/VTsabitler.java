package com.example.fidap;

public class VTsabitler {
    public static final      String VT_ADI     ="KAYITLARIM";
    public static final      int    VT_VERSION = 2;
    public static final      String TABLO_ADI  ="KAYITLARIM_TABLO";
    public static final      String S_ID       ="ID";
    public static final      String S_RESIM    ="RESIM";
    public static final      String S_AD       ="AD";
    public static final      String S_TUR      ="TUR";
    public static final      String S_YAS      ="YAS";
    public static final      String S_ACIKLAMA ="ACIKLAMA";

    public static final     String ALARM_TABLO_ADI ="ALARM_TABLO";
    public static final     String A_ID            ="ALARM_ID";
    public static final     String A_HAYVAN_ID     ="HAYVAN_ID";
    public static final     String A_ZAMAN         ="ALARM_ZAMANI";

    public static final String TABLO_OLUSTUR = "CREATE TABLE " + TABLO_ADI + " ("
            + S_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + S_RESIM + " TEXT, "
            + S_AD + " TEXT, "
            + S_TUR + " TEXT, "
            + S_YAS + " TEXT, "
            + S_ACIKLAMA + " TEXT"
            + ")";

    public static final String ALARM_TABLO_OLUSTUR = "CREATE TABLE " + ALARM_TABLO_ADI + " ("
            + A_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + A_HAYVAN_ID + " INTEGER, "
            + A_ZAMAN + " TEXT, "
            + "FOREIGN KEY(" + A_HAYVAN_ID + ") REFERENCES " + TABLO_ADI + "(" + S_ID + ")"
            + ")";


}