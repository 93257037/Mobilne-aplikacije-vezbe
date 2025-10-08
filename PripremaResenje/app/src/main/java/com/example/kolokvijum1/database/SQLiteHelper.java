package com.example.kolokvijum1.database;

import static com.example.kolokvijum1.utils.DatabaseConstants.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "kolokvijum1.db";
    private static final int DATABASE_VERSION = 1;
    private static SQLiteHelper sqLiteHelper;

    private static final String DB_CREATE_KORISNIK = "create table "
            + TABLE_KORISNIK + "("
            + COLUMN_ID  + " integer primary key autoincrement, "
            + COLUMN_IME + " text"
            + ")";

    private SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteHelper getInstance(Context context) {
        if (sqLiteHelper == null) {
            synchronized (SQLiteHelper.class) {
                if (sqLiteHelper == null)
                    sqLiteHelper = new SQLiteHelper(context);
            }
        }
        return sqLiteHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("KOLOKVIJUM_DB", "ON CREATE SQLITE HELPER");
        db.execSQL(DB_CREATE_KORISNIK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("KOLOKVIJUM_DB", "ON UPGRADE SQLITE HELPER");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KORISNIK);
        onCreate(db);
    }
}

