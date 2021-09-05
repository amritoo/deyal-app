package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.data.Constants;
import app.deyal.deyal_app.data.User;
import app.deyal.deyal_app.managers.AlertManager;
import app.deyal.deyal_app.managers.DataManager;
import app.deyal.deyal_app.managers.StageManager;
import app.deyal.deyal_app.repository.AuthClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class ProfileController {

    @FXML
    public Label userNameLabel;
    @FXML
    public Label fullNameLabel;
    @FXML
    public Label emailLabel;
    @FXML
    public Label phoneNumberLabel;
    @FXML
    public Label birthDateLabel;
    @FXML
    public Label houseLabel;
    @FXML
    public Label blockLabel;
    @FXML
    public Label districtLabel;
    @FXML
    public Label policeStationLabel;
    @FXML
    public Label postOfficeLabel;

    @FXML
    public Label accountAgeLabel;
    @FXML
    public Label ratingClientLabel;
    @FXML
    public Label ratingContractorLabel;
    @FXML
    public Label missionCreatedLabel;
    @FXML
    public Label missionCompletedLabel;
    @FXML
    public Label missionFailedLabel;
    @FXML
    public Label reputationLabel;
    @FXML
    public Button changePasswordButton;
    @FXML
    public Button editProfileButton;

    @FXML
    public void initialize() {
        this.loadProfile();
    }

    public void loadProfile() {
        if (!AuthClient.getUserData(DataManager.getInstance().getToken())) {
            // Shows user data retrieve failed
            AlertManager.showMaterialDialog(DataManager.getInstance().mainRoot, DataManager.getInstance().mainContentRoot,
                    null,
                    "User Profile retrieve failed!",
                    "Please check your internet connection.");
        } else {
            User user = DataManager.getInstance().userData;
            userNameLabel.setText(user.getUserName());
            fullNameLabel.setText(user.getFullName());
            emailLabel.setText(user.getEmail());
            phoneNumberLabel.setText(user.getPhoneNumber());
            birthDateLabel.setText(String.valueOf(LocalDate.ofEpochDay(user.getDateOfBirth()))); //converting user dateOfBirth to LocalDate
            if (user.getAddress() != null) {
                houseLabel.setText(user.getAddress().getHouseAddress());
                blockLabel.setText(user.getAddress().getBlockAddress());
                districtLabel.setText(user.getAddress().getDistrict());
                policeStationLabel.setText(user.getAddress().getPoliceStation());
                postOfficeLabel.setText(user.getAddress().getPostOffice());
            }
            accountAgeLabel.setText(this.calculateAccountAge(user.getRegistrationDate()));
            if (user.getMissionInfo() != null) {
                ratingClientLabel.setText(String.valueOf(user.getMissionInfo().getRatingAsClient()));
                ratingContractorLabel.setText(String.valueOf(user.getMissionInfo().getRatingAsContractor()));
                missionCreatedLabel.setText(String.valueOf(user.getMissionInfo().getCreated().size()));
                missionCompletedLabel.setText(String.valueOf(user.getMissionInfo().getCompleted().size()));
                missionFailedLabel.setText(String.valueOf(user.getMissionInfo().getFailed().size()));
            }
            reputationLabel.setText(String.valueOf(user.getReputation()));
        }
    }

    private String calculateAccountAge(Date date) {
        LocalDate ld = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period p = Period.between(ld, LocalDate.now());
        if (p.getYears() >= 2) {
            return p.getYears() + " years ago";
        } else if (p.getMonths() >= 2) {
            return p.getMonths() + " months ago";
        } else {
            return p.getDays() + (p.getDays() <= 1 ? " day ago" : " days ago");
        }
    }

    @FXML
    public void handleEditProfileButtonAction(ActionEvent actionEvent) {
        StageManager.getInstance().editProfileStage = StageManager.getInstance()
                .loadStage(Constants.EDIT_PROFILE_FXML, Constants.EDIT_PROFILE_TITLE);
        StageManager.getInstance().editProfileStage.showAndWait();
        this.loadProfile();
    }

    @FXML
    public void handleChangePasswordButtonAction(ActionEvent actionEvent) {
        StageManager.getInstance().changePasswordStage.showAndWait();
    }

}
