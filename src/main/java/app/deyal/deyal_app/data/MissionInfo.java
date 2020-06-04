package app.deyal.deyal_app.data;

import java.util.ArrayList;

public class MissionInfo {

    private int ratingAsClient;
    private int ratingAsContractor;
    private ArrayList<String> created;
    private ArrayList<String> completed;
    private ArrayList<String> failed;

    public MissionInfo() {
        this.ratingAsClient = 0;
        this.ratingAsContractor = 0;
    }

    public MissionInfo(int ratingAsClient, int ratingAsContractor, ArrayList<String> created, ArrayList<String> completed, ArrayList<String> failed) {
        this.ratingAsClient = ratingAsClient;
        this.ratingAsContractor = ratingAsContractor;
        this.created = created;
        this.completed = completed;
        this.failed = failed;
    }

    /* ------------------------------------------------------------------------- */

    public int getRatingAsClient() {
        return ratingAsClient;
    }

    public void setRatingAsClient(int ratingAsClient) {
        this.ratingAsClient = ratingAsClient;
    }

    public int getRatingAsContractor() {
        return ratingAsContractor;
    }

    public void setRatingAsContractor(int ratingAsContractor) {
        this.ratingAsContractor = ratingAsContractor;
    }

    public ArrayList<String> getCreated() {
        return created;
    }

    public void setCreated(ArrayList<String> created) {
        this.created = created;
    }

    public ArrayList<String> getCompleted() {
        return completed;
    }

    public void setCompleted(ArrayList<String> completed) {
        this.completed = completed;
    }

    public ArrayList<String> getFailed() {
        return failed;
    }

    public void setFailed(ArrayList<String> failed) {
        this.failed = failed;
    }
}
