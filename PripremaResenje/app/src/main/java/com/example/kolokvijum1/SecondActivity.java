package com.example.kolokvijum1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * SecondActivity - aktivnost koja se prikazuje kada korisnik odbije dozvolu za lokaciju
 * 
 * Zadatak 11: Ako korisnik odbije dozvolu, sačuvati sadržaj tekstualnog polja u
 * SharedPreferences i preći na novu aktivnost SecondActivity koja ima
 * centriran tekst: "Nema dozvole!"
 */
public class SecondActivity extends AppCompatActivity {

    /**
     * onCreate - metoda koja se poziva kada se aktivnost kreira
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Pozivamo onCreate metodu roditeljske klase (obavezno)
        super.onCreate(savedInstanceState);
        
        // Postavljamo layout koji sadrži centriran tekst "Nema dozvole!"
        // Layout activity_second.xml sadrži LinearLayout sa TextView-om u centru
        setContentView(R.layout.activity_second);
        
        // Ova aktivnost je veoma jednostavna - samo prikazuje poruku
        // Nema potrebe za dodatnom logikom ili click listener-ima
    }
}

