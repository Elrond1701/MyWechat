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
    private ArrayList<String> CommentList;

//    public Discover(String Nickname, int Profile, String Text, String PublishedTime, ArrayList<Integer> Images, ArrayList<String> LikeList, ArrayList<Comment> CommentList) {
//        this.Nickname = Nickname;
////        this.Profile = Profile;
//        this.Text = Text;
//        this.PublishedTime = PublishedTime;
//        this.Images = Images;
//        this.LikeList = LikeList;
//        this.CommentList = CommentList;
//    }

//    public Discover(String Id, String Nickname, int Profile, String Text) {
//        this.id = Id;
//        this.Nickname = Nickname;
////        this.Profile = Profile;
//        this.Text = Text;
////        this.PublishedTime = PublishedTime;
//    }

        public Discover(String Id, String Username) {
        this.id = Id;
        this.Username = Username;
    }

    public void setText(String Text) {this.Text = Text;}

    public void setLikeList(ArrayList<String> CommentList) {
            this.CommentList = CommentList;
    }

    public void setCommentList(ArrayList<String> LikeList) {
        this.LikeList = LikeList;
    }

    public String getId() { return id; }

    public String getNickname() { return Username; }

//    public int getAvatarIcon() { return avatarIcon; }

//    public ArrayList<Integer> getImages() { return images; }

    public String getPublishedTime() { return PublishedTime; }

    public String getText() { return Text; }

    public String getLikeListStr() {
        String result="";
        int Length = LikeList.size();
        for (int j = 0; j<Length; j++){
            result = result + LikeList.get(j);
            if (j < Length - 1) {
                result = result + "，";
            }
        }
        return result+"觉得很赞";
    }

    public ArrayList<String> getLikeList(){ return LikeList; }

    public int getLikeListLen() {
            return LikeList.size();
    }

    public ArrayList<String> getCommentList(){ return CommentList; }

//    public int getImageCount() { return images.size(); }
}
