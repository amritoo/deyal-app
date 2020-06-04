package app.deyal.deyal_app.data.events;

public class Approve {
    private String approveMessage;

    public Approve(String approveMessage) {
        this.approveMessage = approveMessage;
    }

    public String getApproveMessage() {
        return approveMessage;
    }

    public void setApproveMessage(String approveMessage) {
        this.approveMessage = approveMessage;
    }
}
