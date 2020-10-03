package app.deyal.deyal_app.data;

import java.net.URL;

public interface Constants {

    String LOG_NAME = "Deyal_log";

    String SERVER_ADDRESS = "http://localhost:3030/v1";

    // Application
    String APP_NAME = "Deyal";
    URL APP_ICON = Constants.class.getResource("/images/ic_deyal.png");

    // Window Titles
    String LOGIN_TITLE = "Deyal - Login";
    String REGISTER_TITLE = "Deyal - Create Account";
    String RECOVER_PASSWORD_TITLE = "Deyal - Recover Password";
    String MAIN_TITLE = "Deyal";
    String CHANGE_PASSWORD_TITLE = "Deyal - Change Password";
    String CREATE_MISSION_TITLE = "Deyal - Create Mission";
    String VIEW_MISSION_TITLE = "Deyal - View Mission";
    String VIEW_PROFILE_TITLE = "Deyal - Profile";
    String REQUEST_MESSAGE_TITLE = "Request Message";
    String ASSIGN_MESSAGE_TITLE = "Assign Message";
    String SUBMIT_MISSION_TITLE = "Submit";
    String VIEW_SUBMISSION_TITLE = "View Submission";
    String JUDGE_MESSAGE_TITLE = "Approve/Reject Message";
    String VIEW_REQUEST_TITLE = "View Request";
    String COMPLETE_MISSION_TITLE = "Complete Mission";
    String NOTIFICATION_TITLE = "Notification";
    String SEARCH_MISSION_TITLE = "Search mission";
    String EDIT_PROFILE_TITLE = "Edit Profile";

    // FXML locations
    URL LOGIN_FXML = Constants.class.getResource("/app/deyal/deyal_app/views/auth/login.fxml");
    URL CREATE_ACCOUNT_FXML = Constants.class.getResource("/app/deyal/deyal_app/views/auth/register.fxml");
    URL SEND_CODE_FXML = Constants.class.getResource("/app/deyal/deyal_app/views/auth/sendCode.fxml");
    URL VERIFY_CODE_FXML = Constants.class.getResource("/app/deyal/deyal_app/views/auth/verifyCode.fxml");
    URL MAIN_FXML = Constants.class.getResource("/app/deyal/deyal_app/views/main/main.fxml");
    URL CHANGE_PASSWORD_FXML = Constants.class.getResource("/app/deyal/deyal_app/views/auth/changePassword.fxml");
    URL CREATE_MISSION_FXML = Constants.class.getResource("/app/deyal/deyal_app/views/mission/createMission.fxml");
    URL SEARCH_MISSION_FXML = Constants.class.getResource("/app/deyal/deyal_app/views/mission/search.fxml");
    URL VIEW_MISSION_FXML = Constants.class.getResource("/app/deyal/deyal_app/views/mission/ViewMission.fxml");
    URL VIEW_PROFILE_FXML = Constants.class.getResource("/app/deyal/deyal_app/views/viewProfile.fxml");
    URL EDIT_PROFILE_FXML = Constants.class.getResource("/app/deyal/deyal_app/views/main/edit_profile.fxml");
    URL REQUEST_MESSAGE_FXML = Constants.class.getResource("/app/deyal/deyal_app/views/event/requestMessage.fxml");
    URL ASSIGN_MESSAGE_FXML = Constants.class.getResource("/app/deyal/deyal_app/views/event/assignMessage.fxml");
    URL SUBMIT_MISSION_FXML = Constants.class.getResource("/app/deyal/deyal_app/views/event/submitMission.fxml");
    URL VIEW_SUBMISSION_FXML = Constants.class.getResource("/app/deyal/deyal_app/views/event/viewSubmission.fxml");
    URL JUDGING_MESSAGE_FXML = Constants.class.getResource("/app/deyal/deyal_app/views/event/judgingMessage.fxml");
    URL VIEW_REQUEST_FXML = Constants.class.getResource("/app/deyal/deyal_app/views/event/viewRequest.fxml");
    URL COMPLETE_MISSION_FXML = Constants.class.getResource("/app/deyal/deyal_app/views/event/completeMission.fxml");
    URL NOTIFICATION_FXML = Constants.class.getResource("/app/deyal/deyal_app/views/notification.fxml");
    URL TOOLBAR_FXML = Constants.class.getResource("/app/deyal/deyal_app/views/main/toolbar.fxml");

    // Themes
    String DARK_THEME_CSS = Constants.class.getResource("/app/deyal/deyal_app/theme_dark.css").toExternalForm();
    String LIGHT_THEME_CSS = Constants.class.getResource("/app/deyal/deyal_app/theme_light.css").toExternalForm();

}
