package com.example.fidap;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private Button setAlarmButton, stopAlarmButton;

    private VTHelper vtYardimci;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        timePicker = findViewById(R.id.timePicker);
        setAlarmButton = findViewById(R.id.setAlarmButton);
        stopAlarmButton = findViewById(R.id.alarmkapat);

        vtYardimci = new VTHelper(this);

        setAlarmButton.setOnClickListener(v -> setAlarm());
        stopAlarmButton.setOnClickListener(v -> stopAlarm());
    }

    @SuppressLint({"ScheduleExactAlarm", "ObsoleteSdkInt"})
    private void setAlarm() {
        Calendar calendar = Calendar.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
            calendar.set(Calendar.MINUTE, timePicker.getMinute());
        } else {
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
        }
        calendar.set(Calendar.SECOND, 0);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);

        int alarmId = (int) System.currentTimeMillis(); // Benzersiz bir alarm ID
        intent.putExtra("ALARM_ID", alarmId);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                alarmId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        if (alarmManager != null) {
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    pendingIntent
            );

            Toast.makeText(this, "Günlük Alarm Kuruldu!", Toast.LENGTH_SHORT).show();
        }
            // Veritabanına alarm bilgilerini ekle
            vtYardimci.alarmEkle(alarmId, calendar.getTimeInMillis());

    }

    private void stopAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);

        int alarmId = (int) System.currentTimeMillis(); // Alarm ID'si
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                alarmId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
            Toast.makeText(this, "Alarm kapatıldı!", Toast.LENGTH_SHORT).show();

            // Veritabanından alarmı sil
            vtYardimci.alarmSil(String.valueOf(alarmId));
        }

        AlarmReceiver.stopRingtone(); // Ringtone'u durdur
    }
}
