package com.kcalorific.odnule.models;

/**
 * Model klasa za proizvod
 * Sadrži informacije o proizvodu: naziv, opis i cena
 */
public class Product {
    
    private int id;                // ID proizvoda
    private String name;           // Naziv proizvoda
    private String description;    // Opis proizvoda
    private double price;          // Cena proizvoda

    /**
     * Konstruktor sa svim parametrima
     */
    public Product(int id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    /**
     * Prazan konstruktor
     */
    public Product() {
    }

    // Getteri i setteri

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Formatira cenu proizvoda sa dinarskim simbolom
     */
    public String getFormattedPrice() {
        return String.format("%.2f RSD", price);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}

