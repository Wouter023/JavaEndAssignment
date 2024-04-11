package com.example.javaendassignment.Controllers;

import com.example.javaendassignment.Database.Database;
import com.example.javaendassignment.Models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddProductDialogController implements Initializable {
    @FXML
    private TableView products;

    @FXML
    private TextField quantityTextField;

    @FXML
    private Label errorLabel;

    private ObservableList<Product> productsList;

    private Database database;

    private Product product;

    private ObservableList<Product> orderedProducts;

    public Product getProduct() {
        return product;
    }

    public AddProductDialogController(Database database, ObservableList<Product> productsList) {
        this.database = database;
        this.orderedProducts = productsList;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productsList = FXCollections.observableList(database.getProducts());
        products.setItems(productsList);
    }

    public void onCancelButtonClick(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void onAddButtonClick(ActionEvent event) {
        try {
            checkForQuantity();
            int quantity = Integer.parseInt(quantityTextField.getText());
            Product selectedProduct = (Product) products.getSelectionModel().getSelectedItem();
            checkForSelectedProduct(selectedProduct);
            if (quantity <= selectedProduct.getStock()) {
                product = new Product(quantity, selectedProduct.getName(), selectedProduct.getCategory(), selectedProduct.getPrice(), selectedProduct.getDescription());
                if (orderedProducts.contains(product)) {
                    int amountOfProduct = 0;
                    for (Product orderedProduct : orderedProducts) {
                        if (orderedProduct.getName().equals(product.getName())) {
                            amountOfProduct += orderedProduct.getStock();
                        }
                    }
                    if (selectedProduct.getStock() - amountOfProduct < product.getStock()) {
                        product = null;
                        throwNotEnoughStockError();
                    }
                }
                clearDialog();
            } else {
                throwNotEnoughStockError();
            }
        } catch (Exception ex) {
            errorLabel.setText(ex.getMessage());
        }

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void clearDialog() {
        errorLabel.setText("");
        quantityTextField.clear();
    }
    public void checkForSelectedProduct(Product selectedProduct) throws Exception {
        if (selectedProduct == null) {
            throw new Exception("No product selected");
        }
    }

    public void checkForQuantity() throws Exception{
        if (quantityTextField.getText().isEmpty()) {
            throw new Exception("No quantity filled in");
        }
    }

    public void throwNotEnoughStockError() throws Exception{
        throw new Exception("Not enough stock");
    }
}