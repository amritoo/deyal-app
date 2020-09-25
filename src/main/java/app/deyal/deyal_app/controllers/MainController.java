package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.managers.DataManager;
import app.deyal.deyal_app.managers.StageManager;
import app.deyal.deyal_app.repository.Auth;
import app.deyal.deyal_app.repository.PreferenceSave;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class MainController {

    @FXML
    public TabPane mainTabPane;

    // Dashboard
    @FXML
    public Tab dashboardTab;
    @FXML
    public DashboardController dashboardTabPageController;

    // My Missions
    @FXML
    public Tab myMissionsTab;
    @FXML
    public MyMissionsController myMissionsTabPageController;

    // Profile
    @FXML
    public Tab profileTab;
    @FXML
    public ProfileController profileTabPageController;

    // About
    public Tab aboutTab;
    public AboutController aboutTabPageController;

    //TODO: handle auto refresh
    @FXML
    private void initialize() {
        if (!Auth.getUserNameList(DataManager.getInstance().token)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Failed");
            alert.setHeaderText("User names loading failed");
            alert.setContentText("Check your internet connection.");
            alert.showAndWait();
        }
        mainTabPane.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) -> {
                    if (newValue == dashboardTab) {
                        dashboardTabPageController.initialize();
                    } else if (newValue == myMissionsTab) {
                        myMissionsTabPageController.initialize();
                    } else if (newValue == profileTab) {
                        profileTabPageController.initialize();
                    } else if (newValue == aboutTab) {
                        aboutTabPageController.initialize();
                    }
                });

    }

    @FXML
    public void handleCreateMissionButtonAction(ActionEvent event) {
        StageManager.getInstance().createMissionStage.showAndWait();
    }

    @FXML
    public void handleSearchMissionButtonAction(ActionEvent event) {
        StageManager.getInstance().searchMissionStage.showAndWait();
    }

    @FXML
    public void handleRefreshButtonAction(ActionEvent event) {
        initialize();
    }

    @FXML
    public void handleNotificationButtonAction(ActionEvent actionEvent) {
        StageManager.getInstance().createNotificationStage();
        StageManager.getInstance().notificationStage.showAndWait();
    }

    @FXML
    public void handleLogoutButtonAction(ActionEvent event) {   //remove preference token
        PreferenceSave.getInstance().setToken(null);
        DataManager.getInstance().clearAllData();
        StageManager.getInstance().mainStage.hide();
        StageManager.getInstance().loginStage.show();
    }

}
