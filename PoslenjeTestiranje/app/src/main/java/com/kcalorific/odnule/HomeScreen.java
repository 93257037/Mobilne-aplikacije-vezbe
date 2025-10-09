package com.kcalorific.odnule;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

/**
 * HomeScreen - Glavni ekran aplikacije nakon prijave
 * Sadrži NavigationDrawer za navigaciju kroz aplikaciju
 */
public class HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Tag za logovanje
    private static final String TAG = "HomeScreen";

    // UI komponente
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private TextView tvWelcome;

    /**
     * onCreate - Inicijalizacija HomeScreen-a
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(TAG, "onCreate: HomeScreen kreiran");

        // Inicijalizacija UI komponenti
        initializeViews();

        // Postavljanje Toolbar-a
        setSupportActionBar(toolbar);

        // Postavljanje NavigationDrawer-a
        setupNavigationDrawer();

        // Učitavanje podataka korisnika
        loadUserData();

        // Podrazumevano učitavanje MainScreen fragmenta
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new MainScreen())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_main);
        }
    }

    /**
     * Inicijalizacija view komponenti
     */
    private void initializeViews() {
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
        tvWelcome = findViewById(R.id.tvWelcome);
    }

    /**
     * Postavljanje NavigationDrawer-a
     */
    private void setupNavigationDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * Učitavanje podataka korisnika iz Intent-a
     */
    private void loadUserData() {
        Intent intent = getIntent();
        String userEmail = intent.getStringExtra("USER_EMAIL");
        String userFirstName = intent.getStringExtra("USER_FIRST_NAME");

        if (userFirstName != null && !userFirstName.isEmpty()) {
            tvWelcome.setText(getString(R.string.welcome_message, userFirstName));
        } else if (userEmail != null && !userEmail.isEmpty()) {
            tvWelcome.setText(getString(R.string.welcome_message_email, userEmail));
        }
    }

    /**
     * Obrada klikova na stavke u Navigation Drawer-u
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.nav_main) {
            // Prelazak na MainScreen
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new MainScreen())
                    .commit();
        } else if (itemId == R.id.nav_account) {
            // Prelazak na AccountScreen
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new AccountScreen())
                    .commit();
        } else if (itemId == R.id.nav_notifications) {
            // Prelazak na NotificationsScreen
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new NotificationsScreen())
                    .commit();
        } else if (itemId == R.id.nav_logout) {
            // Odjava korisnika
            handleLogout();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Obrada odjave korisnika
     */
    private void handleLogout() {
        Intent intent = new Intent(HomeScreen.this, LoginScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    /**
     * Zatvaranje drawer-a pritiskom na back dugme
     */
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: HomeScreen postao vidljiv");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: HomeScreen se restartuje");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: HomeScreen u prvom planu");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: HomeScreen pauziran");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: HomeScreen zaustavljen");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: HomeScreen uništen");
    }
}

