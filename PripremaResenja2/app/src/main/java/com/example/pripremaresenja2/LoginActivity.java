package com.example.pripremaresenja2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * LoginActivity - Ekran za prijavu korisnika
 * ZAHTEV 6: Login sa email i lozinka, čuvanje userID u SharedPreferences
 */
public class LoginActivity extends AppCompatActivity {

    private EditText editTextLoginEmail;
    private EditText editTextLoginLozinka;
    private Button buttonPrijava;
    private DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicijalizacija DatabaseHelper-a i SharedPreferences
        databaseHelper = new DatabaseHelper(this);
        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        // Povezivanje sa elementima iz layout-a
        editTextLoginEmail = findViewById(R.id.editTextLoginEmail);
        editTextLoginLozinka = findViewById(R.id.editTextLoginLozinka);
        buttonPrijava = findViewById(R.id.buttonPrijava);

        // Postavljanje slušača na dugme za prijavu
        buttonPrijava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prijaviKorisnika();
            }
        });
    }

    /**
     * Metoda koja obavlja prijavu korisnika
     * ZAHTEV 6: Provera podataka, čuvanje userID u SharedPreferences, prelazak na HomeActivity
     */
    private void prijaviKorisnika() {
        // Preuzimanje unetih podataka
        String email = editTextLoginEmail.getText().toString().trim();
        String lozinka = editTextLoginLozinka.getText().toString().trim();

        // Validacija - provera da li su oba polja popunjena
        if (email.isEmpty() || lozinka.isEmpty()) {
            Toast.makeText(this, R.string.popunite_sva_polja, Toast.LENGTH_SHORT).show();
            return;
        }

        // ZAHTEV 6: Provera da li su podaci tačni u bazi
        User korisnik = databaseHelper.loginKorisnik(email, lozinka);

        if (korisnik != null) {
            // Podaci su tačni - uspešna prijava
            
            // ZAHTEV 6: Čuvanje userID i imena u SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("userId", korisnik.getId());
            editor.putString("imePrezime", korisnik.getImePrezime());
            editor.putBoolean("isLoggedIn", true);
            editor.apply();

            // Prelazak na HomeActivity
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish(); // Zatvaranje LoginActivity
        } else {
            // ZAHTEV 6: Podaci nisu tačni - prikaži poruku "Pogrešni podaci."
            Toast.makeText(this, R.string.pogresni_podaci, Toast.LENGTH_LONG).show();
        }
    }
}

