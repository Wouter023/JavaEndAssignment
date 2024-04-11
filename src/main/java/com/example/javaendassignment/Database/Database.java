package com.example.javaendassignment.Database;

import com.example.javaendassignment.Models.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private List<User> users = new ArrayList<>();

    public List<User> getUsers(){
        return users;
    }

    private List<Product> products = new ArrayList<>();

    public List<Product> getProducts() {
        return products;
    }

    private List<Order> orders = new ArrayList<>();

    public List<Order> getOrders() {
        return orders;
    }

    private File dataFile;

    private String dataFileLink = "DataFile.dat";

    public Database() {
        dataFile = new File(dataFileLink);
        loadDataFile();
    }

    public void addDefaultData(){
        users.add(new User("WouterZ", "Haarlem123!", "Wouter Zwart", Job.Manager));
        users.add(new User("WouterH", "Heelleuk456?", "Wouter Hulshof", Job.Sales));
        users.add(new User("LucasvV", "Empty1234*", "Lucas van Vianen", Job.Sales));

        products.add(new Product(5, "Metal Guitar", "Guitars", 1200.00, "Metal guitar"));
        products.add(new Product(4, "Wooden Guitar", "Guitars", 1050.00, "Wooden guitar"));
        products.add(new Product(2, "Light Drums", "Drums", 625.00, "Light wooden drums"));
    }

    public void saveDataFile() {
        try {
            dataFile.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(dataFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(users);
            objectOutputStream.writeObject(products);
            objectOutputStream.writeObject(orders);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadDataFile() {
        if(!dataFile.exists()){
            addDefaultData();
            saveDataFile();
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(dataFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            users = (List<User>) objectInputStream.readObject();
            products = (List<Product>) objectInputStream.readObject();
            orders = (List<Order>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
