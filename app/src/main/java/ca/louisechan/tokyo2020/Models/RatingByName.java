package ca.louisechan.tokyo2020.Models;

import androidx.room.ColumnInfo;

public class RatingByName {
    @ColumnInfo(name = "name")
    private String userName;
    @ColumnInfo(name = "attraction_name")
    private String attractionName;
    private double rating;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
