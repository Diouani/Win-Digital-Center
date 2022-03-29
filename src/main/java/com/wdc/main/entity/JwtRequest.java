package com.wdc.main.entity;

public class JwtRequest {

    private String username;
    private String password;

    public JwtRequest() {
    }

    public JwtRequest(String userName, String password) {
        this.username = userName;
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
