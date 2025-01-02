package com.example.fidap;

public class VTsabitlerb {
    public static final String VT_ADI = "KAYITLARIMM";
    public static final int VT_VERSION = 1;
    public static final String TABLO_ADI = "KAYITLARIM_TABLO";
    public static final String S_ID = "ID";
    public static final String S_RESIM = "RESIM";
    public static final String S_AD = "AD";
    public static final String S_TUR = "TUR";
    public static final String S_ACIKLAMA = "ACIKLAMA";

    public static final String ALARM_TABLO_ADI = "ALARM_TABLO";
    public static final String ALARM_ID = "ALARM_ID";
    public static final String BITKI_ID = "BITKI_ID"; // Hangi bitki için alarm
    public static final String ALARM_ZAMANI = "ALARM_ZAMANI";

    public static final String TABLO_OLUSTUR =
            "CREATE TABLE " + TABLO_ADI + " (" +
                    S_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    S_RESIM + " TEXT, " +
                    S_AD + " TEXT, " +
                    S_TUR + " TEXT, " +
                    S_ACIKLAMA + " TEXT)";

    public static final String ALARM_TABLO_OLUSTUR =
            "CREATE TABLE " + ALARM_TABLO_ADI + " (" +
                    ALARM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    BITKI_ID + " INTEGER, " +
                    ALARM_ZAMANI + " INTEGER, " +
                    "FOREIGN KEY(" + BITKI_ID + ") REFERENCES " + TABLO_ADI + "(" + S_ID + ") ON DELETE CASCADE)";


}
