package com.example.mywechat.ui.discover;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mywechat.data.Chat;
import com.example.mywechat.data.Discover;

import java.util.LinkedList;

public class DiscoverViewModel extends ViewModel {

    private LinkedList<Discover> discovers;

    public DiscoverViewModel() {

    }

    public void addDiscovers(Discover discover) {
        discovers.add(discover);
    }

    public void setDiscovers(LinkedList<Discover> discovers) {
        this.discovers = discovers;
    }

    public LinkedList<Discover> getDiscovers() {
        return discovers;
    }
}