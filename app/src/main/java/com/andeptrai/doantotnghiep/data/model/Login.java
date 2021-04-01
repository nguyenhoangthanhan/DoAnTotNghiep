package com.andeptrai.doantotnghiep.data.model;

public class Login {
    private String username;
    private String password;

    public static String currentUsername;
    public static int currentId;
    public static String currentPwd;

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

