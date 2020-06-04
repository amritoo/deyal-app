package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.StageManager;
import app.deyal.deyal_app.data.Register;
import app.deyal.deyal_app.repository.Auth;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Optional;


public class RegisterController {

    @FXML
    private Button backButton;
    @FXML
    private TextField userNameTextField;
    @FXML
    private TextField fullNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private CheckBox checkBox;
    @FXML
    private Button submitButton;

    private Register register;

    private void initialize() {
        register = new Register();
        register.setUserName(userNameTextField.getText());
        register.setFullName(fullNameTextField.getText());
        register.setEmail(emailTextField.getText());
        register.setPassword(passwordField.getText());
        register.setPhoneNumber(phoneTextField.getText());
    }

    @FXML
    private void handleSubmitButtonAction(ActionEvent event) {
        System.out.println("Submit button pressed");

        if (check()) {
            //show must agree to terms and conditions
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Do you want to submit?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                //ok button is pressed
                initialize();
                if (Auth.register(register)) {
                    StageManager.getInstance().registerStage.hide();
                    StageManager.getInstance().loginStage.show();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Registration Failed");
                    alert.setContentText("Registration failed for some reasons.");
                    alert.showAndWait();
                }
            }
        }
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        System.out.println("Back button pressed");
        StageManager.getInstance().registerStage.hide();
        StageManager.getInstance().loginStage.show();
    }

    private boolean check() {
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            //show password mismatch
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Password and confirm password is not same");
            alert.setContentText("Password and confirm password needs to be same.");
            alert.showAndWait();
            return false;
        }
        if (!checkBox.isSelected()) {
            //show must agree to terms and conditions
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Must agree to terms and conditions");
            alert.setContentText("To proceed you MUST agree to terms and conditions.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

}
