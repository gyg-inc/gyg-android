package com.capstonegyg.gyg.UI.Profile;

public class UserData {

    private String user_id;
    private String diaplay_name;
    private String[] skills;

    public UserData() {
    }

    public UserData(String user_id, String diaplay_name, String[] skills) {
        this.user_id = user_id;
        this.diaplay_name = diaplay_name;
        this.skills = skills;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDiaplay_name() {
        return diaplay_name;
    }

    public void setDiaplay_name(String diaplay_name) {
        this.diaplay_name = diaplay_name;
    }

    public String[] getSkills() {
        return skills;
    }

    public void setSkills(String[] skills) {
        this.skills = skills;
    }
}
