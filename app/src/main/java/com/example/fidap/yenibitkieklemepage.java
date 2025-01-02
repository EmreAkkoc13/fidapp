package com.example.fidap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class yenibitkieklemepage extends AppCompatActivity {
    private static final int REQUEST_IMAGE_PICK =2;
    ImageView bitkipp;
    FloatingActionButton saveb;
    private EditText edtad,edttur,edtaciklama;
    private String   ad,tur,aciklama,resim;
    private Uri selectedImageUrib;
    VTHelperb vtHelperb;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_yenibitkieklemepage);
       initializeViews();
       vtHelperb = new VTHelperb(this);
       saveb.setOnClickListener(v -> veriekle());
       bitkipp.setOnClickListener(v->pickImageFromGallery());
    }
    private void initializeViews() {
        edtaciklama   = findViewById(R.id.bitkiaciklama);
        bitkipp       =findViewById(R.id.bitkipp);
        edtad         =findViewById(R.id.bitkiname);
        edttur        =findViewById(R.id.bitkituru);
        saveb         =findViewById(R.id.saveb);
    }
    private void  pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent,REQUEST_IMAGE_PICK);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode == RESULT_OK && requestCode ==REQUEST_IMAGE_PICK){
            Uri imageUri= data.getData();
            if (imageUri !=null){
                String realpath =getRealPathFromURI(imageUri);
                bitkipp.setImageURI(imageUri);
                saveImage(imageUri);
            }
        }
    }
    private void saveImage(Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            File externalDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            String uniqueFilneName ="profile_photo" + System.currentTimeMillis()+".jpg";
            File outputFile = new File(externalDir, uniqueFilneName);
            OutputStream outputStream = new FileOutputStream(outputFile);
            byte[] buffer = new byte[2048];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            inputStream.close();
            outputStream.close();
            // Resmin tam yolunu kaydet
            resim = outputFile.getAbsolutePath();
        } catch (IOException e){e.printStackTrace();}}



    private String getRealPathFromURI(Uri uri) {
        String filePath = null;
        if (uri.getScheme().equals("file")) {
            filePath = uri.getPath();
        } else {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);
                    filePath = cursor.getString(columnIndex);
                }
            }
        }
        return filePath;
    }

    private void veriekle() {
        ad = edtad.getText().toString().trim();
        tur = edttur.getText().toString().trim();
        aciklama = edtaciklama.getText().toString().trim();
        if (resim != null && !resim.isEmpty()) {
            long id = vtHelperb.kayitekle(resim, ad, tur, aciklama);
            if (id > 0) {
                Toast.makeText(this, "Veriniz kaydedildi", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Kayıt sırasında bir hata oluştu", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Lütfen bir resim seçin.", Toast.LENGTH_SHORT).show();
        }
        finish();
     }
    }

