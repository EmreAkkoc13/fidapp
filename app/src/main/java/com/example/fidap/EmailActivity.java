package com.example.fidap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class EmailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final EditText editTextSubject = findViewById(R.id.editTextSubject);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final EditText editTextMessage = findViewById(R.id.editTextMessage);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button buttonSendEmail = findViewById(R.id.buttonSendEmail);

        buttonSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = editTextSubject.getText().toString();
                String message = editTextMessage.getText().toString();
                sendEmail(subject, message);
            }
        });
    }

    private void sendEmail(String subject, String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"fiddapp@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(intent, "E-posta Gönder"));
    }
}
