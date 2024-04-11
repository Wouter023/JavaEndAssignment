package com.example.javaendassignment.Controllers;

import com.example.javaendassignment.Database.Database;
import com.example.javaendassignment.Models.Order;
import com.example.javaendassignment.Models.Product;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderHistoryController implements Initializable {
    @FXML
    private TableView orderHistory;

    @FXML
    private TableView orderedProducts;

    private Database database;

    private ObservableList<Order> orders;

    private ObservableList<Product> products;
    public OrderHistoryController(Database database) {
        this.database = database;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orders = FXCollections.observableList(database.getOrders());
        orderHistory.setItems(orders);

        orderHistory.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Order>() {
            @Override
            public void changed(ObservableValue<? extends Order> observableValue, Order order, Order t1) {
                products = FXCollections.observableList(t1.getProducts());
                orderedProducts.setItems(products);
            }
        });
    }
}
