package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.managers.DataManager;
import app.deyal.deyal_app.managers.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

import java.util.Optional;

public class CompleteMissionController {
    @FXML
    public ChoiceBox<String> gotRewardChoiceBox;
    @FXML
    public TextArea messageTextArea;

    @FXML
    private void initialize() {
        gotRewardChoiceBox.getItems().add("Yes");
        gotRewardChoiceBox.getItems().add("No");
    }

    @FXML
    public void handleCompleteButtonAction(ActionEvent actionEvent) {
        if (gotRewardChoiceBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Not chosen yet");
            alert.setContentText("Please choose whether you got your reward or not.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Are you sure?");
            alert.setContentText("You will complete this mission and can not change it after this.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                DataManager.getInstance().tempChoice = gotRewardChoiceBox.getValue().equals("Yes");
                DataManager.getInstance().tempMessage = messageTextArea.getText();
                messageTextArea.setText("");
                StageManager.getInstance().completeMissionStage.hide();
            }
        }
    }
}
