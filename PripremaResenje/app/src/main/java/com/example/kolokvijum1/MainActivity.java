package com.example.kolokvijum1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.kolokvijum1.fragments.FirstFragment;
import com.example.kolokvijum1.fragments.SecondFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicijalizacija SharedPreferences sa ključem "inicijalno" = "Zdravo!"
        initializeSharedPreferences();

        // Učitavanje fragmenata
        if (savedInstanceState == null) {
            loadFragments();
        }
    }

    private void initializeSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        
        // Proveravamo da li je već postavljena vrednost
        if (!sharedPreferences.contains("inicijalno")) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("inicijalno", "Zdravo!");
            editor.apply();
        }
    }

    private void loadFragments() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        
        // Dodaj FirstFragment u gornji kontejner
        transaction.replace(R.id.firstFragmentContainer, FirstFragment.newInstance());
        
        // Dodaj SecondFragment u donji kontejner
        transaction.replace(R.id.secondFragmentContainer, SecondFragment.newInstance());
        
        transaction.commit();
    }
}

