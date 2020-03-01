package ca.louisechan.tokyo2020.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "ratings", foreignKeys = {
    @ForeignKey(entity = User.class, parentColumns = "email", childColumns = "user_email",
        onDelete = ForeignKey.CASCADE),
    @ForeignKey(entity = Attraction.class, parentColumns = "name", childColumns = "attraction_name",
        onDelete = ForeignKey.CASCADE)
    }, indices = {
        @Index(value = {"user_email"}),
        @Index(value = {"attraction_name"})
    })

public class Rating {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "user_email")
    private String userEmail;
    @ColumnInfo(name = "attraction_name")
    private String attractionName;
    private double rating;

    public Rating(String userEmail, String attractionName, double rating) {
        this.userEmail = userEmail;
        this.attractionName = attractionName;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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


