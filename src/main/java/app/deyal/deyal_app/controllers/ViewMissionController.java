package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.data.Constants;
import app.deyal.deyal_app.data.Mission;
import app.deyal.deyal_app.data.MissionEvent;
import app.deyal.deyal_app.data.events.*;
import app.deyal.deyal_app.managers.AlertManager;
import app.deyal.deyal_app.managers.DataManager;
import app.deyal.deyal_app.managers.StageManager;
import app.deyal.deyal_app.repository.AuthClient;
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

public class ViewMissionController {

    private final String currentUsername = DataManager.getInstance().userData.getUserName();

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
    public Label statusLabel;
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

    private MissionEvent lastEvent;
    private Mission mission;

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

        if (MissionEventClient.getMissionEventList(DataManager.getInstance().token, mission.getId())) {
            lastEvent = DataManager.getInstance().tempMissionEventList.get(DataManager.getInstance().tempMissionEventList.size() - 1);
            addEvents();
            addButtonAndStatusText();
        } else {
            DataManager.getInstance().tempMissionEventList = null;
            lastEvent = null;
            AlertManager.showMaterialDialog(this.root, this.contentRoot,
                    null,
                    "Mission event list retrieve failed!",
                    "There was an unknown error. Please check your internet connection and try again.");
        }
    }

    private void addEvents() {
        // Add events
        ArrayList<MissionEvent> missionEvents = DataManager.getInstance().tempMissionEventList;
        StringBuilder events = new StringBuilder();
        for (MissionEvent missionEvent : missionEvents) {
            events.append(missionEvent.toString()).append("\n..............................\n");
        }
        eventsTextArea.setText(events.toString());
    }

    private void addButtonAndStatusText() {
        // Setting acceptButton text and visibility according to the latest event type
        switch (lastEvent.getEventType()) {
            case CREATE -> {    // Shows Request button to everyone, except creator
                if (DataManager.getInstance().userData.getId().equals(mission.getCreatorId()))
                    acceptButton.setVisible(false);
                else {
                    acceptButton.setText("Request");
                    acceptButton.setVisible(true);
                }
                statusLabel.setText("Ongoing");
            }
            case REQUEST -> {   // Shows Assign button for creator, and Request button for others
                if (DataManager.getInstance().userData.getId().equals(mission.getCreatorId())) {
                    acceptButton.setText("Assign");
                } else {
                    acceptButton.setText("Request");
                }
                acceptButton.setVisible(true);
                statusLabel.setText("Ongoing");
            }
            case ASSIGN -> {    // Shows Submit button only for contractor
                if (DataManager.getInstance().userData.getId().equals(mission.getContractorId())) {
                    acceptButton.setText("Submit");
                    acceptButton.setVisible(true);
                } else
                    acceptButton.setVisible(false);
                statusLabel.setText("Assigned");
            }
            case SUBMIT -> {    // Shows View Submission button only for creator
                if (DataManager.getInstance().userData.getId().equals(mission.getCreatorId())) {
                    acceptButton.setText("View Submission");
                    acceptButton.setVisible(true);
                } else
                    acceptButton.setVisible(false);
                statusLabel.setText("Pending Approval");
            }
            case APPROVE -> {   // Shows Complete button only for contractor
                if (DataManager.getInstance().userData.getId().equals(mission.getContractorId())) {
                    acceptButton.setText("Complete");
                    acceptButton.setVisible(true);
                } else
                    acceptButton.setVisible(false);
                statusLabel.setText("Approved");
            }
            case REJECT -> {
                acceptButton.setVisible(false);
                statusLabel.setText("Failed");
            }
            case REVIEW -> {
                acceptButton.setVisible(false);
                statusLabel.setText("Completed");
            }
            default -> acceptButton.setVisible(false);
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

    /**
     * Creates a new mission request event
     */
    private void requestButton() {
        DataManager.getInstance().tempChoice = false;
        StageManager.getInstance().requestMessageStage.showAndWait();
        if (!DataManager.getInstance().tempChoice) return;

        MissionEvent missionEvent = new MissionEvent(mission.getId(), EventType.REQUEST, currentUsername);
        missionEvent.setRequest(new Request(
                DataManager.getInstance().userData.getId(),
                DataManager.getInstance().tempMessage));
        DataManager.getInstance().tempMessage = "";

        boolean result = MissionEventClient.addEvent(DataManager.getInstance().token, missionEvent);
        if (result) {
            AlertManager.showMaterialDialog(root, contentRoot,
                    null,
                    "Requested to take mission",
                    "Your request is pending for client confirmation. Please wait till the client chooses someone to complete this mission. If you're chosen, you will be notified.");
            initialize();
        } else {
            AlertManager.showMaterialDialog(root, contentRoot,
                    null,
                    "Mission request failed!!",
                    "Please check your internet connection and try again.");
        }
    }

    /**
     * Shows view request stage and after assigning hides this stage
     */
    private void assignButton() {
        DataManager.getInstance().tempMission = mission;
        DataManager.getInstance().tempChoice = false;
        StageManager.getInstance().viewRequestStage = StageManager.getInstance()
                .loadStageUndecorated(Constants.VIEW_REQUEST_FXML, Constants.VIEW_REQUEST_TITLE);
        StageManager.getInstance().viewRequestStage.showAndWait();

        if (DataManager.getInstance().tempChoice) {
            initialize();
        }
    }

    /**
     * Creates a new mission submit event
     */
    private void submitButton() {
        DataManager.getInstance().tempChoice = false;
        StageManager.getInstance().submitMissionStage.showAndWait();
        if (!DataManager.getInstance().tempChoice) return;

        MissionEvent missionEvent = new MissionEvent(mission.getId(), EventType.SUBMIT, currentUsername);
        missionEvent.setSubmit(new Submit(DataManager.getInstance().tempMessage));
        DataManager.getInstance().tempMessage = "";

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

    /**
     * Shows submission results and creates a new approve or reject event
     */
    private void viewSubmissionButton() {
        DataManager.getInstance().tempMessage = lastEvent.getSubmit().getProofOfWork();
        StageManager.getInstance().viewSubmissionStage = StageManager.getInstance()
                .loadStageUndecorated(Constants.VIEW_SUBMISSION_FXML, Constants.VIEW_SUBMISSION_TITLE);
        DataManager.getInstance().tempChoice = false;
        StageManager.getInstance().viewSubmissionStage.showAndWait();
        if (!DataManager.getInstance().tempChoice) return;

        MissionEvent missionEvent;
        if (DataManager.getInstance().tempApprove) {
            missionEvent = new MissionEvent(mission.getId(), EventType.APPROVE, currentUsername);
            missionEvent.setApprove(new Approve(DataManager.getInstance().tempMessage));
        } else {
            missionEvent = new MissionEvent(mission.getId(), EventType.REJECT, currentUsername);
            missionEvent.setReject(new Reject(DataManager.getInstance().tempMessage));
        }
        DataManager.getInstance().tempMessage = "";

        boolean result = MissionEventClient.addEvent(DataManager.getInstance().token, missionEvent);
        if (result) {
            AlertManager.showMaterialDialog(root, contentRoot,
                    null,
                    "Successfully judged submission",
                    "You have successfully judged the submission.\n" +
                            "(Reminder: after approving the reward must be paid to contractor within 2 working days.)");
            initialize();
        } else {
            AlertManager.showMaterialDialog(root, contentRoot,
                    null,
                    "Mission judge failed!",
                    "Please check your internet connection and try again.");
        }
    }

    /**
     * Creates a mission complete event
     */
    private void completeButton() {
        DataManager.getInstance().tempChoice = false;
        StageManager.getInstance().completeMissionStage.showAndWait();
        if (!DataManager.getInstance().tempChoice) return;

        MissionEvent missionEvent = new MissionEvent(mission.getId(), EventType.REVIEW, currentUsername);
        missionEvent.setReview(new Review(DataManager.getInstance().tempApprove, DataManager.getInstance().tempMessage));
        DataManager.getInstance().tempMessage = "";

        boolean result = MissionEventClient.addEvent(DataManager.getInstance().token, missionEvent);
        if (result) {
            AlertManager.showMaterialDialog(root, contentRoot,
                    null,
                    "Successfully completed mission",
                    "Congratulations! You have completed this mission.");
            initialize();
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
        if (!AuthClient.searchUser(DataManager.getInstance().token, mission.getCreatorId())) {
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
        if (!AuthClient.searchUser(DataManager.getInstance().token, mission.getContractorId())) {
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
