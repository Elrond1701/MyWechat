package com.example.mywechat.data;

import java.lang.String;
import java.util.LinkedList;

public class Group {
    private int Number;
    private String Name;
    private int Profile;
    private LinkedList<GroupMember> GroupMembers;

    public Group(int number, String name, int profile, LinkedList<GroupMember> groupmembers){
        Number = number;
        Name = name;
        Profile = profile;
        GroupMembers = groupmembers;
    }

    public int getNumber() {return Number;}
    public String getName() {
        return Name;
    }
    public int getProfile() {return Profile;}
    public LinkedList<GroupMember> getGroupMember() {return GroupMembers;}
}