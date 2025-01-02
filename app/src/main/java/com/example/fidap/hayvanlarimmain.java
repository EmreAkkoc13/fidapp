package com.example.fidap;

import androidx.annotation.Nullable;
import androidx.core.provider.FontRequest;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.emoji2.text.EmojiCompat;
import androidx.emoji2.text.FontRequestEmojiCompatConfig;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class hayvanlarimmain extends AppCompatActivity {
    private FloatingActionButton yenihayvanekle;
    private RecyclerView rcveriler;
    VTHelper vtHelper;
    String adanzye =VTsabitler.S_AD+" ASC ";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hayvanlarimmain);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.hayvanrecycler), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            FontRequest fontRequest = new FontRequest(
                    "com.google.android.gms.fonts",
                    "com.google.android.gms",
                    "Noto Color Emoji Compat",
                    R.array.com_google_android_gms_fonts_certs);
            EmojiCompat.Config config = new FontRequestEmojiCompatConfig(this, fontRequest)
                    .setReplaceAll(true)
                    .registerInitCallback(new EmojiCompat.InitCallback() {
                        @Override
                        public void onInitialized() {
                            // EmojiCompat başlatıldı
                        }
                        @Override
                        public void onFailed(@Nullable Throwable throwable) {
                            // EmojiCompat başlatılamadı
                        }
                    });
            EmojiCompat.init(config);
            return insets;

        });
        rcveriler=findViewById(R.id.rvKayitlar);
        rcveriler.setLayoutManager(new LinearLayoutManager(this));
        vtHelper = new VTHelper(this);
        verileriyukle(adanzye);
        yenihayvanekle = findViewById(R.id.yenihayvanekle);
        yenihayvanekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hayvanekle = new Intent(hayvanlarimmain.this, yenihayvaneklemepage.class);
                startActivity(hayvanekle);
            }
        });
    }
    private void verileriyukle(String siralama) {
        adanzye = siralama;
        KayitAdapter kayitAdapter = new KayitAdapter(hayvanlarimmain.this,vtHelper.butunKayitlariAl(siralama));
        rcveriler.setAdapter(kayitAdapter);
    }
    protected void onResume() {
        verileriyukle(adanzye);
        super.onResume();
    }
}