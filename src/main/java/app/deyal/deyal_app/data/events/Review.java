package app.deyal.deyal_app.data.events;

public class Review {
    private double rating;
    private String message;

    public Review(double rating, String message) {
        this.rating = rating;
        this.message = message;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
