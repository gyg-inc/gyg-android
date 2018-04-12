package com.capstonegyg.gyg.UI.PostGyg;

/**
 *  Written by Jonathan Luetze
 */

public class PostGygData {

    public String gygName;
    public String gygCategory;
    public String gygLocation;
    public Double gygFee;
    public String gygDescription;
    public String gygTime;
    public String gygPosterName;
    public String gygPostedDate;
    public String gygEndDate;
    public Boolean gygVolunteer;



    //Constructor
    public PostGygData(String gygName, String gygCategory, String gygLocation, Double gygFee, String gygDescription, String gygTime, String gygPosterName, String gygPostedDate, String gygEndDate, Boolean gygVolunteer) {
        this.gygName = gygName;
        this.gygCategory = gygCategory;
        this.gygLocation = gygLocation;
        this.gygFee = gygFee;
        this.gygDescription = gygDescription;
        this.gygTime = gygTime;
        this.gygPosterName = gygPosterName;
        this.gygPostedDate = gygPostedDate;
        this.gygEndDate = gygEndDate;
        this.gygVolunteer = gygVolunteer;
    }
    public PostGygData(){}
}
