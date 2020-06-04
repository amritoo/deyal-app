package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.StageManager;
import app.deyal.deyal_app.data.Mission;
import app.deyal.deyal_app.repository.MissionClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CreateMissionController {

    @FXML
    public TextField titleTextField;
    @FXML
    public TextArea shortDescriptionTextArea;
    @FXML
    public TextArea detailsTextArea;
    @FXML
    public ChoiceBox<Integer> levelChoiceBox;
    @FXML
    public Button createButton;
    @FXML
    public Button cancelButton;

    @FXML
    public void initialize() {
        levelChoiceBox.getItems().add(1);
        levelChoiceBox.getItems().add(2);
        levelChoiceBox.getItems().add(3);
        levelChoiceBox.getItems().add(4);
        levelChoiceBox.getItems().add(5);
    }

    @FXML
    public void handleCreateButtonAction(ActionEvent event) {
        Mission mission = new Mission();
        mission.setTitle(titleTextField.getText());
        mission.setDescription(shortDescriptionTextArea.getText());
        mission.setLongDescription(detailsTextArea.getText());
        if (levelChoiceBox.getValue() != null)
            mission.setDifficulty(levelChoiceBox.getValue());

        if (MissionClient.createMission(StageManager.getInstance().getToken(), mission)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Mission created");
            alert.setContentText("Mission created successfully.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Mission couldn't be created");
            alert.setContentText("Mission creation failed.");
            alert.showAndWait();
        }

    }

    @FXML
    public void handleCancelButtonAction(ActionEvent event) {
        StageManager.getInstance().createMissionStage.hide();
    }
}
