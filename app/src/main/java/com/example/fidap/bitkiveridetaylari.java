package com.example.fidap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class bitkiveridetaylari extends AppCompatActivity {
    String veriId;
    ImageView bitkiveripp;
    TextView bitkiveriaciklama,bitkiceriad,bitkiveritur;
    private VTHelperb vtHelperb;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bitkiveridetaylari);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bitkirecycler), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bitkiveripp       = findViewById(R.id.bitkiveripp);
        bitkiveriaciklama = findViewById(R.id.bitkiveriaciklama);
        bitkiceriad       = findViewById(R.id.bitkiveriad);
        bitkiveritur      = findViewById(R.id.bitkiveritur);
        vtHelperb         = new VTHelperb(this);
        Intent verigetir = getIntent();
        veriId = verigetir.getStringExtra("veri_id");
        bitkiveridetaylarigoster();
    }
    private void bitkiveridetaylarigoster(){
        String idyeGoresorgu= "SELECT * FROM " + VTsabitler.TABLO_ADI + " WHERE " + VTsabitler.S_ID + "=\"" + veriId + "\"";
        SQLiteDatabase sqLiteDatabase = vtHelperb.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(idyeGoresorgu,null);
        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range") String id = "  " + cursor.getString(cursor.getColumnIndex(VTsabitlerb.S_ID));
                @SuppressLint("Range") String ad = "  " + cursor.getString(cursor.getColumnIndex(VTsabitlerb.S_AD));
                @SuppressLint("Range") String aciklama = " " + cursor.getString(cursor.getColumnIndex(VTsabitlerb.S_ACIKLAMA));
                @SuppressLint("Range") String tur = "  " + cursor.getString(cursor.getColumnIndex(VTsabitlerb.S_TUR));
                @SuppressLint("Range") String resimUri = cursor.getString(cursor.getColumnIndex(VTsabitler.S_RESIM));

                bitkiceriad.setText(ad);
                bitkiveriaciklama.setText(aciklama);
                bitkiveritur.setText(tur);

                if (resimUri !=null && !resimUri.isEmpty() ) {
                    bitkiveripp.setImageURI(Uri.parse(resimUri));
                }else {
                    bitkiveripp.setImageURI(null);
                }
            }
            while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
    }
}