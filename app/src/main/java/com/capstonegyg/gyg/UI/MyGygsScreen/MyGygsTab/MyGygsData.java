package com.capstonegyg.gyg.UI.MyGygsScreen.MyGygsTab;

public class MyGygsData {

    public String gygName;
    //The poster's name
    public String gygPosterName;
    //The name of the job
    public Double gygFee;
    //The poster's name
    public String gygLocation;
    //Time of post
    public String gygTime;
    //Description of Gyg
    public String gygDescription;
    //Gyg category
    public String gygCategory;
    public String gygKey;
    public String gygWorkerName;

    //Constructor
    public MyGygsData(String gygName, String gygPosterName, String gygWorkerName) {
        this.gygName = gygName;
        this.gygPosterName = gygPosterName;
        this.gygWorkerName = gygWorkerName;
    }

    //Default constructor. (Necassary by Firebase standards)
    public MyGygsData() {
    }
}
