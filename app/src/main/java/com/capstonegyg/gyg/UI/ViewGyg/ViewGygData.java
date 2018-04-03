package com.capstonegyg.gyg.UI.ViewGyg;

/**
 * Created by ipnanayakkara on 3/5/2018.
 *
 *  ViewGyg shows all Gygs that are posted in Firebase
 *  Allows User to search and filter by Area and/or user
 */

/**
   The "schema" file. This is how the JSON node looks like
*/

public class ViewGygData {
    //The name of the job
    public String gygName;
    //The poster's name
    public String gygPosterName;
    //The name of the job
    public Double gygFee;
    //The poster's name
    public String gygLocation;
    public String gygTime;

    //Constructor
    public ViewGygData(String gygName, String gygPosterName) {
        this.gygName = gygName;
        this.gygPosterName = gygPosterName;
    }

    //Default constructor. (Necassary by Firebase standards)
    public ViewGygData() {
    }
}
