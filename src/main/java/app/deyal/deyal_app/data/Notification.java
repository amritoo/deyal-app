package app.deyal.deyal_app.data;

public class Notification {

    private String message;
    private String missionId;

    public Notification() {
    }

    public Notification(String message, String missionId) {
        this.message = message;
        this.missionId = missionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }
}
