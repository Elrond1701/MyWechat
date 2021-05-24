package com.example.mywechat.ui.me.myprofile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mywechat.R;
import com.example.mywechat.data.Friend;
import com.example.mywechat.ui.me.myprofile.change.GenderChangeActivity;
import com.example.mywechat.ui.me.myprofile.change.IDChangeActivity;
import com.example.mywechat.ui.me.myprofile.change.NicknameChangeActivity;
import com.example.mywechat.ui.me.myprofile.change.ProfileChangeActivity;
import com.example.mywechat.ui.me.myprofile.change.RegionChangeActivity;
import com.example.mywechat.ui.me.myprofile.change.WhatsUpChangeActivity;

import org.w3c.dom.Text;

public class MyprofileActivity extends AppCompatActivity {
    private ActionBar actionBar;

    private Friend friend;
    private ImageView profile;
    private View profileLayout;
    private TextView nickname;
    private View nicknameLayout;
    private TextView id;
    private View idLayout;
    private TextView gender;
    private View genderLayout;
    private TextView region;
    private View regionLayout;
    private TextView whatsup;
    private View whatsupLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        friend = new Friend();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.myjpg);
        friend.setProfile(bitmap);
        friend.setGender("male");
        friend.setNickname("HIHI");
        friend.setNumber(0);
        friend.setPhoneNumber("12344");
        friend.setRegion("北京");
        friend.setWhatsUp("Very Good");

        profile = findViewById(R.id.MyprofileActivity_Profile);
        profile.setImageBitmap(friend.getProfile());
        profileLayout = findViewById(R.id.MyprofileActivity_Layout1);
        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyprofileActivity.this, ProfileChangeActivity.class);
                startActivity(intent);
            }
        });

        nickname = findViewById(R.id.MyprofileActivity_Nickname);
        nickname.setText(friend.getNickname());
        nicknameLayout = findViewById(R.id.MyprofileActivity_Layout2);
        nicknameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyprofileActivity.this, NicknameChangeActivity.class);
                startActivity(intent);
            }
        });

        id = findViewById(R.id.MyprofileActivity_PhoneNumber);
        id.setText(friend.getPhoneNumber());
        idLayout = findViewById(R.id.MyprofileActivity_Layout3);
        idLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyprofileActivity.this, IDChangeActivity.class);
                startActivity(intent);
            }
        });

        gender = findViewById(R.id.MyprofileActivity_Gender);
        gender.setText(friend.getGender());
        genderLayout = findViewById(R.id.MyprofileActivity_Layout4);
        genderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyprofileActivity.this, GenderChangeActivity.class);
                startActivity(intent);
            }
        });

        region = findViewById(R.id.MyprofileActivity_Region);
        region.setText(friend.getRegion());
        regionLayout = findViewById(R.id.MyprofileActivity_Layout5);
        regionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyprofileActivity.this, RegionChangeActivity.class);
                startActivity(intent);
            }
        });

        whatsup = findViewById(R.id.MyprofileActivity_WhatsUp);
        whatsup.setText(friend.getWhatsUp());
        whatsupLayout = findViewById(R.id.MyprofileActivity_Layout6);
        whatsupLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyprofileActivity.this, WhatsUpChangeActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}