package com.example.fidap;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class KayitAdapterb extends RecyclerView.Adapter<KayitAdapterb.Kayittutucub> {
    private final Context context;
    private final ArrayList<OrnekKayitb> orneklistb;
    VTHelperb vtHelperb ;
    SQLiteDatabase sqLiteDatabase;
    public KayitAdapterb(Context context , ArrayList<OrnekKayitb> orneklistb){
        this.context =context;
        this.orneklistb=orneklistb;
        vtHelperb = new VTHelperb(context);
    }
    @NonNull
    @Override
    public KayitAdapterb.Kayittutucub onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_satir_gorunumub,parent,false);
        return new Kayittutucub(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Kayittutucub holder,@SuppressLint("RecyclerView") int position) {
        OrnekKayitb ornekKayitb = orneklistb.get(position);
        String id = ornekKayitb.getId();
        String ad = ornekKayitb.getAd();
        String resim = ornekKayitb.getResim();
        String tur = ornekKayitb.getTur();
        String aciklama = ornekKayitb.getAciklama();
        Log.d("KayitAdapterb", "Pozisyon: " + position + ", Resim URI: " + resim);

        holder.adi.setText(ad);
        holder.turu.setText(tur);
        holder.aciklama.setText(aciklama);
        if (resim == null || resim.equals("null")) {
            holder.bitkipp.setImageResource(R.drawable.a01kedi);
        } else {
            holder.bitkipp.setImageURI(Uri.parse(resim));
    }
        holder.btnSilGuncelle.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
               guncellemeSilmePenceresiOlustur(position, id, ad, aciklama, tur, resim);
           }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btkdetay = new Intent( context ,bitkiveridetaylari.class);
                btkdetay.putExtra("veri_id",id);
                context.startActivity(btkdetay);
                Toast.makeText(context, "Detaylar", Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void guncellemeSilmePenceresiOlustur(int position,String id ,String ad,String aciklama, String tur ,String resim){
        if(id==null || id.isEmpty()){
            Log.e("VTHelperb","Silinmeye çalışan id yok");
            Toast.makeText(context,"geçersiz id",Toast.LENGTH_SHORT).show();
            return;
        }
        String[]secencekler={"Alarm oluştur", "Sil"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(secencekler, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i == 0) {
                    Intent intent = new Intent(context, AlarmActivity.class);
                    context.startActivity(intent);
                } else if (i==1) {
                    SQLiteDatabase sqLiteDatabase = null;
                    try {
                        sqLiteDatabase =vtHelperb.getWritableDatabase();
                        Log.d("VTHelper","Tablo adı "+ VTsabitlerb.TABLO_ADI);
                        Log.d("VTHelper","Sütun adı"+VTsabitlerb.S_ID);
                        Log.d("VTHelper","Silinmeye Çalışan ID"+id);
                        int silinenSatirSayisi = sqLiteDatabase.delete(VTsabitler.TABLO_ADI, VTsabitler.S_ID + "=?", new String[]{id});
                        if (silinenSatirSayisi > 0) {
                            // Başarılı silme işlemi
                            Toast.makeText(context, "Kayıt başarıyla silindi", Toast.LENGTH_SHORT).show();
                            orneklistb.remove(position); // Listeden silinen öğeyi kaldır
                            notifyItemRemoved(position); // RecyclerView'i güncelle
                            notifyItemRangeChanged(position, orneklistb.size()); // Diğer öğelerin pozisyonunu güncelle
                        } else {
                            // Kayıt bulunamadıysa
                            Toast.makeText(context, "Silme işlemi başarısız, kayıt bulunamadı", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        Log.e("VTHelper", "Veri silme hatası: " + e.getMessage(), e);
                        Toast.makeText(context, "Hata: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }finally {
                        if (sqLiteDatabase !=null && sqLiteDatabase.isOpen()){
                            sqLiteDatabase.close();
                        }
                    }

                }

            }
        });
        builder.create().show();
    }

    @Override
    public int getItemCount() {
        return orneklistb.size();
    }

    public class Kayittutucub extends RecyclerView.ViewHolder {
        ImageView bitkipp;
        TextView adi,turu,aciklama;
        ImageButton btnSilGuncelle;
        public Kayittutucub(@NonNull View itemView) {
            super(itemView);
            bitkipp       =itemView.findViewById(R.id.bitki_info_resmi);
            adi           =itemView.findViewById(R.id.bitki_info_name);
            turu          =itemView.findViewById(R.id.turu);
            aciklama      =itemView.findViewById(R.id.aciklama);
            btnSilGuncelle= itemView.findViewById(R.id.silguncelle);
        }
        public class App extends Application{
            @Override
            public void onCreate(){
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
