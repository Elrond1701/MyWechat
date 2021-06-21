package com.example.mywechat.data;

import android.graphics.Bitmap;

public class Friend {
    private int number;
    private String nickname;
    private String id;
    private Bitmap profile;
    private String profiledir;
    private String gender;
    private String region;
    private String whatsup;


    public Friend(){
        number = -1;
        nickname = null;
        id = null;
        profile = null;
        profiledir = null;
        gender = null;
        region = null;
        whatsup = null;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public Bitmap getProfile() {
        return profile;
    }

    public void setProfile(Bitmap profile) {
        this.profile = profile;
    }

    public String getProfileDir() {
        return profiledir;
    }

    public void setProfileDir(String profiledir) {
        this.profiledir = profiledir;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getWhatsUp() {
        return whatsup;
    }

    public void setWhatsUp(String whatsup) {
        this.whatsup = whatsup;
    }
}
