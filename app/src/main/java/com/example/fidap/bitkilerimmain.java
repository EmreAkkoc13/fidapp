package com.example.fidap;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.provider.FontRequest;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.emoji2.text.EmojiCompat;
import androidx.emoji2.text.FontRequestEmojiCompatConfig;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class bitkilerimmain extends AppCompatActivity {
    private FloatingActionButton yenibitkiekleme;
    private RecyclerView rcvreriler;
    VTHelperb  vtHelperb;
    String adanzye = VTsabitler.S_AD+" ASC ";
    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bitkilerimmain);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bitkirecycler), (v, insets) -> {
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
        rcvreriler = findViewById(R.id.rvKayitlar);
        rcvreriler.setLayoutManager(new LinearLayoutManager(this));
        yenibitkiekleme = findViewById(R.id.yenibitkiekle);
        vtHelperb = new VTHelperb(this);
        verileriyuklez(adanzye);
        yenibitkiekleme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a =  new Intent(bitkilerimmain.this, yenibitkieklemepage.class);
                startActivity(a);
            }
        });
    }
    private void verileriyuklez(String siralama){
        adanzye = siralama;
        KayitAdapterb kayitAdapterb = new KayitAdapterb(bitkilerimmain.this,vtHelperb.butunkayitlariAL(siralama));
        rcvreriler.setAdapter(kayitAdapterb);

    }
    protected  void onResume(){
        verileriyuklez(adanzye);
        super.onResume();

    }
}