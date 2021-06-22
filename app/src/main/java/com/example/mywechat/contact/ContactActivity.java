package com.example.mywechat.contact;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mywechat.ChatActivity;
import com.example.mywechat.R;
import com.example.mywechat.data.Friend;
import com.example.mywechat.ui.chats.MsgActivity;

import java.io.File;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.myjpg);
        Friend friend = new Friend();
        friend.setNickname(intent.getStringExtra("Nickname"));
        friend.setID(intent.getStringExtra("ID"));
        friend.setProfile(bitmap);
        friend.setGender(intent.getStringExtra("Gender"));
        friend.setBirthDate(intent.getStringExtra("BirthDate"));
        friend.setWhatsUp(intent.getStringExtra("WhatsUp"));
        friend.setNumber(intent.getIntExtra("Number", 0));
        //File FriendJsonFile = new File(getFilesDir(), "FriendJson" + friend.getNumber());
        //friend.get(FriendJsonFile);

        ImageView profile = findViewById(R.id.ContactActivity_Profile);
        profile.setImageBitmap(friend.getProfile());

        ImageView gender = findViewById(R.id.ContactActivity_Gender);
        if (friend.getGender().equals("male")) {
            gender.setImageResource(R.drawable.ic_male_blue_25dp);
        }
        else if (friend.getGender().equals("female")){
            gender.setImageResource(R.drawable.ic_female_blue_25dp);
        }

        TextView nickname = findViewById(R.id.ContactActivity_Nickname);
        nickname.setText(friend.getNickname());

        TextView phonenumber = findViewById(R.id.ContactActivity_PhoneNumber);
        phonenumber.setText(friend.getID());

        TextView region = findViewById(R.id.ContactActivity_Region);
        region.setText(friend.getBirthDate());

        TextView whatsUp = findViewById(R.id.ContactActivity_WhatsUpText);
        whatsUp.setText(friend.getWhatsUp());

        Button messages = findViewById(R.id.ContactActivity_Messages);
        messages.setOnClickListener(v -> {
//            Intent newIntent = new Intent(ContactActivity.this, ChatActivity.class);
//            startActivity(newIntent);
            Intent newIntent = new Intent(ContactActivity.this, MsgActivity.class);
            // Add more information to intent
            newIntent.putExtra("id",friend.getID());
//            newIntent.putExtra("profile",friend.getProfile());
            startActivity(newIntent);
        });

        Button settings = findViewById(R.id.ContactActivity_Settings);
        settings.setOnClickListener(v -> {
            Intent newIntent = new Intent(ContactActivity.this, ContactSettingActivity.class);
            newIntent.putExtra("Number", friend.getNumber());
            startActivity(newIntent);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}