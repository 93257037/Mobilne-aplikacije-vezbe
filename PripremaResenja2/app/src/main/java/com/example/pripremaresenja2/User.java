package com.example.pripremaresenja2;

/**
 * Model klasa koja predstavlja korisnika u aplikaciji.
 * Ova klasa se koristi za prenos podataka između baze podataka i aplikacionog koda.
 */
public class User {
    private int id;
    private String imePrezime;  // Ime i prezime korisnika u jednom polju
    private String email;       // Email adresa (jedinstvena)
    private String lozinka;     // Lozinka korisnika

    // Konstruktor sa svim parametrima
    public User(int id, String imePrezime, String email, String lozinka) {
        this.id = id;
        this.imePrezime = imePrezime;
        this.email = email;
        this.lozinka = lozinka;
    }

    // Konstruktor bez ID-a (koristi se pre upisa u bazu kada ID jos nije dodeljen)
    public User(String imePrezime, String email, String lozinka) {
        this.imePrezime = imePrezime;
        this.email = email;
        this.lozinka = lozinka;
    }

    // Prazan konstruktor
    public User() {
    }

    // Getteri i setteri za sve atribute
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImePrezime() {
        return imePrezime;
    }

    public void setImePrezime(String imePrezime) {
        this.imePrezime = imePrezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", imePrezime='" + imePrezime + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

