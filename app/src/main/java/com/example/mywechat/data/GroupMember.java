package com.example.mywechat.data;

import android.graphics.Bitmap;

public class GroupMember {
    private int Number;
    private String GroupName;
    private String Nickname;
    private Bitmap Profile;

    GroupMember(int number, String groupname, String nickname, Bitmap profile) {
        Number = number;
        GroupName = groupname;
        Nickname = nickname;
        Profile = profile;
    }

    int getNumber() {
        return Number;
    }
    String getGroupName() {
        return GroupName;
    }
    String getNickname() {
        return Nickname;
    }
    Bitmap getProfile() {
        return Profile;
    }
}
