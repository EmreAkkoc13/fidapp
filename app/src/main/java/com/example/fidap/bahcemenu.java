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

public class bahcemenu extends AppCompatActivity {
    ImageButton meyve,sebze;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bahcemenu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        meyve = findViewById(R.id.meyvebtn);
        meyve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent meyvee= new Intent(bahcemenu.this, meyveler.class);
                startActivity(meyvee);
            }
        });
        sebze = findViewById(R.id.bitkibtn);
        sebze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e = new Intent(bahcemenu.this, bitki.class);
                startActivity(e);
            }
        });





    }
}