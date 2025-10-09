package com.kcalorific.vezbepetisest;

/**
 * Model klasa koja predstavlja korisnika u bazi podataka
 * Sadrži sve potrebne atribute korisnika
 */
public class Korisnik {

    private int id;
    private String korisnickoIme;
    private String lozinka;
    private String email;
    private String imePrezime;

    // Konstruktor sa svim parametrima
    public Korisnik(int id, String korisnickoIme, String lozinka, String email, String imePrezime) {
        this.id = id;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
        this.email = email;
        this.imePrezime = imePrezime;
    }

    // Konstruktor bez ID-a (za kreiranje novog korisnika)
    public Korisnik(String korisnickoIme, String lozinka, String email, String imePrezime) {
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
        this.email = email;
        this.imePrezime = imePrezime;
    }

    // Prazan konstruktor
    public Korisnik() {
    }

    // Getteri i Setteri
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImePrezime() {
        return imePrezime;
    }

    public void setImePrezime(String imePrezime) {
        this.imePrezime = imePrezime;
    }

    @Override
    public String toString() {
        return "Korisnik{" +
                "id=" + id +
                ", korisnickoIme='" + korisnickoIme + '\'' +
                ", email='" + email + '\'' +
                ", imePrezime='" + imePrezime + '\'' +
                '}';
    }
}

