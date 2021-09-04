package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.data.Constants;
import app.deyal.deyal_app.managers.DataManager;
import app.deyal.deyal_app.managers.StageManager;
import app.deyal.deyal_app.repository.PreferenceSave;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {

    @FXML
    public StackPane root;
    @FXML
    public AnchorPane contentRoot;

    @FXML
    public TabPane mainTabPane;

    // Side Drawer
    @FXML
    public JFXDrawer drawer;
    @FXML
    public JFXHamburger hamburger;

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

    @FXML
    private void initialize() {
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
        DataManager.getInstance().mainRoot = this.root;
        DataManager.getInstance().mainContentRoot = this.contentRoot;
    }

    private void initializeDrawer() {
        try {
            FXMLLoader loader = new FXMLLoader(Constants.TOOLBAR_FXML);
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

        // Adding effects
        HamburgerSlideCloseTransition transition = new HamburgerSlideCloseTransition(hamburger);
        transition.setRate(-1);

        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> drawer.toggle());

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

    /**
     * This method shows createMissionStage
     */
    public void createMissionButtonAction() {
        StageManager.getInstance().createMissionStage.showAndWait();
    }

    /**
     * This method shows searchMissionStage
     */
    public void searchMissionButtonAction() {
        StageManager.getInstance().searchMissionStage.showAndWait();
    }

    /**
     * Handles refresh button action and reloads the corresponding view.
     */
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

    /**
     * Loads and shows notification Stage
     */
    public void notificationButtonAction() {
        StageManager.getInstance().notificationStage = StageManager.getInstance()
                .loadStage(Constants.NOTIFICATION_FXML, Constants.NOTIFICATION_TITLE);
        StageManager.getInstance().notificationStage.showAndWait();
    }

    /**
     * This method changes current theme and set new theme to parent scene.
     */
    public void themeButtonAction() {
        root.getScene().getStylesheets().remove(StageManager.getInstance().getThemePath());
        StageManager.getInstance().changeTheme();
        StageManager.getInstance().setTheme(root.getScene());
    }

    /**
     * Handles logout action.
     */
    public void logoutButtonAction() {
        PreferenceSave.getInstance().setToken(null);    // remove preference token
        DataManager.getInstance().clearAllData();

        StageManager.getInstance().mainStage.hide();
        StageManager.getInstance().loginStage.show();
    }

}
