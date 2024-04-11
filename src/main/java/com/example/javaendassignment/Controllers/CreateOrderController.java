package com.example.javaendassignment.Controllers;

import com.example.javaendassignment.Database.Database;
import com.example.javaendassignment.MainApplication;
import com.example.javaendassignment.Models.Order;
import com.example.javaendassignment.Models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateOrderController implements Initializable {
    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TableView addedProducts;

    @FXML
    private Label errorLabel;

    private ObservableList<Product> productsList = FXCollections.observableArrayList();

    private Database database;

    public CreateOrderController(Database database) {
        this.database = database;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addedProducts.setItems(productsList);
    }

    public void onAddButtonClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("addproduct-view.fxml"));
            AddProductDialogController addProductDialogController = new AddProductDialogController(database, productsList);
            fxmlLoader.setController(addProductDialogController);
            Scene scene = new Scene(fxmlLoader.load());

            Stage dialog = new Stage();
            dialog.setScene(scene);
            dialog.setTitle("Add product");
            dialog.showAndWait();
            if (addProductDialogController.getProduct() != null) {
                boolean productExists = false;
                for (Product product : productsList) {
                    if (product.getName().equals(addProductDialogController.getProduct().getName())) {
                        int updatedQuantity = product.getStock() + addProductDialogController.getProduct().getStock();
                        product.setStock(updatedQuantity);
                        addedProducts.refresh();
                        productExists = true;
                        break;
                    }
                }
                if (!productExists) {
                    productsList.add(addProductDialogController.getProduct());
                }
                errorLabel.setText("");
                database.saveDataFile();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onDeleteButtonClick() {
        try {
            Product selectedProduct = (Product) addedProducts.getSelectionModel().getSelectedItem();
            if (selectedProduct == null) {
                throw new Exception("No product selected");
            }
            productsList.remove(selectedProduct);
            database.saveDataFile();
        } catch (Exception ex){
            errorLabel.setText(ex.getMessage());
        }
    }

    public void onAddOrderButtonClick() {
        try {
            if (firstNameTextField.getText().isEmpty() && lastNameTextField.getText().isEmpty() && emailTextField.getText().isEmpty() && phoneNumberTextField.getText().isEmpty()) {
                throw new Exception("Not all contact information is filled in");
            } else {
                if (productsList.isEmpty()) {
                    throw new Exception("No products added");
                } else {
                    Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Create order?", ButtonType.YES, ButtonType.NO);
                    confirmation.setTitle("Order confirmation");
                    confirmation.showAndWait();
                    if (confirmation.getResult() == ButtonType.YES) {
                        database.getOrders().add(new Order(firstNameTextField.getText(), lastNameTextField.getText(), emailTextField.getText(), phoneNumberTextField.getText(), productsList.stream().toList()));
                        removeFromStock(database, productsList);
                        clearFields();
                        database.saveDataFile();
                    }
                }
            }
        } catch (Exception ex) {
            errorLabel.setText(ex.getMessage());
        }
    }

    private void clearFields() {
        firstNameTextField.clear();
        lastNameTextField.clear();
        emailTextField.clear();
        phoneNumberTextField.clear();
        productsList.clear();
    }

    private void removeFromStock(Database database, ObservableList<Product> products) {
        for (Product stockProduct : database.getProducts()) {
            for (Product amountProduct : products) {
                if (stockProduct.equals(amountProduct)) {
                    stockProduct.setStock((stockProduct.getStock() - amountProduct.getStock()));
                }
            }
        }
    }
}
