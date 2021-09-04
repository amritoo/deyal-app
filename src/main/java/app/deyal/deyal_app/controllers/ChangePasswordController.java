package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.managers.AlertManager;
import app.deyal.deyal_app.managers.DataManager;
import app.deyal.deyal_app.managers.StageManager;
import app.deyal.deyal_app.repository.AuthClient;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Arrays;

public class ChangePasswordController {

    @FXML
    public StackPane root;
    @FXML
    public VBox contentRoot;
    @FXML
    public JFXPasswordField oldPasswordField;
    @FXML
    public JFXPasswordField newPasswordField;
    @FXML
    public JFXPasswordField newPasswordRepeatField;
    @FXML
    public JFXButton submitButton;
    @FXML
    public JFXButton cancelButton;

    @FXML
    public void handleSubmitButtonAction(ActionEvent actionEvent) {
        String newPassword = newPasswordField.getText();
        String oldPassword = oldPasswordField.getText();
        if (isValid(newPassword, newPasswordRepeatField.getText())) {
            // Buttons to show in confirmation dialog
            JFXButton positiveButton = new JFXButton("Yes");
            positiveButton.setOnMouseClicked(event -> {
                boolean result = AuthClient.changePassword(DataManager.getInstance().getToken(), newPassword, oldPassword);
                if (result) {
                    AlertManager.showMaterialDialog(DataManager.getInstance().mainRoot, DataManager.getInstance().mainContentRoot,
                            null,
                            "Password changed successfully",
                            "Your password has been changed.");
                    resetTexts();
                    StageManager.getInstance().changePasswordStage.hide();
                } else {
                    AlertManager.showMaterialDialog(root, contentRoot,
                            null,
                            "Password couldn't be changed!",
                            "Sorry. Something went wrong. Please try again.");
                }
            });
            JFXButton negativeButton = new JFXButton("No");

            AlertManager.showMaterialDialog(root, contentRoot,
                    Arrays.asList(positiveButton, negativeButton),
                    "Are you sure?",
                    "Your password will be changed after this action.");
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
                    "Your password does not match with second one. Both passwords need to be equal.");
            return false;
        }
    }

    /**
     * Clears all password fields' text.
     */
    private void resetTexts() {
        newPasswordField.setText("");
        newPasswordRepeatField.setText("");
        oldPasswordField.setText("");
    }

    @FXML
    public void handleCancelButtonAction(ActionEvent actionEvent) {
        resetTexts();
        StageManager.getInstance().changePasswordStage.hide();
    }

}
