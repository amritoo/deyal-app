package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.managers.DataManager;
import app.deyal.deyal_app.managers.StageManager;
import app.deyal.deyal_app.repository.Auth;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;

public class verifyCodeController {

    @FXML
    public JFXTextField recoverCodeTextField;
    @FXML
    public JFXPasswordField passwordField;
    @FXML
    public JFXPasswordField passwordRepeatField;

    @FXML
    public void handleVerifyButtonAction(ActionEvent event) {
        String code = recoverCodeTextField.getText();
        String password = passwordField.getText();
        if (!password.equals(passwordRepeatField.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Password Mismatch!");
            alert.setContentText("Given passwords are not same.");
            alert.showAndWait();
        } else if (Auth.verifyCode(DataManager.getInstance().tempMessage, code, password)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Successfully reset your password");
            alert.setContentText("Please login using your new password");
            alert.showAndWait();
            StageManager.getInstance().verifyCodeStage.hide();
        } else {    //show send code failed
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Verify code Failed!");
            alert.setContentText("Please check your verify code or internet connection.");
            alert.showAndWait();
        }
    }

    @FXML
    public void sendCodeAction(ActionEvent event) {
        String email = DataManager.getInstance().tempMessage;
        if (Auth.sendCode(email)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Successfully sent recover code");
            alert.setContentText("An recover code has been sent to your email address.");
            alert.showAndWait();
        } else {    //show send code failed
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Send code Failed!");
            alert.setContentText("Please check your internet connection.");
            alert.showAndWait();
        }
    }

    @FXML
    public void handleShowPasswordButtonAction(ActionEvent actionEvent) {
        //TODO show the entered pass
    }

    @FXML
    public void handleBackButtonAction(ActionEvent event) {
        StageManager.getInstance().verifyCodeStage.hide();
    }

}
