package com.example.mywechat.data;

import java.lang.String;
import java.util.ArrayList;


public class Discover {
    private int avatarIcon;
    private String nickname;
    private String text;
    private String publishedTime;
    private ArrayList<Integer> images;

    public String getNickname() { return nickname; }

    public int getAvatarIcon() { return avatarIcon; }

    public ArrayList<Integer> getImages() { return images; }

    public String getPublishedTime() { return publishedTime; }

    public String getText() { return text; }

    public int getImageCount() { return images.size(); }
}
