package com.capstonegyg.gyg.UI.ViewGyg;

/**
 * Created by ipnanayakkara on 3/5/2018.
 */

public class ViewGygData {
    String jobName;
    String jobPosterName;

    public ViewGygData(String jobName, String jobPosterName) {
        this.jobName = jobName;
        this.jobPosterName = jobPosterName;
    }

    public ViewGygData() {
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public void setJobPosterName(String jobPosterName) {
        this.jobPosterName = jobPosterName;
    }
}
