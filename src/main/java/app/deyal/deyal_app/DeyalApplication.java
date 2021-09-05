package app.deyal.deyal_app;

import app.deyal.deyal_app.data.Constants;
import app.deyal.deyal_app.managers.DataManager;
import app.deyal.deyal_app.managers.StageManager;
import app.deyal.deyal_app.repository.PreferenceSave;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeyalApplication extends Application {

    public static boolean showLogin = true;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            showSplash();
            if (showLogin) {
                StageManager.getInstance().loginStage.show();
            } else {
                DataManager.getInstance().token = PreferenceSave.getInstance().getToken();
                StageManager.getInstance().createMainStage();
                StageManager.getInstance().mainStage.show();
            }
        } catch (Exception e) {
            Logger.getLogger(Constants.LOG_NAME).log(Level.WARNING, "DeyalApplication:start", e);
            StageManager.getInstance().loginStage.show();
        }
    }

    private void showSplash() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(("/app/deyal/deyal_app/views/splash.fxml")));
            Parent root = loader.load();
            Stage stage = new Stage(StageStyle.UNDECORATED);
            Scene scene = new Scene(root);
            StageManager.getInstance().setTheme(scene);
            stage.setScene(scene);
            StageManager.getInstance().setStageIcon(stage);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            Logger.getLogger(Constants.LOG_NAME).log(Level.SEVERE, "DeyalApplication:showSplash", e);
        }
    }

}