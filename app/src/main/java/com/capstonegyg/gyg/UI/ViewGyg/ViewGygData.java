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
    public String jobName;
    //The poster's name
    public String jobPosterName;

    //Constructor
    public ViewGygData(String jobName, String jobPosterName) {
        this.jobName = jobName;
        this.jobPosterName = jobPosterName;
    }

    //Default constructor. (Necassary by Firebase standards)
    public ViewGygData() {
    }
}
