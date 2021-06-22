package com.example.mywechat.data;

import android.provider.ContactsContract;

import java.lang.String;
import java.util.ArrayList;


public class Discover {
//    private int Profile;
    private String id;
    private String Username;
    private String Nickname;
    private String Text;
    private String PublishedTime;
    private ArrayList<Integer> Images;
    private ArrayList<String> LikeList;
    private ArrayList<Comment> CommentList;

//    public Discover(String Nickname, int Profile, String Text, String PublishedTime, ArrayList<Integer> Images, ArrayList<String> LikeList, ArrayList<Comment> CommentList) {
//        this.Nickname = Nickname;
////        this.Profile = Profile;
//        this.Text = Text;
//        this.PublishedTime = PublishedTime;
//        this.Images = Images;
//        this.LikeList = LikeList;
//        this.CommentList = CommentList;
//    }

    public Discover(String Id, String Nickname, int Profile, String Text) {
        this.id = Id;
        this.Nickname = Nickname;
//        this.Profile = Profile;
        this.Text = Text;
//        this.PublishedTime = PublishedTime;
    }

    public String getNickname() { return Nickname; }

//    public int getAvatarIcon() { return avatarIcon; }

//    public ArrayList<Integer> getImages() { return images; }

    public String getPublishedTime() { return PublishedTime; }

    public String getText() { return Text; }

    public String getLikeList() {
        String result="";
        int Length = LikeList.size();
        for (int j = 0; j<Length; j++){
            result = result + LikeList.get(j);
            if (j < Length - 1) {
                result = result + "，";
            }
        }
        return result;
    }
//    public int getImageCount() { return images.size(); }
}
