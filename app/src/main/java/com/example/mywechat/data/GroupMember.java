package com.example.mywechat.data;

public class GroupMember {
    private int Number;
    private String GroupName;
    private String Nickname;
    private int Profile;

    GroupMember(int number, String groupname, String nickname, int profile) {
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
    int getProfile() {
        return Profile;
    }
}
