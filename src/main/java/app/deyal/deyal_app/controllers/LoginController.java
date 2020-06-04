package app.deyal.deyal_app.controllers;


import app.deyal.deyal_app.StageManager;
import app.deyal.deyal_app.repository.Auth;
import app.deyal.deyal_app.repository.MissionClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;


public class LoginController {

    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private CheckBox checkBox;
    @FXML
    private Button signInButton;
    @FXML
    private Label forgotPasswordLabel;
    @FXML
    private Button registerButton;

    @FXML
    public void initialize() {
    }

    @FXML
    private void handleRegisterButtonAction(ActionEvent event) {
        System.out.println("Register button pressed");
        StageManager.getInstance().loginStage.hide();
        StageManager.getInstance().registerStage.show();
    }

    @FXML
    private void handleSignInButtonAction(ActionEvent event) {
        System.out.println("Sign in button pressed");

        String email = emailTextField.getText();
        String password = passwordField.getText();
        boolean remember = checkBox.isSelected();

        if (Auth.login(email, password, remember)) {
//            loadDashboard();
//            loadMyMissions();
//            loadProfile();
            StageManager.getInstance().createMainStage();
            StageManager.getInstance().loginStage.hide();
            StageManager.getInstance().mainStage.show();
        } else {
            //show login failed
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Login Failed!");
            alert.setContentText("Please check your email or password.");
            alert.showAndWait();
        }
    }

    private void loadDashboard() {
        if (!MissionClient.getMissionList(StageManager.getInstance().getToken())) {
            //show user data retrieve failed
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Failed");
            alert.setHeaderText("Mission list retrieve Failed!");
            alert.setContentText("Please check your Internet connection.");
            alert.showAndWait();
        }
    }

    private void loadMyMissions() {
        // load users past mission list
    }

    private void loadProfile() {
        if (!Auth.getUserData(StageManager.getInstance().getToken())) {
            //show user data retrieve failed
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Failed");
            alert.setHeaderText("User Profile retrieve Failed!");
            alert.setContentText("Please check your Internet connection.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleMouseEvent(MouseEvent event) {
        //TODO: for mouse hover action show underline and api call
        if (event.getSource().equals(forgotPasswordLabel)) {
            System.out.println("Forgot Password");
        }
        StageManager.getInstance().loginStage.hide();
        StageManager.getInstance().sendCodeStage.show();
    }

}
