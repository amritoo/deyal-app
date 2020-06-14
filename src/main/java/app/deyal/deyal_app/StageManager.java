package app.deyal.deyal_app;

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

    private StageManager() {
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
            Parent root = FXMLLoader.load(getClass().getResource("/app/deyal/deyal_app/views/auth/sendCode.fxml"));
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
            Parent root = FXMLLoader.load(getClass().getResource("/app/deyal/deyal_app/views/auth/verifyCode.fxml"));
            Scene scene = new Scene(root);
            verifyCodeStage.setScene(scene);
            verifyCodeStage.setTitle("Deyal - Recover Password");
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

    public void createUserProfileStage() {
        userProfileStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/deyal/deyal_app/views/userProfile.fxml"));
            Scene scene = new Scene(root);
            userProfileStage.setScene(scene);
            userProfileStage.setTitle("Deyal - Profile");
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
            requestMessageStage.setScene(scene);
            requestMessageStage.setTitle("Request Message");
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
            assignMessageStage.setScene(scene);
            assignMessageStage.setTitle("Assign Message");
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
            submitMissionStage.setScene(scene);
            submitMissionStage.setTitle("Submit");
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
            viewSubmissionStage.setScene(scene);
            viewSubmissionStage.setTitle("View Submission");
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
            judgingMessageStage.setScene(scene);
            judgingMessageStage.setTitle("Approve/Reject Message");
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
            viewRequestStage.setScene(scene);
            viewRequestStage.setTitle("View Request");
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
            completeMissionStage.setScene(scene);
            completeMissionStage.setTitle("Complete Mission");
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
            notificationStage.setScene(scene);
            notificationStage.setTitle("Notification");
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
            searchMissionStage.setScene(scene);
            searchMissionStage.setTitle("Search mission");
            searchMissionStage.setResizable(false);
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
