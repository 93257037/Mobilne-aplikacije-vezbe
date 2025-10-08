package com.example.kolokvijum1;

// Import-ujemo potrebne klase za rad sa SharedPreferences-om
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

// Import-ujemo AppCompatActivity kao baznu klasu za aktivnost
import androidx.appcompat.app.AppCompatActivity;
// Import-ujemo FragmentTransaction za upravljanje fragmentima
import androidx.fragment.app.FragmentTransaction;

// Import-ujemo naše fragmente koje smo kreirali
import com.example.kolokvijum1.fragments.FirstFragment;
import com.example.kolokvijum1.fragments.SecondFragment;

/**
 * MainActivity - glavna aktivnost aplikacije
 * Ova aktivnost sadrži dva fragmenta: FirstFragment (gore) i SecondFragment (dole)
 * 
 * Zadatak 2: Prva aktivnost (MainActivity) sadrži dva fragmenta.
 * Prvi fragment (FirstFragment) se nalazi u gornjoj polovini aktivnosti,
 * dok se drugi (SecondFragment) nalazi u donjoj polovini aktivnosti.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * onCreate - metoda koja se poziva kada se aktivnost kreira
     * Ovo je prva metoda koja se izvršava kada korisnik otvori aplikaciju
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Pozivamo onCreate metodu roditeljske klase (obavezno)
        super.onCreate(savedInstanceState);
        
        // Postavljamo layout za ovu aktivnost
        // activity_main.xml sadrži dva FrameLayout kontejnera za fragmente
        setContentView(R.layout.activity_main);

        // Zadatak 3: U SharedPreferences pod ključem: "inicijalno" upisati: "Zdravo!"
        // Pozivamo metodu koja inicijalizuje SharedPreferences
        initializeSharedPreferences();

        // Učitavamo fragmente samo ako aktivnost nije prethodno sačuvana
        // savedInstanceState je null kada je aktivnost prvi put kreirana
        // Ako je aktivnost rotirana ili promenjena, savedInstanceState NIJE null
        // i ne treba ponovo dodavati fragmente jer već postoje
        if (savedInstanceState == null) {
            loadFragments();
        }
    }

    /**
     * Zadatak 3: U SharedPreferences pod ključem: "inicijalno" upisati: "Zdravo!"
     * 
     * SharedPreferences je lokalno skladište ključ-vrednost parova
     * Koristi se za čuvanje malih količina podataka (postavke, vrednosti, itd.)
     */
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

    /**
     * Metoda koja učitava oba fragmenta u svoje kontejnere
     * FirstFragment ide u gornji kontejner, SecondFragment u donji
     */
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
        transaction.replace(R.id.secondFragmentContainer, SecondFragment.newInstance());
        
        // commit() izvršava sve promene koje smo dodali u transakciju
        // Ovo zapravo prikazuje fragmente na ekranu
        transaction.commit();
    }
}

