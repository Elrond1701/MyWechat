package com.example.mywechat.ui.chats;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mywechat.data.Chat;

import java.util.LinkedList;

public class ChatsViewModel extends ViewModel {

    private LinkedList<Chat> chats;

    public ChatsViewModel() {

    }

    public void addChats(Chat chat) {
         chats.add(chat);
    }

    public void setChats(LinkedList<Chat> chats) {
        this.chats = chats;
    }

    public LinkedList<Chat> getChats() {
        return chats;
    }
}