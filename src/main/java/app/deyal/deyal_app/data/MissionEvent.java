package app.deyal.deyal_app.data;

import app.deyal.deyal_app.DataManager;
import app.deyal.deyal_app.data.events.*;
import app.deyal.deyal_app.repository.Auth;
import javafx.scene.control.Alert;

import java.util.Date;

public class MissionEvent {
    private String id;

    private EventType eventType;
    private String missionId;
    private Date eventTime;

    private Create create;
    private Update update;
    private Publish publish;
    private Request request;
    private Assign assign;
    private Submit submit;
    private Approve approve;
    private Reject reject;
    private Review review;

    public MissionEvent(String missionId, EventType eventType) {
        this.missionId = missionId;
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        String string = eventTime + "\n";
        switch (eventType) {
            case CREATE:
                if (Auth.searchUser(DataManager.getInstance().token, create.getCreatedBy())) {
                    string += "Mission created by " + DataManager.getInstance().tempUser.getUserName();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Failed");
                    alert.setHeaderText("User name retrieve failed");
                    alert.setContentText("Please check your internet connection.");
                }
                break;
            case UPDATE:
                string += "updated from\n" + update.toString();
                break;
            case PUBLISH:
                string += "published with note:\n" + publish.getNote();
                break;
            case REQUEST:
                if (Auth.searchUser(DataManager.getInstance().token, request.getRequestBy())) {
                    string += "requested by " + DataManager.getInstance().tempUser.getUserName() + "\n" +
                            "with message:\n" + request.getRequestMessage();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Failed");
                    alert.setHeaderText("User name retrieve failed");
                    alert.setContentText("Please check your internet connection.");
                }
                break;
            case ASSIGN:
                string += "assigned to " + assign.getAssignTo() + "\n" +
                        "with message:\n" + assign.getAssignMessage();
                break;
            case SUBMIT:
                string += "submitted with proof of work:\n" + submit.getProofOfWork();
                break;
            case APPROVE:
                string += "approved submission with message:\n" + approve.getApproveMessage();
                break;
            case REJECT:
                string += "rejected with message:\n" + reject.getRejectMessage();
                break;
            case REVIEW:
                string += "reviewed: " + (review.isGotReward() ? "I got my rewards" : "Client didn't pay") + "\n" +
                        "with message:\n" + review.getMessage();
                break;
        }
        return string;
    }

    public String getId() {
        return id;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public Approve getApprove() {
        return approve;
    }

    public void setApprove(Approve approve) {
        this.approve = approve;
    }

    public Assign getAssign() {
        return assign;
    }

    public void setAssign(Assign assign) {
        this.assign = assign;
    }

    public Create getCreate() {
        return create;
    }

    public void setCreate(Create create) {
        this.create = create;
    }

    public Update getUpdate() {
        return update;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    public Publish getPublish() {
        return publish;
    }

    public void setPublish(Publish publish) {
        this.publish = publish;
    }

    public Reject getReject() {
        return reject;
    }

    public void setReject(Reject reject) {
        this.reject = reject;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public Submit getSubmit() {
        return submit;
    }

    public void setSubmit(Submit submit) {
        this.submit = submit;
    }

}
