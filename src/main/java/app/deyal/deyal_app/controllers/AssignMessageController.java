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

public class AssignMessageController {

    @FXML
    public StackPane root;
    @FXML
    public VBox contentRoot;
    @FXML
    public JFXTextArea messageTextArea;

    @FXML
    public void handleSendButtonAction(ActionEvent actionEvent) {
        // Buttons to show in confirmation dialog
        JFXButton positiveButton = new JFXButton("Yes");
        positiveButton.setOnMouseClicked(event -> {
            DataManager.getInstance().tempMessage = messageTextArea.getText();
            messageTextArea.setText("");
            StageManager.getInstance().assignMessageStage.hide();
        });
        JFXButton negativeButton = new JFXButton("No");

        AlertManager.showMaterialDialog(root, contentRoot,
                Arrays.asList(positiveButton, negativeButton),
                "Are you sure?",
                "You can not change this message afterwards.");
    }

}
