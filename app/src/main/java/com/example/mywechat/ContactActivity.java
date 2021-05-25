package com.example.mywechat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mywechat.data.Friend;

public class ContactActivity extends AppCompatActivity {

    ActionBar actionBar;
    Friend friend;

    ImageView profile;
    ImageView gender;
    TextView nickname;
    TextView phonenumber;
    TextView region;
    TextView WhatsUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.myjpg);
        friend = new Friend();
        friend.setNumber(0);
        friend.setNickname("HIHI");
        friend.setPhoneNumber("12344");
        friend.setProfile(bitmap);
        friend.setGender("male");

        profile = findViewById(R.id.ContactActivity_Profile);
        profile.setImageBitmap(friend.getProfile());

        gender = findViewById(R.id.ContactActivity_Gender);
        if (friend.getGender().equals("male")) {
            gender.setImageResource(R.drawable.ic_male_blue_25dp);
        }
        else if (friend.getGender().equals("female")){
            gender.setImageResource(R.drawable.ic_female_blue_25dp);
        }

        nickname = findViewById(R.id.ContactActivity_Nickname);
        nickname.setText(friend.getNickname());

        phonenumber = findViewById(R.id.ContactActivity_PhoneNumber);
        phonenumber.setText(friend.getPhoneNumber());

        region = findViewById(R.id.ContactActivity_Region);
        region.setText(friend.getRegion());

        WhatsUp = findViewById(R.id.ContactActivity_WhatsUpText);
        WhatsUp.setText(friend.getWhatsUp());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}