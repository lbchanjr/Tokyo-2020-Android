package ca.louisechan.tokyo2020.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "wishlists", foreignKeys = {
        @ForeignKey(entity = User.class, parentColumns = "email", childColumns = "user_email",
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Attraction.class, parentColumns = "name", childColumns = "attraction_name",
                onDelete = ForeignKey.CASCADE)
})
public class Wishlist {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "user_email")
    private String userEmail;
    @ColumnInfo(name = "attraction_name")
    private String attractionName;

    public Wishlist(String userEmail, String attractionName) {
        this.userEmail = userEmail;
        this.attractionName = attractionName;
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
}

