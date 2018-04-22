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
    public String gygWorkerName;
    public String gygAcceptedDate;
    public String gygKey;

    //Constructor
    public PostGygData(String gygName, String gygCategory, String gygLocation, Double gygFee, String gygDescription, String gygTime, String gygPosterName, String gygPostedDate, String gygEndDate, Boolean gygVolunteer, String gygWorkerName, String gygAcceptedDate, String gygKey) {
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
        this.gygWorkerName = gygWorkerName;
        this.gygAcceptedDate = gygAcceptedDate;
        this.gygKey = gygKey;
    }
    public PostGygData(){}
}
