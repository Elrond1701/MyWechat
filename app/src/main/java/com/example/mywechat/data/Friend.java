package com.example.mywechat.data;

import android.graphics.Bitmap;

import java.lang.String;

public class Friend {
    private int Number;
    private String Nickname;
    private String PhoneNumber;
    private Bitmap Profile;
    private String Gender;
    private String Region;
    private String WhatsUp;


    public Friend(){
        Number = -1;
        Nickname = null;
        PhoneNumber = null;
        Profile = null;
        Gender = null;
        Region = null;
        WhatsUp = null;
    }

    public int getNumber() {
        return Number;
    }
    public void setNumber(int Number) {
        this.Number = Number;
    }
    public String getNickname() {
        return Nickname;
    }
    public void setNickname(String Nickname) {
        this.Nickname = Nickname;
    }
    public String getPhoneNumber() {
        return PhoneNumber;
    }
    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }
    public Bitmap getProfile() {
        return Profile;
    }
    public void setProfile(Bitmap Profile) {
        this.Profile = Profile;
    }
    public String getGender() {
        return Gender;
    }
    public void setGender(String Gender) {
        this.Gender = Gender;
    }
    public String getRegion() {
        return Region;
    }
    public void setRegion(String Region) {
        this.Region = Region;
    }
    public String getWhatsUp() {
        return WhatsUp;
    }
    public void setWhatsUp(String WhatsUp) {
        this.WhatsUp = WhatsUp;
    }
}
