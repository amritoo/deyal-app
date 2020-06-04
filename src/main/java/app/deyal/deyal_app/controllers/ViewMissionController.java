package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.StageManager;
import app.deyal.deyal_app.data.Mission;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ViewMissionController {

    @FXML
    public Label missionTitleLabel;
    @FXML
    public TextArea shortDescriptionTextArea;
    @FXML
    public TextArea detailsTextArea;
    @FXML
    public TextField levelTextField;
    @FXML
    public TextField creatorTextField;
    @FXML
    public TextField contractorTextField;
    @FXML
    public TextArea eventsTextArea;
    @FXML
    public Button acceptButton;
    @FXML
    public Button cancelButton;

    @FXML
    private void initialize() {
        Mission mission = StageManager.getInstance().mission;
        if(mission != null) {
            missionTitleLabel.setText(mission.getTitle());
            shortDescriptionTextArea.setText(mission.getDescription());
            detailsTextArea.setText(mission.getLongDescription());
            levelTextField.setText(String.valueOf(mission.getDifficulty()));
            creatorTextField.setText(mission.getCreatorId());
            contractorTextField.setText(mission.getContractorId());
            //TODO: add events
        }
    }

    @FXML
    public void handleAcceptButtonAction(ActionEvent actionEvent) {
        //TODO: mission request event api
    }

    @FXML
    public void handleCancelButtonAction(ActionEvent actionEvent) {
        StageManager.getInstance().viewMissionStage.hide();
    }

}
