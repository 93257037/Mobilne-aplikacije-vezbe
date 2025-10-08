package com.example.kolokvijum1.fragments;

// Import za rad sa Android dozvolama (permissions)
import android.Manifest;
import android.content.Context;
import android.content.Intent;  // Za navigaciju između aktivnosti
import android.content.SharedPreferences;
import android.content.pm.PackageManager;  // Za proveru statusa dozvola
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

// ActivityResultLauncher - moderna API za rukovanje rezultatima (dozvole, aktivnosti)
import androidx.activity.result.ActivityResultLauncher;
// Contract koji definiše zahtev za pojedinačnu dozvolu
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
// ContextCompat - compatibility klasa za rad sa Context-om
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.kolokvijum1.R;
import com.example.kolokvijum1.SecondActivity;
import com.example.kolokvijum1.database.KorisnikRepository;

/**
 * SecondFragment - fragment koji se prikazuje u DONJOJ polovini MainActivity
 * 
 * Zadatak 9: SecondFragment ima jedno EditText polje i jedno dugme: "Sačuvaj"
 * Zadatak 10: Klikom na dugme "Sačuvaj" proveriti da li je omogućen rad sa lokacijom
 * Zadatak 11: Ako korisnik odbije dozvolu ili ako je dozvola odobrena
 */
public class SecondFragment extends Fragment {

    // Privatna polja - UI elementi i podaci
    
    // Zadatak 9: EditText polje za unos imena
    private EditText etIme;
    
    // Zadatak 9: Dugme "Sačuvaj"
    private Button btnSacuvaj;
    
    // Repository za čuvanje u bazu
    private KorisnikRepository korisnikRepository;
    
    // SharedPreferences za čuvanje podataka kada nema dozvole
    private SharedPreferences sharedPreferences;

    // Zadatak 10: ActivityResultLauncher za rukovanje dozvolama
    // Ovo je MODERNA API za dozvole (zamena za stari onRequestPermissionsResult)
    // 
    // ActivityResultLauncher<String> - String je tip parametra (naziv dozvole)
    // U callback-u dobijamo Boolean - true ako je odobrena, false ako je odbijena
    private ActivityResultLauncher<String> requestPermissionLauncher;

    /**
     * Factory metoda za kreiranje fragmenta
     * @return nova instanca SecondFragment-a
     */
    public static SecondFragment newInstance() {
        return new SecondFragment();
    }

    /**
     * onCreate - poziva se pre kreiranja UI-a
     * 
     * Ovo je životni ciklus fragmenta: onCreate -> onCreateView -> onViewCreated -> ...
     * 
     * @param savedInstanceState - sačuvano stanje fragmenta
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        // Pozivamo onCreate roditeljske klase (obavezno)
        super.onCreate(savedInstanceState);

        // === REGISTRACIJA LAUNCHER-A ZA DOZVOLE ===
        
        // Zadatak 10: Klikom na dugme "Sačuvaj" proveriti da li je omogućen rad sa lokacijom.
        // Ako nije, zatražiti dozvolu.
        
        // registerForActivityResult() mora se pozvati PRE onCreateView()
        // zato koristimo onCreate() metodu
        
        // registerForActivityResult() registruje "contract" za dozvolu
        // Parametri:
        // 1. ActivityResultContract - tip zahteva (RequestPermission za dozvole)
        // 2. Callback - lambda koja se poziva sa rezultatom
        requestPermissionLauncher = registerForActivityResult(
                // ActivityResultContracts.RequestPermission() - contract za jednu dozvolu
                // Postoje i drugi contracts: RequestMultiplePermissions, StartActivityForResult, itd.
                new ActivityResultContracts.RequestPermission(),
                
                // Lambda funkcija koja se izvršava kada korisnik odgovori na zahtev
                // isGranted - Boolean parametar
                //   true - korisnik je odobrio dozvolu
                //   false - korisnik je odbio dozvolu
                isGranted -> {
                    
                    // Zadatak 11: Logika zavisno od odgovora korisnika
                    
                    if (isGranted) {
                        // === DOZVOLA ODOBRENA ===
                        
                        // Zadatak 11: Ako je dozvoljeno, sadržaj sačuvati u bazu
                        
                        // Pozivamo metodu koja čuva podatke u SQLite bazu
                        saveToDatabase();
                        
                    } else {
                        // === DOZVOLA ODBIJENA ===
                        
                        // Zadatak 11: Ako korisnik odbije dozvolu,
                        // sačuvati sadržaj tekstualnog polja u SharedPreferences
                        // i preći na novu aktivnost SecondActivity
                        
                        // Pozivamo metodu koja:
                        // 1. Čuva podatke u SharedPreferences
                        // 2. Navigira na SecondActivity
                        saveToSharedPreferencesAndNavigate();
                    }
                }
        );
    }

    /**
     * onCreateView - kreira UI fragmenta
     * 
     * @param inflater - za inflate-ovanje XML layout-a
     * @param container - roditeljski kontejner
     * @param savedInstanceState - sačuvano stanje
     * @return View objekat koji predstavlja UI
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate-ujemo layout fragmenta
        // R.layout.fragment_second sadrži EditText i Button
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        // === INICIJALIZACIJA ===
        
        // Dobijamo SharedPreferences (isti kao u FirstFragment)
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        
        // Kreiramo Repository za pristup bazi
        korisnikRepository = new KorisnikRepository(requireContext());

        // === PRONALAŽENJE UI ELEMENATA ===
        
        // Zadatak 9: SecondFragment ima jedno EditText polje i jedno dugme: "Sačuvaj"
        
        // Pronalazimo EditText polje za unos imena
        etIme = view.findViewById(R.id.etIme);
        
        // Pronalazimo dugme "Sačuvaj"
        btnSacuvaj = view.findViewById(R.id.btnSacuvaj);

        // === POSTAVLJANJE EVENT LISTENER-A ===
        
        // Zadatak 10: Klikom na dugme "Sačuvaj" proveriti da li je omogućen rad sa lokacijom
        
        // setOnClickListener postavlja listener koji se poziva na klik
        btnSacuvaj.setOnClickListener(v -> {
            // Pozivamo metodu koja proverava dozvolu za lokaciju
            checkLocationPermission();
        });

        // Vraćamo View
        return view;
    }

    /**
     * Metoda koja proverava da li aplikacija ima dozvolu za lokaciju
     * 
     * Zadatak 10: Klikom na dugme "Sačuvaj" proveriti da li je omogućen rad sa lokacijom.
     * Ako nije, zatražiti dozvolu.
     * 
     * Ova metoda:
     * 1. Proverava trenutni status dozvole
     * 2. Ako je odobrena - čuva u bazu
     * 3. Ako nije odobrena - traži dozvolu od korisnika
     */
    private void checkLocationPermission() {
        
        // === PROVERA TRENUTNOG STATUSA DOZVOLE ===
        
        // ContextCompat.checkSelfPermission() proverava da li aplikacija ima dozvolu
        // Parametri:
        // 1. requireContext() - kontekst aplikacije
        // 2. Manifest.permission.ACCESS_FINE_LOCATION - tip dozvole koja nas zanima
        //    ACCESS_FINE_LOCATION - precizna lokacija (GPS)
        //    ACCESS_COARSE_LOCATION - gruba lokacija (mobilna mreža/WiFi)
        // 
        // Vraća:
        // - PackageManager.PERMISSION_GRANTED - dozvola je odobrena
        // - PackageManager.PERMISSION_DENIED - dozvola nije odobrena
        if (ContextCompat.checkSelfPermission(requireContext(), 
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            
            // === DOZVOLA JE VEĆ ODOBRENA ===
            
            // Korisnik je ranije odobrio dozvolu za lokaciju
            // Ne treba ponovo tražiti dozvolu, odmah čuvamo u bazu
            saveToDatabase();
            
        } else {
            
            // === DOZVOLA NIJE ODOBRENA - TRAŽIMO JE ===
            
            // Zadatak 10: Ako nije, zatražiti dozvolu
            
            // launch() metoda pokreće zahtev za dozvolu
            // Parametar: Manifest.permission.ACCESS_FINE_LOCATION
            // 
            // Ovo prikazuje sistem dialog sa pitanjem:
            // "Da li dozvoljavate aplikaciji da pristupa lokaciji?"
            // Opcije: "Dozvoli" ili "Odbij"
            // 
            // Kada korisnik odgovori, poziva se callback koji smo registrovali u onCreate()
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    /**
     * Čuva ime u SQLite bazu podataka
     * 
     * Zadatak 11: Ako je dozvoljeno, sadržaj sačuvati u bazu
     * 
     * Ova metoda se poziva kada:
     * - Korisnik odobri dozvolu ZA PRVI PUT
     * - Dozvola je već ranije bila odobrena
     */
    private void saveToDatabase() {
        // Čitamo tekst iz EditText polja
        // getText() vraća Editable objekat
        // toString() konvertuje u String
        // trim() uklanja razmake sa početka i kraja stringa
        String ime = etIme.getText().toString().trim();
        
        // Proveravamo da li je ime prazno (ne čuvamo prazne stringove)
        // isEmpty() vraća true ako je string prazan ""
        if (!ime.isEmpty()) {
            
            // Čuvamo ime u bazu preko Repository-ja
            // insertData() dodaje novi red u tabelu KORISNIK
            korisnikRepository.insertData(ime);
            
            // Brišemo sadržaj EditText polja nakon čuvanja
            // setText("") postavlja prazan tekst
            // Ovo daje korisniku vizuelni feedback da je čuvanje uspelo
            etIme.setText("");
        }
    }

    /**
     * Čuva ime u SharedPreferences i navigira na SecondActivity
     * 
     * Zadatak 11: Ako korisnik odbije dozvolu,
     * sačuvati sadržaj tekstualnog polja u SharedPreferences
     * i preći na novu aktivnost SecondActivity koja ima centriran tekst: "Nema dozvole!"
     * 
     * Ova metoda se poziva samo kada korisnik ODBIJE dozvolu
     */
    private void saveToSharedPreferencesAndNavigate() {
        
        // === ČUVANJE U SHAREDPREFERENCES ===
        
        // Čitamo tekst iz EditText polja (isti kod kao u saveToDatabase)
        String ime = etIme.getText().toString().trim();
        
        // Proveravamo da li je ime prazno
        if (!ime.isEmpty()) {
            
            // Kreiramo Editor za izmenu SharedPreferences-a
            SharedPreferences.Editor editor = sharedPreferences.edit();
            
            // Postavljamo uneto ime kao vrednost za ključ "inicijalno"
            // Ovo će zameniti postojeću vrednost "Zdravo!" (ako postoji)
            editor.putString("inicijalno", ime);
            
            // apply() asinkrono čuva promene
            editor.apply();
        }

        // === NAVIGACIJA NA SECONDACTIVITY ===
        
        // Zadatak 11: Preći na novu aktivnost SecondActivity
        
        // Intent je objekat koji opisuje akciju koja treba da se izvrši
        // U ovom slučaju, akcija je pokretanje nove aktivnosti
        // Parametri:
        // 1. requireContext() - kontekst iz kojeg pokrećemo aktivnost
        // 2. SecondActivity.class - klasa aktivnosti koju želimo da pokrenemo
        Intent intent = new Intent(requireContext(), SecondActivity.class);
        
        // startActivity() zapravo pokreće novu aktivnost
        // Ovo otvara SecondActivity koja prikazuje "Nema dozvole!"
        startActivity(intent);
    }
}
