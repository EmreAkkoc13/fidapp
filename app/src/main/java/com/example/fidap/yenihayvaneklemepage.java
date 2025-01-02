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
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class yenihayvaneklemepage extends AppCompatActivity {
    private static final int REQUEST_IMAGE_PICK = 2;

    ImageView hayvaneklepp;
    FloatingActionButton save;
    private EditText edtad, edttur, edtyas, edtaciklama;
    private String ad, tur, yas, aciklama, resim;
    private Uri selectedImageUri;
    VTHelper vtHelper;

    @SuppressLint({"MissingInflatedId", "CutPasteId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yenihayvaneklemepage);
        initializeViews();
        vtHelper = new VTHelper(this);
        save.setOnClickListener(v -> veriekle());
        hayvaneklepp.setOnClickListener(v -> pickImageFromGallery());
    }
    private void initializeViews() {
        hayvaneklepp = findViewById(R.id.hayvaneklepp);
        edtad        = findViewById(R.id.hayvanname);
        edttur       = findViewById(R.id.hayvanturu);
        edtyas       = findViewById(R.id.hayvanage);
        edtaciklama  = findViewById(R.id.hyvanaciklama);
        save         = findViewById(R.id.save);
    }

    // Galeriyi açan metot
    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_PICK) {
            Uri imageUri = data.getData();
            // Seçilen resmi ImageView'de göster
            if (imageUri != null) {
                String realPath = getRealPathFromURI(imageUri); // Gerçek dosya yolunu al
                hayvaneklepp.setImageURI(imageUri); // ImageView'de göster
                saveImage(imageUri); // Resmi kaydet
            }
        }
    }
    private void saveImage(Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            File externalDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

            // Benzersiz bir dosya adı oluştur
            String uniqueFileName = "profile_photo_" + System.currentTimeMillis() + ".jpg";
            File outputFile = new File(externalDir, uniqueFileName);

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

           // Toast.makeText(this, "Resim başarıyla kaydedildi: " + resim, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Resim kaydedilirken bir hata oluştu.", Toast.LENGTH_SHORT).show();
        }
    }


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
            yas = edtyas.getText().toString().trim();
            aciklama = edtaciklama.getText().toString().trim();
            // Eğer resim yolu atanmışsa veritabanına kaydet
            if (resim != null && !resim.isEmpty()) {
                long id = vtHelper.kayitekle(resim, ad, tur, yas, aciklama);
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
