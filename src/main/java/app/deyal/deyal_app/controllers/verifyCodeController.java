package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.managers.AlertManager;
import app.deyal.deyal_app.managers.DataManager;
import app.deyal.deyal_app.managers.StageManager;
import app.deyal.deyal_app.repository.Auth;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class verifyCodeController {

    @FXML
    public StackPane root;
    @FXML
    public VBox contentRoot;
    @FXML
    public JFXTextField recoverCodeTextField;
    @FXML
    public JFXPasswordField passwordField;
    @FXML
    public JFXPasswordField passwordRepeatField;

    @FXML
    public void handleVerifyButtonAction(ActionEvent actionEvent) {
        String code = recoverCodeTextField.getText();
        String password = passwordField.getText();
        if (!isValid(password, passwordRepeatField.getText())) {
            return;
        }

        boolean result = Auth.verifyCode(DataManager.getInstance().tempMessage, code, password);
        if (result) {
            AlertManager.showMaterialDialog(root, contentRoot,
                    null,
                    "Password changed successfully",
                    "Your password has been changed.");
            StageManager.getInstance().verifyCodeStage.hide();
        } else {
            // show send code failed
            AlertManager.showMaterialDialog(root, contentRoot,
                    null,
                    "Password recovery failed!",
                    "Sorry. Something went wrong and we could not verify your identity. Please try again.");
        }
    }

    /**
     * This method shows an alert if both password are not same. otherwise only returns true.
     *
     * @param newPassword     password
     * @param confirmPassword repeat password
     * @return true is both passwords are equal; false otherwise
     */
    private boolean isValid(String newPassword, String confirmPassword) {
        if (newPassword.equals(confirmPassword)) {
            return true;
        } else {
            AlertManager.showMaterialDialog(root, contentRoot,
                    null,
                    "Passwords are not same!",
                    "Your new password does not match with second one. Both passwords need to be equal.");
            return false;
        }
    }

    @FXML
    public void sendCodeAction(MouseEvent mouseEvent) {
        String email = DataManager.getInstance().tempMessage;
        boolean result = Auth.sendCode(email);
        if (result) {
            AlertManager.showMaterialDialog(root, contentRoot,
                    null,
                    "Check your email",
                    "A new recover code has been sent to your email address. Please check your email and follow the instructions given there.");
        } else {
            // show send code failed
            AlertManager.showMaterialDialog(root, contentRoot,
                    null,
                    "Recover code sending failed!",
                    "Recover code could not be sent for unknown reasons. Please check your internet connection and try again.");
        }
    }

    @FXML
    public void handleBackButtonAction(ActionEvent actionEvent) {
        StageManager.getInstance().verifyCodeStage.hide();
    }

}
