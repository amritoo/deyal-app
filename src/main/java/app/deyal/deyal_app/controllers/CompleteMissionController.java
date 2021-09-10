package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.managers.AlertManager;
import app.deyal.deyal_app.managers.DataManager;
import app.deyal.deyal_app.managers.StageManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Arrays;

public class CompleteMissionController {

    @FXML
    public StackPane root;
    @FXML
    public VBox contentRoot;
    @FXML
    public JFXComboBox<String> gotRewardChoiceBox;
    @FXML
    public JFXTextArea messageTextArea;

    @FXML
    private void initialize() {
        gotRewardChoiceBox.getItems().add("Yes");
        gotRewardChoiceBox.getItems().add("No");
    }

    @FXML
    public void handleCompleteButtonAction(ActionEvent actionEvent) {
        if (gotRewardChoiceBox.getValue() == null) {
            AlertManager.showMaterialDialog(root, contentRoot,
                    null,
                    "Reward not chosen yet!",
                    "Please choose whether you got your reward or not.");
        } else {
            // Buttons to show in confirmation dialog
            JFXButton positiveButton = new JFXButton("I'm sure!");
            positiveButton.setOnMouseClicked(event -> {
                DataManager.getInstance().tempApprove = gotRewardChoiceBox.getValue().equals("Yes");
                DataManager.getInstance().tempMessage = messageTextArea.getText();
                messageTextArea.setText("");
                DataManager.getInstance().tempChoice = true;
                StageManager.getInstance().completeMissionStage.hide();
            });
            JFXButton negativeButton = new JFXButton("No");

            AlertManager.showMaterialDialog(root, contentRoot,
                    Arrays.asList(positiveButton, negativeButton),
                    "Are you sure?",
                    "You will complete this mission and can not change this afterwards.");
        }
    }

}
