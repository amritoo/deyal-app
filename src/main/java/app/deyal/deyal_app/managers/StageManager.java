package app.deyal.deyal_app.managers;

import app.deyal.deyal_app.data.Constants;
import app.deyal.deyal_app.repository.PreferenceSave;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StageManager {

    private final String APP_ICON_URL = Constants.APP_ICON.toExternalForm();

    // For authentications
    public Stage loginStage;
    public Stage registerStage;
    public Stage sendCodeStage;
    public Stage verifyCodeStage;

    // For main application
    public Stage mainStage;
    public Stage changePasswordStage;
    public Stage createMissionStage;
    public Stage viewMissionStage;
    public Stage userProfileStage;
    public Stage editProfileStage;
    public Stage notificationStage;
    public Stage searchMissionStage;

    // For messages
    public Stage requestMessageStage;
    public Stage viewRequestStage;
    public Stage assignMessageStage;
    public Stage submitMissionStage;
    public Stage viewSubmissionStage;
    public Stage judgingMessageStage;
    public Stage completeMissionStage;
    private String themePath;

    private StageManager() {
        if (PreferenceSave.getInstance().isDarkTheme())
            themePath = Constants.DARK_THEME_CSS;
        else
            themePath = Constants.LIGHT_THEME_CSS;
        initializeStages();
    }

    public static StageManager getInstance() {
        return Singleton.INSTANCE;
    }

    /**
     * Changes theme between dark and light
     */
    public void changeTheme() {
        PreferenceSave.getInstance().setTheme(!PreferenceSave.getInstance().isDarkTheme());
        if (PreferenceSave.getInstance().isDarkTheme())
            themePath = Constants.DARK_THEME_CSS;
        else
            themePath = Constants.LIGHT_THEME_CSS;
        initializeStages();
    }

    public void initializeStages() {
        loginStage = loadStage(Constants.LOGIN_FXML, Constants.LOGIN_TITLE);
        registerStage = loadStage(Constants.CREATE_ACCOUNT_FXML, Constants.REGISTER_TITLE);
        sendCodeStage = loadStage(Constants.SEND_CODE_FXML, Constants.RECOVER_PASSWORD_TITLE);
        verifyCodeStage = loadStage(Constants.VERIFY_CODE_FXML, Constants.RECOVER_PASSWORD_TITLE);

        changePasswordStage = loadStage(Constants.CHANGE_PASSWORD_FXML, Constants.CHANGE_PASSWORD_TITLE);
        createMissionStage = loadStage(Constants.CREATE_MISSION_FXML, Constants.CREATE_MISSION_TITLE);
        searchMissionStage = loadStage(Constants.SEARCH_MISSION_FXML, Constants.SEARCH_MISSION_TITLE);

        // message getter
        requestMessageStage = loadStageUndecorated(Constants.REQUEST_MESSAGE_FXML, Constants.REQUEST_MESSAGE_TITLE);
        assignMessageStage = loadStageUndecorated(Constants.ASSIGN_MESSAGE_FXML, Constants.ASSIGN_MESSAGE_TITLE);
        submitMissionStage = loadStageUndecorated(Constants.SUBMIT_MISSION_FXML, Constants.SUBMIT_MISSION_TITLE);

        judgingMessageStage = loadStageUndecorated(Constants.JUDGING_MESSAGE_FXML, Constants.JUDGE_MESSAGE_TITLE);
        completeMissionStage = loadStageUndecorated(Constants.COMPLETE_MISSION_FXML, Constants.COMPLETE_MISSION_TITLE);
    }

    public String getThemePath() {
        return themePath;
    }

    public void setTheme(Scene scene) {
        scene.getStylesheets().add(themePath);
    }

    public void setStageIcon(Stage stage) {
        stage.getIcons().add(new Image(APP_ICON_URL));
    }

    /**
     * Loads a stage with given fxml file and title. Also sets theme, icon and common properties.
     *
     * @param fxmlLocation Location of fxml file
     * @param title        stage title
     * @return - created stage
     */
    public Stage loadStage(URL fxmlLocation, String title) {
        Stage stage = null;
        try {
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();
            stage = new Stage(StageStyle.DECORATED);
            Scene scene = new Scene(root);
            setTheme(scene);
            stage.setScene(scene);
            stage.setTitle(title);
            setStageIcon(stage);
            stage.setResizable(false);
        } catch (IOException ex) {
            Logger.getLogger(Constants.LOG_NAME).log(Level.SEVERE, "StageManager:loadStage", ex);
        }
        return stage;
    }

    public Stage loadStageUndecorated(URL fxmlLocation, String title) {
        Stage stage = null;
        try {
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();
            stage = new Stage(StageStyle.DECORATED);
            Scene scene = new Scene(root);
            setTheme(scene);
            stage.setScene(scene);
            stage.setTitle(title);
            setStageIcon(stage);
            stage.setResizable(false);
            Stage finalStage = stage;
            stage.setOnCloseRequest(event -> {
                event.consume();
                finalStage.hide();
            });
        } catch (IOException ex) {
            Logger.getLogger(Constants.LOG_NAME).log(Level.SEVERE, "StageManager:loadStage", ex);
        }
        return stage;
    }

    public void createMainStage() {
        mainStage = loadStage(Constants.MAIN_FXML, Constants.MAIN_TITLE);
    }

    public void createViewMissionStage() {
        viewMissionStage = loadStageUndecorated(Constants.VIEW_MISSION_FXML, Constants.VIEW_MISSION_TITLE);
    }

    public void createUserProfileStage() {
        userProfileStage = loadStageUndecorated(Constants.VIEW_PROFILE_FXML, Constants.VIEW_PROFILE_TITLE);
    }

    private static class Singleton {
        private static final StageManager INSTANCE = new StageManager();
    }

}
