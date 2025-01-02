package com.example.fidap;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class anamenuu extends AppCompatActivity {
    ImageButton btnbahce, btnevcil, btnhesabim, btnpazar, instabtn, websitebtn, mailbtn;
    private static final int PERMISSION_REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_anamenuu);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // İzinleri kontrol et ve iste
        checkAndRequestPermissions();

        btnbahce = findViewById(R.id.bahcemmenubtn);
        btnbahce.setOnClickListener(v -> {
            Intent intent = new Intent(anamenuu.this, bahcemenu.class);
            startActivity(intent);
        });

        btnevcil = findViewById(R.id.evcilhayvanmenubtn);
        btnevcil.setOnClickListener(v -> {
            Intent ev = new Intent(anamenuu.this, hayvanmenu.class);
            startActivity(ev);
        });

        btnhesabim = findViewById(R.id.hesabimmenubtn);
        btnhesabim.setOnClickListener(v -> {
            Intent hesab = new Intent(anamenuu.this, hesabim.class);
            startActivity(hesab);
        });

        btnpazar = findViewById(R.id.pazarmenubtn);
        btnpazar.setOnClickListener(v -> {
            Intent pazar = new Intent(anamenuu.this, pazarsayfasi.class);
            startActivity(pazar);
        });

        mailbtn = findViewById(R.id.mailbtn);
        mailbtn.setOnClickListener(v -> {
            Intent mail = new Intent(anamenuu.this, EmailActivity.class);
            startActivity(mail);
        });

        websitebtn = findViewById(R.id.btnwebsite);
        websitebtn.setOnClickListener(v -> {
            String s = "https://www.fidapp.tech/";
            Intent web = new Intent(Intent.ACTION_VIEW);
            web.setData(Uri.parse(s));
            startActivity(web);
        });

        instabtn = findViewById(R.id.btninsta);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(intent);
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }
    }

    private void checkAndRequestPermissions() {
        String[] permissions;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13+
            permissions = new String[]{
                    Manifest.permission.POST_NOTIFICATIONS // Bildirim izni
            };
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) { // Android 11+
            // Geniş depolama erişimi kontrolü
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            }
            permissions = new String[]{}; // Başka izin yok
        } else {
            // Android 6.0 - Android 10 arası
            permissions = new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
        }

        // Eksik izinleri kontrol et
        boolean allPermissionsGranted = true;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                allPermissionsGranted = false;
                break;
            }
        }

        // Eksik izinleri iste
        if (!allPermissionsGranted) {
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            boolean allGranted = true;

            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }

            if (allGranted) {
                // Tüm izinler verildi, gerekli işlemler yapılabilir
            } else {
                // Kullanıcı izinleri reddetti, açıklama gösterilebilir
            }
        }
    }
}
