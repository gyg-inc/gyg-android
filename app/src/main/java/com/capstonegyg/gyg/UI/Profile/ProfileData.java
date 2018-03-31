package com.capstonegyg.gyg.UI.Profile;

/**
 * Created by Shawn on 3/31/18.
 */

public class ProfileData {
    //The name of the user
    public String userName;
    //The user QR code
    public int qrCode;

    //Constructor
    public ProfileData(String userName, int qrCode) {
        this.userName = userName;
        this.qrCode = qrCode;
    }

    //Default constructor. (Necassary by Firebase standards)
    public ProfileData() {
    }
}
