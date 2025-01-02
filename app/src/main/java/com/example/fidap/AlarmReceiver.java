package com.example.fidap;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "fidapAlarmChannel";
    private static Ringtone ringtone;

    @Override
    public void onReceive(Context context, Intent intent) {
        createNotificationChannel(context);

        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }

        ringtone = RingtoneManager.getRingtone(context, alarmUri);
        if (ringtone != null) {
            ringtone.play();
        }

        int alarmId = intent.getIntExtra("ALARM_ID", -1);

        Intent stopIntent = new Intent(context, AlarmActivity.class);
        stopIntent.setAction("com.example.fidap.STOP_ALARM");
        stopIntent.putExtra("ALARM_ID", alarmId); // Alarm ID'sini gönder
        PendingIntent stopPendingIntent = PendingIntent.getActivity(
                context,
                0,
                stopIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.alarm)
                .setContentTitle("Fidap Alarm")
                .setContentText("Alarmınız çalıyor! Alarmı kapatmak için tıklayın.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(stopPendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(alarmId, builder.build());
        }
    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Fidap Alarm Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Channel for Fidap alarm notifications");

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    public static void stopRingtone() {
        if (ringtone != null && ringtone.isPlaying()) {
            ringtone.stop();
        }
    }
}
