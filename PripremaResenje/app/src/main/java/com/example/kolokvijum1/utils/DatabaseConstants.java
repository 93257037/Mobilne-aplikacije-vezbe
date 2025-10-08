package com.example.kolokvijum1.utils;

/**
 * DatabaseConstants - klasa koja sadrži konstante za rad sa bazom podataka
 * 
 * Ova klasa definiše nazive tabela i kolona koje koristimo u SQLite bazi
 * Korišćenje konstanti je dobra praksa jer:
 * 1. Sprečava greške u kucanju (typo errors)
 * 2. Omogućava laku izmenu naziva na jednom mestu
 * 3. Čini kod čitljivijim i lakšim za održavanje
 */
public class DatabaseConstants {
    
    // Zadatak 8: Kreirati model i tabelu u bazi za entitet Korisnik sa jednim poljem: ime
    
    // Naziv tabele u bazi podataka
    // public static final - znači da je konstanta javna, statička i ne može se menjati
    public static final String TABLE_KORISNIK = "KORISNIK";
    
    // Naziv kolone za primarni ključ
    // Konvencija u Android-u je da se primarni ključ zove "_id"
    public static final String COLUMN_ID = "_id";
    
    // Naziv kolone za polje ime
    // Ovo je jedino polje koje Korisnik ima prema zadatku
    public static final String COLUMN_IME = "ime";
}

