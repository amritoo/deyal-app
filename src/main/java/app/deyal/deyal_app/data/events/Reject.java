package app.deyal.deyal_app.data.events;

public class Reject {
    private String rejectMessage;

    public Reject(String rejectMessage) {
        this.rejectMessage = rejectMessage;
    }

    public String getRejectMessage() {
        return rejectMessage;
    }

    public void setRejectMessage(String rejectMessage) {
        this.rejectMessage = rejectMessage;
    }
}
