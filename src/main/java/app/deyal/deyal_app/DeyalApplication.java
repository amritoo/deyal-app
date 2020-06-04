package app.deyal.deyal_app;

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
        if (Auth.getUserData(PreferenceSave.getInstance().getToken())) {
            StageManager.getInstance().token = PreferenceSave.getInstance().getToken();
            StageManager.getInstance().createMainStage();
            StageManager.getInstance().mainStage.show();
        } else
            StageManager.getInstance().loginStage.show();
    }

}