package com.example.mywechat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mywechat.data.Friend;

public class ContactActivity extends AppCompatActivity {

    Friend friend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
    }
}