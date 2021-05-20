package com.example.mywechat.data;

import java.lang.String;

public class Friend {
    private int Number;
    private String Nickname;
    private String PhoneNumber;
    private int Profile;

    public Friend(int number, String nickname, String phonenumber, int profile){
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
    public int getProfile() {return Profile;}
}
