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

import java.security.PublicKey;
import java.util.ArrayList;

public class VTHelperb extends SQLiteOpenHelper {
    private final Context context;

    public VTHelperb(@Nullable Context context) {
        super(context, VTsabitlerb.VT_ADI, null, VTsabitlerb.VT_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(VTsabitlerb.TABLO_OLUSTUR); // Kayıt tablosu
        sqLiteDatabase.execSQL(VTsabitlerb.ALARM_TABLO_OLUSTUR); // Alarm tablosu
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + VTsabitlerb.TABLO_ADI);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + VTsabitlerb.ALARM_TABLO_ADI);
        onCreate(sqLiteDatabase);
    }
    public long alarmEkle(int bitkiId, long alarmZamani) {
        SQLiteDatabase sqLiteDatabase = null;
        long result = -1;
        try {
            sqLiteDatabase = this.getWritableDatabase();
            ContentValues degerler = new ContentValues();
            degerler.put(VTsabitlerb.BITKI_ID, bitkiId); // Bağlantılı bitkinin ID'si
            degerler.put(VTsabitlerb.ALARM_ZAMANI, alarmZamani); // Alarm zamanı
            result = sqLiteDatabase.insert(VTsabitlerb.ALARM_TABLO_ADI, null, degerler);
        } catch (SQLException e) {
            Log.e("VTHelperb", "Alarm ekleme hatası: " + e.getMessage());
        } finally {
            if (sqLiteDatabase != null) {
                sqLiteDatabase.close();
            }
        }
        return result;
    }
    public int alarmSilByBitkiId(int bitkiId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(
                VTsabitlerb.ALARM_TABLO_ADI,
                VTsabitlerb.BITKI_ID + " = ?",
                new String[]{String.valueOf(bitkiId)}
        );
    }



    public long kayitekle(String resim, String ad, String tur, String aciklama) {
    SQLiteDatabase sqLiteDatabase = null;
    long id = -1;
    try {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues degerler = new ContentValues();
        degerler.put(VTsabitlerb.S_RESIM, resim);
        degerler.put(VTsabitlerb.S_AD, ad);
        degerler.put(VTsabitlerb.S_TUR, tur);
        degerler.put(VTsabitlerb.S_ACIKLAMA, aciklama);
        id = sqLiteDatabase.insert(VTsabitlerb.TABLO_ADI,null,degerler);
    } catch (SQLException e){
        Log.e("VTHelperb"," Kayıt ekleme hatası " + e.getMessage());
        Toast.makeText(context, "HATA", Toast.LENGTH_SHORT).show();
    }finally {
        if (sqLiteDatabase !=null){
            sqLiteDatabase.close();
        }
    }
    return id;
    }





    public ArrayList<OrnekKayitb> butunkayitlariAL(String sirala){
        ArrayList<OrnekKayitb> kayitlarlistesib= new ArrayList<>();
        String secimsorgusu ="SELECT * FROM " + VTsabitlerb.TABLO_ADI + " ORDER BY " + sirala;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor =null;
        try {
            cursor = sqLiteDatabase.rawQuery(secimsorgusu,null);
            if(cursor.moveToFirst()){
                do {
                    @SuppressLint("Range")String resimUri= cursor.getString(cursor.getColumnIndex(VTsabitlerb.S_RESIM));
                   // Log.d("VTHelper", "Resim URI: " + resimUri); // Resim URI'sini logla
                    @SuppressLint("Range") OrnekKayitb ornekKayitb = new OrnekKayitb(
                            cursor.getString(cursor.getColumnIndex(VTsabitlerb.S_ID)),
                            cursor.getString(cursor.getColumnIndex(VTsabitlerb.S_AD)),
                            resimUri,
                            cursor.getString(cursor.getColumnIndex(VTsabitlerb.S_TUR)),
                            cursor.getString(cursor.getColumnIndex(VTsabitlerb.S_ACIKLAMA))
                    );
                    kayitlarlistesib.add(ornekKayitb);
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            Log.e("bitkilerimmain","Kayıt alma hatası"+e.getMessage());
        }finally {
            if (cursor != null) {
                cursor.close();
            }
            sqLiteDatabase.close();
        }
        return kayitlarlistesib;
    }

}
