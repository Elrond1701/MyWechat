package com.example.mywechat.data;

import android.graphics.Bitmap;

import java.lang.String;

public class Friend {
    private int Number;
    private String Nickname;
    private String PhoneNumber;
    private Bitmap Profile;
    private String Sex;

    public Friend(int number, String nickname, String phonenumber, Bitmap profile){
        Number = number;
        Nickname = nickname;
        PhoneNumber = phonenumber;
        Profile = profile;
    }

    public int getNumber() {return Number;}
    public String getNickname() {
        return Nickname;
    }
    public String getPhoneNumber() {
        return PhoneNumber;
    }
    public Bitmap getProfile() {return Profile;}
    public String getSex() {return Sex;}
}
