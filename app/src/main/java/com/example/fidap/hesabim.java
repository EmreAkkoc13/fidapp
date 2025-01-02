package com.example.fidap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class hesabim extends AppCompatActivity {
    ImageButton hayvankaydetme, bitkikaydetme;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hesabim);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bitkikaydetme  = findViewById(R.id.bitkieklemebtn);
        bitkikaydetme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(hesabim.this , bitkilerimmain.class);
                startActivity(a);
            }
        });

        hayvankaydetme = findViewById(R.id.hayvaneklemebtn);
        hayvankaydetme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hayvann = new Intent(hesabim.this,com.example.fidap.hayvanlarimmain.class);
                startActivity(hayvann);
            }
        });
    }
}