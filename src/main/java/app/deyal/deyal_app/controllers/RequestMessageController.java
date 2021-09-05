package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.managers.AlertManager;
import app.deyal.deyal_app.managers.DataManager;
import app.deyal.deyal_app.managers.StageManager;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Arrays;

public class RequestMessageController {

    @FXML
    public StackPane root;
    @FXML
    public VBox contentRoot;
    @FXML
    public TextArea messageTextArea;

    @FXML
    public void handleSendButtonAction(ActionEvent actionEvent) {
        // Buttons to show in confirmation dialog
        JFXButton positiveButton = new JFXButton("Yes");
        positiveButton.setOnMouseClicked(event -> {
            DataManager.getInstance().tempMessage = messageTextArea.getText();
            messageTextArea.setText("");
            StageManager.getInstance().requestMessageStage.hide();
        });
        JFXButton negativeButton = new JFXButton("Yes");

        AlertManager.showMaterialDialog(root, contentRoot,
                Arrays.asList(positiveButton, negativeButton),
                "Are you sure?",
                "You can not change this message afterwards.");
    }

}
