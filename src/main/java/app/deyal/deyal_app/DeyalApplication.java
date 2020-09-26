package app.deyal.deyal_app;

import app.deyal.deyal_app.managers.DataManager;
import app.deyal.deyal_app.managers.StageManager;
import app.deyal.deyal_app.repository.Auth;
import app.deyal.deyal_app.repository.PreferenceSave;
import javafx.application.Application;
import javafx.stage.Stage;

public class DeyalApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
//            System.out.println("java version: " + System.getProperty("java.version"));
//            System.out.println("javafx.version: " + System.getProperty("javafx.version"));
        if (Auth.getUserData(PreferenceSave.getInstance().getToken())) {
            DataManager.getInstance().token = PreferenceSave.getInstance().getToken();
            StageManager.getInstance().createMainStage();
            StageManager.getInstance().mainStage.show();
        } else {
            StageManager.getInstance().loginStage.show();
        }
    }

}