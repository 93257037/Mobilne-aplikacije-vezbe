package com.example.kolokvijum1.models;

public class Korisnik {
    private int id;
    private String ime;

    public Korisnik() {
    }

    public Korisnik(int id, String ime) {
        this.id = id;
        this.ime = ime;
    }

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

    @Override
    public String toString() {
        return "Korisnik{" +
                "id=" + id +
                ", ime='" + ime + '\'' +
                '}';
    }
}

