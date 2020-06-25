package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.data.Mission;
import app.deyal.deyal_app.data.MissionDifficulty;
import app.deyal.deyal_app.managers.DataManager;
import app.deyal.deyal_app.managers.StageManager;
import app.deyal.deyal_app.repository.MissionClient;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

public class CreateMissionController {

    @FXML
    public JFXTextField titleTextField;
    @FXML
    public JFXTextArea shortDescriptionTextArea;
    @FXML
    public JFXTextArea detailsTextArea;
    @FXML
    public ComboBox<String> levelChoiceBox;
    @FXML
    public JFXButton createButton;
    @FXML
    public JFXButton cancelButton;

    public CreateMissionController() {
        //TODO: error handle
        levelChoiceBox = new JFXComboBox<>();
//        levelChoiceBox.setValue("Very easy");
//        levelChoiceBox.setValue("Easy");
//        levelChoiceBox.setValue("Medium");
//        levelChoiceBox.getItems().add("Very easy");
//        levelChoiceBox.getItems().add("Easy");
//        levelChoiceBox.getItems().add("Medium");
//        levelChoiceBox.getItems().add("Hard");
//        levelChoiceBox.getItems().add("Very hard");
    }

    @FXML
    public void initialize() {
    }

    @FXML
    public void handleCreateButtonAction(ActionEvent event) {
        Mission mission = new Mission();
        mission.setTitle(titleTextField.getText());
        mission.setDescription(shortDescriptionTextArea.getText());
        mission.setLongDescription(detailsTextArea.getText());
        mission.setDifficulty(this.getDifficulty());

        if (MissionClient.createMission(DataManager.getInstance().getToken(), mission)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Mission created");
            alert.setContentText("Mission created successfully.");
            alert.showAndWait();
        } else {    //show mission could not be created
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setHeaderText("Mission creation failed");
            alert.setContentText("Mission couldn't be created. Please check your internet connection.");
            alert.showAndWait();
        }
        StageManager.getInstance().createMissionStage.hide();
    }

    private MissionDifficulty getDifficulty() {
        switch (levelChoiceBox.getValue()) {
            case "Very easy":
                return MissionDifficulty.VERY_EASY;
            case "Easy":
                return MissionDifficulty.EASY;
            case "Medium":
                return MissionDifficulty.MEDIUM;
            case "Hard":
                return MissionDifficulty.HARD;
            case "Very hard":
                return MissionDifficulty.VERY_HARD;
            default:
                return MissionDifficulty.UNKNOWN;
        }
    }

    @FXML
    public void handleCancelButtonAction(ActionEvent event) {
        StageManager.getInstance().createMissionStage.hide();
    }
}
