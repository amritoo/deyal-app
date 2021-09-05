package app.deyal.deyal_app.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AboutController {

    @FXML
    public Label firstMemberName;
    @FXML
    public Label firstMemberDetails;
    @FXML
    public Label secondMemberName;
    @FXML
    public Label secondMemberDetails;
    @FXML
    public Label thirdMemberName;
    @FXML
    public Label thirdMemberDetails;

    @FXML
    public void initialize() {
        firstMemberName.setText("Amrito Das Tipu");
        firstMemberDetails.setText("Hi, I'm currently studying CSE at HSTU. Developing this project was so much fun and I learned a ton. Here's a phrase that motivates me: Good code is an art.");

        secondMemberName.setText("Md. Mahadi Hasan");
        secondMemberDetails.setText("This is Mahadi Hasan, undergraduate student at CSE department of HSTU. In this project I was a cooperative member. From my passion I have taken this as a challenge to get bright performance.");

        thirdMemberName.setText("Jannatul Ferdouse");
        thirdMemberDetails.setText("This is Jannatul Ferdouse, undergraduate student at CSE department of HSTU. From my passion I have taken this project as a challenge to do develop something great.");
    }

}
