package com.kcalorific.testtttttt;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import com.kcalorific.testtttttt.fragments.FirstFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        initializeSharedPreferences();
        if (savedInstanceState == null) {
            loadFragments();
        }
    }
    private void initializeSharedPreferences() {
        // Dobijamo instancu SharedPreferences-a sa nazivom "MyPrefs"
        // MODE_PRIVATE znači da su podaci dostupni samo ovoj aplikaciji
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // Proveravamo da li ključ "inicijalno" već postoji
        // Ovo sprečava da pregazimo postojeću vrednost svaki put kada se pokrene aplikacija
        if (!sharedPreferences.contains("inicijalno")) {
            // Kreiramo Editor objekat koji omogućava izmenu SharedPreferences-a
            SharedPreferences.Editor editor = sharedPreferences.edit();

            // Postavljamo vrednost "Zdravo!" za ključ "inicijalno"
            editor.putString("inicijalno", "Zdravo!");

            // apply() asinkrono čuva promene (ne blokira UI thread)
            // Alternativa je commit() koja čuva sinkrono i vraća boolean
            editor.apply();
        }
    }
    private void loadFragments() {
        // Dobijamo FragmentTransaction objekat koji omogućava dodavanje/zamenu fragmenata
        // FragmentTransaction radi kao transakcija u bazi - sve promene se izvršavaju odjednom
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Dodajemo FirstFragment u gornji kontejner (firstFragmentContainer)
        // replace() metoda zamenjuje postojeći fragment ili dodaje novi ako ne postoji
        // R.id.firstFragmentContainer je ID FrameLayout-a iz activity_main.xml
        // FirstFragment.newInstance() poziva statičku factory metodu koja kreira fragment
        transaction.replace(R.id.firstFragmentContainer, FirstFragment.newInstance());

        // Dodajemo SecondFragment u donji kontejner (secondFragmentContainer)
        // Oba fragmenta se dodaju u istoj transakciji
        //transaction.replace(R.id.secondFragmentContainer, SecondFragment.newInstance());

        // commit() izvršava sve promene koje smo dodali u transakciju
        // Ovo zapravo prikazuje fragmente na ekranu
        transaction.commit();
    }
}