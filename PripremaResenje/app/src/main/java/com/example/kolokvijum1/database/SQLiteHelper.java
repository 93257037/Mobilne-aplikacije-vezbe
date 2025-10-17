package com.example.kolokvijum1.database;

// Importujemo konstantu klasu gde su definisani nazivi tabela i kolona
import static com.example.kolokvijum1.utils.DatabaseConstants.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * SQLiteHelper - klasa koja upravlja SQLite bazom podataka
 * 
 * Zadatak 8: Kreirati model i tabelu u bazi za entitet Korisnik sa jednim poljem: ime
 * 
 * SQLiteOpenHelper je bazna klasa koju Android pruža za rad sa SQLite bazom
 * Ova klasa omogućava:
 * 1. Kreiranje baze podataka
 * 2. Otvaranje konekcije ka bazi
 * 3. Upravljanje verzijama baze (upgrade/downgrade)
 * 
 * Koristimo Singleton pattern da bi imali samo jednu instancu baze u celoj aplikaciji
 * Singleton sprečava kreiranje više konekcija ka istoj bazi
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    
    // Naziv baze podataka
    // Fajl se čuva u /data/data/com.example.kolokvijum1/databases/kolokvijum1.db
    private static final String DATABASE_NAME = "kolokvijum1.db";
    
    // Verzija baze podataka
    // Kada povećamo verziju, poziva se onUpgrade() metoda
    // Koristimo verziju 1 jer je ovo prva verzija naše baze
    private static final int DATABASE_VERSION = 1;
    
    // Singleton instanca - samo jedna instanca SQLiteHelper-a u celoj aplikaciji
    // static - znači da pripada klasi, ne objektu
    // Inicijalno je null, kreira se pri prvom pozivu getInstance()
    private static SQLiteHelper sqLiteHelper;

    /**
     * SQL upit za kreiranje tabele OSOBA
     * 
     * Struktura tabele:
     * - _id: integer primary key autoincrement
     * - ime: text
     * - godiste: integer
     */
    private static final String DB_CREATE_OSOBA = "create table "
            + TABLE_OSOBA + "("
            + COLUMN_ID  + " integer primary key autoincrement, "
            + COLUMN_IME + " text, "
            + COLUMN_GODISTE + " integer"
            + ")";

    /**
     * Privatni konstruktor - deo Singleton pattern-a
     * 
     * private - sprečava kreiranje objekta direktno (new SQLiteHelper())
     * Objekti se mogu kreirati samo preko getInstance() metode
     * 
     * @param context - kontekst aplikacije potreban za rad sa bazom
     */
    private SQLiteHelper(Context context) {
        // Pozivamo konstruktor roditeljske klase SQLiteOpenHelper
        // Parametri:
        // 1. context - kontekst aplikacije
        // 2. DATABASE_NAME - naziv baze
        // 3. null - CursorFactory (null znači default)
        // 4. DATABASE_VERSION - verzija baze
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * getInstance - metoda za dobijanje Singleton instance
     * 
     * Singleton pattern osigurava da postoji samo jedna instanca SQLiteHelper-a
     * Thread-safe implementacija koristi double-checked locking
     * 
     * @param context - kontekst aplikacije
     * @return jedinstvena instanca SQLiteHelper-a
     */
    public static SQLiteHelper getInstance(Context context) {
        // Prva provetra - da li instanca već postoji
        // Ovo je brza provera bez sinhronizacije
        if (sqLiteHelper == null) {
            // Sinhronizujemo na klasi da bi bili thread-safe
            // synchronized blokira pristup drugom thread-u dok prvi ne završi
            // SQLiteHelper.class - zaključavamo na samoj klasi
            synchronized (SQLiteHelper.class) {
                // Druga provera - unutar synchronized bloka
                // Provera je potrebna jer možda drugi thread je već kreirao instancu
                // dok smo čekali da uđemo u synchronized blok
                if (sqLiteHelper == null)
                    sqLiteHelper = new SQLiteHelper(context);
            }
        }
        // Vraćamo jedinstvenu instancu
        return sqLiteHelper;
    }

    /**
     * onCreate - poziva se kada se baza kreira PRVI PUT
     * 
     * Ova metoda se izvršava samo jednom - kada aplikacija prvi put pristupa bazi
     * Ovde kreiramo sve tabele koje trebaju postojati u bazi
     * 
     * @param db - SQLiteDatabase objekat koji predstavlja otvorenu bazu
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("KOLOKVIJUM_DB", "ON CREATE SQLITE HELPER");
        db.execSQL(DB_CREATE_OSOBA);
    }

    /**
     * onUpgrade - poziva se kada se verzija baze poveća
     * 
     * Ova metoda se poziva kada DATABASE_VERSION bude veća od verzije u bazi
     * Koristi se za migraciju podataka i izmenu strukture baze
     * 
     * NAPOMENA: Ovaj pristup BRIŠE sve podatke! U realnoj aplikaciji bi trebalo:
     * 1. Sačuvati podatke iz stare tabele
     * 2. Kreirati novu tabelu
     * 3. Preneti podatke u novu tabelu
     * 
     * @param db - SQLiteDatabase objekat
     * @param oldVersion - stara verzija baze
     * @param newVersion - nova verzija baze
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("KOLOKVIJUM_DB", "ON UPGRADE SQLITE HELPER");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OSOBA);
        onCreate(db);
    }
}
