package com.capstonegyg.gyg;

/**
 * Created by Shawn on 3/23/18.
 */

public class User {

    public String firstName;
    public String lastName;
    public String email;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

}
