package ca.louisechan.tokyo2020.Models;

public class Attraction {
    private String name;
    private String address;
    private String imageLink;
    private String briefDesc;
    private String website;
    private String videoLink;
    private String detailedDesc;
    private Double visitFee;

    public Attraction(String name) {
        this.name = name;
        address = "";
        imageLink = "";
        briefDesc = "";
        website = "";
        videoLink = "";
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

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
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

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
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
