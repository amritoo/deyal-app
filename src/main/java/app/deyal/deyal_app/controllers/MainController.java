package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.DataManager;
import app.deyal.deyal_app.StageManager;
import app.deyal.deyal_app.data.Address;
import app.deyal.deyal_app.data.Mission;
import app.deyal.deyal_app.data.User;
import app.deyal.deyal_app.repository.Auth;
import app.deyal.deyal_app.repository.MissionClient;
import app.deyal.deyal_app.repository.MissionEventClient;
import app.deyal.deyal_app.repository.PreferenceSave;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class MainController {
    //Dashboard
    @FXML
    public TableView<Mission> dashboardTableView;
    @FXML
    public TableColumn<Mission, String> missionTitleTableColumn;
    @FXML
    public TableColumn<Mission, String> missionLevelTableColumn;
    @FXML
    public TableColumn<Mission, String> missionCreatorTableColumn;
    @FXML
    public TableColumn<Mission, String> missionDescriptionTableColumn;

    //My Missions
    @FXML
    public TableView<Mission> myMissionTableView;
    @FXML
    public TableColumn<Mission, String> mmMissionTitleTableColumn;
    @FXML
    public TableColumn<Mission, String> mmMissionStatusTableColumn;
    @FXML
    public TableColumn<Mission, String> mmMissionLevelTableColumn;
    @FXML
    public TableColumn<Mission, String> mmMissionDescriptionTableColumn;

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
    public Button changePasswordButton;
    @FXML
    public Button editProfileButton;

    @FXML
    private void initialize() {
        this.loadDashboard();
        this.loadMyMissions();
        this.loadProfile();
    }

    @FXML
    public void handleCreateMissionButtonAction(ActionEvent event) {
        StageManager.getInstance().createMissionStage.showAndWait();
    }

    @FXML
    public void handleSearchMissionButtonAction(ActionEvent event) {
        StageManager.getInstance().searchMissionStage.showAndWait();
    }

    @FXML
    public void handleRefreshButtonAction(ActionEvent event) {
        initialize();
    }

    @FXML
    public void handleNotificationButtonAction(ActionEvent actionEvent) {
        StageManager.getInstance().createNotificationStage();
        StageManager.getInstance().notificationStage.showAndWait();
    }

    @FXML
    public void handleLogoutButtonAction(ActionEvent event) {   //remove preference token
        PreferenceSave.getInstance().setToken(null);
        StageManager.getInstance().mainStage.hide();
        StageManager.getInstance().loginStage.show();
    }

    public void loadDashboard() {
        if (!MissionClient.getMissionList(DataManager.getInstance().getToken())) {  //show mission list retrieve failed
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Failed");
            alert.setHeaderText("Mission list retrieve Failed!");
            alert.setContentText("Please check your Internet connection.");
            alert.showAndWait();
        } else {
            ArrayList<Mission> missionArrayList = DataManager.getInstance().allMissionsList;

            missionTitleTableColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            missionLevelTableColumn.setCellValueFactory(new PropertyValueFactory<>("difficulty") {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Mission, String> param) {
                    Mission mission = param.getValue();
                    return new ReadOnlyObjectWrapper<>(mission.getDifficultyAsString());
                }
            });
            missionCreatorTableColumn.setCellValueFactory(new PropertyValueFactory<>("creatorId") {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Mission, String> param) {
                    Mission mission = param.getValue();
                    if (!Auth.searchUser(DataManager.getInstance().token, mission.getCreatorId())) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Failed");
                        alert.setHeaderText("creator name retrieve failed");
                        alert.setContentText("Please check your Internet connection.");
                        alert.showAndWait();
                    }
                    String name = DataManager.getInstance().tempUser.getUserName();
                    return new ReadOnlyObjectWrapper<>(name);
                }
            });
            missionDescriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            dashboardTableView.getItems().setAll(missionArrayList);

            // custom sort dashboard table
            dashboardTableView.setSortPolicy(tv -> {
                final ObservableList<Mission> itemsList = dashboardTableView.getItems();
                if (itemsList == null || itemsList.isEmpty()) {
                    return true;
                }
                final ArrayList<TableColumn<Mission, ?>> columns = new ArrayList<>(dashboardTableView.getSortOrder());
                if (columns.isEmpty()) {
                    return true;
                }
                FXCollections.sort(itemsList, (a, b) -> {
                    for (TableColumn<Mission, ?> col : columns) {
                        if (col.getSortType() == null || !col.isSortable()) {
                            continue;
                        }

                        Object value1 = col.getCellData(a);
                        Object value2 = col.getCellData(b);
                        if (missionLevelTableColumn.equals(col)) {
                            value1 = a.getDifficulty();
                            value2 = b.getDifficulty();
                        }

                        @SuppressWarnings("unchecked") final Comparator<Object> c = (Comparator<Object>) col.getComparator();
                        final int result = TableColumn.SortType.ASCENDING.equals(col.getSortType()) ? c.compare(value1, value2)
                                : c.compare(value2, value1);
                        if (result != 0) {
                            return result;
                        }
                    }
                    return 0;
                });
                return true;
            });

            //selecting a mission from dashboard
            dashboardTableView.setOnMouseClicked((MouseEvent event) -> {
                if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                    int index = dashboardTableView.getSelectionModel().getSelectedIndex();
                    if (index < 0) return;
                    DataManager.getInstance().tempMission = dashboardTableView.getItems().get(index);
                    if (!MissionEventClient.getMissionEventList(DataManager.getInstance().token,
                            DataManager.getInstance().tempMission.getId())) {   //show mission event list retrieve failed
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Failed");
                        alert.setHeaderText("Mission event list retrieve Failed!");
                        alert.setContentText("Please check your Internet connection.");
                        alert.showAndWait();
                    }
                    StageManager.getInstance().createViewMissionStage();
                    StageManager.getInstance().viewMissionStage.showAndWait();
                }
            });
        }
    }

    public void loadMyMissions() {
        if (!MissionClient.getMyMissionList(DataManager.getInstance().getToken())) {    //show user's mission data retrieve failed
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Failed");
            alert.setHeaderText("My mission list retrieve Failed!");
            alert.setContentText("Please check your Internet connection.");
            alert.showAndWait();
        } else {
            ArrayList<Mission> missionArrayList = DataManager.getInstance().myMissionsList;

            mmMissionTitleTableColumn.setCellValueFactory(new PropertyValueFactory<Mission, String>("title"));
            mmMissionLevelTableColumn.setCellValueFactory(new PropertyValueFactory<Mission, String>("difficulty") {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Mission, String> param) {
                    Mission mission = param.getValue();
                    return new ReadOnlyObjectWrapper<>(mission.getDifficultyAsString());
                }
            });
            mmMissionDescriptionTableColumn.setCellValueFactory(new PropertyValueFactory<Mission, String>("description"));
            mmMissionStatusTableColumn.setCellValueFactory(new PropertyValueFactory<>("id") {   //set status (completed, created, failed, ongoing) of my missions
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Mission, String> param) {
                    Mission mission = param.getValue();
                    String status = mission.findStatus(mission.getId());
                    return new ReadOnlyObjectWrapper<>(status);
                }
            });
            myMissionTableView.getItems().setAll(missionArrayList);

            // custom sort dashboard table
            myMissionTableView.setSortPolicy(tv -> {
                final ObservableList<Mission> itemsList = myMissionTableView.getItems();
                if (itemsList == null || itemsList.isEmpty()) {
                    return true;
                }
                final ArrayList<TableColumn<Mission, ?>> columns = new ArrayList<>(myMissionTableView.getSortOrder());
                if (columns.isEmpty()) {
                    return true;
                }
                FXCollections.sort(itemsList, (a, b) -> {
                    for (TableColumn<Mission, ?> col : columns) {
                        if (col.getSortType() == null || !col.isSortable()) {
                            continue;
                        }

                        Object value1 = col.getCellData(a);
                        Object value2 = col.getCellData(b);
                        if (mmMissionLevelTableColumn.equals(col)) {
                            value1 = a.getDifficulty();
                            value2 = b.getDifficulty();
                        }

                        @SuppressWarnings("unchecked") final Comparator<Object> c = (Comparator<Object>) col.getComparator();
                        final int result = TableColumn.SortType.ASCENDING.equals(col.getSortType()) ? c.compare(value1, value2)
                                : c.compare(value2, value1);
                        if (result != 0) {
                            return result;
                        }
                    }
                    return 0;
                });
                return true;
            });

            //selecting a mission from dashboard
            myMissionTableView.setOnMouseClicked((MouseEvent event) -> {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    int index = myMissionTableView.getSelectionModel().getSelectedIndex();
                    DataManager.getInstance().tempMission = myMissionTableView.getItems().get(index); //sometime shows index out of bound error
                    if (!MissionEventClient.getMissionEventList(DataManager.getInstance().token,
                            DataManager.getInstance().tempMission.getId())) {   //show mission event list retrieve failed
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Failed");
                        alert.setHeaderText("Mission event list retrieve Failed!");
                        alert.setContentText("Please check your Internet connection.");
                        alert.showAndWait();
                    }
                    StageManager.getInstance().createViewMissionStage();
                    StageManager.getInstance().viewMissionStage.showAndWait();
                }
            });
        }
    }

    public void loadProfile() {
        if (!Auth.getUserData(DataManager.getInstance().getToken())) {  //show user data retrieve failed
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Failed");
            alert.setHeaderText("User Profile retrieve Failed!");
            alert.setContentText("Please check your Internet connection.");
            alert.showAndWait();
        } else {
            User user = DataManager.getInstance().userData;
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

    private void changeEditOption(boolean show) {
        userNameTextField.setEditable(show);
        fullNameTextField.setEditable(show);
        phoneNumberTextField.setEditable(show);
        birthDatePicker.setVisible(show);
        houseAddressTextField.setEditable(show);
        blockAddressTextField.setEditable(show);
        districtTextField.setEditable(show);
        policeStationTextField.setEditable(show);
        postOfficeTextField.setEditable(show);
    }

    @FXML
    public void handleEditProfileButtonAction(ActionEvent event) {
        if (editProfileButton.getText().equals("Edit Profile")) {
            changeEditOption(true);
            editProfileButton.setText("Save");
        } else if (editProfileButton.getText().equals("Save")) {
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
            if (Auth.updateProfile(DataManager.getInstance().getToken(), user) &&
                    Auth.getUserData(DataManager.getInstance().getToken())) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("User Profile updated");
                alert.setContentText("Your profile has been updated successfully.");
                alert.showAndWait();
                loadProfile();
                changeEditOption(false);
                editProfileButton.setText("Edit Profile");
            } else {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Failed");
                alert.setHeaderText("User Profile update Failed!");
                alert.setContentText("Please check your Internet connection. Update failed for some reasons.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void handleChangePasswordButtonAction(ActionEvent event) {
        StageManager.getInstance().changePasswordStage.showAndWait();
    }
}
