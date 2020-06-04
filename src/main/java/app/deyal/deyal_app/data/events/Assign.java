package app.deyal.deyal_app.data.events;

public class Assign {
    private String assignTo;
    private String assignMessage;

    public Assign(String assignTo, String assignMessage) {
        this.assignTo = assignTo;
        this.assignMessage = assignMessage;
    }

    public String getAssignMessage() {
        return assignMessage;
    }

    public void setAssignMessage(String assignMessage) {
        this.assignMessage = assignMessage;
    }

    public String getAssignTo() {
        return assignTo;
    }

    public void setAssignTo(String assignTo) {
        this.assignTo = assignTo;
    }
}
