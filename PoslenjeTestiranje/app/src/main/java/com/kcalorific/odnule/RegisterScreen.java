package com.kcalorific.odnule;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * RegisterScreen - Ekran za registraciju novog korisnika
 * Sadrži polja za unos podataka o korisniku i dugme za registraciju
 */
public class RegisterScreen extends AppCompatActivity {

    // Tag za logovanje
    private static final String TAG = "RegisterScreen";

    // UI komponente
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etEmail;
    private EditText etPhone;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btnRegister;
    private Button btnBackToLogin;

    /**
     * onCreate - Inicijalizacija ekrana za registraciju
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.d(TAG, "onCreate: RegisterScreen kreiran");

        // Inicijalizacija UI komponenti
        initializeViews();

        // Postavljanje listenera na dugme za registraciju
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRegistration();
            }
        });

        // Postavljanje listenera na dugme za povratak na login
        btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Vraćanje na prethodni ekran (LoginScreen)
            }
        });
    }

    /**
     * Inicijalizacija view komponenti
     */
    private void initializeViews() {
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etRegisterEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etRegisterPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnBackToLogin = findViewById(R.id.btnBackToLogin);
    }

    /**
     * Obrada registracije korisnika
     * Validira unos i prelazi na HomeScreen
     */
    private void handleRegistration() {
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        // Validacija unosa
        if (firstName.isEmpty()) {
            etFirstName.setError(getString(R.string.error_first_name_required));
            etFirstName.requestFocus();
            return;
        }

        if (lastName.isEmpty()) {
            etLastName.setError(getString(R.string.error_last_name_required));
            etLastName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            etEmail.setError(getString(R.string.error_email_required));
            etEmail.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            etPhone.setError(getString(R.string.error_phone_required));
            etPhone.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError(getString(R.string.error_password_required));
            etPassword.requestFocus();
            return;
        }

        if (confirmPassword.isEmpty()) {
            etConfirmPassword.setError(getString(R.string.error_confirm_password_required));
            etConfirmPassword.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError(getString(R.string.error_passwords_not_match));
            etConfirmPassword.requestFocus();
            return;
        }

        // Simulacija uspešne registracije
        Toast.makeText(this, getString(R.string.registration_success), Toast.LENGTH_SHORT).show();

        // Prelazak na HomeScreen
        Intent intent = new Intent(RegisterScreen.this, HomeScreen.class);
        intent.putExtra("USER_EMAIL", email);
        intent.putExtra("USER_FIRST_NAME", firstName);
        intent.putExtra("USER_LAST_NAME", lastName);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: RegisterScreen postao vidljiv");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: RegisterScreen se restartuje");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: RegisterScreen u prvom planu");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: RegisterScreen pauziran");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: RegisterScreen zaustavljen");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: RegisterScreen uništen");
    }
}

