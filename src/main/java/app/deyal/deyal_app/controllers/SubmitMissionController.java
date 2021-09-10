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

public class
SubmitMissionController {

    @FXML
    public StackPane root;
    @FXML
    public VBox contentRoot;
    @FXML
    public TextArea messageTextArea;

    @FXML
    public void handleSubmitButtonAction(ActionEvent actionEvent) {
        // Buttons to show in confirmation dialog
        JFXButton positiveButton = new JFXButton("I'm sure!");
        positiveButton.setOnMouseClicked(event -> {
            DataManager.getInstance().tempMessage = messageTextArea.getText();
            messageTextArea.setText("");
            DataManager.getInstance().tempChoice = true;
            StageManager.getInstance().submitMissionStage.hide();
        });
        JFXButton negativeButton = new JFXButton("No");

        AlertManager.showMaterialDialog(root, contentRoot,
                Arrays.asList(positiveButton, negativeButton),
                "Are you sure?",
                "You can not change this information afterwards.");
    }

}
