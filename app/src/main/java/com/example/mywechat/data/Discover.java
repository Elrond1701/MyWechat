package com.example.mywechat.data;

import java.lang.String;
import java.util.ArrayList;


public class Discover {
//    private int Profile;
    private String Nickname;
    private String Text;
    private String PublishedTime;
    private ArrayList<Integer> Images;

    public Discover(String Nickname, int Profile, String Text, String PublishedTime) {
        this.Nickname = Nickname;
//        this.Profile = Profile;
        this.Text = Text;
        this.PublishedTime = PublishedTime;
    }

    public String getNickname() { return Nickname; }

//    public int getAvatarIcon() { return avatarIcon; }

//    public ArrayList<Integer> getImages() { return images; }

    public String getPublishedTime() { return PublishedTime; }

    public String getText() { return Text; }

//    public int getImageCount() { return images.size(); }
}
