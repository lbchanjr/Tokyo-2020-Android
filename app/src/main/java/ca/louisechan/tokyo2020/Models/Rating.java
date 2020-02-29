package ca.louisechan.tokyo2020.Models;

public class Rating {
    private int id;
    private int userID;
    private String attractionName;
    private double rating;

    public Rating(int userID, String attractionName, double rating) {
        this.userID = userID;
        this.attractionName = attractionName;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getAttractionName() {
        return attractionName;
    }

    public void setAttractionName(String attractionName) {
        this.attractionName = attractionName;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}


