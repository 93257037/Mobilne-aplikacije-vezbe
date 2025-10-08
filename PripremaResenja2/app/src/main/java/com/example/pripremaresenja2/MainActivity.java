package com.example.pripremaresenja2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * MainActivity - Ekran za registraciju korisnika
 * ZAHTEVI 2, 3, 4: Registracija sa ime, prezime, email, lozinka (skrivena)
 * Provera da li email već postoji u bazi
 */
public class MainActivity extends AppCompatActivity {

    private EditText editTextImePrezime;
    private EditText editTextEmail;
    private EditText editTextLozinka;
    private Button buttonRegistracija;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicijalizacija DatabaseHelper-a
        databaseHelper = new DatabaseHelper(this);

        // Povezivanje sa elementima iz layout-a
        editTextImePrezime = findViewById(R.id.editTextImePrezime);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextLozinka = findViewById(R.id.editTextLozinka);
        buttonRegistracija = findViewById(R.id.buttonRegistracija);

        // Postavljanje slušača na dugme za registraciju
        buttonRegistracija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrujKorisnika();
            }
        });
    }

    /**
     * Metoda koja obavlja registraciju korisnika
     * ZAHTEV 4: Provera da li email već postoji, čuvanje u bazu, prelazak na LoginActivity
     */
    private void registrujKorisnika() {
        // Preuzimanje unetih podataka iz polja
        String imePrezime = editTextImePrezime.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String lozinka = editTextLozinka.getText().toString().trim();

        // Validacija - provera da li su sva polja popunjena
        if (imePrezime.isEmpty() || email.isEmpty() || lozinka.isEmpty()) {
            Toast.makeText(this, R.string.popunite_sva_polja, Toast.LENGTH_SHORT).show();
            return;
        }

        // ZAHTEV 4: Provera da li email već postoji u bazi
        if (databaseHelper.emailPostoji(email)) {
            // Ako email postoji, prikaži poruku "Email je već registrovan!"
            Toast.makeText(this, R.string.email_vec_postoji, Toast.LENGTH_LONG).show();
            return;
        }

        // Kreiranje novog User objekta
        User noviKorisnik = new User(imePrezime, email, lozinka);

        // Čuvanje korisnika u bazu
        long rezultat = databaseHelper.dodajKorisnika(noviKorisnik);

        if (rezultat != -1) {
            // Uspešna registracija
            Toast.makeText(this, R.string.uspesna_registracija, Toast.LENGTH_SHORT).show();

            // ZAHTEV 4: Prebacivanje na LoginActivity nakon uspešne registracije
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Zatvaranje MainActivity da se ne može vratiti nazad
        } else {
            // Greška pri registraciji
            Toast.makeText(this, "Greška pri registraciji!", Toast.LENGTH_SHORT).show();
        }
    }
}
