package com.example.fidap;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;


import java.util.ArrayList;

public class VTHelper extends SQLiteOpenHelper {
    private final Context context;
    public VTHelper(@Nullable Context context) {
        super(context,VTsabitler.VT_ADI,null , VTsabitler.VT_VERSION);
        this.context = context;
    }
    @Override
    //TABLO OLUŞTURUDUĞUMUZ YER
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(VTsabitler.TABLO_OLUSTUR);
        sqLiteDatabase.execSQL(VTsabitler.ALARM_TABLO_OLUSTUR);

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            sqLiteDatabase.execSQL(VTsabitler.ALARM_TABLO_OLUSTUR);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + VTsabitler.TABLO_ADI);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + VTsabitler.ALARM_TABLO_ADI);
        onCreate(sqLiteDatabase);}
    }

    public long kayitekle(String resim, String ad, String tur, String yas, String aciklama) {
        SQLiteDatabase sqLiteDatabase = null;
        long id = -1;
        try {
            sqLiteDatabase = this.getWritableDatabase();
            ContentValues degerler = new ContentValues();
            degerler.put(VTsabitler.S_RESIM, resim);
            degerler.put(VTsabitler.S_AD, ad);
            degerler.put(VTsabitler.S_TUR, tur);
            degerler.put(VTsabitler.S_YAS, yas);
            degerler.put(VTsabitler.S_ACIKLAMA, aciklama);
            id = sqLiteDatabase.insert(VTsabitler.TABLO_ADI, null, degerler);
        } catch (SQLException e) {
            Log.e("VTHelper", "Kayıt ekleme hatası: " + e.getMessage());
            Toast.makeText(context, "Hata: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        }
        return id;
    }
    //veri silici
    public int veriSil(String id) {
        SQLiteDatabase sqLiteDatabase = null;
        int silinenSatirSayisi = 0;
        try {
            // Veritabanını yazılabilir modda aç
            sqLiteDatabase = this.getWritableDatabase();

            // Log ile `id` değerini kontrol edelim
            Log.d("VTHelper", "Silinmeye çalışılan ID: " + id);

            // Veritabanından ilgili kaydı sil
            silinenSatirSayisi = sqLiteDatabase.delete(
                    VTsabitler.TABLO_ADI,
                    VTsabitler.S_ID + "=?",
                    new String[]{id}
            );

            // Silinen satır sayısına göre işlem sonucunu bildir
            if (silinenSatirSayisi > 0) {
                Log.d("VTHelper", "Kayıt başarıyla silindi, silinen satır sayısı: " + silinenSatirSayisi);
            } else {
                Log.d("VTHelper", "Silme işlemi başarısız, kayıt bulunamadı");
            }
        } catch (Exception e) {
            // Hata durumunda log'a yazdır ve hata mesajını göster
            Log.e("VTHelper", "Veri silme hatası: " + e.getMessage(), e);
        } finally {
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                // Veritabanı bağlantısını kapat
                sqLiteDatabase.close();
            }
        }

        return silinenSatirSayisi;
    }
    public long alarmEkle(int hayvanId, long alarmZamani) {
        SQLiteDatabase sqLiteDatabase = null;
        long id = -1;
        try {
            sqLiteDatabase = this.getWritableDatabase();
            ContentValues degerler = new ContentValues();
            degerler.put(VTsabitler.A_HAYVAN_ID, hayvanId);
            degerler.put(VTsabitler.A_ZAMAN, alarmZamani);
            id = sqLiteDatabase.insert(VTsabitler.ALARM_TABLO_ADI, null, degerler);
        } catch (SQLException e) {
            Log.e("VTHelper", "Alarm ekleme hatası: " + e.getMessage());
            Toast.makeText(context, "Hata: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        }
        return id;
    }
    public int alarmSil(String alarmId) {
        SQLiteDatabase sqLiteDatabase = null;
        int silinenSatirSayisi = 0;
        try {
            sqLiteDatabase = this.getWritableDatabase();
            silinenSatirSayisi = sqLiteDatabase.delete(
                    VTsabitler.ALARM_TABLO_ADI,
                    VTsabitler.A_ID + "=?",
                    new String[]{alarmId}
            );
        } catch (Exception e) {
            Log.e("VTHelper", "Alarm silme hatası: " + e.getMessage());
        } finally {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        }
        return silinenSatirSayisi;
    }
    public ArrayList<String> hayvaninAlarmlariniAl(String hayvanId) {
        ArrayList<String> alarmlarListesi = new ArrayList<>();
        String secimSorgusu = "SELECT * FROM " + VTsabitler.ALARM_TABLO_ADI + " WHERE " + VTsabitler.A_HAYVAN_ID + "='1'";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.rawQuery(secimSorgusu, new String[]{hayvanId});
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String alarmZamani = cursor.getString(cursor.getColumnIndex(VTsabitler.A_ZAMAN));
                    alarmlarListesi.add(alarmZamani);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("VTHelper", "Alarmları alma hatası: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            sqLiteDatabase.close();
        }
        return alarmlarListesi;
    }



    public ArrayList<OrnekKayit> butunKayitlariAl(String sirala) {
        ArrayList<OrnekKayit> kayitlarListesi = new ArrayList<>();
        String secimSorgusu = "SELECT * FROM " + VTsabitler.TABLO_ADI + " ORDER BY " + sirala;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.rawQuery(secimSorgusu, null);
            if(cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String resimUri = cursor.getString(cursor.getColumnIndex(VTsabitler.S_RESIM));
                   // Log.d("VTHelper", "Resim URI: " + resimUri); // Resim URI'sini logla
                    @SuppressLint("Range") OrnekKayit ornekKayit = new OrnekKayit(
                            cursor.getString(cursor.getColumnIndex(VTsabitler.S_ID)),
                            cursor.getString(cursor.getColumnIndex(VTsabitler.S_AD)),
                            resimUri,
                            cursor.getString(cursor.getColumnIndex(VTsabitler.S_TUR)),
                            cursor.getString(cursor.getColumnIndex(VTsabitler.S_ACIKLAMA)),
                            cursor.getString(cursor.getColumnIndex(VTsabitler.S_YAS))
                    );
                    kayitlarListesi.add(ornekKayit);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("VTHelper", "Kayıtları alma hatası: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            sqLiteDatabase.close();
        }
        return kayitlarListesi;
    }





}
