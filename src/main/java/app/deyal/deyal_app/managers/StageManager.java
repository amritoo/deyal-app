package app.deyal.deyal_app.managers;

import app.deyal.deyal_app.data.Texts;
import app.deyal.deyal_app.repository.PreferenceSave;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StageManager {

    //For authentications
    public Stage loginStage;
    public Stage registerStage;
    public Stage sendCodeStage;
    public Stage verifyCodeStage;

    //For main application
    public Stage mainStage;
    public Stage changePasswordStage;
    public Stage createMissionStage;
    public Stage viewMissionStage;
    public Stage userProfileStage;
    public Stage editProfileStage;

    //For notification or messages
    public Stage requestMessageStage;
    public Stage viewRequestStage;
    public Stage assignMessageStage;
    public Stage submitMissionStage;
    public Stage viewSubmissionStage;   //message loader
    public Stage judgingMessageStage;
    public Stage completeMissionStage;
    public Stage notificationStage;
    public Stage searchMissionStage;

    private final String themeUrl;

    private StageManager() {
        if (PreferenceSave.getInstance().getBoolean("DarkTheme"))
            themeUrl = getClass().getResource("/app/deyal/deyal_app/theme_dark.css").toExternalForm();
        else
            themeUrl = getClass().getResource("/app/deyal/deyal_app/theme_light.css").toExternalForm();

        createLoginStage();
        createRegisterStage();
        createSendCodeStage();
        createVerifyCodeStage();

        createChangePasswordStage();
        createCreateMissionStage();

        createRequestMessageStage();    //message getter
        createAssignMessageStage();
        createSubmitMissionStage();
        createJudgingMessageStage();
        createCompleteMissionStage();
        createSearchMissionStage();
    }

    private void setTheme(Scene scene) {
        scene.getStylesheets().add(themeUrl);
    }

    private void createLoginStage() {
        loginStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/deyal/deyal_app/views/auth/login.fxml"));
            Scene scene = new Scene(root);
            setTheme(scene);
            loginStage.setScene(scene);
            loginStage.setTitle(Texts.TITLE_LOGIN);
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
            setTheme(scene);
            registerStage.setScene(scene);
            registerStage.setTitle(Texts.TITLE_REGISTER);
            registerStage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createSendCodeStage() {
        sendCodeStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/deyal/deyal_app/views/auth/sendCode.fxml"));
            Scene scene = new Scene(root);
            setTheme(scene);
            sendCodeStage.setScene(scene);
            sendCodeStage.setTitle(Texts.TITLE_RECOVER_PASSWORD);
            sendCodeStage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createVerifyCodeStage() {
        verifyCodeStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/deyal/deyal_app/views/auth/verifyCode.fxml"));
            Scene scene = new Scene(root);
            setTheme(scene);
            verifyCodeStage.setScene(scene);
            verifyCodeStage.setTitle(Texts.TITLE_RECOVER_PASSWORD);
            verifyCodeStage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createMainStage() {
        mainStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/deyal/deyal_app/views/main.fxml"));
            Scene scene = new Scene(root);
            setTheme(scene);
            mainStage.setScene(scene);
            mainStage.setTitle(Texts.TITLE_MAIN);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createChangePasswordStage() {
        changePasswordStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/deyal/deyal_app/views/auth/changePassword.fxml"));
            Scene scene = new Scene(root);
            setTheme(scene);
            changePasswordStage.setScene(scene);
            changePasswordStage.setTitle(Texts.TITLE_CHANGE_PASSWORD);
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
            setTheme(scene);
            createMissionStage.setScene(scene);
            createMissionStage.setTitle(Texts.TITLE_CREATE_MISSION);
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
            setTheme(scene);
            viewMissionStage.setScene(scene);
            viewMissionStage.setTitle(Texts.TITLE_VIEW_MISSION);
            viewMissionStage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createUserProfileStage() {
        userProfileStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/deyal/deyal_app/views/viewProfile.fxml"));
            Scene scene = new Scene(root);
            setTheme(scene);
            userProfileStage.setScene(scene);
            userProfileStage.setTitle(Texts.TITLE_PROFILE);
            userProfileStage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createRequestMessageStage() {
        requestMessageStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/deyal/deyal_app/views/event/requestMessage.fxml"));
            Scene scene = new Scene(root);
            setTheme(scene);
            requestMessageStage.setScene(scene);
            requestMessageStage.setTitle(Texts.TITLE_REQUEST_MESSAGE);
            requestMessageStage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createAssignMessageStage() {
        assignMessageStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/deyal/deyal_app/views/event/assignMessage.fxml"));
            Scene scene = new Scene(root);
            setTheme(scene);
            assignMessageStage.setScene(scene);
            assignMessageStage.setTitle(Texts.TITLE_ASSIGN_MESSAGE);
            assignMessageStage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createSubmitMissionStage() {
        submitMissionStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/deyal/deyal_app/views/event/submitMission.fxml"));
            Scene scene = new Scene(root);
            setTheme(scene);
            submitMissionStage.setScene(scene);
            submitMissionStage.setTitle(Texts.TITLE_SUBMIT_MISSION);
            submitMissionStage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createViewSubmissionStage() {
        viewSubmissionStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/deyal/deyal_app/views/event/viewSubmission.fxml"));
            Scene scene = new Scene(root);
            setTheme(scene);
            viewSubmissionStage.setScene(scene);
            viewSubmissionStage.setTitle(Texts.TITLE_VIEW_SUBMISSION);
            viewSubmissionStage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createJudgingMessageStage() {
        judgingMessageStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/deyal/deyal_app/views/event/judgingMessage.fxml"));
            Scene scene = new Scene(root);
            setTheme(scene);
            judgingMessageStage.setScene(scene);
            judgingMessageStage.setTitle(Texts.TITLE_JUDGE_MESSAGE);
            judgingMessageStage.setResizable(false);
            //TODO: set closing property, cannot close
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createViewRequestStage() {
        viewRequestStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/deyal/deyal_app/views/event/viewRequest.fxml"));
            Scene scene = new Scene(root);
            setTheme(scene);
            viewRequestStage.setScene(scene);
            viewRequestStage.setTitle(Texts.TITLE_VIEW_REQUEST);
            viewRequestStage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createCompleteMissionStage() {
        completeMissionStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/deyal/deyal_app/views/event/completeMission.fxml"));
            Scene scene = new Scene(root);
            setTheme(scene);
            completeMissionStage.setScene(scene);
            completeMissionStage.setTitle(Texts.TITLE_COMPLETE_MISSION);
            completeMissionStage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createNotificationStage() {
        notificationStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/deyal/deyal_app/views/notification.fxml"));
            Scene scene = new Scene(root);
            setTheme(scene);
            notificationStage.setScene(scene);
            notificationStage.setTitle(Texts.TITLE_NOTIFICATION);
            notificationStage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createSearchMissionStage() {
        searchMissionStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/deyal/deyal_app/views/mission/search.fxml"));
            Scene scene = new Scene(root);
            setTheme(scene);
            searchMissionStage.setScene(scene);
            searchMissionStage.setTitle(Texts.TITLE_SEARCH_MISSION);
            searchMissionStage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createEditProfileStage() {
        editProfileStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/deyal/deyal_app/views/editProfile.fxml"));
            Scene scene = new Scene(root);
            setTheme(scene);
            editProfileStage.setScene(scene);
            editProfileStage.setTitle(Texts.TITLE_EDIT_PROFILE);
            editProfileStage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static StageManager getInstance() {
        return Singleton.INSTANCE;
    }

    private static class Singleton {
        private static final StageManager INSTANCE = new StageManager();
    }
}
