package app.deyal.deyal_app.data;

import java.util.ArrayList;

public class MissionInfo {

    private double ratingAsClient;
    private double ratingAsContractor;
    private ArrayList<String> created;
    private ArrayList<String> completed;
    private ArrayList<String> failed;
    private ArrayList<String> ongoing;

    public MissionInfo() {
        this.ratingAsClient = 0;
        this.ratingAsContractor = 0;
    }

    public MissionInfo(double ratingAsClient, double ratingAsContractor, ArrayList<String> created, ArrayList<String> completed, ArrayList<String> failed, ArrayList<String> ongoing) {
        this.ratingAsClient = ratingAsClient;
        this.ratingAsContractor = ratingAsContractor;
        this.created = created;
        this.completed = completed;
        this.failed = failed;
        this.ongoing = ongoing;
    }

    /* ------------------------------------------------------------------------- */

    public double getRatingAsClient() {
        return ratingAsClient;
    }

    public void setRatingAsClient(double ratingAsClient) {
        this.ratingAsClient = ratingAsClient;
    }

    public double getRatingAsContractor() {
        return ratingAsContractor;
    }

    public void setRatingAsContractor(double ratingAsContractor) {
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

    public ArrayList<String> getOngoing() {
        return ongoing;
    }

    public void setOngoing(ArrayList<String> ongoing) {
        this.ongoing = ongoing;
    }
}
