package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.DeyalApplication;
import app.deyal.deyal_app.repository.AuthClient;
import app.deyal.deyal_app.repository.PreferenceSave;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class SplashController {

    @FXML
    public StackPane root;

    @FXML
    public void initialize() {
        // Add effect fade in and fade out
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), root);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), root);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setCycleCount(1);

        fadeIn.play();
        fadeIn.setOnFinished(event -> {
            DeyalApplication.showLogin = !AuthClient.getUserData(PreferenceSave.getInstance().getToken());
            fadeOut.play();
        });

        fadeOut.setOnFinished(event -> root.getScene().getWindow().hide());
    }

}
