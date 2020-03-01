package ca.louisechan.tokyo2020.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "wishlists", foreignKeys = {
        @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id",
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Attraction.class, parentColumns = "name", childColumns = "attraction_name",
                onDelete = ForeignKey.CASCADE)
})
public class Wishlist {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "user_id")
    private int userID;
    @ColumnInfo(name = "attraction_name")
    private String attractionName;

    public Wishlist(int userID, String attractionName) {
        this.userID = userID;
        this.attractionName = attractionName;
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
}

