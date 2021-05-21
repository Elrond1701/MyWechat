package com.example.mywechat.data;

import android.graphics.Bitmap;

import java.lang.String;
import java.util.LinkedList;

public class Group {
    private int Number;
    private String Name;
    private Bitmap Profile;
    private LinkedList<GroupMember> GroupMembers;

    public Group(int number, String name, Bitmap profile, LinkedList<GroupMember> groupmembers){
        Number = number;
        Name = name;
        Profile = profile;
        GroupMembers = groupmembers;
    }

    public int getNumber() {return Number;}
    public String getName() {
        return Name;
    }
    public Bitmap getProfile() {return Profile;}
    public LinkedList<GroupMember> getGroupMember() {return GroupMembers;}
}