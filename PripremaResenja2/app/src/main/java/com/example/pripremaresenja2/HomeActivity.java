package com.example.pripremaresenja2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * HomeActivity - Početni ekran nakon uspešne prijave
 * ZAHTEV 7: Toolbar sa stavkom menija "Moje beleške", prikazuje ime i prezime korisnika
 * ZAHTEV 8: Klikom na "Moje beleške" otvara BeleškeFragment
 */
public class HomeActivity extends AppCompatActivity {

    private TextView textViewDobrodosli;
    private TextView textViewImePrezime;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // ZAHTEV 7: Postavljanje Toolbar-a
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Inicijalizacija SharedPreferences
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        // Povezivanje sa TextView elementima
        textViewDobrodosli = findViewById(R.id.textViewDobrodosli);
        textViewImePrezime = findViewById(R.id.textViewImePrezime);

        // ZAHTEV 7: Učitavanje i prikaz imena i prezimena korisnika iz SharedPreferences
        String imePrezime = sharedPreferences.getString("imePrezime", "Korisnik");
        textViewImePrezime.setText(imePrezime);
    }

    /**
     * ZAHTEV 7: Kreiranje menija na Toolbar-u
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    /**
     * ZAHTEV 8: Obrada klika na stavku menija "Moje beleške"
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_moje_beleske) {
            // Otvaranje BeleškeFragment-a
            int userId = sharedPreferences.getInt("userId", -1);
            
            BeleškeFragment beleškeFragment = BeleškeFragment.newInstance(userId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, beleškeFragment)
                    .commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

