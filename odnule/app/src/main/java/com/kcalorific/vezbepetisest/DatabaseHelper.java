package com.kcalorific.vezbepetisest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * DatabaseHelper klasa za upravljanje SQLite bazom podataka
 * Implementira sve CRUD operacije za tabelu korisnici
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    // Informacije o bazi podataka
    private static final String DATABASE_NAME = "aplikacija.db";
    private static final int DATABASE_VERSION = 1;

    // Ime tabele
    private static final String TABLE_KORISNICI = "korisnici";

    // Kolone tabele
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_KORISNICKO_IME = "korisnicko_ime";
    private static final String COLUMN_LOZINKA = "lozinka";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_IME_PREZIME = "ime_prezime";

    // SQL za kreiranje tabele
    private static final String CREATE_TABLE_KORISNICI = "CREATE TABLE " + TABLE_KORISNICI + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_KORISNICKO_IME + " TEXT NOT NULL UNIQUE, "
            + COLUMN_LOZINKA + " TEXT NOT NULL, "
            + COLUMN_EMAIL + " TEXT NOT NULL, "
            + COLUMN_IME_PREZIME + " TEXT NOT NULL"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Kreiranje tabele korisnici
        db.execSQL(CREATE_TABLE_KORISNICI);
        Log.d(TAG, "Tabela korisnici kreirana");

        // Dodavanje test korisnika
        insertTestUsers(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Brisanje stare tabele i kreiranje nove
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KORISNICI);
        onCreate(db);
        Log.d(TAG, "Baza podataka ažurirana");
    }

    /**
     * Dodavanje test korisnika u bazu prilikom kreiranja
     */
    private void insertTestUsers(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        
        // Test korisnik 1
        values.put(COLUMN_KORISNICKO_IME, "admin");
        values.put(COLUMN_LOZINKA, "admin123");
        values.put(COLUMN_EMAIL, "admin@example.com");
        values.put(COLUMN_IME_PREZIME, "Admin Administrator");
        db.insert(TABLE_KORISNICI, null, values);

        // Test korisnik 2
        values.clear();
        values.put(COLUMN_KORISNICKO_IME, "korisnik1");
        values.put(COLUMN_LOZINKA, "pass123");
        values.put(COLUMN_EMAIL, "korisnik1@example.com");
        values.put(COLUMN_IME_PREZIME, "Petar Petrović");
        db.insert(TABLE_KORISNICI, null, values);

        Log.d(TAG, "Test korisnici dodati u bazu");
    }

    /**
     * CREATE - Dodavanje novog korisnika u bazu
     * @param korisnik Objekat korisnika koji se dodaje
     * @return ID novog korisnika ili -1 ako dodavanje nije uspelo
     */
    public long dodajKorisnika(Korisnik korisnik) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_KORISNICKO_IME, korisnik.getKorisnickoIme());
        values.put(COLUMN_LOZINKA, korisnik.getLozinka());
        values.put(COLUMN_EMAIL, korisnik.getEmail());
        values.put(COLUMN_IME_PREZIME, korisnik.getImePrezime());

        long id = db.insert(TABLE_KORISNICI, null, values);
        db.close();

        Log.d(TAG, "Korisnik dodat sa ID: " + id);
        return id;
    }

    /**
     * READ - Dohvatanje korisnika po ID-u
     * @param id ID korisnika
     * @return Objekat korisnika ili null ako ne postoji
     */
    public Korisnik dohvatiKorisnika(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Korisnik korisnik = null;

        Cursor cursor = db.query(
                TABLE_KORISNICI,
                null,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            korisnik = new Korisnik(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_KORISNICKO_IME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOZINKA)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IME_PREZIME))
            );
            cursor.close();
        }

        db.close();
        Log.d(TAG, "Korisnik dohvaćen: " + korisnik);
        return korisnik;
    }

    /**
     * READ - Dohvatanje korisnika po korisničkom imenu
     * @param korisnickoIme Korisničko ime
     * @return Objekat korisnika ili null ako ne postoji
     */
    public Korisnik dohvatiKorisnikaPoImenu(String korisnickoIme) {
        SQLiteDatabase db = this.getReadableDatabase();
        Korisnik korisnik = null;

        Cursor cursor = db.query(
                TABLE_KORISNICI,
                null,
                COLUMN_KORISNICKO_IME + "=?",
                new String[]{korisnickoIme},
                null, null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            korisnik = new Korisnik(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_KORISNICKO_IME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOZINKA)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IME_PREZIME))
            );
            cursor.close();
        }

        db.close();
        return korisnik;
    }

    /**
     * READ - Dohvatanje svih korisnika
     * @return Lista svih korisnika
     */
    public List<Korisnik> dohvatiSveKorisnike() {
        List<Korisnik> listaKorisnika = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_KORISNICI,
                null, null, null, null, null,
                COLUMN_ID + " ASC"
        );

        if (cursor.moveToFirst()) {
            do {
                Korisnik korisnik = new Korisnik(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_KORISNICKO_IME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOZINKA)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IME_PREZIME))
                );
                listaKorisnika.add(korisnik);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        
        Log.d(TAG, "Dohvaćeno " + listaKorisnika.size() + " korisnika");
        return listaKorisnika;
    }

    /**
     * UPDATE - Ažuriranje podataka korisnika
     * @param korisnik Objekat korisnika sa ažuriranim podacima
     * @return Broj ažuriranih redova
     */
    public int azurirajKorisnika(Korisnik korisnik) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_KORISNICKO_IME, korisnik.getKorisnickoIme());
        values.put(COLUMN_LOZINKA, korisnik.getLozinka());
        values.put(COLUMN_EMAIL, korisnik.getEmail());
        values.put(COLUMN_IME_PREZIME, korisnik.getImePrezime());

        int rowsAffected = db.update(
                TABLE_KORISNICI,
                values,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(korisnik.getId())}
        );

        db.close();
        Log.d(TAG, "Korisnik ažuriran, redova: " + rowsAffected);
        return rowsAffected;
    }

    /**
     * DELETE - Brisanje korisnika
     * @param id ID korisnika koji se briše
     * @return Broj obrisanih redova
     */
    public int obrisiKorisnika(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        int rowsAffected = db.delete(
                TABLE_KORISNICI,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}
        );

        db.close();
        Log.d(TAG, "Korisnik obrisan, redova: " + rowsAffected);
        return rowsAffected;
    }

    /**
     * Provera da li korisnik sa datim korisničkim imenom i lozinkom postoji
     * @param korisnickoIme Korisničko ime
     * @param lozinka Lozinka
     * @return true ako korisnik postoji i lozinka je tačna, false inače
     */
    public boolean proveraLogovanja(String korisnickoIme, String lozinka) {
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.query(
                TABLE_KORISNICI,
                new String[]{COLUMN_ID},
                COLUMN_KORISNICKO_IME + "=? AND " + COLUMN_LOZINKA + "=?",
                new String[]{korisnickoIme, lozinka},
                null, null, null, null
        );

        boolean postoji = cursor.getCount() > 0;
        cursor.close();
        db.close();

        Log.d(TAG, "Provera logovanja za " + korisnickoIme + ": " + postoji);
        return postoji;
    }

    /**
     * Dohvatanje broja korisnika u bazi
     * @return Broj korisnika
     */
    public int getBrojKorisnika() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_KORISNICI, null);
        
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        
        cursor.close();
        db.close();
        return count;
    }
}

