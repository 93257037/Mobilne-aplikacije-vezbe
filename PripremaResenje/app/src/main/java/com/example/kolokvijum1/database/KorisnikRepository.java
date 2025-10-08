package com.example.kolokvijum1.database;

// Import-ujemo klase potrebne za rad sa bazom podataka
import android.content.ContentValues;  // Za čuvanje parova ključ-vrednost prilikom insert/update operacija
import android.content.Context;         // Kontekst aplikacije
import android.database.Cursor;         // Za čitanje rezultata upita iz baze
import android.database.sqlite.SQLiteDatabase;  // Klasa koja predstavlja SQLite bazu

import com.example.kolokvijum1.utils.DatabaseConstants;  // Naše konstante

/**
 * KorisnikRepository - klasa koja implementira Repository pattern za Korisnik entitet
 * 
 * Repository pattern je dizajn pattern koji:
 * 1. Odvaja logiku pristupa podacima od ostatka aplikacije
 * 2. Pruža čistu API za CRUD operacije (Create, Read, Update, Delete)
 * 3. Omogućava laku izmenu načina čuvanja podataka (npr. prelazak sa SQLite na Room)
 * 
 * Ova klasa sadrži sve metode za rad sa KORISNIK tabelom u bazi
 */
public class KorisnikRepository {
    
    // Privatna polja klase
    
    // Objekat koji predstavlja otvorenu bazu podataka
    // Preko njega izvršavamo sve operacije (insert, update, delete, query)
    private SQLiteDatabase database;
    
    // Helper objekat koji upravlja bazom (kreiranje, otvaranje, verzionisanje)
    private SQLiteHelper dbHelper;

    /**
     * Konstruktor Repository-ja
     * 
     * @param context - kontekst aplikacije (potreban za pristup bazi)
     */
    public KorisnikRepository(Context context) {
        // Dobijamo Singleton instancu SQLiteHelper-a
        // getInstance() osigurava da cela aplikacija koristi istu instancu baze
        dbHelper = SQLiteHelper.getInstance(context);
    }

    /**
     * Insert metoda - dodaje novog korisnika u bazu
     * 
     * Zadatak 11: Ako je dozvoljeno, sadržaj sačuvati u bazu.
     * 
     * @param ime - ime korisnika koje želimo sačuvati
     * @return ID novokreiranog reda (row ID), ili -1 ako je insert neuspešan
     */
    public long insertData(String ime) {
        // Dobijamo referencu na bazu u režimu pisanja
        // getWritableDatabase() otvara bazu za čitanje I pisanje
        // Ako baza ne postoji, kreira je pozivom onCreate() metode iz SQLiteHelper-a
        database = dbHelper.getWritableDatabase();
        
        // ContentValues je klasa slična HashMap-i
        // Čuva parove: naziv_kolone -> vrednost
        // Koristi se za insert i update operacije
        ContentValues values = new ContentValues();
        
        // Dodajemo vrednost za kolonu "ime"
        // put(String ključ, String vrednost)
        // DatabaseConstants.COLUMN_IME je "ime" (naziv kolone)
        values.put(DatabaseConstants.COLUMN_IME, ime);
        
        // insert() metoda dodaje novi red u tabelu
        // Parametri:
        // 1. DatabaseConstants.TABLE_KORISNIK - naziv tabele ("KORISNIK")
        // 2. null - nullColumnHack (ne koristimo, ostavljamo null)
        // 3. values - ContentValues objekat sa podacima
        // 
        // Vraća: ID novokreiranog reda ili -1 ako je neuspešno
        return database.insert(DatabaseConstants.TABLE_KORISNIK, null, values);
    }

    /**
     * Get metoda - dobavlja sve korisnike iz baze
     * 
     * @param projection - niz naziva kolona koje želimo da dobijemo
     *                   npr. {"_id", "ime"} - vraća samo ID i ime
     *                   null - vraća sve kolone
     * @return Cursor objekat koji sadrži rezultate upita
     */
    public Cursor getData(String[] projection) {
        // Dobijamo bazu u režimu pisanja (može i getReadableDatabase())
        database = dbHelper.getWritableDatabase();
        
        // query() metoda izvršava SELECT upit nad tabelom
        // Parametri:
        // 1. TABLE_KORISNIK - naziv tabele
        // 2. projection - kolone koje želimo (null = sve kolone)
        // 3. selection - WHERE uslov (null = bez uslova)
        // 4. selectionArgs - argumenti za WHERE (null = nema argumenata)
        // 5. groupBy - GROUP BY (null = bez grupisanja)
        // 6. having - HAVING uslov (null = bez having-a)
        // 7. orderBy - ORDER BY (null = bez sortiranja)
        // 
        // Ekvivalent SQL: SELECT * FROM KORISNIK
        return database.query(DatabaseConstants.TABLE_KORISNIK, projection, null, null, null, null, null);
    }

    /**
     * Get metoda - dobavlja poslednjeg korisnika (po ID-u)
     * 
     * Zadatak 7: Klikom na drugo dugme pojavljuje se Toast poruka sa tekstom koji sadrži
     * ime poslednje sačuvanog entiteta u bazi
     * 
     * Ova metoda vraća korisnika sa najvećim ID-em (poslednji uneti)
     * 
     * @return Cursor sa jednim redom - poslednji korisnik
     */
    public Cursor getLastKorisnik() {
        // Otvaramo bazu za pisanje
        database = dbHelper.getWritableDatabase();
        
        // Definišemo koje kolone želimo da dobijemo
        // Vraćamo i ID i ime
        String[] projection = {DatabaseConstants.COLUMN_ID, DatabaseConstants.COLUMN_IME};
        
        // query() metoda sa dodatnim parametrima za sortiranje i limit
        // Parametri:
        // 1. TABLE_KORISNIK - naziv tabele ("KORISNIK")
        // 2. projection - kolone koje želimo {"_id", "ime"}
        // 3. null - WHERE selection (null = bez uslova)
        // 4. null - WHERE selectionArgs
        // 5. null - GROUP BY
        // 6. null - HAVING
        // 7. COLUMN_ID + " DESC" - ORDER BY _id DESC (sortiranje po ID-u opadajuće)
        //    DESC (descending) znači od najvećeg ka najmanjem
        //    Ovim dobijamo poslednji uneti red na vrhu
        // 8. "1" - LIMIT 1 (vraća samo prvi red iz rezultata)
        // 
        // Ekvivalent SQL: SELECT _id, ime FROM KORISNIK ORDER BY _id DESC LIMIT 1
        return database.query(
            DatabaseConstants.TABLE_KORISNIK,    // Tabela
            projection,                          // Kolone
            null,                                // WHERE
            null,                                // WHERE argumenti
            null,                                // GROUP BY
            null,                                // HAVING
            DatabaseConstants.COLUMN_ID + " DESC",  // ORDER BY _id DESC
            "1"                                  // LIMIT 1
        );
    }

    /**
     * Update metoda - ažurira postojećeg korisnika
     * 
     * @param id - ID korisnika kojeg želimo ažurirati
     * @param ime - novo ime korisnika
     * @return broj ažuriranih redova (obično 1, ili 0 ako korisnik ne postoji)
     */
    public int updateData(int id, String ime) {
        // Otvaramo bazu za pisanje
        database = dbHelper.getWritableDatabase();
        
        // Kreiramo ContentValues sa novim vrednostima
        ContentValues values = new ContentValues();
        values.put(DatabaseConstants.COLUMN_IME, ime);

        // WHERE uslov - string sa ? kao placeholder
        // "? " će biti zamenjen sa vrednošću iz whereArgs niza
        // Korišćenje ? je bezbedno i sprečava SQL injection napade
        String whereClause = DatabaseConstants.COLUMN_ID + " = ?";
        
        // Argumenti za WHERE uslov
        // whereArgs[0] će zameniti prvi ? u whereClause
        // valueOf() konvertuje int u String
        String[] whereArgs = {String.valueOf(id)};
        
        // update() metoda ažurira redove koji odgovaraju WHERE uslovu
        // Parametri:
        // 1. TABLE_KORISNIK - tabela
        // 2. values - nove vrednosti
        // 3. whereClause - WHERE uslov
        // 4. whereArgs - argumenti za WHERE
        // 
        // Ekvivalent SQL: UPDATE KORISNIK SET ime = 'novo_ime' WHERE _id = id
        return database.update(DatabaseConstants.TABLE_KORISNIK, values, whereClause, whereArgs);
    }

    /**
     * Delete metoda - briše korisnika iz baze
     * 
     * @param id - ID korisnika kojeg želimo obrisati
     * @return broj obrisanih redova (obično 1, ili 0 ako korisnik ne postoji)
     */
    public int deleteData(int id) {
        // Otvaramo bazu za pisanje
        database = dbHelper.getWritableDatabase();

        // WHERE uslov - korisnik sa određenim ID-em
        String whereClause = DatabaseConstants.COLUMN_ID + " = ?";
        
        // Argumenti za WHERE
        String[] whereArgs = {String.valueOf(id)};
        
        // delete() metoda briše redove koji odgovaraju WHERE uslovu
        // Parametri:
        // 1. TABLE_KORISNIK - tabela
        // 2. whereClause - WHERE uslov
        // 3. whereArgs - argumenti
        // 
        // Ekvivalent SQL: DELETE FROM KORISNIK WHERE _id = id
        return database.delete(DatabaseConstants.TABLE_KORISNIK, whereClause, whereArgs);
    }

    /**
     * Provera da li ima bilo koji unos u bazi
     * 
     * Zadatak 7: Ukoliko nema sačuvanih entiteta, prikazati sadržaj iz SharedPreferences
     * 
     * Ova metoda proverava da li tabela KORISNIK ima bar jedan red
     * 
     * @return true ako ima podataka, false ako je tabela prazna
     */
    public boolean hasAnyData() {
        // Otvaramo bazu
        database = dbHelper.getWritableDatabase();
        
        // rawQuery() izvršava direktan SQL upit (za kompleksnije upite)
        // SELECT COUNT(*) - broji ukupan broj redova u tabeli
        // COUNT(*) vraća jedan red sa jednom kolono - broj redova
        Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM " + DatabaseConstants.TABLE_KORISNIK, null);
        
        // Pomeramo kursor na prvi (i jedini) red rezultata
        // moveToFirst() vraća true ako uspešno, false ako nema redova
        cursor.moveToFirst();
        
        // Čitamo vrednost COUNT(*) koja se nalazi u prvoj koloni (indeks 0)
        // getInt(0) - čita integer vrednost sa pozicije 0
        int count = cursor.getInt(0);
        
        // VAŽNO: Zatvaramo Cursor da bi oslobodili resurse
        // Nezatvoreni Cursor-i mogu prouzrokovati memory leak
        cursor.close();
        
        // Vraćamo true ako count > 0 (ima podataka), inače false
        return count > 0;
    }

    /**
     * Zatvara konekciju sa bazom
     * 
     * Ova metoda bi trebalo da se pozove kada više ne treba repository
     * U praksi, često se ne poziva jer koristimo Singleton instancu koja živi
     * tokom celog životnog ciklusa aplikacije
     */
    public void DBClose() {
        // Zatvara SQLiteHelper što zatvara i sve otvorene konekcije ka bazi
        dbHelper.close();
    }
}
