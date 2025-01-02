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

public class hayvanmenu extends AppCompatActivity {
    String m1[]={"Kedi","Köpek","Tavşan","Muhabbet Kuşu","Su Kaplumbağası","Hamster","Akvaryum Balığı","Sultan Papağanı"};
    int resimler[]={R.drawable.a01kedi,R.drawable.a02kopek,R.drawable.a03tavsan,R.drawable.a04muhabbetkusu,R.drawable.a05sukaplumbagasi,R.drawable.a06hamster,R.drawable.a07balik,R.drawable.a03sultanpapagani};
    RecyclerView rececyler;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hayvanmenu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        rececyler = findViewById(R.id.hayvanrecycler);
        hayvanadapter hayvanadapter = new hayvanadapter(this , m1 , resimler);
        rececyler.setAdapter(hayvanadapter);
        rececyler.setLayoutManager(new LinearLayoutManager(this));

    }
}