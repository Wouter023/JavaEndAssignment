package com.example.javaendassignment.Controllers;

import com.example.javaendassignment.*;
import com.example.javaendassignment.Database.Database;
import com.example.javaendassignment.Models.Job;
import com.example.javaendassignment.Models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NavigationController implements Initializable {
    @FXML
    private HBox layout;

    @FXML
    private Button dashboardButton;

    @FXML
    private Button createOrderButton;

    @FXML
    private Button productInventoryButton;

    @FXML
    private Button orderHistoryButton;

    private Database database;

    private User user;

    public NavigationController(Database database, User user) {
        this.database = database;
        this.user = user;
    }

    public void loadScene(String name, Object controller) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(name));
            fxmlLoader.setController(controller);
            Scene scene = new Scene(fxmlLoader.load());
            if (layout.getChildren().size() > 1)
                layout.getChildren().remove(1);
            layout.getChildren().add(scene.getRoot());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    protected void onDashButtonClick(ActionEvent event){
        loadScene("dashboard-view.fxml", new DashboardController(user));
    }
    @FXML
    protected void onCreateOrderButtonClick(ActionEvent event){
        loadScene("createorder-view.fxml", new CreateOrderController(database));
    }
    @FXML
    protected void onProductInventoryButtonClick(ActionEvent event){
        loadScene("productinventory-view.fxml", new ProductInventoryController(database));
    }
    @FXML
    protected void onOrderHistoryButtonClick(ActionEvent event){
        loadScene("orderhistory-view.fxml", new OrderHistoryController(database));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        database = new Database();
        loadScene("dashboard-view.fxml", new DashboardController(user));
        disableButtons();
    }

    public void disableButtons(){
        if(user.getJobRole().equals(Job.Manager)){
            createOrderButton.setVisible(false);
            createOrderButton.setManaged(false);
        } else if (user.getJobRole().equals(Job.Sales)) {
            productInventoryButton.setVisible(false);
            productInventoryButton.setManaged(false);

            orderHistoryButton.setVisible(false);
            orderHistoryButton.setManaged(false);
        }
    }
}
