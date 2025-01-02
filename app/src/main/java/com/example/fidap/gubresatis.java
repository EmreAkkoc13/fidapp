package com.example.fidap;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

public class gubresatis extends AppCompatActivity {
    RecyclerView recyclerView;
    String m1[] = {"3'ü bir arada", "Toprak Gübre", "Beni Unutma Çiçeği", "Siyah domates", "Gül tohumları"};
    int Resimler[] = {R.drawable.g1, R.drawable.g2, R.drawable.th1, R.drawable.th2, R.drawable.th3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gubresatis);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.gubrerecycler);

    }
}