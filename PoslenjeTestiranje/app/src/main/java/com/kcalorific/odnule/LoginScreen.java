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
 * LoginScreen - Ekran za prijavu korisnika
 * Sadrži polja za unos email-a i lozinke, dugme za prijavu i dugme za registraciju
 */
public class LoginScreen extends AppCompatActivity {

    // Tag za logovanje
    private static final String TAG = "LoginScreen";

    // UI komponente
    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnGoToRegister;

    /**
     * onCreate - Inicijalizacija ekrana za prijavu
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(TAG, "onCreate: LoginScreen kreiran");

        // Inicijalizacija UI komponenti
        initializeViews();

        // Postavljanje listenera na dugme za prijavu
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin();
            }
        });

        // Postavljanje listenera na dugme za prelazak na registraciju
        btnGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegisterScreen();
            }
        });
    }

    /**
     * Inicijalizacija view komponenti
     */
    private void initializeViews() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoToRegister = findViewById(R.id.btnGoToRegister);
    }

    /**
     * Obrada prijave korisnika
     * Proverava da li su polja popunjena i prelazi na HomeScreen
     */
    private void handleLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validacija unosa
        if (email.isEmpty()) {
            etEmail.setError(getString(R.string.error_email_required));
            etEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError(getString(R.string.error_password_required));
            etPassword.requestFocus();
            return;
        }

        // Simulacija uspešne prijave
        Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();

        // Prelazak na HomeScreen
        Intent intent = new Intent(LoginScreen.this, HomeScreen.class);
        intent.putExtra("USER_EMAIL", email);
        startActivity(intent);
        finish();
    }

    /**
     * Prelazak na RegisterScreen
     */
    private void goToRegisterScreen() {
        Intent intent = new Intent(LoginScreen.this, RegisterScreen.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: LoginScreen postao vidljiv");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: LoginScreen se restartuje");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: LoginScreen u prvom planu");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: LoginScreen pauziran");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: LoginScreen zaustavljen");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: LoginScreen uništen");
    }
}

