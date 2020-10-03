package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.data.Constants;
import app.deyal.deyal_app.data.Mission;
import app.deyal.deyal_app.data.MissionEvent;
import app.deyal.deyal_app.data.events.*;
import app.deyal.deyal_app.managers.AlertManager;
import app.deyal.deyal_app.managers.DataManager;
import app.deyal.deyal_app.managers.StageManager;
import app.deyal.deyal_app.repository.Auth;
import app.deyal.deyal_app.repository.MissionEventClient;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;

public class ViewMissionController {

    @FXML
    public StackPane root;
    @FXML
    public VBox contentRoot;
    @FXML
    public Label missionTitleLabel;
    @FXML
    public JFXTextArea shortDescriptionTextArea;
    @FXML
    public JFXTextArea detailsTextArea;
    @FXML
    public Label levelLabel;
    @FXML
    public Label creatorLabel;
    @FXML
    public Label contractorLabel;
    @FXML
    public JFXTextArea eventsTextArea;
    @FXML
    public JFXButton acceptButton;

    private Mission mission;
    private final MissionEvent lastEvent;
    private final String currentUsername = DataManager.getInstance().userData.getUserName();

    public ViewMissionController() {
        lastEvent = DataManager.getInstance().tempMissionEventList.get(DataManager.getInstance().tempMissionEventList.size() - 1);
    }

    @FXML
    private void initialize() {
        mission = DataManager.getInstance().tempMission;
        if (mission == null) {
            return;
        }

        missionTitleLabel.setText(mission.getTitle());
        shortDescriptionTextArea.setText(mission.getDescription());
        detailsTextArea.setText(mission.getLongDescription());
        levelLabel.setText(mission.getDifficultyAsString());
        creatorLabel.setText(mission.getCreatorName());
        contractorLabel.setText(mission.getContractorName());

        // add events
        ArrayList<MissionEvent> missionEvents = DataManager.getInstance().tempMissionEventList;
        StringBuilder events = new StringBuilder();
        for (MissionEvent missionEvent : missionEvents) {
            events.append(missionEvent.toString()).append("\n..............................\n");
        }
        eventsTextArea.setText(events.toString());

        // setting acceptButton text and visibility according to latest event type
        switch (lastEvent.getEventType()) {
            case CREATE:
                if (DataManager.getInstance().userData.getId().equals(mission.getCreatorId()))
                    acceptButton.setVisible(false);
                else {
                    acceptButton.setText("Request");
                    acceptButton.setVisible(true);
                }
                break;
            case REQUEST:
                if (DataManager.getInstance().userData.getId().equals(mission.getCreatorId())) {
                    acceptButton.setText("Assign");
                } else {
                    acceptButton.setText("Request");
                }
                acceptButton.setVisible(true);
                break;
            case ASSIGN:
                if (DataManager.getInstance().userData.getId().equals(mission.getContractorId())) {
                    acceptButton.setText("Submit");
                    acceptButton.setVisible(true);
                } else
                    acceptButton.setVisible(false);
                break;
            case SUBMIT:
                if (DataManager.getInstance().userData.getId().equals(mission.getCreatorId())) {
                    acceptButton.setText("View Submission");
                    acceptButton.setVisible(true);
                } else
                    acceptButton.setVisible(false);
                break;
            case APPROVE:
                if (DataManager.getInstance().userData.getId().equals(mission.getContractorId())) {
                    acceptButton.setText("Complete");
                    acceptButton.setVisible(true);
                } else
                    acceptButton.setVisible(false);
                break;
            default:
                acceptButton.setVisible(false);
                break;
        }
    }

    @FXML
    public void handleAcceptButtonAction(ActionEvent actionEvent) {
        switch (acceptButton.getText()) {
            case "Request" -> requestButton();
            case "Assign" -> assignButton();
            case "Submit" -> submitButton();
            case "View Submission" -> viewSubmissionButton();
            case "Complete" -> completeButton();
        }
        acceptButton.setVisible(false);
    }

    private void requestButton() {
        StageManager.getInstance().requestMessageStage.showAndWait();
        MissionEvent missionEvent = new MissionEvent(mission.getId(), EventType.REQUEST, currentUsername);
        missionEvent.setRequest(new Request(DataManager.getInstance().userData.getId(),
                DataManager.getInstance().tempMessage));

        boolean result = MissionEventClient.addEvent(DataManager.getInstance().token, missionEvent);
        if (result) {
            JFXButton okayButton = new JFXButton("Okay");
            okayButton.setOnMouseClicked(event -> StageManager.getInstance().viewMissionStage.hide());
            AlertManager.showMaterialDialog(root, contentRoot,
                    Collections.singletonList(okayButton),
                    "Requested to take mission",
                    "Your request is pending for client confirmation. Please wait till the client chooses someone to complete this mission. If you're chosen, you will be notified.");
        } else {
            AlertManager.showMaterialDialog(root, contentRoot,
                    null,
                    "Mission request failed!!",
                    "Please check your internet connection and try again.");
        }
    }

    private void assignButton() {
        DataManager.getInstance().tempMission = mission;
        StageManager.getInstance().viewRequestStage = StageManager.getInstance()
                .loadStage(Constants.VIEW_REQUEST_FXML, Constants.VIEW_REQUEST_TITLE);
        StageManager.getInstance().viewRequestStage.showAndWait();
    }

    private void submitButton() {
        StageManager.getInstance().submitMissionStage.showAndWait();

        MissionEvent missionEvent = new MissionEvent(mission.getId(), EventType.SUBMIT, currentUsername);
        missionEvent.setSubmit(new Submit(DataManager.getInstance().tempMessage));

        boolean result = MissionEventClient.addEvent(DataManager.getInstance().token, missionEvent);
        if (result) {
            AlertManager.showMaterialDialog(root, contentRoot,
                    null,
                    "Successfully submitted the mission",
                    "Your submission is pending for client approval");
        } else {
            AlertManager.showMaterialDialog(root, contentRoot,
                    null,
                    "Mission submission failed!",
                    "Please check your internet connection and try again.");
        }
    }

    private void viewSubmissionButton() {
        DataManager.getInstance().tempMessage = lastEvent.getSubmit().getProofOfWork();
        StageManager.getInstance().viewSubmissionStage = StageManager.getInstance()
                .loadStage(Constants.VIEW_SUBMISSION_FXML, Constants.VIEW_SUBMISSION_TITLE);
        StageManager.getInstance().viewSubmissionStage.showAndWait();

        MissionEvent missionEvent;
        if (DataManager.getInstance().tempChoice) {
            missionEvent = new MissionEvent(mission.getId(), EventType.APPROVE, currentUsername);
            missionEvent.setApprove(new Approve(DataManager.getInstance().tempMessage));
        } else {
            missionEvent = new MissionEvent(mission.getId(), EventType.REJECT, currentUsername);
            missionEvent.setReject(new Reject(DataManager.getInstance().tempMessage));
        }

        boolean result = MissionEventClient.addEvent(DataManager.getInstance().token, missionEvent);
        if (result) {
            JFXButton okayButton = new JFXButton("Okay");
            okayButton.setOnMouseClicked(event -> {
                StageManager.getInstance().viewSubmissionStage.hide();
            });
            AlertManager.showMaterialDialog(root, contentRoot,
                    Collections.singletonList(okayButton),
                    "Successfully judged submission",
                    "You have successfully judged the submission.\n" +
                            "(Reminder: after approving the reward must be paid to contractor within 2 working days.)");
        } else {
            AlertManager.showMaterialDialog(root, contentRoot,
                    null,
                    "Mission judge failed!",
                    "Please check your internet connection and try again.");
        }
    }

    private void completeButton() {
        StageManager.getInstance().completeMissionStage.showAndWait();

        MissionEvent missionEvent = new MissionEvent(mission.getId(), EventType.REVIEW, currentUsername);
        missionEvent.setReview(new Review(DataManager.getInstance().tempChoice,
                DataManager.getInstance().tempMessage));

        boolean result = MissionEventClient.addEvent(DataManager.getInstance().token, missionEvent);
        if (result) {
            AlertManager.showMaterialDialog(root, contentRoot,
                    null,
                    "Successfully completed mission",
                    "Congratulations! You have completed this mission.");
        } else {
            AlertManager.showMaterialDialog(root, contentRoot,
                    null,
                    "Mission complete failed!",
                    "Please check your internet connection and try again.");
        }
    }

    @FXML
    public void handleCancelButtonAction(ActionEvent actionEvent) {
        StageManager.getInstance().viewMissionStage.hide();
    }

    @FXML
    public void showCreatorProfileAction(MouseEvent actionEvent) {
        if (!Auth.searchUser(DataManager.getInstance().token, mission.getCreatorId())) {
            AlertManager.showMaterialDialog(root, contentRoot,
                    null,
                    "User data retrieve failed!",
                    "Please check your internet connection and try again.");
            return;
        }
        StageManager.getInstance().createUserProfileStage();
        StageManager.getInstance().userProfileStage.showAndWait();
    }

    @FXML
    public void showContractorProfileAction(MouseEvent actionEvent) {
        if (!Auth.searchUser(DataManager.getInstance().token, mission.getContractorId())) {
            AlertManager.showMaterialDialog(root, contentRoot,
                    null,
                    "User data retrieve failed!",
                    "Please check your internet connection and try again.");
            return;
        }
        StageManager.getInstance().createUserProfileStage();
        StageManager.getInstance().userProfileStage.showAndWait();
    }

}
