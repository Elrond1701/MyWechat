package com.example.mywechat.data;

public class User extends Friend{

    public static final boolean USER = true;
    private String password;

    public User() {
        password = null;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
