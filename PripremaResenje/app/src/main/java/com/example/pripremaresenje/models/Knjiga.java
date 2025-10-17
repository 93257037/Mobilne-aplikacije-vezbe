package com.example.pripremaresenje.models;

public class Knjiga {
    private int ID;
    private String naslov;
    private int brStranica;
    private String povez;
    private String zanr;
    private Autor autor;

    public Knjiga(){

    }
    public Knjiga(int id, String naslov, int brStranica, String povez, String zanr, Autor autor) {
        this.ID = id;
        this.naslov = naslov;
        this.brStranica = brStranica;
        this.povez = povez;
        this.zanr = zanr;
        this.autor = autor;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public int getBrStranica() {
        return brStranica;
    }

    public void setBrStranica(int brStranica) {
        this.brStranica = brStranica;
    }

    public String getPovez() {
        return povez;
    }

    public void setPovez(String povez) {
        this.povez = povez;
    }

    public String getZanr() {
        return zanr;
    }

    public void setZanr(String zanr) {
        this.zanr = zanr;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
}
