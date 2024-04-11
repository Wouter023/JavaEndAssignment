package com.example.javaendassignment.Controllers;

import com.example.javaendassignment.Database.Database;
import com.example.javaendassignment.MainApplication;
import com.example.javaendassignment.Models.User;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Pattern;

public class LoginController {
    @FXML
    private Button loginButton;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private Database database = new Database();

    private User user;

    @FXML
    public void onPasswordTextChange(StringProperty observable, String oldValue, String password){
        boolean containsNumber = false;
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        boolean containsSpecialCharacter = pattern.matcher(password).find();

        for (char c: password.toCharArray()) {
            if(Character.isDigit(c)){
                containsNumber = true;
            }
        }

        loginButton.setDisable(password.length() < 8 || !containsNumber || !containsSpecialCharacter);
    }

    @FXML
    protected void onLoginButtonClick(ActionEvent e) throws IOException {
        if(checkForValidUser() == null){
            errorLabel.setText("Invalid username or password!");
        }
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("navigation-view.fxml"));
            fxmlLoader.setController(new NavigationController(database, user));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Wouter's Music Application");
            stage.setScene(scene);
            stage.show();

            final Node source = (Node) e.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close();
        }

    }

    private User checkForValidUser(){
        for(User user : database.getUsers()){
            if(user.getUsername().equals(usernameField.getText()) && user.getPassword().equals(passwordField.getText())){
                return this.user = user;
            }
        }
        return null;
    }
}
