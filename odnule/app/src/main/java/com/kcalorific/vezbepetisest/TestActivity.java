package com.kcalorific.vezbepetisest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * TestActivity koja prikazuje tekst "Oboji me!"
 * Tekst se boji u crveno kada BroadcastReceiver primi signal
 */
public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";
    private TextView colorTextView;
    private ColorBroadcastReceiver colorReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // Inicijalizacija TextView-a
        colorTextView = findViewById(R.id.colorTextView);

        // Registracija BroadcastReceiver-a za primanje signala za bojenje
        colorReceiver = new ColorBroadcastReceiver();
        IntentFilter filter = new IntentFilter("ACTION_COLOR_TEXT");
        
        // Za Android 13+ potreban je eksplicitan flag
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(colorReceiver, filter, Context.RECEIVER_NOT_EXPORTED);
        } else {
            registerReceiver(colorReceiver, filter);
        }
        
        Log.d(TAG, "BroadcastReceiver registrovan");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        // Deregistracija BroadcastReceiver-a
        if (colorReceiver != null) {
            unregisterReceiver(colorReceiver);
            Log.d(TAG, "BroadcastReceiver deregistrovan");
        }
    }

    /**
     * Unutrašnja klasa BroadcastReceiver-a za bojenje teksta
     * Ova klasa osluškuje broadcast i boji tekst u crveno
     */
    private class ColorBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && "ACTION_COLOR_TEXT".equals(intent.getAction())) {
                // Bojenje teksta u crveno
                if (colorTextView != null) {
                    colorTextView.setTextColor(Color.RED);
                    Log.d(TAG, "Tekst obojen u crveno");
                }
            }
        }
    }
}

