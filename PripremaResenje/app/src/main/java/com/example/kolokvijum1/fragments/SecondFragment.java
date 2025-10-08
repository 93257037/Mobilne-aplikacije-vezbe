package com.example.kolokvijum1.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.kolokvijum1.R;
import com.example.kolokvijum1.SecondActivity;
import com.example.kolokvijum1.database.KorisnikRepository;

public class SecondFragment extends Fragment {

    private EditText etIme;
    private Button btnSacuvaj;
    private KorisnikRepository korisnikRepository;
    private SharedPreferences sharedPreferences;

    private ActivityResultLauncher<String> requestPermissionLauncher;

    public static SecondFragment newInstance() {
        return new SecondFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Registrujemo launcher za dozvolu
        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        // Dozvola odobrena - sačuvaj u bazu
                        saveToDatabase();
                    } else {
                        // Dozvola odbijena - sačuvaj u SharedPreferences i pređi na SecondActivity
                        saveToSharedPreferencesAndNavigate();
                    }
                }
        );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        // Inicijalizacija
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        korisnikRepository = new KorisnikRepository(requireContext());

        etIme = view.findViewById(R.id.etIme);
        btnSacuvaj = view.findViewById(R.id.btnSacuvaj);

        // Klik na dugme "Sačuvaj"
        btnSacuvaj.setOnClickListener(v -> {
            checkLocationPermission();
        });

        return view;
    }

    private void checkLocationPermission() {
        // Provera da li je dozvola za lokaciju već odobrena
        if (ContextCompat.checkSelfPermission(requireContext(), 
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Dozvola već postoji - sačuvaj u bazu
            saveToDatabase();
        } else {
            // Traži dozvolu
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    private void saveToDatabase() {
        String ime = etIme.getText().toString().trim();
        if (!ime.isEmpty()) {
            korisnikRepository.insertData(ime);
            etIme.setText("");
        }
    }

    private void saveToSharedPreferencesAndNavigate() {
        String ime = etIme.getText().toString().trim();
        if (!ime.isEmpty()) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("inicijalno", ime);
            editor.apply();
        }

        // Pređi na SecondActivity
        Intent intent = new Intent(requireContext(), SecondActivity.class);
        startActivity(intent);
    }
}

