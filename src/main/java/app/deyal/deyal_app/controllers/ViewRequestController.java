package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.DataManager;
import app.deyal.deyal_app.StageManager;
import app.deyal.deyal_app.data.MissionEvent;
import app.deyal.deyal_app.data.User;
import app.deyal.deyal_app.data.events.Assign;
import app.deyal.deyal_app.data.events.EventType;
import app.deyal.deyal_app.repository.Auth;
import app.deyal.deyal_app.repository.MissionEventClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class ViewRequestController {

    @FXML
    public Label currentNumber;
    @FXML
    public Label totalNumber;
    @FXML
    public Label totalNumber2;

    @FXML
    public Label nameLabel;
    @FXML
    public Label timeLabel;
    @FXML
    public TextArea messageTextArea;

    private ArrayList<MissionEvent> requestEventsList;
    private int currentIndex;
    private User requester;
    private MissionEvent missionEvent;

    @FXML
    private void initialize() {
        requestEventsList = DataManager.getInstance().getRequestEvents();
        totalNumber.setText(String.valueOf(requestEventsList.size()));
        totalNumber2.setText(String.valueOf(requestEventsList.size()));
        currentIndex = 1;
        handlePreviousButtonAction();
    }

    @FXML
    public void handlePreviousButtonAction() {
        if (currentIndex == 0)
            return;
        currentIndex--;
        missionEvent = requestEventsList.get(currentIndex);
        if (Auth.searchUser(DataManager.getInstance().token, missionEvent.getRequest().getRequestBy())) {
            requester = DataManager.getInstance().tempUser;
            nameLabel.setText(requester.getUserName());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setHeaderText("Couldn't retrieve requester details!");
            alert.setContentText("Please check your Internet connection.");
            alert.showAndWait();
        }
        currentNumber.setText(String.valueOf(currentIndex + 1));
        timeLabel.setText(missionEvent.getEventTime().toString());
        messageTextArea.setText(missionEvent.getRequest().getRequestMessage());
    }

    @FXML
    public void handleNextButtonAction(ActionEvent actionEvent) {
        if (currentIndex == requestEventsList.size() - 1)
            return;
        currentIndex++;
        missionEvent = requestEventsList.get(currentIndex);
        if (Auth.searchUser(DataManager.getInstance().token, missionEvent.getRequest().getRequestBy())) {
            requester = DataManager.getInstance().tempUser;
            nameLabel.setText(requester.getUserName());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setHeaderText("Couldn't retrieve requester details!");
            alert.setContentText("Please check your Internet connection.");
            alert.showAndWait();
        }
        currentNumber.setText(String.valueOf(currentIndex + 1));
        timeLabel.setText(missionEvent.getEventTime().toString());
        messageTextArea.setText(missionEvent.getRequest().getRequestMessage());
    }

    @FXML
    public void handleAcceptButtonAction(ActionEvent actionEvent) {
        StageManager.getInstance().assignMessageStage.showAndWait();
        MissionEvent event = new MissionEvent(missionEvent.getMissionId(), EventType.ASSIGN);
        event.setAssign(new Assign(requester.getId(), DataManager.getInstance().tempMessage));
        if (MissionEventClient.addEvent(DataManager.getInstance().token, event)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Successfully Assigned someone to complete your mission");
            alert.setContentText("Your mission is being completed by the contractor");
            alert.showAndWait();
            StageManager.getInstance().viewRequestStage.hide();
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
        StageManager.getInstance().viewRequestStage.hide();
    }

    @FXML
    public void showRequesterProfile(MouseEvent mouseEvent) {
        DataManager.getInstance().tempUser = requester;
        StageManager.getInstance().createUserProfileStage();
        StageManager.getInstance().userProfileStage.showAndWait();
    }
}
