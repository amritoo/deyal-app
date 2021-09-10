package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.managers.AlertManager;
import app.deyal.deyal_app.managers.DataManager;
import app.deyal.deyal_app.managers.StageManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Arrays;

public class JudgingMessageController {

    @FXML
    public StackPane root;
    @FXML
    public VBox contentRoot;
    @FXML
    public JFXTextArea messageTextArea;

    @FXML
    public void handleFinaliseButtonAction(ActionEvent actionEvent) {
        // Buttons to show in confirmation dialog
        JFXButton positiveButton = new JFXButton("I'm sure!");
        positiveButton.setOnMouseClicked(event -> {
            DataManager.getInstance().tempMessage = messageTextArea.getText();
            messageTextArea.setText("");
            DataManager.getInstance().tempChoice = true;
            StageManager.getInstance().judgingMessageStage.hide();
        });
        JFXButton negativeButton = new JFXButton("No");

        AlertManager.showMaterialDialog(root, contentRoot,
                Arrays.asList(positiveButton, negativeButton),
                "Are you sure?",
                "You can not change this message afterwards.");
    }

}
