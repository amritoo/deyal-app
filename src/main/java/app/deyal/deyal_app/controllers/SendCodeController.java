package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.managers.DataManager;
import app.deyal.deyal_app.managers.StageManager;
import app.deyal.deyal_app.repository.Auth;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class SendCodeController {

    @FXML
    public JFXTextField emailTextField;

    @FXML
    public void handleSendButtonAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Is your email address correct?");
        alert.setContentText("You cannot change it after this action.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            String email = emailTextField.getText();
            if (Auth.sendCode(email)) {
                DataManager.getInstance().tempMessage = email;
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Successfully sent recover code");
                alert.setContentText("An recover code has been sent to your email address.");
                alert.showAndWait();
                DataManager.getInstance().tempChoice = true;
                StageManager.getInstance().sendCodeStage.hide();
            } else {    //show send code failed
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Send code Failed!");
                alert.setContentText("Please check your email or internet connection.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void handleBackButtonAction(ActionEvent actionEvent) {
        StageManager.getInstance().sendCodeStage.hide();
    }
}
