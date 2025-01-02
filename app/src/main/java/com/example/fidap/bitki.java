package com.example.fidap;
import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class bitki extends AppCompatActivity {
    String m1[] = {"Kadife", "Kekik", "Maydonoz", "Nane", "Orkide", "Papatya", "Paşa Kılıcı", "Philodendron", "Sardunya", "Semiz Otu", "Afrika Menekşesi", "Aleo Vera", "Barış Çiçeği", "Benjamin", "Biberiye", "Dere otu", "Deve Tabanı", "Fesleğen", "Gül"};
    int Resimler[] = {R.drawable.a10kadife, R.drawable.a11kekik, R.drawable.a12maydonoz, R.drawable.a13nane, R.drawable.a14orkide, R.drawable.a15papatya, R.drawable.a16pasakilici, R.drawable.a17philodendron, R.drawable.a18sardunya, R.drawable.a19semizotu, R.drawable.a20afrika_menekesi, R.drawable.a21aleovera, R.drawable.a22bariscicegi, R.drawable.a23benjamin, R.drawable.a24biberiye, R.drawable.a25dereotu, R.drawable.a26devetaban, R.drawable.a27fesleen, R.drawable.a28gul};
    RecyclerView reco;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bitki);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        reco = findViewById(R.id.bitkirecycler);
        bitkiadapter bitkiadapter = new bitkiadapter(this,m1,Resimler);
        reco.setAdapter(bitkiadapter);
        reco.setLayoutManager(new LinearLayoutManager(this));

    }
}