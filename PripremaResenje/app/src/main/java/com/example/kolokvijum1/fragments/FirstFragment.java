package com.example.kolokvijum1.fragments;

// Import-ujemo potrebne klase za rad sa SharedPreferences
import android.content.Context;
import android.content.SharedPreferences;
// Import za rad sa Cursor-om (rezultati upita iz baze)
import android.database.Cursor;
import android.os.Bundle;
// Import-ujemo klase za rad sa View-ovima i layout-om
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;  // Za prikazivanje Toast poruka

// AndroidX anotacije za proveru null vrednosti
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
// Fragment bazna klasa
import androidx.fragment.app.Fragment;

// Import naših klasa
import com.example.kolokvijum1.R;
import com.example.kolokvijum1.database.KorisnikRepository;
import com.example.kolokvijum1.utils.DatabaseConstants;

/**
 * FirstFragment - fragment koji se prikazuje u GORNJOJ polovini MainActivity
 * 
 * Zadatak 4: FirstFragment ima dva dugmeta
 * Zadatak 5: Na drugo dugme se inicijalno ne može kliknuti
 * Zadatak 6: Klikom na prvo dugme drugo dugme postaje klikabilno (toggle)
 * Zadatak 7: Klikom na drugo dugme pojavljuje se Toast poruka
 */
public class FirstFragment extends Fragment {

    // Privatna polja - reference na UI elemente i podatke
    
    // Zadatak 4: Prvo dugme ima tekst: "Proveri" i zelene je boje
    private Button btnProveri;
    
    // Zadatak 4: Drugo dugme ima tekst: "Ispiši"
    private Button btnIspisi;
    
    // Repository za pristup bazi podataka
    private KorisnikRepository korisnikRepository;
    
    // SharedPreferences za čuvanje i čitanje ključ-vrednost parova
    private SharedPreferences sharedPreferences;

    /**
     * Factory metoda za kreiranje instance fragmenta
     * 
     * Ovo je pattern koji se preporučuje za kreiranje fragmenata
     * umesto direktnog poziva konstruktora
     * 
     * @return nova instanca FirstFragment-a
     */
    public static FirstFragment newInstance() {
        // Kreiramo i vraćamo novi fragment
        // Moglo bi se koristiti i new FirstFragment(), ali ovo je čistija praksa
        return new FirstFragment();
    }

    /**
     * onCreateView - poziva se kada fragment treba da kreira svoj UI
     * 
     * Ova metoda "naduvava" (inflate) XML layout u View objekte
     * i postavlja sve potrebne event listener-e
     * 
     * @param inflater - objekat koji konvertuje XML u View objekte
     * @param container - roditeljski ViewGroup u koji će fragment biti smešten
     * @param savedInstanceState - sačuvano stanje fragmenta (ako postoji)
     * @return View objekat koji predstavlja UI fragmenta
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate (naduvavanje) layout-a fragmenta
        // inflater.inflate() učitava XML i kreira View objekte
        // Parametri:
        // 1. R.layout.fragment_first - ID layout resursa
        // 2. container - roditeljski kontejner
        // 3. false - ne dodajemo odmah u kontejner (sistem to radi za nas)
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        // === INICIJALIZACIJA ===
        
        // Dobijamo SharedPreferences sa nazivom "MyPrefs"
        // requireActivity() vraća aktivnost koja hostuje ovaj fragment
        // getSharedPreferences() dobija SharedPreferences instancu
        // Context.MODE_PRIVATE - podaci su privatni za ovu aplikaciju
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        
        // Kreiramo instancu Repository-ja za rad sa bazom
        // requireContext() vraća kontekst (potreban za pristup bazi)
        korisnikRepository = new KorisnikRepository(requireContext());

        // === PRONALAŽENJE VIEW ELEMENATA ===
        
        // findViewById() traži View sa određenim ID-em u layout-u
        // R.id.btnProveri je ID koji smo definisali u fragment_first.xml
        // (Button) je cast jer findViewById() vraća generički View
        btnProveri = view.findViewById(R.id.btnProveri);
        btnIspisi = view.findViewById(R.id.btnIspisi);

        // === POSTAVLJANJE EVENT LISTENER-A ===
        
        // Zadatak 6: Klikom na prvo dugme drugo dugme postaje klikabilno, ako nije bilo,
        // i obrnuto, onemogućuje se klik, ako je klik na njega bio moguć
        
        // setOnClickListener() postavlja listener koji se poziva kada korisnik klikne dugme
        // Lambda izraz: v -> { ... } je skraćeni zapis za anonimnu klasu
        // v je View na koji je kliknuto (u ovom slučaju btnProveri)
        btnProveri.setOnClickListener(v -> {
            // Toggle (prebacivanje) enabled stanja dugmeta "Ispiši"
            // !btnIspisi.isEnabled() - logički NOT operator
            // Ako je dugme disabled (false), postavlja se enabled (true)
            // Ako je dugme enabled (true), postavlja se disabled (false)
            btnIspisi.setEnabled(!btnIspisi.isEnabled());
        });

        // Zadatak 7: Klikom na drugo dugme pojavljuje se Toast poruka
        btnIspisi.setOnClickListener(v -> {
            // Pozivamo helper metodu koja sadrži kompleksnu logiku za Toast
            handleIspisiClick();
        });

        // Vraćamo View objekat koji smo kreirali
        // Fragment Manager će ovaj View dodati u fragment kontejner
        return view;
    }

    /**
     * Metoda koja se poziva kada korisnik klikne na dugme "Ispiši"
     * 
     * Zadatak 7: Klikom na drugo dugme pojavljuje se Toast poruka sa tekstom koji sadrži
     * ime poslednje sačuvanog entiteta u bazi;
     * Ukoliko korisnik nema sačuvanih entiteta, prikazati sadržaj iz SharedPreferences;
     * Ukoliko je sadržaj iz SharedPreferences ime entiteta, nakon Toast poruke
     * zameniti sadržaj iz SharedPreferences u: "Zdravo!"
     */
    private void handleIspisiClick() {
        // === PROVERA DA LI IMA PODATAKA U BAZI ===
        
        // hasAnyData() vraća true ako tabela KORISNIK ima bar jedan red
        if (korisnikRepository.hasAnyData()) {
            
            // === SLUČAJ 1: IMA PODATAKA U BAZI ===
            
            // Dobavljamo poslednjeg korisnika iz baze (sa najvećim ID-em)
            // getLastKorisnik() vraća Cursor sa jednim redom
            Cursor cursor = korisnikRepository.getLastKorisnik();
            
            // Pomeramo kursor na prvi red rezultata
            // moveToFirst() vraća true ako uspe, false ako nema redova
            // U našem slučaju bi trebalo uvek da vrati true jer smo proverili hasAnyData()
            if (cursor.moveToFirst()) {
                
                // Dobijamo indeks kolone "ime" u Cursor-u
                // getColumnIndex() vraća poziciju kolone (0, 1, 2, ...)
                // DatabaseConstants.COLUMN_IME je "ime" (naziv kolone)
                int imeIndex = cursor.getColumnIndex(DatabaseConstants.COLUMN_IME);
                
                // Čitamo vrednost iz kolone "ime"
                // getString(imeIndex) vraća String vrednost sa date pozicije
                String ime = cursor.getString(imeIndex);
                
                // VAŽNO: Zatvaramo Cursor da bi oslobodili resurse
                // Nezatvoreni Cursor-i uzrokuju memory leak
                cursor.close();

                // Prikazujemo Toast sa imenom poslednjeg korisnika
                // Toast.makeText() kreira Toast objekat
                // Parametri:
                // 1. requireContext() - kontekst aplikacije
                // 2. ime - tekst koji prikazujemo
                // 3. Toast.LENGTH_SHORT - trajanje (kratko)
                // show() zapravo prikazuje Toast na ekranu
                Toast.makeText(requireContext(), ime, Toast.LENGTH_SHORT).show();

                // === PROVERA I ZAMENA VREDNOSTI U SHAREDPREFERENCES ===
                
                // Zadatak 7: Ukoliko je sadržaj iz SharedPreferences ime entiteta,
                // nakon Toast poruke zameniti sadržaj iz SharedPreferences u: "Zdravo!"
                
                // Čitamo trenutnu vrednost iz SharedPreferences za ključ "inicijalno"
                // getString(key, defaultValue)
                // Ako ključ ne postoji, vraća "" (prazan string)
                String inicijalnoValue = sharedPreferences.getString("inicijalno", "");
                
                // Proveravamo da li je vrednost iz SharedPreferences jednaka imenu iz baze
                // equals() poredi stringove (ne koristimo == za stringove!)
                if (inicijalnoValue.equals(ime)) {
                    
                    // Ako se poklapaju, menjamo vrednost u SharedPreferences na "Zdravo!"
                    
                    // Kreiramo Editor za izmenu SharedPreferences-a
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    
                    // Postavljamo novu vrednost "Zdravo!" za ključ "inicijalno"
                    editor.putString("inicijalno", "Zdravo!");
                    
                    // apply() asinkrono čuva promene
                    editor.apply();
                }
            }
            
        } else {
            
            // === SLUČAJ 2: NEMA PODATAKA U BAZI ===
            
            // Zadatak 7: Ukoliko nema sačuvanih entiteta,
            // prikazati sadržaj iz SharedPreferences
            
            // Čitamo vrednost iz SharedPreferences za ključ "inicijalno"
            // Ako ne postoji, vraća "" (prazan string)
            String inicijalnoValue = sharedPreferences.getString("inicijalno", "");
            
            // Prikazujemo Toast sa vrednošću iz SharedPreferences
            Toast.makeText(requireContext(), inicijalnoValue, Toast.LENGTH_SHORT).show();
        }
    }
}
