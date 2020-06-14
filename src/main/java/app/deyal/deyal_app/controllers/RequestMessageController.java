package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.DataManager;
import app.deyal.deyal_app.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;

import java.util.Optional;

public class RequestMessageController {

    @FXML
    public TextArea messageTextArea;

    @FXML
    public void handleSendButtonAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("You can not change it after this.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            DataManager.getInstance().tempMessage = messageTextArea.getText();
            messageTextArea.setText("");
            StageManager.getInstance().requestMessageStage.hide();
        }
    }

}
