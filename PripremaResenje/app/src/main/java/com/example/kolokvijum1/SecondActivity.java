package com.example.kolokvijum1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kolokvijum1.receivers.ColorChangeReceiver;
import com.example.kolokvijum1.services.BackgroundService;

public class SecondActivity extends AppCompatActivity {

    private Switch switchService;
    private LinearLayout layout;
    private ColorChangeReceiver colorChangeReceiver;
    private ColorUpdateReceiver colorUpdateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        
        layout = findViewById(R.id.secondActivityLayout);
        switchService = findViewById(R.id.switchService);
        
        colorChangeReceiver = new ColorChangeReceiver();
        IntentFilter filter = new IntentFilter("com.example.kolokvijum1.COLOR_CHANGE");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(colorChangeReceiver, filter, Context.RECEIVER_NOT_EXPORTED);
        } else {
            registerReceiver(colorChangeReceiver, filter);
        }
        
        colorUpdateReceiver = new ColorUpdateReceiver();
        IntentFilter updateFilter = new IntentFilter("com.example.kolokvijum1.UPDATE_COLOR");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(colorUpdateReceiver, updateFilter, Context.RECEIVER_NOT_EXPORTED);
        } else {
            registerReceiver(colorUpdateReceiver, updateFilter);
        }
        
        switchService.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent serviceIntent = new Intent(SecondActivity.this, BackgroundService.class);
                
                if (isChecked) {
                    startService(serviceIntent);
                } else {
                    stopService(serviceIntent);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (colorChangeReceiver != null) {
            unregisterReceiver(colorChangeReceiver);
        }
        if (colorUpdateReceiver != null) {
            unregisterReceiver(colorUpdateReceiver);
        }
    }
    
    private class ColorUpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String color = intent.getStringExtra("color");
            if (color != null) {
                if (color.equals("BLUE")) {
                    layout.setBackgroundColor(Color.BLUE);
                } else if (color.equals("GREEN")) {
                    layout.setBackgroundColor(Color.GREEN);
                }
            }
        }
    }
}

