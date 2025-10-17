package com.example.kolokvijum1.receivers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.kolokvijum1.R;

public class ColorChangeReceiver extends BroadcastReceiver {
    
    private static final String TAG = "ColorChangeReceiver";
    private static final String CHANNEL_ID = "color_channel";
    private static final int NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        String color = intent.getStringExtra("color");
        Log.d(TAG, "Received color: " + color);
        
        if (color != null) {
            Intent colorIntent = new Intent("com.example.kolokvijum1.UPDATE_COLOR");
            colorIntent.putExtra("color", color);
            context.sendBroadcast(colorIntent);
            
            sendNotification(context, color);
        }
    }

    private void sendNotification(Context context, String color) {
        NotificationManager notificationManager = 
            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Color Change Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }
        
        String notificationText = color.equals("BLUE") ? 
            "Boja pozadine je plava" : "Boja pozadine je zelena";
        
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Promena boje")
            .setContentText(notificationText)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true);
        
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
