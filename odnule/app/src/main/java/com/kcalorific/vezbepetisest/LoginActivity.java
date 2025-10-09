package com.kcalorific.vezbepetisest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * LoginActivity - ekran za prijavljivanje korisnika
 * Korisnik se prijavljuje koristeći korisničko ime i lozinku
 * Podaci se proveravaju iz baze, a ulogovani korisnik se čuva u SharedPreferences
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private DatabaseHelper databaseHelper;
    private PreferencesManager preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicijalizacija komponenti
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // Inicijalizacija database helper-a i preferences manager-a
        databaseHelper = new DatabaseHelper(this);
        preferencesManager = new PreferencesManager(this);

        // Logika za dugme prijave
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // Validacija unosa
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Molimo unesite korisničko ime i lozinku", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Provera kredencijala u bazi
                if (databaseHelper.proveraLogovanja(username, password)) {
                    // Dohvatanje korisnika iz baze
                    Korisnik korisnik = databaseHelper.dohvatiKorisnikaPoImenu(username);
                    
                    if (korisnik != null) {
                        // Čuvanje ulogovanog korisnika u SharedPreferences
                        preferencesManager.sacuvajUlogovanogKorisnika(korisnik);
                        
                        Toast.makeText(LoginActivity.this, "Uspešno prijavljivanje!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Korisnik " + username + " uspešno prijavljen");

                        // Prelazak na glavnu aktivnost
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Pogrešno korisničko ime ili lozinka", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Neuspešan pokušaj prijavljivanja za: " + username);
                }
            }
        });
    }
}

