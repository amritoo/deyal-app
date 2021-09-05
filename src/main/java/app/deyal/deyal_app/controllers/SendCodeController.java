package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.managers.AlertManager;
import app.deyal.deyal_app.managers.DataManager;
import app.deyal.deyal_app.managers.StageManager;
import app.deyal.deyal_app.repository.AuthClient;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.Collections;

public class SendCodeController {

    @FXML
    public StackPane root;
    @FXML
    public VBox contentRoot;
    @FXML
    public JFXTextField emailTextField;

    @FXML
    public void handleSendButtonAction(ActionEvent actionEvent) {
        // Buttons to show in confirmation dialog
        JFXButton positiveButton = new JFXButton("Yes");
        positiveButton.setOnMouseClicked(event -> {
            String email = emailTextField.getText();
            boolean result = AuthClient.sendCode(email);
            if (result) {
                DataManager.getInstance().tempMessage = email;
                JFXButton okayButton = new JFXButton("Okay");
                okayButton.setOnMouseClicked(event1 -> {
                    DataManager.getInstance().tempChoice = true;
                    StageManager.getInstance().sendCodeStage.hide();
                    StageManager.getInstance().verifyCodeStage.show();
                });
                AlertManager.showMaterialDialog(root, contentRoot,
                        Collections.singletonList(okayButton),
                        "Check your email",
                        "A recover code has been sent to your email address. Please check your email and follow the instructions given there.");
            } else {
                // Shows send code failed
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
