package app.deyal.deyal_app.controllers;


import app.deyal.deyal_app.managers.DataManager;
import app.deyal.deyal_app.managers.StageManager;
import app.deyal.deyal_app.repository.Auth;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;


public class LoginController {

    @FXML
    public JFXTextField emailTextField;
    @FXML
    public JFXPasswordField passwordField;
    @FXML
    public JFXCheckBox rememberCheckbox;

    @FXML
    private void handleRegisterButtonAction(ActionEvent event) {
        StageManager.getInstance().loginStage.hide();
        StageManager.getInstance().registerStage.showAndWait();
    }

    @FXML
    private void handleSignInButtonAction(ActionEvent event) {
        String email = emailTextField.getText();
        String password = passwordField.getText();
        boolean remember = rememberCheckbox.isSelected();

        if (Auth.login(email, password, remember)) {
            StageManager.getInstance().loginStage.hide();
            StageManager.getInstance().createMainStage();
            StageManager.getInstance().mainStage.show();
        } else {    //show login failed
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Login Failed!");
            alert.setContentText("Please check your email or password.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleForgotPasswordAction(ActionEvent event) {
        //TODO: for mouse hover action show underline
        if (!DataManager.getInstance().tempChoice) {
            StageManager.getInstance().sendCodeStage.showAndWait();
        }
        if(DataManager.getInstance().tempChoice) {
            StageManager.getInstance().verifyCodeStage.showAndWait();
        }
    }

}
