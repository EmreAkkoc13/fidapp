package com.example.fidap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class meyveler extends AppCompatActivity {
    String m1[]={"Domates","Salatalık","Patlıcan","Biber","Soğan","Ispanak","Marul","Patates","Havuç","Çilek","Kavun","Karpuz","Bodur elma","Nar"};
    int   meyveresimleri[]= {R.drawable.a01domates,R.drawable.a02salatalik,R.drawable.a03patlican,R.drawable.a04biber,R.drawable.a05sogan,R.drawable.a06ispanak,R.drawable.a07marul,R.drawable.a08patates,R.drawable.a10havuc,R.drawable.a12cilek,R.drawable.a13kavun,R.drawable.a14karpuz,R.drawable.a15bodurelma,R.drawable.a16nar};
    RecyclerView recyclerview ;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_meyveler);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerview = findViewById(R.id.meyverecycler);
        meyveadapter meeyveadapter =new meyveadapter(this,m1,meyveresimleri);
        recyclerview.setAdapter(meeyveadapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
    }
}