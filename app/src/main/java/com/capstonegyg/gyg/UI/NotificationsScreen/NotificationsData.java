package com.capstonegyg.gyg.UI.NotificationsScreen;

/**
 * Created by aimestudent1 on 4/23/18.
 */

public class NotificationsData {
    String gygName;
    String gygRef;
    String gygPosterName;
    String gygPosterRef;

    public NotificationsData(String gygName, String gygPosterName) {
        this.gygName = gygName;
        this.gygPosterName = gygPosterName;
    }

    public NotificationsData() {
    }

    public NotificationsData(String gygName, String gygRef, String gygPosterName, String gygPosterRef) {
        this.gygName = gygName;
        this.gygRef = gygRef;
        this.gygPosterName = gygPosterName;
        this.gygPosterRef = gygPosterRef;
    }
}
