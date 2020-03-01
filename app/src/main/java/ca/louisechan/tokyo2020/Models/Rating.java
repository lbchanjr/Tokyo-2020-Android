package ca.louisechan.tokyo2020.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "ratings", foreignKeys = {
    @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id",
        onDelete = ForeignKey.CASCADE),
    @ForeignKey(entity = Attraction.class, parentColumns = "name", childColumns = "attraction_name",
        onDelete = ForeignKey.CASCADE)
    })
public class Rating {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "user_id")
    private int userID;
    @ColumnInfo(name = "attraction_name")
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


