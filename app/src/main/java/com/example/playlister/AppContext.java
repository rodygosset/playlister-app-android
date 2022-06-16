package com.example.playlister;

import android.app.Application;

public class AppContext extends Application {

    private UserData userData;

    public void setUserData(String newTokenType, String newAccessToken, String newUsername, String newFirstName, String newLastName) {
        this.userData = new UserData();
        this.userData.setUserData(newTokenType, newAccessToken, newUsername, newFirstName, newLastName);
    }

    public UserData getUserData() { return this.userData; }

}
