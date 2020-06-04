package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RecoverPasswordController {
    //use StackPane for these three fxml files??!!

    @FXML
    public Button backToLoginButton;
    @FXML
    public TextField emailTextField;
    @FXML
    public Button sendButton;

    @FXML
    public Button backToSendCodeButton;
    @FXML
    public TextField resetCodeTextField;
    @FXML
    public Button verifyButton;

    @FXML
    public Button backToVerifyCodeButton;
    @FXML
    public PasswordField passwordField;
    @FXML
    public PasswordField passwordRepeatField;
    @FXML
    public Button submitButton;


    @FXML
    public void handleSendButtonAction(ActionEvent event) {
        //TODO: send email to server to get code
        System.out.println("Send Code button pressed");
        String email = emailTextField.getText();
        System.out.println(email);
        StageManager.getInstance().sendCodeStage.hide();
        StageManager.getInstance().verifyCodeStage.show();
    }

    @FXML
    public void handleVerifyButtonAction(ActionEvent event) {
        //TODO: send reset code to server and verify
        System.out.println("Verify Code button pressed");
        String code = resetCodeTextField.getText();
        System.out.println(code);
        StageManager.getInstance().verifyCodeStage.hide();
        StageManager.getInstance().resetPasswordStage.show();
    }

    @FXML
    public void handleSubmitButtonAction(ActionEvent event) {
        //TODO: send password to server
        System.out.println("Submit button pressed");
        String password = passwordField.getText();
        if (!password.equals(passwordRepeatField.getText())) {
            System.out.println("password mismatch!!!");
            return;
        }
        System.out.println(password);
        StageManager.getInstance().resetPasswordStage.hide();
        StageManager.getInstance().loginStage.show();
    }

    @FXML
    public void handleBackButtonAction(ActionEvent event) {
        if(event.getSource().equals(backToLoginButton)) {
            StageManager.getInstance().sendCodeStage.hide();
            StageManager.getInstance().loginStage.show();
        }
        else if(event.getSource().equals(backToSendCodeButton)) {
            StageManager.getInstance().verifyCodeStage.hide();
            StageManager.getInstance().sendCodeStage.show();
        }
        else if(event.getSource().equals(backToVerifyCodeButton)) {
            StageManager.getInstance().resetPasswordStage.hide();
            StageManager.getInstance().verifyCodeStage.show();
        }
     }
}
