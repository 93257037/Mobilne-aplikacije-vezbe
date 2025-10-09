package com.kcalorific.vezbepetisest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * SettingsActivity - ekran za podešavanje sinhronizacije
 * Korisnik može odabrati interval sinhronizacije: nikad, 1 min, 15 min, 30 min
 * Podešavanja se čuvaju u SharedPreferences
 */
public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "SettingsActivity";

    private RadioGroup rgSyncInterval;
    private RadioButton rbNever, rb1Min, rb15Min, rb30Min;
    private Button btnSave;
    private TextView tvCurrentInterval;
    private PreferencesManager preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Inicijalizacija komponenti
        rgSyncInterval = findViewById(R.id.rgSyncInterval);
        rbNever = findViewById(R.id.rbNever);
        rb1Min = findViewById(R.id.rb1Min);
        rb15Min = findViewById(R.id.rb15Min);
        rb30Min = findViewById(R.id.rb30Min);
        btnSave = findViewById(R.id.btnSave);
        tvCurrentInterval = findViewById(R.id.tvCurrentInterval);

        // Inicijalizacija preferences manager-a
        preferencesManager = new PreferencesManager(this);

        // Učitavanje trenutnog podešavanja
        loadCurrentSettings();

        // Logika za dugme čuvanja
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSyncSettings();
            }
        });
    }

    /**
     * Učitavanje trenutnih podešavanja iz SharedPreferences
     */
    private void loadCurrentSettings() {
        long currentInterval = preferencesManager.getIntervalSinhronizacije();
        
        // Postavljanje odgovarajućeg radio button-a
        if (currentInterval == PreferencesManager.SYNC_NEVER) {
            rbNever.setChecked(true);
        } else if (currentInterval == PreferencesManager.SYNC_1_MIN) {
            rb1Min.setChecked(true);
        } else if (currentInterval == PreferencesManager.SYNC_15_MIN) {
            rb15Min.setChecked(true);
        } else if (currentInterval == PreferencesManager.SYNC_30_MIN) {
            rb30Min.setChecked(true);
        }

        // Prikaz trenutnog intervala
        updateCurrentIntervalText();
    }

    /**
     * Čuvanje podešavanja sinhronizacije
     */
    private void saveSyncSettings() {
        int selectedId = rgSyncInterval.getCheckedRadioButtonId();
        long interval;

        // Određivanje intervala na osnovu odabranog radio button-a
        if (selectedId == R.id.rbNever) {
            interval = PreferencesManager.SYNC_NEVER;
        } else if (selectedId == R.id.rb1Min) {
            interval = PreferencesManager.SYNC_1_MIN;
        } else if (selectedId == R.id.rb15Min) {
            interval = PreferencesManager.SYNC_15_MIN;
        } else if (selectedId == R.id.rb30Min) {
            interval = PreferencesManager.SYNC_30_MIN;
        } else {
            Toast.makeText(this, "Molimo odaberite interval", Toast.LENGTH_SHORT).show();
            return;
        }

        // Čuvanje podešavanja
        preferencesManager.postaviIntervalSinhronizacije(interval);
        
        // Ažuriranje prikaza
        updateCurrentIntervalText();
        
        Toast.makeText(this, "Podešavanja sačuvana!", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Interval sinhronizacije sačuvan: " + preferencesManager.getIntervalText(interval));
    }

    /**
     * Ažuriranje prikaza trenutnog intervala
     */
    private void updateCurrentIntervalText() {
        String intervalText = preferencesManager.getCurrentSyncIntervalText();
        tvCurrentInterval.setText("Trenutni interval: " + intervalText);
    }
}

