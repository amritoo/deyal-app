package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.data.Mission;
import app.deyal.deyal_app.managers.AlertManager;
import app.deyal.deyal_app.managers.DataManager;
import app.deyal.deyal_app.managers.StageManager;
import app.deyal.deyal_app.repository.Auth;
import app.deyal.deyal_app.repository.MissionClient;
import app.deyal.deyal_app.repository.MissionEventClient;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class DashboardController {

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

    @FXML
    public void initialize() {
        this.loadDashboard();
    }

    public void loadDashboard() {
        if (!MissionClient.getMissionList(DataManager.getInstance().getToken())) {
            // show mission list retrieve failed
            AlertManager.showMaterialDialog(DataManager.getInstance().mainRoot, DataManager.getInstance().mainContentRoot,
                    null,
                    "Mission list retrieve failed!",
                    "Please check your internet connection and refresh.");
        } else {
            ArrayList<Mission> missionArrayList = DataManager.getInstance().allMissionsList;
            // TODO  check if works... but first update server code
//            List<Mission> missionLinkedList = sortMissionList(missionArrayList);

            missionTitleTableColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            missionLevelTableColumn.setCellValueFactory(new PropertyValueFactory<>("difficulty") {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Mission, String> param) {
                    Mission mission = param.getValue();
                    return new ReadOnlyObjectWrapper<>(mission.getDifficultyAsString());
                }
            });
            missionCreatorTableColumn.setCellValueFactory(new PropertyValueFactory<>("creatorName"));
            missionDescriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            dashboardTableView.getItems().setAll(missionArrayList);
//            dashboardTableView.getItems().setAll(missionLinkedList);

            // custom sort dashboard table
            dashboardTableView.setSortPolicy(param -> {
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

            // selecting a mission from dashboard
            dashboardTableView.setOnMouseClicked(event -> {
                if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                    int index = dashboardTableView.getSelectionModel().getSelectedIndex();
                    if (index < 0) return;
                    DataManager.getInstance().tempMission = dashboardTableView.getItems().get(index);
                    String missionId = DataManager.getInstance().tempMission.getId();
                    if (!MissionEventClient.getMissionEventList(DataManager.getInstance().token, missionId)) {
                        // show mission event list retrieve failed
                        AlertManager.showMaterialDialog(DataManager.getInstance().mainRoot, DataManager.getInstance().mainContentRoot,
                                null,
                                "Mission event list retrieve failed!",
                                "There was an unknown error. Please check your internet connection and try again.");
                    }
                    Auth.getUserName(DataManager.getInstance().token, DataManager.getInstance().tempMission.getCreatorId());
                    Auth.getUserName(DataManager.getInstance().token, DataManager.getInstance().tempMission.getContractorId());
                    StageManager.getInstance().createViewMissionStage();
                    StageManager.getInstance().viewMissionStage.showAndWait();
                }
            });
        }
    }

//    private List<Mission> sortMissionList(List<Mission> missionList) {
//        LinkedList<Mission> notAssigned = new LinkedList<>();
//        LinkedList<Mission> assigned = new LinkedList<>();
//        for (Mission mission : missionList) {
//            if (mission.isAssigned())
//                assigned.add(mission);
//            else
//                notAssigned.add(mission);
//        }
//        notAssigned.addAll(assigned);
//        return notAssigned;
//    }

}
