package com.kcalorific.vezbepetisest;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Manager klasa za upravljanje SharedPreferences
 * Čuva podatke o ulogovanom korisniku i podešavanja sinhronizacije
 */
public class PreferencesManager {

    private static final String TAG = "PreferencesManager";
    
    // Ime SharedPreferences fajla
    private static final String PREF_NAME = "AppPreferences";

    // Ključevi za podatke o korisniku
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_FULL_NAME = "full_name";

    // Ključevi za podešavanja sinhronizacije
    private static final String KEY_SYNC_ENABLED = "sync_enabled";
    private static final String KEY_SYNC_INTERVAL = "sync_interval";

    // Vrednosti intervala sinhronizacije (u milisekundama)
    public static final long SYNC_NEVER = 0;
    public static final long SYNC_1_MIN = 60 * 1000;
    public static final long SYNC_15_MIN = 15 * 60 * 1000;
    public static final long SYNC_30_MIN = 30 * 60 * 1000;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public PreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    /**
     * Čuvanje podataka o ulogovanom korisniku
     * @param korisnik Objekat korisnika
     */
    public void sacuvajUlogovanogKorisnika(Korisnik korisnik) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putInt(KEY_USER_ID, korisnik.getId());
        editor.putString(KEY_USERNAME, korisnik.getKorisnickoIme());
        editor.putString(KEY_EMAIL, korisnik.getEmail());
        editor.putString(KEY_FULL_NAME, korisnik.getImePrezime());
        editor.apply();
        
        Log.d(TAG, "Korisnik " + korisnik.getKorisnickoIme() + " sačuvan u SharedPreferences");
    }

    /**
     * Provera da li je korisnik ulogovan
     * @return true ako je korisnik ulogovan, false inače
     */
    public boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    /**
     * Dohvatanje ID-a ulogovanog korisnika
     * @return ID korisnika ili -1 ako nije ulogovan
     */
    public int getUserId() {
        return sharedPreferences.getInt(KEY_USER_ID, -1);
    }

    /**
     * Dohvatanje korisničkog imena ulogovanog korisnika
     * @return Korisničko ime ili null ako nije ulogovan
     */
    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    /**
     * Dohvatanje email-a ulogovanog korisnika
     * @return Email ili null ako nije ulogovan
     */
    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, null);
    }

    /**
     * Dohvatanje punog imena ulogovanog korisnika
     * @return Ime i prezime ili null ako nije ulogovan
     */
    public String getFullName() {
        return sharedPreferences.getString(KEY_FULL_NAME, null);
    }

    /**
     * Odjavljivanje korisnika - brisanje svih podataka o korisniku
     */
    public void odjaviKorisnika() {
        editor.putBoolean(KEY_IS_LOGGED_IN, false);
        editor.remove(KEY_USER_ID);
        editor.remove(KEY_USERNAME);
        editor.remove(KEY_EMAIL);
        editor.remove(KEY_FULL_NAME);
        editor.apply();
        
        Log.d(TAG, "Korisnik odjavljen iz sistema");
    }

    /**
     * Postavljanje intervala sinhronizacije
     * @param interval Interval u milisekundama (koristiti konstante SYNC_*)
     */
    public void postaviIntervalSinhronizacije(long interval) {
        editor.putLong(KEY_SYNC_INTERVAL, interval);
        editor.putBoolean(KEY_SYNC_ENABLED, interval != SYNC_NEVER);
        editor.apply();
        
        String intervalText = getIntervalText(interval);
        Log.d(TAG, "Interval sinhronizacije postavljen na: " + intervalText);
    }

    /**
     * Dohvatanje intervala sinhronizacije
     * @return Interval u milisekundama
     */
    public long getIntervalSinhronizacije() {
        return sharedPreferences.getLong(KEY_SYNC_INTERVAL, SYNC_NEVER);
    }

    /**
     * Provera da li je sinhronizacija omogućena
     * @return true ako je sinhronizacija omogućena, false inače
     */
    public boolean isSyncEnabled() {
        return sharedPreferences.getBoolean(KEY_SYNC_ENABLED, false);
    }

    /**
     * Dobijanje tekstualnog prikaza intervala sinhronizacije
     * @param interval Interval u milisekundama
     * @return Tekstualni prikaz intervala
     */
    public String getIntervalText(long interval) {
        if (interval == SYNC_NEVER) {
            return "Nikad";
        } else if (interval == SYNC_1_MIN) {
            return "Svakog 1 minuta";
        } else if (interval == SYNC_15_MIN) {
            return "Svakih 15 minuta";
        } else if (interval == SYNC_30_MIN) {
            return "Svakih 30 minuta";
        } else {
            return "Nepoznato";
        }
    }

    /**
     * Dohvatanje trenutnog intervala sinhronizacije kao tekst
     * @return Tekstualni prikaz trenutnog intervala
     */
    public String getCurrentSyncIntervalText() {
        return getIntervalText(getIntervalSinhronizacije());
    }

    /**
     * Brisanje svih podataka iz SharedPreferences
     */
    public void clearAll() {
        editor.clear();
        editor.apply();
        Log.d(TAG, "Svi podaci obrisani iz SharedPreferences");
    }
}

