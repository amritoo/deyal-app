package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.data.Mission;
import app.deyal.deyal_app.data.MissionDifficulty;
import app.deyal.deyal_app.managers.AlertManager;
import app.deyal.deyal_app.managers.DataManager;
import app.deyal.deyal_app.managers.StageManager;
import app.deyal.deyal_app.repository.MissionClient;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class CreateMissionController {

    @FXML
    public StackPane root;
    @FXML
    public VBox contentRoot;
    @FXML
    public JFXTextField titleTextField;
    @FXML
    public JFXTextArea shortDescriptionTextArea;
    @FXML
    public JFXTextArea detailsTextArea;
    @FXML
    public JFXComboBox<String> levelChoiceBox;
    @FXML
    public JFXButton createButton;
    @FXML
    public JFXButton cancelButton;

    @FXML
    public void initialize() {
        levelChoiceBox.getItems().add("Very easy");
        levelChoiceBox.getItems().add("Easy");
        levelChoiceBox.getItems().add("Medium");
        levelChoiceBox.getItems().add("Hard");
        levelChoiceBox.getItems().add("Very hard");
    }

    @FXML
    public void handleCreateButtonAction(ActionEvent actionEvent) {
        Mission mission = new Mission();
        mission.setTitle(titleTextField.getText());
        mission.setDescription(shortDescriptionTextArea.getText());
        mission.setLongDescription(detailsTextArea.getText());
        mission.setDifficulty(this.getDifficulty());

        boolean result = MissionClient.createMission(DataManager.getInstance().getToken(), mission);
        if (result) {
            AlertManager.showMaterialDialog(DataManager.getInstance().mainRoot, DataManager.getInstance().mainContentRoot,
                    null,
                    "Mission created",
                    "Mission created successfully.");
            StageManager.getInstance().createMissionStage.hide();
        } else {
            AlertManager.showMaterialDialog(root, contentRoot,
                    null,
                    "Mission creation failed!",
                    "Mission couldn't be created. Please check your internet connection and try again.");
        }
    }

    /**
     * Gets and converts the chosen difficulty to MissionDifficulty object.
     *
     * @return the corresponding {@link MissionDifficulty}
     */
    private MissionDifficulty getDifficulty() {
        return switch (levelChoiceBox.getValue()) {
            case "Very easy" -> MissionDifficulty.VERY_EASY;
            case "Easy" -> MissionDifficulty.EASY;
            case "Medium" -> MissionDifficulty.MEDIUM;
            case "Hard" -> MissionDifficulty.HARD;
            case "Very hard" -> MissionDifficulty.VERY_HARD;
            default -> MissionDifficulty.UNKNOWN;
        };
    }

    @FXML
    public void handleCancelButtonAction(ActionEvent actionEvent) {
        StageManager.getInstance().createMissionStage.hide();
    }

}
