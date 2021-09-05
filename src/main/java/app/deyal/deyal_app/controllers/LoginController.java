package app.deyal.deyal_app.controllers;


import app.deyal.deyal_app.managers.AlertManager;
import app.deyal.deyal_app.managers.DataManager;
import app.deyal.deyal_app.managers.StageManager;
import app.deyal.deyal_app.repository.AuthClient;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class LoginController {

    @FXML
    public StackPane root;
    @FXML
    public VBox contentRoot;
    @FXML
    public JFXTextField emailTextField;
    @FXML
    public JFXPasswordField passwordField;
    @FXML
    public JFXCheckBox rememberCheckbox;

    @FXML
    private void handleRegisterButtonAction(ActionEvent actionEvent) {
        StageManager.getInstance().loginStage.hide();
        StageManager.getInstance().registerStage.showAndWait();
    }

    @FXML
    private void handleSignInButtonAction(ActionEvent actionEvent) {
        String email = emailTextField.getText();
        String password = passwordField.getText();
        boolean remember = rememberCheckbox.isSelected();

        if (AuthClient.login(email, password, remember)) {
            StageManager.getInstance().loginStage.hide();
            StageManager.getInstance().createMainStage();
            StageManager.getInstance().mainStage.show();
        } else {    // Shows login failed
            AlertManager.showMaterialDialog(root, contentRoot,
                    null,
                    "Login Failed!",
                    "Please check your email or password and try again.");
            emailTextField.getStyleClass().add("wrong-credentials");
            passwordField.getStyleClass().add("wrong-credentials");
        }
    }

    @FXML
    private void handleForgotPasswordAction(MouseEvent mouseEvent) {
        if (DataManager.getInstance().tempChoice) {
            StageManager.getInstance().verifyCodeStage.showAndWait();
        } else {
            StageManager.getInstance().sendCodeStage.showAndWait();
        }
    }

}
