package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.data.Register;
import app.deyal.deyal_app.managers.AlertManager;
import app.deyal.deyal_app.managers.StageManager;
import app.deyal.deyal_app.repository.AuthClient;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.Collections;


public class RegisterController {

    @FXML
    public StackPane root;
    @FXML
    public VBox contentRoot;
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
        register.setUserName(extractText(userNameTextField));
        register.setFullName(extractText(firstNameTextField) + " " + extractText(lastNameTextField));
        register.setEmail(extractText(emailTextField));
        register.setPassword(passwordField.getText());
        register.setPhoneNumber(extractText(phoneTextField));
    }

    /**
     * This method calls getText() method on given TextField and trims the string before returning.
     *
     * @param textField the object to get text from
     * @return text without beginning and trailing spaces
     */
    private String extractText(JFXTextField textField) {
        return textField.getText().trim();
    }

    @FXML
    private void handleCreateButtonAction(ActionEvent actionEvent) {
        if (!check()) {
            return;
        }

        JFXButton positiveButton = new JFXButton("Yes");
        positiveButton.setOnMouseClicked(event -> {
            loadRegister();
            if (AuthClient.register(register)) {
                JFXButton okayButton = new JFXButton("Okay");
                okayButton.setOnMouseClicked(event1 -> {
                    StageManager.getInstance().registerStage.hide();
                    StageManager.getInstance().loginStage.show();
                });
                AlertManager.showMaterialDialog(root, contentRoot,
                        Collections.singletonList(okayButton),
                        "Successfully registered new account",
                        "Your new account has been created. Please login to use Deyal app.");
            } else {
                AlertManager.showMaterialDialog(root, contentRoot,
                        null,
                        "Registration Failed!",
                        "Registration failed for unknown reasons. Please check your internet connection and try again.");
            }
        });
        JFXButton negativeButton = new JFXButton("No");

        AlertManager.showMaterialDialog(root, contentRoot,
                Arrays.asList(positiveButton, negativeButton),
                "Are you sure?",
                "If you are sure, then press yes and your account will be created using the given email. You can not change email later.");
    }

    @FXML
    private void handleBackButtonAction(ActionEvent actionEvent) {
        StageManager.getInstance().registerStage.hide();
        StageManager.getInstance().loginStage.show();
    }

    private boolean check() {
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            // Shows password mismatch
            AlertManager.showMaterialDialog(root, contentRoot,
                    null,
                    "Passwords are not same!",
                    "Your password does not match with second one. Both passwords need to be equal.");
            return false;
        }
        if (!agreementCheckBox.isSelected()) {
            // Shows must agree to term and conditions
            AlertManager.showMaterialDialog(root, contentRoot,
                    null,
                    "Must agree to terms and conditions!",
                    "To proceed you MUST agree to terms and conditions.");
            return false;
        }
        return true;
    }

}
