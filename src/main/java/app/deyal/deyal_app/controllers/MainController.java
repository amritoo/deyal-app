package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.managers.DataManager;
import app.deyal.deyal_app.managers.StageManager;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

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

    //TODO: handle auto refresh
    @FXML
    private void initialize() {
        if (!Auth.getUserNameList(DataManager.getInstance().token)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Failed");
            alert.setHeaderText("User names loading failed");
            alert.setContentText("Check your internet connection.");
            alert.showAndWait();
        }
        this.loadDashboard();
        this.loadMyMissions();
        this.loadProfile();
    }

    @FXML
    public void handleCreateMissionButtonAction(ActionEvent event) {
        StageManager.getInstance().createMissionStage.showAndWait();
        this.loadDashboard();
        this.loadMyMissions();
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
        DataManager.getInstance().clearAllData();
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
                    String name = DataManager.getInstance().getUserName(mission.getCreatorId());
                    if (name == null) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Failed");
                        alert.setHeaderText("Creator name retrieve failed");
                        alert.setContentText("Please check your Internet connection.");
                        alert.showAndWait();
                    }
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

            // custom sort My Mission table
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
                if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
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
    public void handleEditProfileButtonAction(ActionEvent event) {
        StageManager.getInstance().createEditProfileStage();
        StageManager.getInstance().editProfileStage.showAndWait();
        this.loadProfile();
    }

    @FXML
    public void handleChangePasswordButtonAction(ActionEvent event) {
        StageManager.getInstance().changePasswordStage.showAndWait();
    }
}
