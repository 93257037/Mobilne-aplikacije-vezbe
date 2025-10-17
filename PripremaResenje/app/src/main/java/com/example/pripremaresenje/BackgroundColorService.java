package com.example.pripremaresenje;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

public class BackgroundColorService extends Service {
    private Handler handler;
    private Runnable colorCheckRunnable;
    private static final int CHECK_INTERVAL = 10000; // 10 seconds

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startColorChecking();
        return START_STICKY; // Restart if killed
    }

    private void startColorChecking() {
        colorCheckRunnable = new Runnable() {
            @Override
            public void run() {
                // Send broadcast to check background color
                Intent broadcastIntent = new Intent("CHECK_BACKGROUND_COLOR");
                sendBroadcast(broadcastIntent);
                
                // Schedule next check
                handler.postDelayed(this, CHECK_INTERVAL);
            }
        };
        
        handler.post(colorCheckRunnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null && colorCheckRunnable != null) {
            handler.removeCallbacks(colorCheckRunnable);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
