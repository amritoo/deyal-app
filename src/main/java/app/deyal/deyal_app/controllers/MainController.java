package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.managers.DataManager;
import app.deyal.deyal_app.managers.StageManager;
import app.deyal.deyal_app.repository.Auth;
import app.deyal.deyal_app.repository.PreferenceSave;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    @FXML
    public Tab aboutTab;
    @FXML
    public AboutController aboutTabPageController;

    // Side Drawer
    @FXML
    public JFXDrawer drawer;
    @FXML
    public JFXHamburger hamburger;

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
        initializeDrawer();
    }

    private void initializeDrawer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/deyal/deyal_app/views/main/toolbar.fxml"));
            VBox toolbar = loader.load();
            drawer.setSidePane(toolbar);

            for (Node node : toolbar.getChildren()) {
                node.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if (node.getAccessibleText() != null) {
                        switch (node.getAccessibleText()) {
                            case "createMissionButton" -> createMissionButtonAction();
                            case "searchMissionButton" -> searchMissionButtonAction();
                            case "refreshButton" -> refreshButtonAction();
                            case "notificationButton" -> notificationButtonAction();
                            case "themeButton" -> themeButtonAction();
                            case "logoutButton" -> logoutButtonAction();
                        }
                    }
                });
            }
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        HamburgerSlideCloseTransition transition = new HamburgerSlideCloseTransition(hamburger);
        transition.setRate(-1);

        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            drawer.toggle();
        });

        drawer.setOnDrawerOpening(event -> {
            transition.setRate(transition.getRate() * -1);
            transition.play();
            drawer.toFront();
        });

        drawer.setOnDrawerClosed(event -> {
            drawer.toBack();
            transition.setRate(transition.getRate() * -1);
            transition.play();
        });
    }

    public void createMissionButtonAction() {
        StageManager.getInstance().createMissionStage.showAndWait();
    }

    public void searchMissionButtonAction() {
        StageManager.getInstance().searchMissionStage.showAndWait();
    }

    public void refreshButtonAction() {
        if (dashboardTab.isSelected()) {
            dashboardTabPageController.initialize();
        } else if (myMissionsTab.isSelected()) {
            myMissionsTabPageController.initialize();
        } else if (profileTab.isSelected()) {
            profileTabPageController.initialize();
        } else {
            aboutTabPageController.initialize();
        }
    }

    public void notificationButtonAction() {
        StageManager.getInstance().createNotificationStage();
        StageManager.getInstance().notificationStage.showAndWait();
    }

    public void themeButtonAction() {
        PreferenceSave.getInstance().setTheme(!PreferenceSave.getInstance().isDarkTheme());
        // TODO show notification and ask to restart
    }

    public void logoutButtonAction() {
        PreferenceSave.getInstance().setToken(null);    //remove preference token
        DataManager.getInstance().clearAllData();
        StageManager.getInstance().mainStage.hide();
        StageManager.getInstance().loginStage.show();
    }

}
