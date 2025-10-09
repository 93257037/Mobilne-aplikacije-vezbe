package com.kcalorific.odnule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kcalorific.odnule.models.User;

/**
 * AccountScreen - Fragment za prikaz i upravljanje nalogom korisnika
 * Prikazuje podatke o korisniku
 */
public class AccountScreen extends Fragment {

    private TextView tvAccountName;
    private TextView tvAccountEmail;
    private TextView tvAccountBirthYear;
    private TextView tvAccountAge;

    private User currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        // Inicijalizacija view komponenti
        initializeViews(view);

        // Učitavanje mokap podataka korisnika
        loadMockUserData();

        // Prikazivanje podataka
        displayUserData();

        return view;
    }

    /**
     * Inicijalizacija view komponenti
     */
    private void initializeViews(View view) {
        tvAccountName = view.findViewById(R.id.tvAccountName);
        tvAccountEmail = view.findViewById(R.id.tvAccountEmail);
        tvAccountBirthYear = view.findViewById(R.id.tvAccountBirthYear);
        tvAccountAge = view.findViewById(R.id.tvAccountAge);
    }

    /**
     * Učitavanje mokap podataka korisnika
     */
    private void loadMockUserData() {
        // Kreiranje mokap korisnika
        currentUser = new User("Marko", "Marković", 1995, "marko.markovic@example.com");
    }

    /**
     * Prikazivanje podataka korisnika
     */
    private void displayUserData() {
        if (currentUser != null) {
            tvAccountName.setText(currentUser.getFullName());
            tvAccountEmail.setText(currentUser.getEmail());
            tvAccountBirthYear.setText(getString(R.string.account_birth_year, currentUser.getBirthYear()));
            tvAccountAge.setText(getString(R.string.account_age, currentUser.getAge()));
        }
    }
}

