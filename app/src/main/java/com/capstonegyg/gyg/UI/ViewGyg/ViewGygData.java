package com.capstonegyg.gyg.UI.ViewGyg;

/**
 * Created by ipnanayakkara on 3/5/2018.
 *
 *  ViewGyg shows all Gygs that are posted in Firebase
 *  Allows User to search and filter by Area and/or user
 */

public class ViewGygData {
    public String jobName;
    public String jobPosterName;

    public ViewGygData(String jobName, String jobPosterName) {
        this.jobName = jobName;
        this.jobPosterName = jobPosterName;
    }

    public ViewGygData() {
    }
}
