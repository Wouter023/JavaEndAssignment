package com.example.javaendassignment.Controllers;

import com.example.javaendassignment.Database.Database;
import com.example.javaendassignment.Models.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddEditInventoryDialogController implements Initializable {
    @FXML
    private TextField stockTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField categoryTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private Label errorLabel;

    private int stock;
    private String name;
    private String category;
    private double price;
    private String description;
    private Boolean isEdit;

    private Database database;

    private Product product;

    private Product selectedProduct;

    private Product editedProduct;

    public Product getProduct() {
        return product;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public Product getEditedProduct() {
        return editedProduct;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(selectedProduct != null){
            stockTextField.setText(String.valueOf(selectedProduct.getStock()));
            nameTextField.setText(selectedProduct.getName());
            categoryTextField.setText(selectedProduct.getCategory());
            priceTextField.setText(String.valueOf(selectedProduct.getPrice()));
            descriptionTextField.setText(selectedProduct.getDescription());
        }
    }

    public AddEditInventoryDialogController(Database database) {
        isEdit = false;
        this.database = database;
    }

    public AddEditInventoryDialogController(Product selectedProduct, Database database) {
        this.selectedProduct = selectedProduct;
        this.database = database;
        isEdit = true;
    }

    public void onCancelButtonClick(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void onSaveButtonClick(ActionEvent event) {
        try {
            makeProduct();
            checkIfProductExists();

            if (isEdit){
                editedProduct = makeProduct();
            }else {
                product = makeProduct();
            }

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.close();
        } catch (Exception ex) {
            errorLabel.setText(ex.getMessage());
        }
    }

    public void checkIfProductExists() throws Exception{
        for (Product product : database.getProducts()){
            if (product.getName().equals(nameTextField.getText())){
                throw new Exception("Product name already exists");
            }
        }
    }

    public Product makeProduct() throws Exception {

        if (stockTextField.getText().isEmpty() || nameTextField.getText().isEmpty() || categoryTextField.getText().isEmpty() || priceTextField.getText().isEmpty() || descriptionTextField.getText().isEmpty()) {
            throw new Exception("Not all field filled in");
        }
        try {
            stock = Integer.parseInt(stockTextField.getText());
        } catch (Exception ex) {
            throw new Exception("Can't convert stock");
        }
        try {
            price = Double.parseDouble(priceTextField.getText());
        } catch (Exception ex) {
            throw new Exception("Can't convert price");
        }

        name = nameTextField.getText();
        category = categoryTextField.getText();
        description = descriptionTextField.getText();
        Product product = new Product(stock, name, category, price, description);
        return product;
    }

    public void clearTextFields() {
        stockTextField.clear();
        nameTextField.clear();
        categoryTextField.clear();
        priceTextField.clear();
        descriptionTextField.clear();
    }

}
