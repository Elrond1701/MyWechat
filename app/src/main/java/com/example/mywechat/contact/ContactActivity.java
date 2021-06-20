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

import com.example.mywechat.ChatActivity;
import com.example.mywechat.R;
import com.example.mywechat.data.Friend;

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
        friend.setRegion(intent.getStringExtra("Region"));
        friend.setWhatsUp(intent.getStringExtra("WhatsUp"));

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
        region.setText(friend.getRegion());

        TextView whatsUp = findViewById(R.id.ContactActivity_WhatsUpText);
        whatsUp.setText(friend.getWhatsUp());

        Button messages = findViewById(R.id.ContactActivity_Messages);
        messages.setOnClickListener(v -> {
            Intent newIntent = new Intent(ContactActivity.this, ChatActivity.class);
            startActivity(newIntent);
        });

        Button settings = findViewById(R.id.ContactActivity_Settings);
        settings.setOnClickListener(v -> {
            Intent newIntent = new Intent(ContactActivity.this, ContactSettingActivity.class);
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