package com.example.pripremaresenja2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * DatabaseHelper klasa za upravljanje SQLite bazom podataka.
 * Ova klasa kreira i upravlja tabelama za korisnike i beleške.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Konstante za bazu podataka
    private static final String DATABASE_NAME = "kolokvijum.db";
    private static final int DATABASE_VERSION = 1;

    // Tabela: korisnici
    private static final String TABLE_USERS = "korisnici";
    private static final String USER_ID = "id";
    private static final String USER_IME_PREZIME = "ime_prezime";
    private static final String USER_EMAIL = "email";
    private static final String USER_LOZINKA = "lozinka";

    // Tabela: beleske
    private static final String TABLE_BELESKE = "beleske";
    private static final String BELESKA_ID = "id";
    private static final String BELESKA_NASLOV = "naslov";
    private static final String BELESKA_TEKST = "tekst";
    private static final String BELESKA_DATUM = "datum";
    private static final String BELESKA_USER_ID = "user_id";

    // SQL za kreiranje tabele korisnici
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + " ("
            + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_IME_PREZIME + " TEXT NOT NULL, "
            + USER_EMAIL + " TEXT NOT NULL UNIQUE, "  // UNIQUE osigurava da email može biti samo jednom u bazi
            + USER_LOZINKA + " TEXT NOT NULL)";

    // SQL za kreiranje tabele beleske
    private static final String CREATE_TABLE_BELESKE = "CREATE TABLE " + TABLE_BELESKE + " ("
            + BELESKA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + BELESKA_NASLOV + " TEXT NOT NULL, "
            + BELESKA_TEKST + " TEXT NOT NULL, "
            + BELESKA_DATUM + " TEXT NOT NULL, "
            + BELESKA_USER_ID + " INTEGER NOT NULL, "
            + "FOREIGN KEY(" + BELESKA_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + USER_ID + "))";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Kreiranje tabela
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_BELESKE);

        // ZAHTEV 5: Inicijalno ubacivanje 3 beleške u bazu
        // Prvo kreiramo test korisnika kome će pripadati ove beleške
        ContentValues userValues = new ContentValues();
        userValues.put(USER_IME_PREZIME, "Test Korisnik");
        userValues.put(USER_EMAIL, "test@example.com");
        userValues.put(USER_LOZINKA, "test123");
        long userId = db.insert(TABLE_USERS, null, userValues);

        // Format za datum
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String danasnji = dateFormat.format(new Date());

        // Ubacivanje prve beleške
        ContentValues beleska1 = new ContentValues();
        beleska1.put(BELESKA_NASLOV, "Prva beleška");
        beleska1.put(BELESKA_TEKST, "Ovo je sadržaj prve inicijalne beleške");
        beleska1.put(BELESKA_DATUM, danasnji);
        beleska1.put(BELESKA_USER_ID, userId);
        db.insert(TABLE_BELESKE, null, beleska1);

        // Ubacivanje druge beleške
        ContentValues beleska2 = new ContentValues();
        beleska2.put(BELESKA_NASLOV, "Druga beleška");
        beleska2.put(BELESKA_TEKST, "Ovo je sadržaj druge inicijalne beleške");
        beleska2.put(BELESKA_DATUM, danasnji);
        beleska2.put(BELESKA_USER_ID, userId);
        db.insert(TABLE_BELESKE, null, beleska2);

        // Ubacivanje treće beleške
        ContentValues beleska3 = new ContentValues();
        beleska3.put(BELESKA_NASLOV, "Treća beleška");
        beleska3.put(BELESKA_TEKST, "Ovo je sadržaj treće inicijalne beleške");
        beleska3.put(BELESKA_DATUM, danasnji);
        beleska3.put(BELESKA_USER_ID, userId);
        db.insert(TABLE_BELESKE, null, beleska3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Brisanje starih tabela i kreiranje novih
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BELESKE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // ==================== METODE ZA RAD SA KORISNICIMA ====================

    /**
     * Dodaje novog korisnika u bazu.
     * @param user Objekat korisnika koji se dodaje
     * @return ID novog korisnika ili -1 ako je došlo do greške
     */
    public long dodajKorisnika(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_IME_PREZIME, user.getImePrezime());
        values.put(USER_EMAIL, user.getEmail());
        values.put(USER_LOZINKA, user.getLozinka());

        long id = db.insert(TABLE_USERS, null, values);
        db.close();
        return id;
    }

    /**
     * Proverava da li email već postoji u bazi.
     * @param email Email adresa koja se proverava
     * @return true ako email postoji, false ako ne postoji
     */
    public boolean emailPostoji(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + USER_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        boolean postoji = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return postoji;
    }

    /**
     * Proverava da li su email i lozinka tačni (za login).
     * @param email Email adresa
     * @param lozinka Lozinka
     * @return User objekat ako su podaci tačni, null ako nisu
     */
    public User loginKorisnik(String email, String lozinka) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + USER_EMAIL + " = ? AND " + USER_LOZINKA + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, lozinka});

        User user = null;
        if (cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndexOrThrow(USER_ID)));
            user.setImePrezime(cursor.getString(cursor.getColumnIndexOrThrow(USER_IME_PREZIME)));
            user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(USER_EMAIL)));
            user.setLozinka(cursor.getString(cursor.getColumnIndexOrThrow(USER_LOZINKA)));
        }
        cursor.close();
        db.close();
        return user;
    }

    /**
     * Vraća korisnika na osnovu ID-a.
     * @param userId ID korisnika
     * @return User objekat ili null ako korisnik ne postoji
     */
    public User getKorisnikPoId(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + USER_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        User user = null;
        if (cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndexOrThrow(USER_ID)));
            user.setImePrezime(cursor.getString(cursor.getColumnIndexOrThrow(USER_IME_PREZIME)));
            user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(USER_EMAIL)));
            user.setLozinka(cursor.getString(cursor.getColumnIndexOrThrow(USER_LOZINKA)));
        }
        cursor.close();
        db.close();
        return user;
    }

    // ==================== METODE ZA RAD SA BELEŠKAMA ====================

    /**
     * Dodaje novu belešku u bazu.
     * @param beleska Objekat beleške koji se dodaje
     * @return ID nove beleške ili -1 ako je došlo do greške
     */
    public long dodajBelesku(Beleska beleska) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BELESKA_NASLOV, beleska.getNaslov());
        values.put(BELESKA_TEKST, beleska.getTekst());
        values.put(BELESKA_DATUM, beleska.getDatum());
        values.put(BELESKA_USER_ID, beleska.getUserId());

        long id = db.insert(TABLE_BELESKE, null, values);
        db.close();
        return id;
    }

    /**
     * Vraća sve beleške koje pripadaju određenom korisniku.
     * @param userId ID korisnika
     * @return Lista beleški
     */
    public List<Beleska> getBeleskePoCorisniku(int userId) {
        List<Beleska> beleske = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_BELESKE + " WHERE " + BELESKA_USER_ID + " = ? ORDER BY " + BELESKA_ID + " DESC";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                Beleska beleska = new Beleska();
                beleska.setId(cursor.getInt(cursor.getColumnIndexOrThrow(BELESKA_ID)));
                beleska.setNaslov(cursor.getString(cursor.getColumnIndexOrThrow(BELESKA_NASLOV)));
                beleska.setTekst(cursor.getString(cursor.getColumnIndexOrThrow(BELESKA_TEKST)));
                beleska.setDatum(cursor.getString(cursor.getColumnIndexOrThrow(BELESKA_DATUM)));
                beleska.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(BELESKA_USER_ID)));
                beleske.add(beleska);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return beleske;
    }

    /**
     * Vraća beleške korisnika filtrirane po današnjem datumu.
     * @param userId ID korisnika
     * @param danas Današnji datum u formatu dd.MM.yyyy
     * @return Lista beleški kreiranih danas
     */
    public List<Beleska> getBeleskePoDatumu(int userId, String danas) {
        List<Beleska> beleske = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_BELESKE + " WHERE " + BELESKA_USER_ID + " = ? AND " + BELESKA_DATUM + " = ? ORDER BY " + BELESKA_ID + " DESC";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId), danas});

        if (cursor.moveToFirst()) {
            do {
                Beleska beleska = new Beleska();
                beleska.setId(cursor.getInt(cursor.getColumnIndexOrThrow(BELESKA_ID)));
                beleska.setNaslov(cursor.getString(cursor.getColumnIndexOrThrow(BELESKA_NASLOV)));
                beleska.setTekst(cursor.getString(cursor.getColumnIndexOrThrow(BELESKA_TEKST)));
                beleska.setDatum(cursor.getString(cursor.getColumnIndexOrThrow(BELESKA_DATUM)));
                beleska.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(BELESKA_USER_ID)));
                beleske.add(beleska);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return beleske;
    }

    /**
     * Ažurira postojeću belešku u bazi.
     * @param beleska Objekat beleške sa novim podacima
     * @return Broj ažuriranih redova
     */
    public int azurirajBelesku(Beleska beleska) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BELESKA_NASLOV, beleska.getNaslov());
        values.put(BELESKA_TEKST, beleska.getTekst());
        values.put(BELESKA_DATUM, beleska.getDatum());

        int broj = db.update(TABLE_BELESKE, values, BELESKA_ID + " = ?",
                new String[]{String.valueOf(beleska.getId())});
        db.close();
        return broj;
    }

    /**
     * Briše belešku iz baze.
     * @param beleskaId ID beleške koja se briše
     */
    public void obrisiBelesku(int beleskaId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BELESKE, BELESKA_ID + " = ?", new String[]{String.valueOf(beleskaId)});
        db.close();
    }
}

