package com.example.kolokvijum1.models;

/**
 * Osoba - model klasa koja predstavlja entitet u bazi podataka
 * Sa poljima: ime i godiste
 */
public class Osoba {
    
    private int id;
    private String ime;
    private int godiste;

    /**
     * Prazan konstruktor
     */
    public Osoba() {
    }

    /**
     * Konstruktor sa parametrima
     */
    public Osoba(int id, String ime, int godiste) {
        this.id = id;
        this.ime = ime;
        this.godiste = godiste;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public int getGodiste() {
        return godiste;
    }

    public void setGodiste(int godiste) {
        this.godiste = godiste;
    }

    @Override
    public String toString() {
        return "Osoba{" +
                "id=" + id +
                ", ime='" + ime + '\'' +
                ", godiste=" + godiste +
                '}';
    }
}
