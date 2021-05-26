package com.example.mywechat.ui.contacts;

import androidx.lifecycle.ViewModel;

import com.example.mywechat.data.Friend;
import com.example.mywechat.data.Group;

import java.util.LinkedList;

public class ContactsViewModel extends ViewModel {

    private LinkedList<Friend> friends;
    private LinkedList<Group> groups;

    public ContactsViewModel() {

    }

    public void addFriend(Friend friend) {
        friends.add(friend);
    }

    public void setFriends(LinkedList<Friend> friends) {
        this.friends = friends;
    }

    public LinkedList<Friend> getFriends() {
        return friends;
    }

    public void setGroups(LinkedList<Group> groups) {
        this.groups = groups;
    }

    public LinkedList<Group> getGroups() {
        return groups;
    }
}