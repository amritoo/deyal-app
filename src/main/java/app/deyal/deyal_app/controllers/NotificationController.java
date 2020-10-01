package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.data.Notification;
import app.deyal.deyal_app.managers.DataManager;
import app.deyal.deyal_app.managers.StageManager;
import app.deyal.deyal_app.repository.MissionEventClient;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class NotificationController {

    @FXML
    public TableView<Notification> notificationTableView;
//    @FXML
//    public TableColumn<Notification, Integer> indexNumberTableColumn;
    @FXML
    public TableColumn<Notification, String> notificationTableColumn;

    @FXML
    private void initialize() {
        //TODO show row number
        ArrayList<Notification> notificationArrayList = DataManager.getInstance().userData.getNotificationsInReverse();

        notificationTableColumn.setCellValueFactory(new PropertyValueFactory<Notification, String>("message"));
        notificationTableView.getItems().setAll(notificationArrayList);

        //selecting a mission from notification table view
        notificationTableView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                int index = notificationTableView.getSelectionModel().getSelectedIndex();
                String missionId = notificationTableView.getItems().get(index).getMissionId();
                DataManager.getInstance().tempMission = DataManager.getInstance().searchMissionById(missionId);
                if (DataManager.getInstance().tempMission != null) {
                    if (!MissionEventClient.getMissionEventList(DataManager.getInstance().token,
                            DataManager.getInstance().tempMission.getId())) {   //show mission event list retrieve failed
                        // TODO replace alert
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Failed");
                        alert.setHeaderText("Mission event list retrieve Failed!");
                        alert.setContentText("Please check your Internet connection.");
                        alert.showAndWait();
                    }
                    StageManager.getInstance().createViewMissionStage();
                    StageManager.getInstance().viewMissionStage.showAndWait();
                }
            }
        });
    }
}
