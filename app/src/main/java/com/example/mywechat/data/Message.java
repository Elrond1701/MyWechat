package com.example.mywechat.data;

import android.graphics.Bitmap;

public class Message {

//    private int Profile;
    private int AsgType;
//    private String Time;
    private String Content;
//    private Bitmap Photo;

    public static final int MSG_TEXT_LEFT = 101;
    public static final int MSG_TEXT_RIGHT = 102;
    public static final int MSG_VOICE_LEFT = 201;
    public static final int MSG_VOICE_RIGHT = 202;
    public static final int MSG_PHOTO_LEFT = 301;
    public static final int MSG_PHOTO_RIGHT = 302;

    public Message(String Content, int AsgType) {
//        this.Profile = Profile;
        this.Content = Content;
        this.AsgType = AsgType;
    }

//    public void setText(String Text){
//        this.Text = Text;
//    }
//
//    public void setPhoto(Bitmap Photo) {
//        this.Photo = Photo;
//    }
//
//
////    public Message(int AsgType) {
////        this.AsgType = AsgType;
////    }

    public int getAsgType() { return AsgType; }

//    public String getText() { return Text; }
//
//    public Bitmap getPhoto() { return Photo; }

    public String getContent() {return Content;}

}
