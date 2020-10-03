package app.deyal.deyal_app.data;

import app.deyal.deyal_app.data.events.*;

import java.util.Date;

public class MissionEvent {
    private String id;

    private EventType eventType;
    private String missionId;
    private Date eventTime;
    private String username;

    private Create create;
    private Update update;
    private Publish publish;
    private Request request;
    private Assign assign;
    private Submit submit;
    private Approve approve;
    private Reject reject;
    private Review review;

    public MissionEvent(String missionId, EventType eventType, String username) {
        this.missionId = missionId;
        this.eventType = eventType;
        this.username = username;
    }

    @Override
    public String toString() {
        String string = eventTime + "\n";
        switch (eventType) {
            case CREATE -> string += "Mission created by " + this.getUsername();
            case UPDATE -> string += "updated from\n" + update.toString();
            case PUBLISH -> string += "published with note:\n" + publish.getNote();
            case REQUEST -> {
                string += "requested by " + this.getUsername();
                if (request.getRequestMessage().length() > 0)
                    string += "\n" + "with message:\n" + request.getRequestMessage();
            }
            case ASSIGN -> {
                string += "assigned to " + this.getUsername();
                if (assign.getAssignMessage().length() > 0)
                    string += "\n" + "with message:\n" + assign.getAssignMessage();
            }
            case SUBMIT -> {
                string += "submitted by " + this.getUsername();
                if (submit.getProofOfWork().length() > 0)
                    string += "\n" + "with proof of work:\n" + submit.getProofOfWork();
            }
            case APPROVE -> {
                string += "submission approved by " + this.getUsername();
                if (approve.getApproveMessage().length() > 0)
                    string += "\n" + "with message:\n" + approve.getApproveMessage();
            }
            case REJECT -> {
                string += "submission rejected by " + this.getUsername();
                if (reject.getRejectMessage().length() > 0)
                    string += "\n" + "with message:\n" + reject.getRejectMessage();
            }
            case REVIEW -> {
                string += this.getUsername() + " reviewed: " + (review.isGotReward() ? "I got my rewards :)" : "Client didn't pay :(");
                if (review.getMessage().length() > 0)
                    string += "\n" + "with message:\n" + review.getMessage();
            }
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
