package app.deyal.deyal_app.controllers;

import app.deyal.deyal_app.data.Address;
import app.deyal.deyal_app.data.User;
import app.deyal.deyal_app.managers.AlertManager;
import app.deyal.deyal_app.managers.DataManager;
import app.deyal.deyal_app.managers.StageManager;
import app.deyal.deyal_app.repository.AuthClient;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class EditProfileController {

    @FXML
    public StackPane root;
    @FXML
    public VBox contentRoot;
    @FXML
    public JFXTextField userNameTextField;
    @FXML
    public JFXTextField firstNameTextField;
    @FXML
    public JFXTextField lastNameTextField;
    @FXML
    public JFXTextField phoneNumberTextField;
    @FXML
    public DatePicker birthDatePicker;
    @FXML
    public JFXTextField houseAddressTextField;
    @FXML
    public JFXTextField blockAddressTextField;
    @FXML
    public JFXTextField districtTextField;
    @FXML
    public JFXTextField policeStationTextField;
    @FXML
    public JFXTextField postOfficeTextField;

    @FXML
    private void initialize() {
        User user = DataManager.getInstance().userData;
        // Loading data
        userNameTextField.setText(user.getUserName());
        String fullName = user.getFullName();
        int pos = fullName.lastIndexOf(" ");
        firstNameTextField.setText(fullName.substring(0, pos));
        lastNameTextField.setText(fullName.substring(pos + 1));
        phoneNumberTextField.setText(user.getPhoneNumber());
        birthDatePicker.setValue(LocalDate.ofEpochDay(user.getDateOfBirth())); //converting user dateOfBirth to LocalDate
        if (user.getAddress() != null) {
            houseAddressTextField.setText(user.getAddress().getHouseAddress());
            blockAddressTextField.setText(user.getAddress().getBlockAddress());
            districtTextField.setText(user.getAddress().getDistrict());
            policeStationTextField.setText(user.getAddress().getPoliceStation());
            postOfficeTextField.setText(user.getAddress().getPostOffice());
        }

        birthDatePicker.getEditor().setOnMouseClicked(event -> birthDatePicker.show());
    }

    @FXML
    public void handleSaveButtonAction(ActionEvent actionEvent) {
        User user = new User();
        user.setUserName(extractText(userNameTextField));
        user.setFullName(extractText(firstNameTextField) + " " + extractText(lastNameTextField));
        user.setPhoneNumber(extractText(phoneNumberTextField));
        if (birthDatePicker.getValue() != null)
            user.setDateOfBirth(birthDatePicker.getValue().toEpochDay()); //converting dateOfBirth to EpochDay
        user.setAddress(new Address());
        user.getAddress().setHouseAddress(extractText(houseAddressTextField));
        user.getAddress().setBlockAddress(extractText(blockAddressTextField));
        user.getAddress().setDistrict(extractText(districtTextField));
        user.getAddress().setPoliceStation(extractText(policeStationTextField));
        user.getAddress().setPostOffice(extractText(postOfficeTextField));

        boolean result = AuthClient.updateProfile(DataManager.getInstance().getToken(), user);
        if (result) {
            AuthClient.getUserData(DataManager.getInstance().getToken());
            AlertManager.showMaterialDialog(root, contentRoot,
                    null,
                    "Profile updated successfully",
                    "Your profile has been updated successfully.");
        } else {
            AlertManager.showMaterialDialog(DataManager.getInstance().mainRoot, DataManager.getInstance().mainContentRoot,
                    null,
                    "Profile update failed!",
                    "Update failed for some reasons. Please check your Internet connection and try again.");
        }
        StageManager.getInstance().editProfileStage.hide();
    }

    /**
     * This method calls getText() method on given TextField and trims the string before returning.
     *
     * @param textField the object to get text from
     * @return text
     */
    private String extractText(JFXTextField textField) {
        return textField.getText().trim();
    }

    @FXML
    public void handleCancelButtonAction(ActionEvent actionEvent) {
        StageManager.getInstance().editProfileStage.hide();
    }

}
