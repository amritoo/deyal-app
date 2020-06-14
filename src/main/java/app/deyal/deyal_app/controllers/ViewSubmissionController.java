package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.DataManager;
import app.deyal.deyal_app.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ViewSubmissionController {

    @FXML
    public TextArea messageTextArea;

    @FXML
    private void initialize() {
        String message = DataManager.getInstance().tempMessage;
        messageTextArea.setText(message);
    }

    @FXML
    public void handleApproveButtonAction(ActionEvent actionEvent) {
        DataManager.getInstance().tempChoice = true;
        StageManager.getInstance().judgingMessageStage.showAndWait();
        StageManager.getInstance().viewSubmissionStage.hide();
    }

    @FXML
    public void handleRejectButtonAction(ActionEvent actionEvent) {
        DataManager.getInstance().tempChoice = false;
        StageManager.getInstance().judgingMessageStage.showAndWait();
        StageManager.getInstance().viewSubmissionStage.hide();
    }
}
