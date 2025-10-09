package com.kcalorific.vezbepetisest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * MainActivity - Glavna aktivnost aplikacije
 * Sadrži dva glavna dugmeta:
 * 1. Dugme za pokretanje MusicService-a sa notifikacijom
 * 2. Dugme za prelazak na TestActivity
 * 
 * Takođe prikazuje informacije o ulogovanom korisniku i omogućava pristup:
 * - Podešavanjima sinhronizacije
 * - Kontaktima (ContentProvider)
 * - Odjavi korisnika
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int PERMISSION_REQUEST_NOTIFICATION = 101;

    private Button btnStartMusic, btnGoToTest, btnSettings, btnContacts, btnLogout;
    private TextView tvWelcome, tvSyncInfo;
    private PreferencesManager preferencesManager;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicijalizacija PreferencesManager-a i DatabaseHelper-a
        preferencesManager = new PreferencesManager(this);
        databaseHelper = new DatabaseHelper(this);

        // Provera da li je korisnik ulogovan
        if (!preferencesManager.isUserLoggedIn()) {
            // Korisnik nije ulogovan, prelazak na LoginActivity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        // Inicijalizacija komponenti
        initializeViews();

        // Postavljanje listener-a
        setupListeners();

        // Prikaz informacija o korisniku
        displayUserInfo();

        // Traženje dozvole za notifikacije (Android 13+)
        requestNotificationPermission();
    }

    /**
     * Inicijalizacija view komponenti
     */
    private void initializeViews() {
        tvWelcome = findViewById(R.id.tvWelcome);
        tvSyncInfo = findViewById(R.id.tvSyncInfo);
        btnStartMusic = findViewById(R.id.btnStartMusic);
        btnGoToTest = findViewById(R.id.btnGoToTest);
        btnSettings = findViewById(R.id.btnSettings);
        btnContacts = findViewById(R.id.btnContacts);
        btnLogout = findViewById(R.id.btnLogout);
    }

    /**
     * Postavljanje click listener-a na dugmad
     */
    private void setupListeners() {
        // Dugme 1: Pokretanje MusicService-a
        btnStartMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMusicService();
            }
        });

        // Dugme 2: Prelazak na TestActivity
        btnGoToTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });

        // Dugme za podešavanja sinhronizacije
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        // Dugme za kontakte (ContentProvider)
        btnContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContactsActivity.class);
                startActivity(intent);
            }
        });

        // Dugme za odjavu
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }

    /**
     * Pokretanje MusicService-a
     * Servis će pustiti muziku i prikazati notifikaciju
     */
    private void startMusicService() {
        try {
            Intent serviceIntent = new Intent(this, MusicService.class);
            
            // Za Android 8.0+ koristimo startForegroundService
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent);
            } else {
                startService(serviceIntent);
            }

            Toast.makeText(this, "MusicService pokrenut!", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "MusicService pokrenut uspešno");
        } catch (Exception e) {
            Toast.makeText(this, "Greška pri pokretanju servisa: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(TAG, "Greška pri pokretanju MusicService: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Prikaz informacija o ulogovanom korisniku i podešavanjima
     */
    private void displayUserInfo() {
        // Dohvatanje informacija o korisniku
        String username = preferencesManager.getUsername();
        String fullName = preferencesManager.getFullName();

        // Prikaz poruke dobrodošlice
        tvWelcome.setText("Dobrodošli, " + fullName + " (" + username + ")!");

        // Prikaz informacija o sinhronizaciji
        String syncInfo = "Sinhronizacija: " + preferencesManager.getCurrentSyncIntervalText();
        tvSyncInfo.setText(syncInfo);

        Log.d(TAG, "Prikazan korisnički info za: " + username);
    }

    /**
     * Odjava korisnika i povratak na LoginActivity
     */
    private void logoutUser() {
        preferencesManager.odjaviKorisnika();
        Toast.makeText(this, "Uspešno ste se odjavili", Toast.LENGTH_SHORT).show();
        
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Traženje dozvole za notifikacije (Android 13+)
     */
    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        PERMISSION_REQUEST_NOTIFICATION);
                Log.d(TAG, "Traži se dozvola za notifikacije");
            }
        }
    }

    /**
     * Obrada rezultata traženja dozvole za notifikacije
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_NOTIFICATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Dozvola za notifikacije odobrena", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Dozvola za notifikacije odobrena");
            } else {
                Toast.makeText(this, "Dozvola za notifikacije odbijena. Notifikacije neće biti prikazane.", Toast.LENGTH_LONG).show();
                Log.d(TAG, "Dozvola za notifikacije odbijena");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Ažuriranje informacija kada se korisnik vrati na aktivnost
        displayUserInfo();
    }
}
