package app.deyal.deyal_app.data.events;

public class Request {
    private String requestBy;
    private String requestMessage;

    public Request(String requestBy, String requestMessage) {
        this.requestBy = requestBy;
        this.requestMessage = requestMessage;
    }

    public String getRequestBy() {
        return requestBy;
    }

    public void setRequestBy(String requestBy) {
        this.requestBy = requestBy;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }
}
