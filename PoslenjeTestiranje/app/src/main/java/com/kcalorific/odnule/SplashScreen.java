package com.kcalorific.odnule;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

/**
 * SplashScreen - Početni ekran aplikacije koji se prikazuje 5 sekundi
 * Automatski prelazi na LoginScreen nakon isteka vremena
 */
public class SplashScreen extends AppCompatActivity {

    // Tag za logovanje - koristi se za praćenje životnog ciklusa
    private static final String TAG = "SplashScreen";
    
    // Trajanje splash ekrana u milisekundama (5 sekundi)
    private static final int SPLASH_DURATION = 5000;

    /**
     * onCreate - Poziva se kada se aktivnost kreira
     * Postavlja layout i pokreće timer za prelazak na LoginScreen
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Log.d(TAG, "onCreate: SplashScreen kreiran");

        // Handler za odloženo izvršavanje - prelazak na LoginScreen nakon 5 sekundi
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Kreiranje intenta za prelazak na LoginScreen
                Intent intent = new Intent(SplashScreen.this, LoginScreen.class);
                startActivity(intent);
                // Zatvaranje SplashScreen-a kako se ne bi vraćao pritiskom na back dugme
                finish();
            }
        }, SPLASH_DURATION);
    }

    /**
     * onStart - Poziva se nakon onCreate ili kada se aktivnost vraća iz pauziranog stanja
     * Aktivnost postaje vidljiva korisniku
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: SplashScreen postao vidljiv");
    }

    /**
     * onRestart - Poziva se kada se aktivnost ponovo pokreće nakon što je bila zaustavljena
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: SplashScreen se restartuje");
    }

    /**
     * onResume - Poziva se kada aktivnost počinje da interaguje sa korisnikom
     * Aktivnost je u prvom planu
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: SplashScreen u prvom planu");
    }

    /**
     * onPause - Poziva se kada aktivnost gubi fokus
     * Korisnik napušta aktivnost
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: SplashScreen pauziran");
    }

    /**
     * onStop - Poziva se kada aktivnost više nije vidljiva korisniku
     */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: SplashScreen zaustavljen");
    }

    /**
     * onDestroy - Poziva se pre uništenja aktivnosti
     * Finalno čišćenje resursa
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: SplashScreen uništen");
    }
}

