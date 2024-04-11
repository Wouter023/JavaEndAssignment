package com.example.javaendassignment.Controllers;

import com.example.javaendassignment.Models.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashboardController implements Initializable {
    @FXML
    public Label welcomeTextLabel;

    @FXML
    public Label roleTextLabel;

    @FXML
    public Label dateTextLabel;

    private User user;

    public DashboardController(User user) {
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcomeTextLabel.setText("Welcome " + user.getFullName());
        roleTextLabel.setText("Your role is: " + user.getJobRole());

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formattedDateTime = currentDateTime.format(formatter);
        dateTextLabel.setText("It is now: " + formattedDateTime);
    }
}
