package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.StageManager;
import app.deyal.deyal_app.data.Address;
import app.deyal.deyal_app.data.Mission;
import app.deyal.deyal_app.data.User;
import app.deyal.deyal_app.repository.Auth;
import app.deyal.deyal_app.repository.MissionClient;
import app.deyal.deyal_app.repository.PreferenceSave;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;

public class MainController {

    //Dashboard
    @FXML
    public TableView<Mission> dashboardTableView;
    @FXML
    public TableColumn<Mission, String> missionTitleTableColumn;
    @FXML
    public TableColumn<Mission, Integer> missionLevelTableColumn;
    @FXML
    public TableColumn<Mission, String> missionContractorTableColumn;
    @FXML
    public TableColumn<Mission, String> missionDescriptionTableColumn;

    //My Missions
    @FXML
    public Button createMissionButton;

    //Profile
    @FXML
    public TextField userNameTextField;
    @FXML
    public TextField fullNameTextField;
    @FXML
    public TextField emailTextField;
    @FXML
    public DatePicker birthDatePicker;
    @FXML
    public TextField phoneNumberTextField;
    @FXML
    public ImageView avatarImageView;

    @FXML
    public TextField houseAddressTextField;
    @FXML
    public TextField blockAddressTextField;
    @FXML
    public TextField districtTextField;
    @FXML
    public TextField policeStationTextField;
    @FXML
    public TextField postOfficeTextField;
    @FXML
    public Label createTimeLabel;
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
    public Button updateProfileButton;
    @FXML
    public Button changePasswordButton;
    @FXML
    public Button editProfileButton;

    @FXML
    private void initialize() {
        this.loadDashboard();
        this.loadProfile();
        this.loadMyMissions();
    }

    @FXML
    public void handleEditProfileButtonAction(ActionEvent event) {
        editProfileButton.setVisible(false);
        updateProfileButton.setVisible(true);
        changeEditOption(true);
    }

    @FXML
    public void handleUpdateProfileButtonAction(ActionEvent event) {
        User user = new User();
        user.setUserName(userNameTextField.getText());
        user.setFullName(fullNameTextField.getText());
        user.setEmail(emailTextField.getText());
        user.setPhoneNumber(phoneNumberTextField.getText());
        if (birthDatePicker.getValue() != null)
            user.setDateOfBirth(birthDatePicker.getValue().toEpochDay()); //converting dateOfBirth to EpochDay
        user.setAddress(new Address());
        user.getAddress().setHouseAddress(houseAddressTextField.getText());
        user.getAddress().setBlockAddress(blockAddressTextField.getText());
        user.getAddress().setDistrict(districtTextField.getText());
        user.getAddress().setPoliceStation(policeStationTextField.getText());
        user.getAddress().setPostOffice(postOfficeTextField.getText());

        Alert alert;
        if (Auth.updateProfile(StageManager.getInstance().getToken(), user) && Auth.getUserData(StageManager.getInstance().getToken())) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("User Profile updated");
            alert.setContentText("Your profile has been updated successfully.");
            alert.showAndWait();
            updateProfileButton.setVisible(false);
            loadProfile();
            updateProfileButton.setVisible(false);
            editProfileButton.setVisible(true);
            changeEditOption(false);
        } else {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Failed");
            alert.setHeaderText("User Profile update Failed!");
            alert.setContentText("Please check your Internet connection. Update failed for some reasons.");
            alert.showAndWait();
        }
    }

    @FXML
    public void handleChangePasswordButtonAction(ActionEvent event) {
        StageManager.getInstance().changePasswordStage.show();
    }

    private void changeEditOption(boolean show) {
        userNameTextField.setEditable(show);
        fullNameTextField.setEditable(show);
        phoneNumberTextField.setEditable(show);
        birthDatePicker.setEditable(show);
        houseAddressTextField.setEditable(show);
        blockAddressTextField.setEditable(show);
        districtTextField.setEditable(show);
        policeStationTextField.setEditable(show);
        postOfficeTextField.setEditable(show);
    }

    public void loadProfile() {
        if (!Auth.getUserData(StageManager.getInstance().getToken())) {
            //show user data retrieve failed
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Failed");
            alert.setHeaderText("User Profile retrieve Failed!");
            alert.setContentText("Please check your Internet connection.");
            alert.showAndWait();
        } else {
            User user = StageManager.getInstance().user;
            userNameTextField.setText(user.getUserName());
            fullNameTextField.setText(user.getFullName());
            emailTextField.setText(user.getEmail());
            phoneNumberTextField.setText(user.getPhoneNumber());
            birthDatePicker.setValue(LocalDate.ofEpochDay(user.getDateOfBirth())); //converting user dateOfBirth to LocalDate
            if (user.getAddress() != null) {
                houseAddressTextField.setText(user.getAddress().getHouseAddress());
                blockAddressTextField.setText(user.getAddress().getBlockAddress());
                districtTextField.setText(user.getAddress().getDistrict());
                policeStationTextField.setText(user.getAddress().getPoliceStation());
                postOfficeTextField.setText(user.getAddress().getPostOffice());
            }
            createTimeLabel.setText(user.getRegistrationDate().toString());
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

    public void loadDashboard() {
        if (!MissionClient.getMissionList(StageManager.getInstance().getToken())) {
            //show user data retrieve failed
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Failed");
            alert.setHeaderText("Mission list retrieve Failed!");
            alert.setContentText("Please check your Internet connection.");
            alert.showAndWait();
        } else {
            ArrayList<Mission> missionArrayList = StageManager.getInstance().missionList;

            missionTitleTableColumn.setCellValueFactory(new PropertyValueFactory<Mission, String>("title"));
            missionLevelTableColumn.setCellValueFactory(new PropertyValueFactory<Mission, Integer>("difficulty"));
            missionContractorTableColumn.setCellValueFactory(new PropertyValueFactory<Mission, String>("creatorId"));
            missionDescriptionTableColumn.setCellValueFactory(new PropertyValueFactory<Mission, String>("description"));

            dashboardTableView.getItems().setAll(missionArrayList);

            //selecting a mission from dashboard
            dashboardTableView.setOnMouseClicked((MouseEvent event) -> {
                if(event.getButton().equals(MouseButton.PRIMARY)) {
                    int index = dashboardTableView.getSelectionModel().getSelectedIndex();
                    StageManager.getInstance().mission = dashboardTableView.getItems().get(index);
                    StageManager.getInstance().createViewMissionStage();
                    StageManager.getInstance().viewMissionStage.show();
                }
            });
        }
    }

    public void loadMyMissions() {
        //TODO: load users past mission list
    }

    @FXML
    public void handleCreateMissionButtonAction(ActionEvent event) {
        StageManager.getInstance().createMissionStage.show();
    }

    @FXML
    public void handleRefreshButtonAction(ActionEvent event) {
        initialize();
    }

    @FXML
    public void handleLogoutButtonAction(ActionEvent event) {
        //remove preference token
        PreferenceSave.getInstance().setToken(null);
        StageManager.getInstance().mainStage.hide();
        StageManager.getInstance().loginStage.show();
    }

}
