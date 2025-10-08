package com.example.pripremaresenja2;

/**
 * Model klasa koja predstavlja belešku u aplikaciji.
 * Svaka beleška ima naslov, tekst, datum i pripada određenom korisniku.
 */
public class Beleska {
    private int id;
    private String naslov;      // Naslov beleške
    private String tekst;       // Sadržaj beleške
    private String datum;       // Datum kreiranja beleške (format: dd.MM.yyyy)
    private int userId;         // ID korisnika kojem pripada beleška (foreign key)

    // Konstruktor sa svim parametrima
    public Beleska(int id, String naslov, String tekst, String datum, int userId) {
        this.id = id;
        this.naslov = naslov;
        this.tekst = tekst;
        this.datum = datum;
        this.userId = userId;
    }

    // Konstruktor bez ID-a (koristi se pre upisa u bazu)
    public Beleska(String naslov, String tekst, String datum, int userId) {
        this.naslov = naslov;
        this.tekst = tekst;
        this.datum = datum;
        this.userId = userId;
    }

    // Prazan konstruktor
    public Beleska() {
    }

    // Getteri i setteri za sve atribute
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Beleska{" +
                "id=" + id +
                ", naslov='" + naslov + '\'' +
                ", datum='" + datum + '\'' +
                ", userId=" + userId +
                '}';
    }
}

