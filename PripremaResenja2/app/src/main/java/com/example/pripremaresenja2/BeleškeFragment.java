package com.example.pripremaresenja2;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * BeleškeFragment - Fragment koji prikazuje listu beleški korisnika
 * ZAHTEV 8: Lista beleški sa dugmadima "Dodaj belešku" i "Filtriraj po datumu"
 * ZAHTEV 9: Provera dozvole za čitanje skladišta, otvaranje forme
 * ZAHTEV 10: Čuvanje beleške u bazi
 * ZAHTEV 11: Filtriranje po današnjem datumu
 */
public class BeleškeFragment extends Fragment {

    private static final String ARG_USER_ID = "userId";
    
    private int userId;
    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerViewBeleske;
    private BeleskeAdapter adapter;
    private Button buttonDodajBelesku;
    private Button buttonFiltriraj;
    private boolean jeFiltrirano = false; // Prati da li je filter aktivan
    
    // ZAHTEV 9: Launcher za proveru dozvole čitanja skladišta
    private ActivityResultLauncher<String> permissionLauncher;

    public BeleškeFragment() {
        // Required empty public constructor
    }

    public static BeleškeFragment newInstance(int userId) {
        BeleškeFragment fragment = new BeleškeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getInt(ARG_USER_ID);
        }
        
        databaseHelper = new DatabaseHelper(requireContext());
        
        // ZAHTEV 9: Registracija launcher-a za dozvolu
        permissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        // Dozvola je odobrena - otvori formu
                        otvoriFormuZaDodavanje();
                    } else {
                        // ZAHTEV 9: Dozvola je odbijena - prikaži dijalog
                        prikaziDijaglogOdbijeneDozvole();
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_beleske, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ZAHTEV 8: Inicijalizacija RecyclerView-a za prikaz liste beleški
        recyclerViewBeleske = view.findViewById(R.id.recyclerViewBeleske);
        recyclerViewBeleske.setLayoutManager(new LinearLayoutManager(requireContext()));
        
        // Učitavanje svih beleški korisnika
        List<Beleska> beleske = databaseHelper.getBeleskePoCorisniku(userId);
        adapter = new BeleskeAdapter(beleske);
        recyclerViewBeleske.setAdapter(adapter);

        // ZAHTEV 8: Dugme "Dodaj belešku"
        buttonDodajBelesku = view.findViewById(R.id.buttonDodajBelesku);
        buttonDodajBelesku.setOnClickListener(v -> proveriDozvoluIDodajBelesku());

        // ZAHTEV 11: Dugme "Filtriraj po datumu"
        buttonFiltriraj = view.findViewById(R.id.buttonFiltriraj);
        buttonFiltriraj.setOnClickListener(v -> filtrirajBeleske());
    }

    /**
     * ZAHTEV 9: Provera da li je omogućeno čitanje iz skladišta
     * Ako nije, zatražiti dozvolu; ako jeste, otvoriti formu
     * 
     * NAPOMENA: Na Android 13+ (API 33+) koristimo READ_MEDIA_IMAGES
     * Na starijim verzijama koristimo READ_EXTERNAL_STORAGE
     */
    private void proveriDozvoluIDodajBelesku() {
        // Određivanje koje dozvole tražiti na osnovu API nivoa
        String dozvola;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            // Android 13+ (API 33+)
            dozvola = Manifest.permission.READ_MEDIA_IMAGES;
        } else {
            // Starije verzije
            dozvola = Manifest.permission.READ_EXTERNAL_STORAGE;
        }
        
        // Provera da li je dozvola već odobrena
        if (ContextCompat.checkSelfPermission(requireContext(), dozvola) == PackageManager.PERMISSION_GRANTED) {
            // Dozvola je već odobrena
            otvoriFormuZaDodavanje();
        } else {
            // ZAHTEV 9: Dozvola nije odobrena - zatražiti je
            permissionLauncher.launch(dozvola);
        }
    }

    /**
     * ZAHTEV 9: Prikazuje dijalog "Pristup skladištu nije dozvoljen!"
     */
    private void prikaziDijaglogOdbijeneDozvole() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Dozvola odbijena")
                .setMessage(R.string.pristup_skladistu_nije_dozvoljen)
                .setPositiveButton(R.string.ok, null)
                .show();
    }

    /**
     * ZAHTEV 9: Otvara formu za dodavanje nove beleške
     */
    private void otvoriFormuZaDodavanje() {
        // Kreiranje custom dialoga sa formom
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = LayoutInflater.from(requireContext())
                .inflate(R.layout.dialog_dodaj_belesku, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        // Pronalaženje UI elemenata iz forme
        EditText editTextNaslov = dialogView.findViewById(R.id.editTextNaslov);
        EditText editTextTekst = dialogView.findViewById(R.id.editTextTekst);
        DatePicker datePicker = dialogView.findViewById(R.id.datePicker);
        Button buttonSacuvaj = dialogView.findViewById(R.id.buttonSacuvaj);

        // ZAHTEV 10: Klikom na "Sačuvaj", beleška se čuva u bazi
        buttonSacuvaj.setOnClickListener(v -> {
            String naslov = editTextNaslov.getText().toString().trim();
            String tekst = editTextTekst.getText().toString().trim();

            // Validacija
            if (naslov.isEmpty() || tekst.isEmpty()) {
                Toast.makeText(requireContext(), R.string.popunite_sva_polja, 
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // Preuzimanje izabranog datuma sa DatePicker-a
            int dan = datePicker.getDayOfMonth();
            int mesec = datePicker.getMonth();
            int godina = datePicker.getYear();
            
            Calendar calendar = Calendar.getInstance();
            calendar.set(godina, mesec, dan);
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            String datum = dateFormat.format(calendar.getTime());

            // ZAHTEV 10: Kreiranje i čuvanje beleške u bazi sa povezanim userID
            Beleska novaBeleska = new Beleska(naslov, tekst, datum, userId);
            long rezultat = databaseHelper.dodajBelesku(novaBeleska);

            if (rezultat != -1) {
                Toast.makeText(requireContext(), R.string.beleska_sacuvana, 
                        Toast.LENGTH_SHORT).show();
                
                // Osvežavanje liste beleški
                osveziListu();
                dialog.dismiss();
            } else {
                Toast.makeText(requireContext(), "Greška pri čuvanju beleške!", 
                        Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    /**
     * ZAHTEV 11: Filtriranje beleški po današnjem datumu
     * Ponovnim klikom prikazuju se sve beleške
     */
    private void filtrirajBeleske() {
        if (!jeFiltrirano) {
            // Filtriraj po današnjem datumu
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            String danas = dateFormat.format(new Date());
            
            List<Beleska> filtriraneBeleske = databaseHelper.getBeleskePoDatumu(userId, danas);
            adapter.azurirajBeleske(filtriraneBeleske);
            
            buttonFiltriraj.setText(R.string.sve_beleske);
            jeFiltrirano = true;
        } else {
            // Prikaži sve beleške
            osveziListu();
            buttonFiltriraj.setText(R.string.filtriraj_po_datumu);
            jeFiltrirano = false;
        }
    }

    /**
     * Osvežava listu beleški učitavanjem svih beleški iz baze
     */
    private void osveziListu() {
        List<Beleska> beleske = databaseHelper.getBeleskePoCorisniku(userId);
        adapter.azurirajBeleske(beleske);
    }
}

