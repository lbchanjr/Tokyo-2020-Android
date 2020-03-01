package ca.louisechan.tokyo2020.Models;

import androidx.room.ColumnInfo;

public class WishlistByName {
    @ColumnInfo(name = "name")
    private String userName;
    @ColumnInfo(name = "attraction_name")
    private String attractionName;

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
}
