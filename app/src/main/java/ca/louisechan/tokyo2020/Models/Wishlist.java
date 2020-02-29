package ca.louisechan.tokyo2020.Models;

public class Wishlist {
    private int id;
    private int userID;
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

