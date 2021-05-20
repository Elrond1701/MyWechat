package com.example.mywechat.ui.contacts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mywechat.data.Friend;

import java.util.LinkedList;

public class ContactsViewModel extends ViewModel {

    private LinkedList<Friend> friends;

    public ContactsViewModel() {

    }

    public void addFriends(Friend friend) {
        friends.add(friend);
    }

    public void setFriends(LinkedList<Friend> newfriends) {friends = newfriends;}

    public LinkedList<Friend> getFriends() {
        return friends;
    }
}