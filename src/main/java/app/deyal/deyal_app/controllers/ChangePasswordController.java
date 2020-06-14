package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.DataManager;
import app.deyal.deyal_app.StageManager;
import app.deyal.deyal_app.repository.Auth;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;

import java.util.Optional;

public class ChangePasswordController {

    @FXML
    public PasswordField oldPasswordField;
    @FXML
    public PasswordField newPasswordField;
    @FXML
    public PasswordField newPasswordRepeatField;
    @FXML
    public Button submitButton;
    @FXML
    public Button cancelButton;

    @FXML
    public void handleSubmitButtonAction(ActionEvent event) {
        String newPassword = newPasswordField.getText();
        String oldPassword = oldPasswordField.getText();
        if (newPasswordRepeatField.getText().equals(newPassword)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Are you sure?");
            alert.setContentText("Your password will be changed after this action.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                if (Auth.changePassword(DataManager.getInstance().getToken(), newPassword, oldPassword)) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("Password changed successfully");
                    alert.setContentText("Your password has been changed.");
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Password couldn't be changed");
                    alert.setContentText("Something went wrong.");
                }
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Passwords are not same");
            alert.setContentText("Your new password and confirm password is different.");
            alert.showAndWait();
        }
    }

    @FXML
    public void handleCancelButtonAction(ActionEvent event) {
        newPasswordField.setText("");
        newPasswordRepeatField.setText("");
        oldPasswordField.setText("");
        StageManager.getInstance().changePasswordStage.hide();
    }
}
