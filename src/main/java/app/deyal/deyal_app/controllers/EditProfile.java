package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.managers.DataManager;
import app.deyal.deyal_app.managers.StageManager;
import app.deyal.deyal_app.data.Address;
import app.deyal.deyal_app.data.User;
import app.deyal.deyal_app.repository.Auth;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class EditProfile {

    @FXML
    public TextField userNameTextField;
    @FXML
    public TextField fullNameTextField;
    @FXML
    public TextField phoneNumberTextField;
    @FXML
    public DatePicker birthDatePicker;
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
    public JFXTextField firstNameTextField;
    public JFXTextField lastNameTextField;

    @FXML
    private void initialize() {
        User user = DataManager.getInstance().userData;
        userNameTextField.setText(user.getUserName());
        fullNameTextField.setText(user.getFullName());
        phoneNumberTextField.setText(user.getPhoneNumber());
        birthDatePicker.setValue(LocalDate.ofEpochDay(user.getDateOfBirth())); //converting user dateOfBirth to LocalDate
        if (user.getAddress() != null) {
            houseAddressTextField.setText(user.getAddress().getHouseAddress());
            blockAddressTextField.setText(user.getAddress().getBlockAddress());
            districtTextField.setText(user.getAddress().getDistrict());
            policeStationTextField.setText(user.getAddress().getPoliceStation());
            postOfficeTextField.setText(user.getAddress().getPostOffice());
        }
    }

    @FXML
    public void handleSaveButtonAction(ActionEvent actionEvent) {
        User user = new User();
        user.setUserName(userNameTextField.getText());
        user.setFullName(fullNameTextField.getText());
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
        } else {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Failed");
            alert.setHeaderText("User Profile update Failed!");
            alert.setContentText("Please check your Internet connection. Update failed for some reasons.");
        }
        alert.showAndWait();
        StageManager.getInstance().editProfileStage.hide();
    }

    @FXML
    public void handleCancelButtonAction(ActionEvent actionEvent) {
        StageManager.getInstance().editProfileStage.hide();
    }

}
