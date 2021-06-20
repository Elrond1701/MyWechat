package com.example.mywechat.data;

public class User extends Friend{

    public static final boolean USER = true;
    private String password;
    private String email;

    public User() {
        password = null;
        email = null;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
