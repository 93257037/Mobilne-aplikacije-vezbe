package com.kcalorific.vezbepetisest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * BroadcastReceiver koji reaguje na klik dugmeta iz notifikacije
 * i obavlja dve akcije:
 * 1. Ispisuje poruku u logcat
 * 2. Šalje broadcast za bojenje teksta u TestActivity
 */
public class PrintBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "PrintBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Provera akcije
        if (intent != null && "ACTION_PRINT".equals(intent.getAction())) {
            // Ispis poruke u logcat
            Log.d(TAG, "Pozdrav iz servisa!");

            // Slanje broadcast-a za bojenje teksta u TestActivity
            Intent colorIntent = new Intent("ACTION_COLOR_TEXT");
            context.sendBroadcast(colorIntent);
            
            Log.d(TAG, "Broadcast poslat za bojenje teksta");
        }
    }
}

