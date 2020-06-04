package app.deyal.deyal_app;

import app.deyal.deyal_app.data.Mission;
import app.deyal.deyal_app.data.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class StageManager {

    public Stage loginStage;
    public Stage registerStage;
    public Stage sendCodeStage;
    public Stage verifyCodeStage;
    public Stage resetPasswordStage;
    public Stage mainStage;
    public Stage changePasswordStage;
    public Stage createMissionStage;
    public Stage viewMissionStage;

    public String token;
    public User user;
    public Mission mission;
    public ArrayList<Mission> missionList;

    private StageManager() {
        createLoginStage();
        createRegisterStage();
        createSendCodeStage();
        createVerifyCodeStage();
        createResetPasswordStage();
//        createMainStage();
        createChangePasswordStage();
        createCreateMissionStage();
    }

    public static StageManager getInstance() {
        return Singleton.INSTANCE;
    }

    private void createLoginStage() {
        loginStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/deyal/deyal_app/views/auth/login.fxml"));
            Scene scene = new Scene(root);
            loginStage.setScene(scene);
            loginStage.setTitle("Deyal - Login");
            loginStage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createRegisterStage() {
        registerStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/deyal/deyal_app/views/auth/register.fxml"));
            Scene scene = new Scene(root);
            registerStage.setScene(scene);
            registerStage.setTitle("Deyal - Create Account");
            registerStage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createSendCodeStage() {
        sendCodeStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/deyal/deyal_app/views/auth/RecoverPasswordSendCode.fxml"));
            Scene scene = new Scene(root);
            sendCodeStage.setScene(scene);
            sendCodeStage.setTitle("Deyal - Recover Password");
            sendCodeStage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createVerifyCodeStage() {
        verifyCodeStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/deyal/deyal_app/views/auth/RecoverPasswordVerifyCode.fxml"));
            Scene scene = new Scene(root);
            verifyCodeStage.setScene(scene);
            verifyCodeStage.setTitle("Deyal - Recover Password");
            verifyCodeStage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createResetPasswordStage() {
        resetPasswordStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/deyal/deyal_app/views/auth/RecoverPasswordResetPassword.fxml"));
            Scene scene = new Scene(root);
            resetPasswordStage.setScene(scene);
            resetPasswordStage.setTitle("Deyal - Recover Password");
            resetPasswordStage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createMainStage() {
        mainStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/deyal/deyal_app/views/main.fxml"));
            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.setTitle("Deyal");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createChangePasswordStage() {
        changePasswordStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/deyal/deyal_app/views/auth/changePassword.fxml"));
            Scene scene = new Scene(root);
            changePasswordStage.setScene(scene);
            changePasswordStage.setTitle("Deyal - Change Password");
            changePasswordStage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createCreateMissionStage() {
        createMissionStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/deyal/deyal_app/views/mission/createMission.fxml"));
            Scene scene = new Scene(root);
            createMissionStage.setScene(scene);
            createMissionStage.setTitle("Deyal - Create Mission");
            createMissionStage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createViewMissionStage() {
        viewMissionStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/deyal/deyal_app/views/mission/ViewMission.fxml"));
            Scene scene = new Scene(root);
            viewMissionStage.setScene(scene);
            viewMissionStage.setTitle("Deyal - View Mission");
            viewMissionStage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getToken() {
        return token;
    }

    private static class Singleton {
        private static final StageManager INSTANCE = new StageManager();
    }
}
