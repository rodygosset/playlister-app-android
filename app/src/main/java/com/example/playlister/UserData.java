package com.example.playlister;

public class UserData {

    private AuthToken authToken;

    private String username;
    private String firstName;
    private String lastName;


    private static final UserData singleInstance = new UserData();

    public static UserData getUserData() {
        return singleInstance;
    }

    private UserData() {
    }

    public String getAuthHeader() {
        return this.authToken.getAuthHeader();
    }

    public String getUsername() {
        return this.username;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }



    public void setUserData(String newTokenType, String newAccessToken, String newUsername, String newFirstName, String newLastName) {
        this.authToken = new AuthToken(newTokenType, newAccessToken);
        this.username = newUsername;
        this.firstName = newFirstName;
        this.lastName = newLastName;
    }
}
