package com.kcalorific.odnule.models;

/**
 * Model klasa za korisnika
 * Sadrži osnovne informacije o korisniku: ime, prezime, godište i email
 */
public class User {
    
    private String firstName;      // Ime korisnika
    private String lastName;       // Prezime korisnika
    private int birthYear;         // Godište korisnika
    private String email;          // Email adresa korisnika

    /**
     * Konstruktor sa svim parametrima
     */
    public User(String firstName, String lastName, int birthYear, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthYear = birthYear;
        this.email = email;
    }

    /**
     * Prazan konstruktor
     */
    public User() {
    }

    // Getteri i setteri

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Vraća puno ime korisnika (ime + prezime)
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Izračunava starost korisnika
     */
    public int getAge() {
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        return currentYear - birthYear;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthYear=" + birthYear +
                ", email='" + email + '\'' +
                '}';
    }
}

