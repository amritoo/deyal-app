package app.deyal.deyal_app.data;

import app.deyal.deyal_app.data.events.*;

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

    public MissionEvent() {
    }

    public MissionEvent(EventType eventType) {
        this.eventType = eventType;
        this.missionId = "5e9af7c0ff648649f0410ac4";
        this.eventTime = new Date();
        switch (eventType) {
            case CREATE:
                this.create = new Create();
                this.create.setCreatedBy(this.missionId);
                break;
            case UPDATE:
                this.update = new Update();
                this.update.setOldMission(new Mission());
                break;
            case PUBLISH:
                this.publish = new Publish();
                break;
            case REQUEST:
                this.request = new Request("5e9af7c0ff648649f0410ac5", "I can certainly do it!");
                break;
            case ASSIGN:
                this.assign = new Assign("5e9af7c0ff648649f0410ac5", "Alright! You got the job!!");
                break;
            case SUBMIT:
                this.submit = new Submit("Here is all the proof needed.");
                break;
            case APPROVE:
                this.approve = new Approve("You've done well. I accept your work.");
                break;
            case REJECT:
                this.reject = new Reject("No, you didn't finish it as requested. Sadly, I cannot accept your work.");
                break;
            case REVIEW:
                this.review = new Review(4.5, "This was a nice mission.");
                break;
        }
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
