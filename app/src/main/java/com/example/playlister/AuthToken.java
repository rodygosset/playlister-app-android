package com.example.playlister;

public class AuthToken {

    private String tokenType;
    private String accessToken;

    public AuthToken(String type, String access) {
        this.tokenType = type;
        this.accessToken = access;
    }

    public String getAuthHeader() {
        return this.tokenType + " " + this.accessToken;
    }
}
