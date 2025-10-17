package com.example.pripremaresenje;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    private Switch colorSwitch;
    private boolean isBlue = true;
    private Intent serviceIntent;
    private BroadcastReceiver colorCheckReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        
        // Set blue background initially
        getWindow().getDecorView().setBackgroundColor(Color.BLUE);
        
        colorSwitch = findViewById(R.id.colorSwitch);
        
        colorSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Start the background color monitoring service
                    startColorMonitoringService();
                } else {
                    // Stop the service
                    stopColorMonitoringService();
                }
            }
        });
        
        // Register broadcast receiver for color check requests
        colorCheckReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if ("COLOR_CHECK_REQUEST".equals(intent.getAction())) {
                    // Change color and send notification
                    changeBackgroundColor();
                    sendColorChangeBroadcast();
                }
            }
        };
        
        IntentFilter filter = new IntentFilter("COLOR_CHECK_REQUEST");
        registerReceiver(colorCheckReceiver, filter);
    }

    private void startColorMonitoringService() {
        serviceIntent = new Intent(this, BackgroundColorService.class);
        startService(serviceIntent);
    }

    private void stopColorMonitoringService() {
        if (serviceIntent != null) {
            stopService(serviceIntent);
        }
    }

    public void changeBackgroundColor() {
        if (isBlue) {
            getWindow().getDecorView().setBackgroundColor(Color.GREEN);
            isBlue = false;
        } else {
            getWindow().getDecorView().setBackgroundColor(Color.BLUE);
            isBlue = true;
        }
    }

    public boolean isBackgroundBlue() {
        return isBlue;
    }
    
    private void sendColorChangeBroadcast() {
        Intent intent = new Intent("COLOR_CHANGED");
        intent.putExtra("is_blue", isBlue);
        sendBroadcast(intent);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (colorCheckReceiver != null) {
            unregisterReceiver(colorCheckReceiver);
        }
        stopColorMonitoringService();
    }
}
