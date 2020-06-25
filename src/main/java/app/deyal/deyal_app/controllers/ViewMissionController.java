package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.data.Mission;
import app.deyal.deyal_app.data.MissionEvent;
import app.deyal.deyal_app.data.User;
import app.deyal.deyal_app.data.events.*;
import app.deyal.deyal_app.managers.DataManager;
import app.deyal.deyal_app.managers.StageManager;
import app.deyal.deyal_app.repository.Auth;
import app.deyal.deyal_app.repository.MissionEventClient;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class ViewMissionController {

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
    private User creator;
    private User contractor;
    private final MissionEvent lastEvent;

    public ViewMissionController() {
        lastEvent = DataManager.getInstance().tempMissionEventList.get(DataManager.getInstance().tempMissionEventList.size() - 1);
    }

    @FXML
    private void initialize() {
        mission = DataManager.getInstance().tempMission;
        if (mission != null) {
            missionTitleLabel.setText(mission.getTitle());
            shortDescriptionTextArea.setText(mission.getDescription());
            detailsTextArea.setText(mission.getLongDescription());
            levelLabel.setText(mission.getDifficultyAsString());
            if (Auth.searchUser(DataManager.getInstance().token, mission.getCreatorId())) {
                creator = DataManager.getInstance().tempUser;
                creatorLabel.setText(creator.getUserName());
            }
            if (Auth.searchUser(DataManager.getInstance().token, mission.getContractorId())) {
                contractor = DataManager.getInstance().tempUser;
                contractorLabel.setText(contractor.getUserName());
            }
            //add events
            ArrayList<MissionEvent> missionEvents = DataManager.getInstance().tempMissionEventList;
            StringBuilder events = new StringBuilder();
            for (MissionEvent missionEvent : missionEvents) {
                events.append(missionEvent.toString()).append("\n..............................\n");
            }
            eventsTextArea.setText(events.toString());

            //setting acceptButton text and visibility according to latest event type
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
    }

    @FXML
    public void handleAcceptButtonAction(ActionEvent actionEvent) {
        switch (acceptButton.getText()) {
            case "Request":
                requestButton();
                break;
            case "Assign":
                assignButton();
                break;
            case "Submit":
                submitButton();
                break;
            case "View Submission":
                viewSubmissionButton();
                break;
            case "Complete":
                completeButton();
                break;
        }
        acceptButton.setVisible(false);
    }

    private void requestButton() {
        StageManager.getInstance().requestMessageStage.showAndWait();
        MissionEvent missionEvent = new MissionEvent(mission.getId(), EventType.REQUEST);
        missionEvent.setRequest(new Request(DataManager.getInstance().userData.getId(),
                DataManager.getInstance().tempMessage));
        if (MissionEventClient.addEvent(DataManager.getInstance().token, missionEvent)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Successfully requested for mission");
            alert.setContentText("Your request is pending for client confirmation");
            alert.showAndWait();
            StageManager.getInstance().viewMissionStage.hide();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setHeaderText("Your request couldn't be sent");
            alert.setContentText("Please check your Internet connection.");
            alert.showAndWait();
        }
    }

    private void assignButton() {
        DataManager.getInstance().tempMission = mission;
        StageManager.getInstance().createViewRequestStage();
        StageManager.getInstance().viewRequestStage.showAndWait();
    }

    private void submitButton() {
        StageManager.getInstance().submitMissionStage.showAndWait();
        MissionEvent missionEvent = new MissionEvent(mission.getId(), EventType.SUBMIT);
        missionEvent.setSubmit(new Submit(DataManager.getInstance().tempMessage));
        if (MissionEventClient.addEvent(DataManager.getInstance().token, missionEvent)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Successfully submitted the mission");
            alert.setContentText("Your submission is pending for client approval");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setHeaderText("Your submission couldn't be sent");
            alert.setContentText("Please check your Internet connection.");
            alert.showAndWait();
        }
    }

    private void viewSubmissionButton() {
        DataManager.getInstance().tempMessage = lastEvent.getSubmit().getProofOfWork();
        StageManager.getInstance().createViewSubmissionStage();
        StageManager.getInstance().viewSubmissionStage.showAndWait();
        MissionEvent missionEvent;
        if (DataManager.getInstance().tempChoice) {
            missionEvent = new MissionEvent(mission.getId(), EventType.APPROVE);
            missionEvent.setApprove(new Approve(DataManager.getInstance().tempMessage));
        } else {
            missionEvent = new MissionEvent(mission.getId(), EventType.REJECT);
            missionEvent.setReject(new Reject(DataManager.getInstance().tempMessage));
        }

        if (MissionEventClient.addEvent(DataManager.getInstance().token, missionEvent)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Successfully judged submission");
            alert.setContentText("You have successfully judged the submission.\n" +
                    "(Reminder: after approving the reward must be paid to contractor within 2 days.)");
            alert.showAndWait();
            StageManager.getInstance().viewSubmissionStage.hide();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setHeaderText("Your judgement couldn't be sent");
            alert.setContentText("Please check your Internet connection.");
            alert.showAndWait();
        }
    }

    private void completeButton() {
        StageManager.getInstance().completeMissionStage.showAndWait();
        MissionEvent missionEvent = new MissionEvent(mission.getId(), EventType.REVIEW);
        missionEvent.setReview(new Review(DataManager.getInstance().tempChoice,
                DataManager.getInstance().tempMessage));
        if (MissionEventClient.addEvent(DataManager.getInstance().token, missionEvent)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Successfully completed this mission");
            alert.setContentText("Congratulations! You have completed this mission.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setHeaderText("Your submission couldn't be sent");
            alert.setContentText("Please check your Internet connection.");
            alert.showAndWait();
        }
    }

    @FXML
    public void handleCancelButtonAction(ActionEvent actionEvent) {
        StageManager.getInstance().viewMissionStage.hide();
    }

    @FXML
    public void showCreatorProfileAction(MouseEvent actionEvent) {
        DataManager.getInstance().tempUser = creator;
        StageManager.getInstance().createUserProfileStage();
        StageManager.getInstance().userProfileStage.showAndWait();
    }

    @FXML
    public void showContractorProfileAction(MouseEvent actionEvent) {
        DataManager.getInstance().tempUser = contractor;
        StageManager.getInstance().createUserProfileStage();
        StageManager.getInstance().userProfileStage.showAndWait();
    }

}
