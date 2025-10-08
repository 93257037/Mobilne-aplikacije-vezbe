package com.example.kolokvijum1.models;

/**
 * Korisnik - model klasa koja predstavlja entitet u bazi podataka
 * 
 * Zadatak 8: Kreirati model i tabelu u bazi za entitet Korisnik sa jednim poljem: ime
 * 
 * Ova klasa je POJO (Plain Old Java Object) - jednostavan Java objekat
 * Model klase služe za:
 * 1. Predstavljanje podataka iz baze u objektnom obliku
 * 2. Lak rad sa podacima u Java kodu
 * 3. Mapiranje između baze podataka i Java objekta
 */
public class Korisnik {
    
    // Privatna polja klase
    // private - znači da su dostupna samo unutar ove klase
    
    // ID korisnika - primarni ključ u bazi
    private int id;
    
    // Ime korisnika - jedino polje prema zadatku 8
    private String ime;

    /**
     * Prazan konstruktor (bez parametara)
     * Potreban je za kreiranje praznih objekata
     * Često se koristi kod deserijalizacije i ORM biblioteka
     */
    public Korisnik() {
        // Prazan konstruktor - ne radi ništa, samo omogućava kreiranje objekta
    }

    /**
     * Konstruktor sa parametrima
     * Omogućava kreiranje objekta sa već postavljenim vrednostima
     * 
     * @param id - ID korisnika (primarni ključ)
     * @param ime - ime korisnika
     */
    public Korisnik(int id, String ime) {
        // this.id - odnosi se na polje klase
        // id - odnosi se na parametar konstruktora
        this.id = id;
        this.ime = ime;
    }

    // GETTER METODE - služe za čitanje privatnih polja

    /**
     * Getter za ID
     * @return trenutna vrednost ID-a
     */
    public int getId() {
        return id;
    }

    /**
     * Getter za ime
     * @return trenutna vrednost imena
     */
    public String getIme() {
        return ime;
    }

    // SETTER METODE - služe za postavljanje novih vrednosti privatnih polja

    /**
     * Setter za ID
     * @param id nova vrednost ID-a
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setter za ime
     * @param ime nova vrednost imena
     */
    public void setIme(String ime) {
        this.ime = ime;
    }

    /**
     * toString metoda - vraća tekstualnu reprezentaciju objekta
     * Koristi se za ispis objekta, debugging, logovanje
     * 
     * @return String reprezentacija objekta
     */
    @Override
    public String toString() {
        return "Korisnik{" +
                "id=" + id +
                ", ime='" + ime + '\'' +
                '}';
    }
}

