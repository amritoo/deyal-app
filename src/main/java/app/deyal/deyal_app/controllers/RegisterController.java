package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.managers.StageManager;
import app.deyal.deyal_app.data.Register;
import app.deyal.deyal_app.repository.Auth;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;


public class RegisterController {

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

    private Register register;

    private void loadRegister() {
        register = new Register();
        register.setUserName(userNameTextField.getText());
        register.setFullName(fullNameTextField.getText());
        register.setEmail(emailTextField.getText());
        register.setPassword(passwordField.getText());
        register.setPhoneNumber("+880" + phoneTextField.getText());
    }

    @FXML
    private void handleCreateButtonAction(ActionEvent event) {
        if (check()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Do you want to submit?");
            alert.setContentText("If you submit your account will be created using the given email. You can not change email later.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                loadRegister();
                if (Auth.register(register)) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("Successfully registered account.");
                    alert.setContentText("Your new account has been created. Please login to use Deyal app.");
                    alert.showAndWait();
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
        StageManager.getInstance().registerStage.hide();
        StageManager.getInstance().loginStage.show();
    }

    private boolean check() {
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {  //show password mismatch
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Password and confirm password is not same");
            alert.setContentText("Password and confirm password needs to be same.");
            alert.showAndWait();
            return false;
        }
        if (!checkBox.isSelected()) {   //show must agree to terms and conditions
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
