package ca.louisechan.tokyo2020.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "attractions")
public class Attraction {
    @PrimaryKey
    private String name;
    private String address;
    @ColumnInfo(name = "image_url")
    private String imageUrl;
    @ColumnInfo(name = "brief_desc")
    private String briefDesc;
    private String website;
    @ColumnInfo(name = "youtube_url")
    private String youtubeUrl;
    @ColumnInfo(name = "detailed_desc")
    private String detailedDesc;
    @ColumnInfo(name = "visit_fee")
    private Double visitFee;

    public Attraction(String name) {
        this.name = name;
        address = "";
        imageUrl = "";
        briefDesc = "";
        website = "";
        youtubeUrl = "";
        detailedDesc = "";
        visitFee = 0.0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBriefDesc() {
        return briefDesc;
    }

    public void setBriefDesc(String briefDesc) {
        this.briefDesc = briefDesc;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public String getDetailedDesc() {
        return detailedDesc;
    }

    public void setDetailedDesc(String detailedDesc) {
        this.detailedDesc = detailedDesc;
    }

    public Double getVisitFee() {
        return visitFee;
    }

    public void setVisitFee(Double visitFee) {
        this.visitFee = visitFee;
    }
}
