package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.data.Notification;
import app.deyal.deyal_app.managers.DataManager;
import app.deyal.deyal_app.managers.StageManager;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class NotificationController {

    @FXML
    public StackPane root;
    @FXML
    public AnchorPane contentRoot;
    @FXML
    public TableView<Notification> notificationTableView;
    @FXML
    public TableColumn<Notification, String> notificationTableColumn;

    @FXML
    private void initialize() {
        ArrayList<Notification> notificationArrayList = DataManager.getInstance().userData.getNotificationsInReverse();

        notificationTableColumn.setCellValueFactory(new PropertyValueFactory<>("message"));
        notificationTableColumn.setCellFactory(param -> {
            TableCell<Notification, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(notificationTableColumn.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        notificationTableView.getItems().setAll(notificationArrayList);

        // Selecting a mission from notification table view
        notificationTableView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                int index = notificationTableView.getSelectionModel().getSelectedIndex();
                if (index < 0 || index >= notificationArrayList.size())
                    return;
                String missionId = notificationTableView.getItems().get(index).getMissionId();
                DataManager.getInstance().tempMission = DataManager.getInstance().searchMissionById(missionId);
                if (DataManager.getInstance().tempMission != null) {
                    StageManager.getInstance().createViewMissionStage();
                    StageManager.getInstance().viewMissionStage.showAndWait();
                }
            }
        });
    }
}
