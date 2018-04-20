package com.capstonegyg.gyg.UI.MyGygs;

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

    //Constructor
    public MyGygsData(String gygName, String gygPosterName) {
        this.gygName = gygName;
        this.gygPosterName = gygPosterName;
    }

    //Default constructor. (Necassary by Firebase standards)
    public MyGygsData() {
    }
}
