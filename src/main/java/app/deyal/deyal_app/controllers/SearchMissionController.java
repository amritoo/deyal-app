package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.data.Mission;
import app.deyal.deyal_app.managers.AlertManager;
import app.deyal.deyal_app.managers.DataManager;
import app.deyal.deyal_app.managers.StageManager;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Comparator;

public class SearchMissionController {

    @FXML
    public StackPane root;
    @FXML
    public VBox contentRoot;
    @FXML
    public TextField searchTextField;
    @FXML
    public TableView<Mission> searchMissionTableView;
    @FXML
    public TableColumn<Mission, String> missionTitleTableColumn;
    @FXML
    public TableColumn<Mission, String> missionLevelTableColumn;
    @FXML
    public TableColumn<Mission, String> missionCreatorTableColumn;
    @FXML
    public TableColumn<Mission, String> missionDescriptionTableColumn;


    @FXML
    public void handleSearchMissionButtonAction(ActionEvent actionEvent) {
        String title = searchTextField.getText();
        if (title == null || title.length() == 0) {
            AlertManager.showMaterialDialog(root, contentRoot,
                    null,
                    "No title given!",
                    "Please enter some texts to find all missions containing that text.");
            return;
        }

        // Getting an arraylist containing the inquired missions
        ArrayList<Mission> missionArrayList = DataManager.getInstance().searchMissionByTitle(title);
        if (missionArrayList.size() == 0) {
            AlertManager.showMaterialDialog(root, contentRoot,
                    null,
                    "No mission found!",
                    "A mission title containing the given text does not exist yet.");
        } else {
            AlertManager.showMaterialDialog(root, contentRoot,
                    null,
                    "Mission found",
                    "A total of " + missionArrayList.size() + " missions were found containing the given text in title.");
        }

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
        searchMissionTableView.getItems().setAll(missionArrayList);

        // Custom sort table
        searchMissionTableView.setSortPolicy(param -> {
            final ObservableList<Mission> itemsList = searchMissionTableView.getItems();
            if (itemsList == null || itemsList.isEmpty()) {
                return true;
            }
            final ArrayList<TableColumn<Mission, ?>> columns = new ArrayList<>(searchMissionTableView.getSortOrder());
            if (columns.isEmpty()) {
                return true;
            }
            FXCollections.sort(itemsList, (o1, o2) -> {
                for (TableColumn<Mission, ?> col : columns) {
                    if (col.getSortType() == null || !col.isSortable()) {
                        continue;
                    }

                    Object value1 = col.getCellData(o1);
                    Object value2 = col.getCellData(o2);
                    if (missionLevelTableColumn.equals(col)) {
                        value1 = o1.getDifficulty();
                        value2 = o2.getDifficulty();
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

        // Selecting a mission from tableview
        searchMissionTableView.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                int index = searchMissionTableView.getSelectionModel().getSelectedIndex();
                DataManager.getInstance().tempMission = searchMissionTableView.getItems().get(index);
                StageManager.getInstance().createViewMissionStage();
                StageManager.getInstance().viewMissionStage.showAndWait();
            }
        });
    }

    @FXML
    public void handleCancelButtonAction(ActionEvent actionEvent) {
        StageManager.getInstance().searchMissionStage.hide();
    }

}
