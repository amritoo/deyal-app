package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.data.Register;
import app.deyal.deyal_app.managers.StageManager;
import app.deyal.deyal_app.repository.Auth;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;


public class RegisterController {

    @FXML
    public JFXTextField firstNameTextField;
    @FXML
    public JFXTextField lastNameTextField;
    @FXML
    public JFXTextField userNameTextField;
    @FXML
    public JFXTextField emailTextField;
    @FXML
    public JFXPasswordField passwordField;
    @FXML
    public JFXPasswordField confirmPasswordField;
    @FXML
    public JFXTextField phoneTextField;
    @FXML
    public JFXCheckBox agreementCheckBox;

    private Register register;

    private void loadRegister() {
        register = new Register();
        register.setUserName(userNameTextField.getText());
        register.setFullName(firstNameTextField.getText() + " " + lastNameTextField.getText());
        register.setEmail(emailTextField.getText());
        register.setPassword(passwordField.getText());
        register.setPhoneNumber("+88" + phoneTextField.getText());
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
        if (!agreementCheckBox.isSelected()) {   //show must agree to terms and conditions
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
