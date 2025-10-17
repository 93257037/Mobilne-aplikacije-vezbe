package com.example.kolokvijum1.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

/**
 * BackgroundService - servis koji proverava boju pozadine na svakih 10 sekundi
 */
public class BackgroundService extends Service {
    
    private static final String TAG = "BackgroundService";
    private Handler handler;
    private Runnable runnable;
    private boolean isBlue = true; // Pocetna boja je plava

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Service created");
        
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                // Menjamo boju
                isBlue = !isBlue;
                String color = isBlue ? "BLUE" : "GREEN";
                
                Log.d(TAG, "Sending broadcast with color: " + color);
                
                // Šaljemo broadcast sa bojom
                Intent intent = new Intent("com.example.kolokvijum1.COLOR_CHANGE");
                intent.putExtra("color", color);
                sendBroadcast(intent);
                
                // Ponavljamo posle 10 sekundi
                handler.postDelayed(this, 10000);
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service started");
        // Pokrećemo runnable
        handler.post(runnable);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Service destroyed");
        // Zaustavljamo runnable kada se servis zaustavi
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
