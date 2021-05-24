package com.example.mywechat.data;

import java.lang.String;

public class Chat {
    private  int Profile; // 头像
    private String Nickname; // 昵称
    private String LastSpeak; //最后聊天内容
    private String LastSpeakTime; //最后联络时间


    public int getProfile() { return Profile; }

    public String getLastSpeak() { return LastSpeak; }

    public String getLastSpeakTime() { return LastSpeakTime; }

    public String getNickname() { return Nickname; }
}
