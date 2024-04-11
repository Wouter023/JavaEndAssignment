package com.example.javaendassignment.Models;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Order implements Serializable {
    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private List<Product> products;

    private LocalDateTime orderDate;

    private String name;

    public Order(String firstName, String lastName, String email, String phoneNumber, List<Product> products) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.products = products;
        this.orderDate = LocalDateTime.now();
        this.name = getFullName();
    }

    public String getTotalPrice(){
        double price = 0;
        for (Product product : products){
            price += (product.getPrice() * product.getStock());
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return "€" + decimalFormat.format(price);
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return orderDate.format(formatter);
    }
}
