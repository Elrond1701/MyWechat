package com.example.mywechat.ui.me.myprofile;

import androidx.annotation.Nullable;
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
import android.widget.Toast;

import com.example.mywechat.R;
import com.example.mywechat.data.Friend;
import com.example.mywechat.ui.me.myprofile.change.GenderChangeActivity;
import com.example.mywechat.ui.me.myprofile.change.NicknameChangeActivity;
import com.example.mywechat.ui.me.myprofile.change.ProfileChangeActivity;
import com.example.mywechat.ui.me.myprofile.change.BirthDateChangeActivity;
import com.example.mywechat.ui.me.myprofile.change.WhatsUpChangeActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MyprofileActivity extends AppCompatActivity {

    Intent intent;

    private Friend friend;
    private ImageView profile;
    private TextView nickname;
    private TextView gender;
    private TextView whatsup;
    private TextView birthdate;

    public static final int PROFILE = 101;
    public static final int NICKNAME = 102;
    public static final int GENDER = 104;
    public static final int BIRTHDATE = 105;
    public static final int WHATSUP = 106;

    public MyprofileActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        intent = getIntent();
        friend = new Friend();
        Bitmap bitmap = null;
        File file = new File(this.getFilesDir(), intent.getStringExtra("ProfileDir"));
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(fileInputStream);
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "FileNotFoundException" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        friend.setProfile(bitmap);
        friend.setProfileDir(intent.getStringExtra("ProfileDir"));
        friend.setGender(intent.getStringExtra("Gender"));
        friend.setNickname(intent.getStringExtra("Nickname"));
        friend.setID(intent.getStringExtra("ID"));
        friend.setBirthDate(intent.getStringExtra("Region"));
        friend.setWhatsUp(intent.getStringExtra("WhatsUp"));
        friend.setBirthDate(intent.getStringExtra("BirthDate"));

        profile = findViewById(R.id.MyprofileActivity_Profile);
        profile.setImageBitmap(friend.getProfile());
        View profileLayout = findViewById(R.id.MyprofileActivity_Layout1);
        profileLayout.setOnClickListener(v -> {
            Intent intent = new Intent(MyprofileActivity.this, ProfileChangeActivity.class);
            intent.putExtra("ProfileDir", friend.getProfileDir());
            startActivityForResult(intent, PROFILE);
        });

        nickname = findViewById(R.id.MyprofileActivity_Nickname);
        nickname.setText(friend.getNickname());
        View nicknameLayout = findViewById(R.id.MyprofileActivity_Layout2);
        nicknameLayout.setOnClickListener(v -> {
            Intent intent = new Intent(MyprofileActivity.this, NicknameChangeActivity.class);
            intent.putExtra("Nickname", friend.getNickname());
            startActivityForResult(intent, NICKNAME);
        });

        gender = findViewById(R.id.MyprofileActivity_Gender);
        gender.setText(friend.getGender());
        View genderLayout = findViewById(R.id.MyprofileActivity_Layout4);
        genderLayout.setOnClickListener(v -> {
            Intent intent = new Intent(MyprofileActivity.this, GenderChangeActivity.class);
            intent.putExtra("Gender", friend.getGender());
            startActivityForResult(intent, GENDER);
        });

        birthdate = findViewById(R.id.MyprofileActivity_Region);
        birthdate.setText(friend.getBirthDate());
        View regionLayout = findViewById(R.id.MyprofileActivity_Layout5);
        regionLayout.setOnClickListener(v -> {
            Intent intent = new Intent(MyprofileActivity.this, BirthDateChangeActivity.class);
            intent.putExtra("BirthDate", friend.getBirthDate());
            startActivityForResult(intent, BIRTHDATE);
        });

        whatsup = findViewById(R.id.MyprofileActivity_WhatsUp);
        whatsup.setText(friend.getWhatsUp());
        View whatsupLayout = findViewById(R.id.MyprofileActivity_Layout6);
        whatsupLayout.setOnClickListener(v -> {
            Intent intent = new Intent(MyprofileActivity.this, WhatsUpChangeActivity.class);
            intent.putExtra("WhatsUp", friend.getWhatsUp());
            startActivityForResult(intent, WHATSUP);
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            intent.putExtra("ProfileDir", friend.getProfileDir());
            intent.putExtra("Nickname", friend.getNickname());
            intent.putExtra("ID", friend.getID());
            intent.putExtra("Gender", friend.getGender());
            intent.putExtra("BirthDate", friend.getBirthDate());
            intent.putExtra("WhatsUp", friend.getWhatsUp());
            this.setResult(0, intent);
            this.finish(); // back button
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PROFILE:
                if (resultCode == 0) {
                    Bitmap bitmap = null;
                    File file = null;
                    try {
                        assert data != null;
                        file = new File(this.getFilesDir(), data.getStringExtra("ProfileDir"));
                    } catch (NullPointerException e) {
                        Toast.makeText(this, "FileNotFoundException" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    try {
                        FileInputStream fileInputStream = new FileInputStream(file);
                        bitmap = BitmapFactory.decodeStream(fileInputStream);
                    } catch (FileNotFoundException e) {
                        Toast.makeText(this, "FileNotFoundException" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    profile.setImageBitmap(bitmap);
                } else {
                        Toast.makeText(this, "Profile Wrong set", Toast.LENGTH_LONG).show();
                    }
                break;
            case NICKNAME:
                switch (resultCode) {
                    case 0:
                        break;
                    case 1:
                        friend.setNickname(data.getStringExtra("Nickname"));
                        nickname.setText(friend.getNickname());
                        break;
                    default:
                        Toast.makeText(this, "Nickname Wrong set", Toast.LENGTH_LONG).show();
                        break;
                }
                break;
            case GENDER:
                switch (resultCode) {
                    case 0:
                        break;
                    case 1:
                        friend.setGender(data.getStringExtra("Gender"));
                        gender.setText(friend.getGender());
                        break;
                    default:
                        Toast.makeText(this, "Gender Wrong set", Toast.LENGTH_LONG).show();
                        break;
                }
                break;
            case BIRTHDATE:
                switch (resultCode) {
                    case 0:
                        break;
                    case 1:
                        friend.setBirthDate(data.getStringExtra("BirthDate"));
                        birthdate.setText(friend.getBirthDate());
                        break;
                    default:
                        Toast.makeText(this, "BirthDate Wrong set", Toast.LENGTH_LONG).show();
                        break;
                }
                break;
            case WHATSUP:
                switch (resultCode) {
                    case 0:
                        break;
                    case 1:
                        friend.setWhatsUp(data.getStringExtra("WhatsUp"));
                        whatsup.setText(friend.getWhatsUp());
                        break;
                    default:
                        Toast.makeText(this, "What's up Wrong set", Toast.LENGTH_LONG).show();
                        break;
                }
                break;
        }
    }
}