package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.data.MissionEvent;
import app.deyal.deyal_app.data.User;
import app.deyal.deyal_app.data.events.Assign;
import app.deyal.deyal_app.data.events.EventType;
import app.deyal.deyal_app.managers.AlertManager;
import app.deyal.deyal_app.managers.DataManager;
import app.deyal.deyal_app.managers.StageManager;
import app.deyal.deyal_app.repository.AuthClient;
import app.deyal.deyal_app.repository.MissionEventClient;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.Collections;

public class ViewRequestController {

    @FXML
    public StackPane root;
    @FXML
    public BorderPane contentRoot;

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
        setData();
    }

    @FXML
    public void handleNextButtonAction(ActionEvent actionEvent) {
        if (currentIndex == requestEventsList.size() - 1)
            return;
        currentIndex++;
        setData();
    }

    private void setData() {
        missionEvent = requestEventsList.get(currentIndex);

        boolean result = AuthClient.searchUser(DataManager.getInstance().token, missionEvent.getRequest().getRequestBy());
        if (result) {
            requester = DataManager.getInstance().tempUser;
            nameLabel.setText(requester.getUserName());
        } else {
            AlertManager.showMaterialDialog(root, contentRoot,
                    null,
                    "Couldn't retrieve requester details!",
                    "Please check your internet connection.");
        }
        currentNumber.setText(String.valueOf(currentIndex + 1));
        timeLabel.setText(missionEvent.getEventTime().toString());
        messageTextArea.setText(missionEvent.getRequest().getRequestMessage());
    }

    @FXML
    public void handleAcceptButtonAction(ActionEvent actionEvent) {
        StageManager.getInstance().assignMessageStage.showAndWait();

        MissionEvent event = new MissionEvent(missionEvent.getMissionId(), EventType.ASSIGN, requester.getUserName());
        event.setAssign(new Assign(requester.getId(), DataManager.getInstance().tempMessage));
        DataManager.getInstance().tempMessage = "";

        boolean result = MissionEventClient.addEvent(DataManager.getInstance().token, event);
        if (result) {
            JFXButton okayButton = new JFXButton("Okay");
            DataManager.getInstance().tempChoice = true;
            okayButton.setOnMouseClicked(event1 -> StageManager.getInstance().viewRequestStage.hide());
            AlertManager.showMaterialDialog(root, contentRoot,
                    Collections.singletonList(okayButton),
                    "Successfully assigned contractor",
                    "Your mission is being completed by the contractor. To know current progress contact your contractor.");
        } else {
            AlertManager.showMaterialDialog(root, contentRoot,
                    null,
                    "Contractor assign failed!",
                    "Please check your internet connection and try again.");
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
