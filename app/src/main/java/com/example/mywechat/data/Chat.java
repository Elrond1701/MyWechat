package com.example.mywechat.data;

import java.lang.String;

public class Chat {
//    private  int Profile; // 头像
    private final String Nickname; // 昵称
    private final String LastSpeak; //最后聊天内容
    private final String LastSpeakTime; //最后联络时间

    public Chat(String Nickname, int Profile, String LastSpeak, String LastSpeakTime) {
        this.Nickname = Nickname;
//        this.Profile = Profile;
        this.LastSpeak = LastSpeak;
        this.LastSpeakTime = LastSpeakTime;
    }

//    public int getProfile() { return Profile; }

    public String getLastSpeak() { return LastSpeak; }

    public String getLastSpeakTime() { return LastSpeakTime; }

    public String getNickname() { return Nickname; }
}
