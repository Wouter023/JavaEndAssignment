package com.example.javaendassignment.Models;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Objects;

public class Product implements Serializable {
    private int stock;
    private String name;
    private String category;
    private double price;
    private String description;

    public Product(int stock, String name, String category, double price, String description) {
        this.stock = stock;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    public String getFormattedPrice(){
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return "€" + decimalFormat.format(price);
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Product other = (Product) obj;
        return this.name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
