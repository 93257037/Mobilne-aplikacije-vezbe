package com.example.kolokvijum1.database;

import static com.example.kolokvijum1.utils.DatabaseConstants.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kolokvijum1.models.Osoba;

import java.util.ArrayList;
import java.util.List;

/**
 * OsobaRepository - klasa za rad sa Osoba entitetom u bazi
 */
public class OsobaRepository {
    
    private SQLiteHelper sqLiteHelper;

    public OsobaRepository(Context context) {
        sqLiteHelper = SQLiteHelper.getInstance(context);
    }

    /**
     * Dodaje novu osobu u bazu
     */
    public long insert(String ime, int godiste) {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(COLUMN_IME, ime);
        values.put(COLUMN_GODISTE, godiste);
        
        long id = db.insert(TABLE_OSOBA, null, values);
        return id;
    }

    /**
     * Vraća sve osobe iz baze
     */
    public List<Osoba> getAll() {
        List<Osoba> osobe = new ArrayList<>();
        SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
        
        Cursor cursor = db.query(TABLE_OSOBA, null, null, null, null, null, null);
        
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String ime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IME));
                int godiste = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_GODISTE));
                
                Osoba osoba = new Osoba(id, ime, godiste);
                osobe.add(osoba);
            } while (cursor.moveToNext());
            
            cursor.close();
        }
        
        return osobe;
    }
}
