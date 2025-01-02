package com.example.fidap;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class hayvanveridetaylari extends AppCompatActivity {
    String veriId;
    ImageView hayvanveripp;
    TextView hayvanveriaciklama, hayvanveriad, hayvanveritur;
    private VTHelper vtHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hayvanveridetaylari);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.hayvanrecycler), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        hayvanveripp = findViewById(R.id.hayvanveripp);
        hayvanveriaciklama = findViewById(R.id.hayvanveriaciklama);
        hayvanveriad = findViewById(R.id.hayvanveriad);
        hayvanveritur = findViewById(R.id.hayvanveritur);
        vtHelper = new VTHelper(this);
        Intent verigetir = getIntent();
        veriId = verigetir.getStringExtra("veri_id");
        hayvanveridetaylarigoster();
    }




    private void hayvanveridetaylarigoster() {
        String idyeGoreSorgu = "SELECT * FROM " + VTsabitler.TABLO_ADI + " WHERE " + VTsabitler.S_ID + "=?";
        SQLiteDatabase sqLiteDatabase = vtHelper.getReadableDatabase();
        Cursor cursor = null;
        Cursor alarmCursor = null;

        try {
            // Ana tablodan veriyi çek
            cursor = sqLiteDatabase.rawQuery(idyeGoreSorgu, new String[]{veriId});
            if (cursor.moveToFirst()) {
                @SuppressLint("Range") String id    = cursor.getString(cursor.getColumnIndex(VTsabitler.S_ID));
                @SuppressLint("Range") String ad    = cursor.getString(cursor.getColumnIndex(VTsabitler.S_AD));
                @SuppressLint("Range") String tur    = cursor.getString(cursor.getColumnIndex(VTsabitler.S_TUR));
                @SuppressLint("Range") String aciklama = cursor.getString(cursor.getColumnIndex(VTsabitler.S_ACIKLAMA));
                @SuppressLint("Range") String resimUri = cursor.getString(cursor.getColumnIndex(VTsabitler.S_RESIM));

                // Verileri ayarla
                hayvanveriad.setText(ad);
                hayvanveriaciklama.setText(aciklama);
                hayvanveritur.setText(tur);


                // Resmi URI ile ayarla
                if (resimUri != null && !resimUri.isEmpty()) {
                    hayvanveripp.setImageURI(Uri.parse(resimUri));
                } else {
                    hayvanveripp.setImageResource(R.drawable.a01kedi); // Resim yoksa varsayılan bir resim kullan
                }



            }
        } catch (Exception e) {
            hayvanveriaciklama.setText("Hata: " + e.getMessage());
        }

    }

}
