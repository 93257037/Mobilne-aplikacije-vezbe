package com.example.pripremaresenje;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class ColorChangeReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "COLOR_CHANGE_CHANNEL";
    private static final int NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("CHECK_BACKGROUND_COLOR".equals(intent.getAction())) {
            // Find the SecondActivity and check its background color
            Intent activityIntent = new Intent(context, SecondActivity.class);
            activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            
            // We'll use a different approach - send a broadcast to SecondActivity
            Intent colorCheckIntent = new Intent("COLOR_CHECK_REQUEST");
            context.sendBroadcast(colorCheckIntent);
        } else if ("COLOR_CHANGED".equals(intent.getAction())) {
            boolean isBlue = intent.getBooleanExtra("is_blue", true);
            showNotification(context, isBlue);
        }
    }

    private void showNotification(Context context, boolean isBlue) {
        createNotificationChannel(context);
        
        String colorText = isBlue ? "plava" : "zelena";
        String message = "Boja pozadine je " + colorText;
        
        Intent intent = new Intent(context, SecondActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Promena boje pozadine")
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        
        NotificationManager notificationManager = 
            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Color Change Notifications";
            String description = "Notifications for background color changes";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            
            NotificationManager notificationManager = 
                context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
