package com.example.mywechat.data;

import android.graphics.Bitmap;

import java.lang.String;
import java.util.LinkedList;

public class Group {
    private int number;
    private String name;
    private Bitmap profile;
    private String profiledir;
    private LinkedList<GroupMember> groupmembers;

    public Group() {
        number = -1;
        name = null;
        profile = null;
        profiledir = null;
        groupmembers = null;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getProfile() {
        return profile;
    }

    public void setProfile(Bitmap profile) {
        this.profile = profile;
    }

    public String getProfileDir() {
        return profiledir;
    }

    public void setProfileDir(String profiledir) {
        this.profiledir = profiledir;
    }

    public LinkedList<GroupMember> getGroupMember() {
        return groupmembers;
    }

    public void setGroupmembers(LinkedList<GroupMember> groupmembers) {
        this.groupmembers = groupmembers;
    }
}