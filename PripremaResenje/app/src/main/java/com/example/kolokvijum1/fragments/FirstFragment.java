package com.example.kolokvijum1.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kolokvijum1.R;
import com.example.kolokvijum1.database.KorisnikRepository;
import com.example.kolokvijum1.utils.DatabaseConstants;

public class FirstFragment extends Fragment {

    private Button btnProveri;
    private Button btnIspisi;
    private KorisnikRepository korisnikRepository;
    private SharedPreferences sharedPreferences;

    public static FirstFragment newInstance() {
        return new FirstFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        // Inicijalizacija SharedPreferences i Repository
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        korisnikRepository = new KorisnikRepository(requireContext());

        // Inicijalizacija dugmadi
        btnProveri = view.findViewById(R.id.btnProveri);
        btnIspisi = view.findViewById(R.id.btnIspisi);

        // Klik na dugme "Proveri" - toggle enabled/disabled stanje dugmeta "Ispiši"
        btnProveri.setOnClickListener(v -> {
            btnIspisi.setEnabled(!btnIspisi.isEnabled());
        });

        // Klik na dugme "Ispiši" - prikazuje Toast sa imenom poslednjeg korisnika ili SharedPreferences vrednost
        btnIspisi.setOnClickListener(v -> {
            handleIspisiClick();
        });

        return view;
    }

    private void handleIspisiClick() {
        // Provera da li postoji korisnik u bazi
        if (korisnikRepository.hasAnyData()) {
            // Uzmi poslednjeg korisnika iz baze
            Cursor cursor = korisnikRepository.getLastKorisnik();
            if (cursor.moveToFirst()) {
                int imeIndex = cursor.getColumnIndex(DatabaseConstants.COLUMN_IME);
                String ime = cursor.getString(imeIndex);
                cursor.close();

                // Prikaži Toast sa imenom
                Toast.makeText(requireContext(), ime, Toast.LENGTH_SHORT).show();

                // Proveri da li je sadržaj iz SharedPreferences jednak imenu
                String inicijalnoValue = sharedPreferences.getString("inicijalno", "");
                if (inicijalnoValue.equals(ime)) {
                    // Zameni sadržaj u SharedPreferences sa "Zdravo!"
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("inicijalno", "Zdravo!");
                    editor.apply();
                }
            }
        } else {
            // Nema korisnika u bazi - prikaži vrednost iz SharedPreferences
            String inicijalnoValue = sharedPreferences.getString("inicijalno", "");
            Toast.makeText(requireContext(), inicijalnoValue, Toast.LENGTH_SHORT).show();
        }
    }
}

