package com.example.javaendassignment.Controllers;

import com.example.javaendassignment.Database.Database;
import com.example.javaendassignment.MainApplication;
import com.example.javaendassignment.Models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductInventoryController implements Initializable {
    private ObservableList<Product> products;
    @FXML
    private TableView inventoryProducts;

    private Database database;
    public ProductInventoryController(Database database) {
        this.database = database;
    }

    private Product selectedProduct;

    @FXML
    private Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        products = FXCollections.observableList(database.getProducts());
        inventoryProducts.setItems(products);
    }

    public void onDeleteButtonClick(){
        try {
            selectedProduct = (Product) inventoryProducts.getSelectionModel().getSelectedItem();
            if (selectedProduct == null){throw new Exception("No product chosen");}
            products.remove(selectedProduct);
            database.saveDataFile();
        }catch (Exception ex){
            errorLabel.setText(ex.getMessage());
        }
    }

    public void onAddButtonClick(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("add-editinventory-view.fxml"));
            AddEditInventoryDialogController addEditInventoryDialogController = new AddEditInventoryDialogController(database);
            fxmlLoader.setController(addEditInventoryDialogController);
            Scene scene = new Scene(fxmlLoader.load());

            Stage dialog = new Stage();
            dialog.setScene(scene);
            dialog.setTitle("Add product");
            dialog.showAndWait();
            if(addEditInventoryDialogController.getProduct() != null){
                products.add(addEditInventoryDialogController.getProduct());
                database.saveDataFile();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void onEditButtonClick(){
        try {
            selectedProduct = (Product) inventoryProducts.getSelectionModel().getSelectedItem();
            if (selectedProduct == null){throw new Exception("No product chosen");}
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("add-editinventory-view.fxml"));
            AddEditInventoryDialogController addEditInventoryDialogController = new AddEditInventoryDialogController(selectedProduct, database);
            fxmlLoader.setController(addEditInventoryDialogController);
            Scene scene = new Scene(fxmlLoader.load());

            Stage dialog = new Stage();
            dialog.setScene(scene);
            dialog.setTitle("Edit product");
            dialog.showAndWait();
            if(addEditInventoryDialogController.getEditedProduct() != null){
                products.remove((Product)inventoryProducts.getSelectionModel().getSelectedItem());
                products.add(addEditInventoryDialogController.getEditedProduct());
                database.saveDataFile();
            }
        } catch (Exception ex) {
            errorLabel.setText(ex.getMessage());
        }
    }
}
