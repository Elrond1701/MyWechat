package com.example.mywechat.contact;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mywechat.R;
import com.example.mywechat.data.Friend;

public class ContactActivity extends AppCompatActivity {

    private ActionBar actionBar;

    private Friend friend;

    private Intent intent;

    private ImageView profile;
    private ImageView gender;
    private TextView nickname;
    private TextView phonenumber;
    private TextView region;
    private TextView WhatsUp;
    private Button messages;
    private Button settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        intent = getIntent();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.myjpg);
        friend = new Friend();
        friend.setNickname(intent.getStringExtra("Nickname"));
        friend.setPhoneNumber(intent.getStringExtra("ID"));
        friend.setProfile(bitmap);
        friend.setGender(intent.getStringExtra("Gender"));
        friend.setRegion(intent.getStringExtra("Region"));
        friend.setWhatsUp(intent.getStringExtra("WhatsUp"));

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

        messages = findViewById(R.id.ContactActivity_Messages);
        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactActivity.this, ContactActivity.class);
                startActivity(intent);
            }
        });

        settings = findViewById(R.id.ContactActivity_Settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactActivity.this, ContactSettingActivity.class);
                startActivity(intent);
            }
        });
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