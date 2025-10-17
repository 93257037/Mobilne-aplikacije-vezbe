package com.example.kolokvijum1.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class BackgroundService extends Service {
    
    private static final String TAG = "BackgroundService";
    private Handler handler;
    private Runnable runnable;
    private boolean isBlue = true;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Service created");
        
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                isBlue = !isBlue;
                String color = isBlue ? "BLUE" : "GREEN";
                
                Log.d(TAG, "Sending broadcast with color: " + color);
                
                Intent intent = new Intent("com.example.kolokvijum1.COLOR_CHANGE");
                intent.putExtra("color", color);
                sendBroadcast(intent);
                
                handler.postDelayed(this, 10000);
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service started");
        handler.post(runnable);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Service destroyed");
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
