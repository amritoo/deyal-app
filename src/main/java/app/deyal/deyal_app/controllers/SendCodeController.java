package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.managers.AlertManager;
import app.deyal.deyal_app.managers.DataManager;
import app.deyal.deyal_app.managers.StageManager;
import app.deyal.deyal_app.repository.Auth;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Arrays;

public class SendCodeController {

    @FXML
    public StackPane root;
    @FXML
    public VBox contentRoot;
    @FXML
    public JFXTextField emailTextField;

    @FXML
    public void handleSendButtonAction(ActionEvent event) {
        // Buttons to show in confirmation dialog
        JFXButton positiveButton = new JFXButton("Yes");
        positiveButton.setOnMouseClicked(event1 -> {
            String email = emailTextField.getText();
            boolean result = Auth.sendCode(email);
            if (result) {
                DataManager.getInstance().tempMessage = email;
                AlertManager.showMaterialDialog(root, contentRoot,
                        null,
                        "Check your email",
                        "A recover code has been sent to your email address. Please check your email and follow the instructions given there.");
                DataManager.getInstance().tempChoice = true;
                StageManager.getInstance().sendCodeStage.hide();
            } else {
                // show send code failed
                AlertManager.showMaterialDialog(root, contentRoot,
                        null,
                        "Recover code sending failed!",
                        "Recover code could not be sent for unknown reasons. Please check your internet connection and try again.");
            }
        });
        JFXButton negativeButton = new JFXButton("No");

        AlertManager.showMaterialDialog(root, contentRoot,
                Arrays.asList(positiveButton, negativeButton),
                "Are you sure?",
                "Please check if you entered a valid email address and an account exists with this email.");
    }

    @FXML
    public void handleBackButtonAction(ActionEvent actionEvent) {
        StageManager.getInstance().sendCodeStage.hide();
    }

}
