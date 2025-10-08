package com.example.kolokvijum1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kolokvijum1.utils.DatabaseConstants;

public class KorisnikRepository {
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public KorisnikRepository(Context context) {
        dbHelper = SQLiteHelper.getInstance(context);
    }

    // Insert metoda - dodaje novog korisnika
    public long insertData(String ime) {
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseConstants.COLUMN_IME, ime);
        return database.insert(DatabaseConstants.TABLE_KORISNIK, null, values);
    }

    // Get metoda - dobavlja sve korisnike
    public Cursor getData(String[] projection) {
        database = dbHelper.getWritableDatabase();
        return database.query(DatabaseConstants.TABLE_KORISNIK, projection, null, null, null, null, null);
    }

    // Get metoda - dobavlja poslednjeg korisnika (po ID-u)
    public Cursor getLastKorisnik() {
        database = dbHelper.getWritableDatabase();
        String[] projection = {DatabaseConstants.COLUMN_ID, DatabaseConstants.COLUMN_IME};
        return database.query(
            DatabaseConstants.TABLE_KORISNIK,
            projection,
            null,
            null,
            null,
            null,
            DatabaseConstants.COLUMN_ID + " DESC",
            "1"
        );
    }

    // Update metoda
    public int updateData(int id, String ime) {
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseConstants.COLUMN_IME, ime);

        String whereClause = DatabaseConstants.COLUMN_ID + " = ?";
        String[] whereArgs = {String.valueOf(id)};
        return database.update(DatabaseConstants.TABLE_KORISNIK, values, whereClause, whereArgs);
    }

    // Delete metoda
    public int deleteData(int id) {
        database = dbHelper.getWritableDatabase();
        String whereClause = DatabaseConstants.COLUMN_ID + " = ?";
        String[] whereArgs = {String.valueOf(id)};
        return database.delete(DatabaseConstants.TABLE_KORISNIK, whereClause, whereArgs);
    }

    // Provera da li ima bilo koji unos u bazi
    public boolean hasAnyData() {
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM " + DatabaseConstants.TABLE_KORISNIK, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }

    public void DBClose() {
        dbHelper.close();
    }
}

