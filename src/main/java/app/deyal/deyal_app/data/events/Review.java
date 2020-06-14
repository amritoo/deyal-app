package app.deyal.deyal_app.data.events;

public class Review {
    private String message;
    private boolean gotReward;

    public Review() {
    }

    public Review(boolean gotReward, String message) {
        this.gotReward = gotReward;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isGotReward() {
        return gotReward;
    }

    public void setGotReward(boolean gotReward) {
        this.gotReward = gotReward;
    }

}
