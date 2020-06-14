package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.DataManager;
import app.deyal.deyal_app.StageManager;
import app.deyal.deyal_app.data.Mission;
import app.deyal.deyal_app.repository.Auth;
import app.deyal.deyal_app.repository.MissionEventClient;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Comparator;

public class SearchMissionController {

    @FXML
    public TextField titleTextField;
    @FXML
    public TableView<Mission> dashboardTableView;
    @FXML
    public TableColumn<Mission, String> missionTitleTableColumn;
    @FXML
    public TableColumn<Mission, Integer> missionLevelTableColumn;
    @FXML
    public TableColumn<Mission, String> missionCreatorTableColumn;
    @FXML
    public TableColumn<Mission, String> missionDescriptionTableColumn;


    @FXML
    public void handleSearchMissionButtonAction(ActionEvent actionEvent) {
        String title = titleTextField.getText();
        if (title == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No title given!");
            alert.setContentText("Please enter the mission title you're looking for.");
            alert.showAndWait();
        } else {
            ArrayList<Mission> missionArrayList = DataManager.getInstance().searchMissionByTitle(title);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Result");
            if (missionArrayList == null) {
                alert.setHeaderText("No mission found.");
                alert.setContentText("A mission having the given title is not created yet.");
            } else {
                alert.setHeaderText("Mission found.");
                alert.setContentText("A total of " + missionArrayList.size() + " missions were found having the given title.");
            }
            alert.showAndWait();

            missionTitleTableColumn.setCellValueFactory(new PropertyValueFactory<Mission, String>("title"));
            missionLevelTableColumn.setCellValueFactory(new PropertyValueFactory<Mission, Integer>("difficulty"));
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
            missionDescriptionTableColumn.setCellValueFactory(new PropertyValueFactory<Mission, String>("description"));
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
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    int index = dashboardTableView.getSelectionModel().getSelectedIndex();
                    DataManager.getInstance().tempMission = dashboardTableView.getItems().get(index);
                    if (!MissionEventClient.getMissionEventList(DataManager.getInstance().token,
                            DataManager.getInstance().tempMission.getId())) {   //show mission event list retrieve failed
                        Alert alert2 = new Alert(Alert.AlertType.WARNING);
                        alert2.setTitle("Failed");
                        alert2.setHeaderText("Mission event list retrieve Failed!");
                        alert2.setContentText("Please check your Internet connection.");
                        alert2.showAndWait();
                    }
                    StageManager.getInstance().createViewMissionStage();
                    StageManager.getInstance().viewMissionStage.showAndWait();
                }
            });
        }
    }

    @FXML
    public void handleCancelButtonAction(ActionEvent actionEvent) {
        StageManager.getInstance().searchMissionStage.hide();
    }

}
