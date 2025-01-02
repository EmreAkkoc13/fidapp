package com.example.fidap;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class KayitAdapter extends RecyclerView.Adapter<KayitAdapter.KayitTutucu> {
    private final Context context;
    private final ArrayList<OrnekKayit> ornekList;
    VTHelper vtHelper;
    SQLiteDatabase sqLiteDatabase;

    public KayitAdapter(Context context, ArrayList<OrnekKayit> ornekList) {
        this.context = context;
        this.ornekList = ornekList;
        vtHelper = new VTHelper(context);
    }

    @NonNull
    @Override
    public KayitAdapter.KayitTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_satir_gorunumu, parent, false);
        return new KayitTutucu(view);
    }


    @Override
    public void onBindViewHolder(@NonNull KayitAdapter.KayitTutucu holder, @SuppressLint("RecyclerView") int position) {
        OrnekKayit ornekKayit = ornekList.get(position);
        String id = ornekKayit.getId();
        String ad = ornekKayit.getAd();
        String resim = ornekKayit.getResim();
        String tur = ornekKayit.getTur();
        String yas = ornekKayit.getYas();
        String aciklama = ornekKayit.getAciklama();
        Log.d("KayitAdapter", "Pozisyon: " + position + ", Resim URI: " + resim);

        holder.adi.setText(ad);
        holder.turu.setText(tur);
        holder.yasi.setText(yas);
        holder.aciklama.setText(aciklama);
        // Resim URI kontrolü
        if (resim == null || resim.equals("null")) {
            holder.hayvanpp.setImageResource(R.drawable.a01kedi);
        } else {
            Uri uri = Uri.parse(resim);
            holder.hayvanpp.setImageURI(uri);
        }

        // Sil ve Güncelle butonuna tıklama işlemi
        holder.btnSilGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guncellemeSilmePenceresiOlustur(position, id, ad, aciklama, tur, resim);
            }
        });

        // Detaylar sayfasına geçiş
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hyvndetay = new Intent(context, hayvanveridetaylari.class);
                hyvndetay.putExtra("veri_id", id);  // Intent'e veriyi ekleme
                context.startActivity(hyvndetay);   // startActivity ile tek bir Intent başlat
                Toast.makeText(context, "Detaylar", Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Güncelleme ve Silme işlemi penceresi
    public void guncellemeSilmePenceresiOlustur(int position, String id, String ad, String aciklama, String tur, String resim) {
        // ID kontrolü
        if (id == null || id.isEmpty()) {
            Log.e("VTHelper", "Silinmeye çalışılan ID geçerli değil.");
            Toast.makeText(context, "Geçersiz ID.", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] secenekler = {"Alarm oluştur", "Sil"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setItems(secenekler, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i == 0) {
                    // Alarm sayfasına giriş kısmı
                    Intent intent = new Intent(context, AlarmActivity.class);
                    context.startActivity(intent);
                } else if (i == 1) {
                    SQLiteDatabase sqLiteDatabase = null;
                    try {
                        // Veritabanını yazılabilir modda aç
                        sqLiteDatabase = vtHelper.getWritableDatabase();
                        Log.d("VTHelper", "Tablo Adı: " + VTsabitler.TABLO_ADI);
                        Log.d("VTHelper", "Sütun Adı: " + VTsabitler.S_ID);
                        Log.d("VTHelper", "Silinmeye çalışılan ID: " + id);

                        // Silme işlemini gerçekleştir
                        int silinenSatirSayisi = sqLiteDatabase.delete(VTsabitler.TABLO_ADI, VTsabitler.S_ID + "=?", new String[]{id});
                        if (silinenSatirSayisi > 0) {
                            // Başarılı silme işlemi
                            Toast.makeText(context, "Kayıt başarıyla silindi", Toast.LENGTH_SHORT).show();
                            ornekList.remove(position); // Listeden silinen öğeyi kaldır
                            notifyItemRemoved(position); // RecyclerView'i güncelle
                            notifyItemRangeChanged(position, ornekList.size()); // Diğer öğelerin pozisyonunu güncelle
                        } else {
                            // Kayıt bulunamadıysa
                            Toast.makeText(context, "Silme işlemi başarısız, kayıt bulunamadı", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        // Hata durumunda hatayı logcat'e yazdır
                        Log.e("VTHelper", "Veri silme hatası: " + e.getMessage(), e);
                        Toast.makeText(context, "Hata: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    } finally {
                        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                            sqLiteDatabase.close(); // Veritabanı bağlantısını kapat
                        }
                    }
                }
            }
        });
        builder.create().show();
    }

    @Override
    public int getItemCount() {
        return ornekList.size();
    }

    public class KayitTutucu extends RecyclerView.ViewHolder {
        ImageView hayvanpp;
        TextView adi, turu, yasi, aciklama;
        ImageButton btnSilGuncelle;

        public KayitTutucu(@NonNull View itemView) {
            super(itemView);
            hayvanpp = itemView.findViewById(R.id.hayvan_info_resmi);
            adi = itemView.findViewById(R.id.hayvan_info_name);
            turu = itemView.findViewById(R.id.turu_);
            yasi = itemView.findViewById(R.id.yasi_);
            aciklama = itemView.findViewById(R.id.aciklama_);
            btnSilGuncelle = itemView.findViewById(R.id.silguncelle);
        }
        public class App extends Application {
            @Override
            public void onCreate() {
                super.onCreate();
                createNotificationChannel();
            }
            private void createNotificationChannel() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    String channelId = "fidapAlarmChannel";
                    String channelName = "Fidap Alarm Kanalı";
                    String channelDescription = "Fidap uygulaması alarm bildirimleri için kullanılır.";
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
                    channel.setDescription(channelDescription);
                    NotificationManager notificationManager = getSystemService(NotificationManager.class);
                    if (notificationManager != null) {
                        notificationManager.createNotificationChannel(channel);
                    }
                }
            }
        }
    }
}
