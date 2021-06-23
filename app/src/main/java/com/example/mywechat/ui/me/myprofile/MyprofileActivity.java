package com.example.mywechat.ui.me.myprofile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mywechat.R;
import com.example.mywechat.data.User;
import com.example.mywechat.ui.me.myprofile.change.BirthDateChangeActivity;
import com.example.mywechat.ui.me.myprofile.change.GenderChangeActivity;
import com.example.mywechat.ui.me.myprofile.change.NicknameChangeActivity;
import com.example.mywechat.ui.me.myprofile.change.ProfileChangeActivity;
import com.example.mywechat.ui.me.myprofile.change.WhatsUpChangeActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MyprofileActivity extends AppCompatActivity {

    Intent intent;

    User user;

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

        show();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            intent.putExtra("ProfileDir", user.getProfileDir());
            intent.putExtra("Nickname", user.getNickname());
            intent.putExtra("ID", user.getID());
            intent.putExtra("Gender", user.getGender());
            intent.putExtra("BirthDate", user.getBirthDate());
            intent.putExtra("WhatsUp", user.getWhatsUp());
            user.save(getFilesDir());
            this.setResult(0, intent);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        show();

        switch (requestCode) {
            case PROFILE:
                if (resultCode == 0) {
                    Bitmap bitmap = null;
                    File file = null;
                    try {
                        assert data != null;
                        file = new File(this.getFilesDir(), "UserProfile");
                    } catch (NullPointerException e) {
                        Toast.makeText(this, "FileNotFoundException*" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    try {
                        FileInputStream fileInputStream = new FileInputStream(file);
                        bitmap = BitmapFactory.decodeStream(fileInputStream);
                    } catch (FileNotFoundException e) {
                        Toast.makeText(this, "FileNotFoundException" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    profile.setImageBitmap(user.getProfile());
                } else {
                        Toast.makeText(this, "Profile Wrong set", Toast.LENGTH_LONG).show();
                    }
                break;
            case NICKNAME:
                switch (resultCode) {
                    case 0:
                        break;
                    case 1:
                        assert data != null;
                        user.setNickname(data.getStringExtra("Nickname"));
                        nickname.setText(user.getNickname());
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
                        assert data != null;
                        user.setGender(data.getStringExtra("Gender"));
                        gender.setText(user.getGender());
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
                        assert data != null;
                        user.setBirthDate(data.getStringExtra("BirthDate"));
                        birthdate.setText(user.getBirthDate());
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
                        assert data != null;
                        user.setWhatsUp(data.getStringExtra("WhatsUp"));
                        whatsup.setText(user.getWhatsUp());
                        break;
                    default:
                        Toast.makeText(this, "What's up Wrong set", Toast.LENGTH_LONG).show();
                        break;
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        user.save(getFilesDir());
    }

    private void show() {
        intent = getIntent();
        user = new User();
        user.get(getFilesDir());

        user.setProfileDir(intent.getStringExtra("ProfileDir"));
        user.setGender(intent.getStringExtra("Gender"));
        user.setNickname(intent.getStringExtra("Nickname"));
        user.setID(intent.getStringExtra("ID"));
        user.setBirthDate(intent.getStringExtra("Region"));
        user.setWhatsUp(intent.getStringExtra("WhatsUp"));
        user.setBirthDate(intent.getStringExtra("BirthDate"));

        profile = findViewById(R.id.MyprofileActivity_Profile);
        profile.setImageBitmap(user.getProfile());
        View profileLayout = findViewById(R.id.MyprofileActivity_Layout1);
        profileLayout.setOnClickListener(v -> {
            Intent intent = new Intent(MyprofileActivity.this, ProfileChangeActivity.class);
            intent.putExtra("ProfileDir", user.getProfileDir());
            startActivityForResult(intent, PROFILE);
        });

        nickname = findViewById(R.id.MyprofileActivity_Nickname);
        nickname.setText(user.getNickname());
        View nicknameLayout = findViewById(R.id.MyprofileActivity_Layout2);
        nicknameLayout.setOnClickListener(v -> {
            Intent intent = new Intent(MyprofileActivity.this, NicknameChangeActivity.class);
            intent.putExtra("Nickname", user.getNickname());
            startActivityForResult(intent, NICKNAME);
        });

        gender = findViewById(R.id.MyprofileActivity_Gender);
        gender.setText(user.getGender());
        View genderLayout = findViewById(R.id.MyprofileActivity_Layout4);
        genderLayout.setOnClickListener(v -> {
            Intent intent = new Intent(MyprofileActivity.this, GenderChangeActivity.class);
            intent.putExtra("Gender", user.getGender());
            startActivityForResult(intent, GENDER);
        });

        birthdate = findViewById(R.id.MyprofileActivity_Region);
        birthdate.setText(user.getBirthDate());
        View regionLayout = findViewById(R.id.MyprofileActivity_Layout5);
        regionLayout.setOnClickListener(v -> {
            Intent intent = new Intent(MyprofileActivity.this, BirthDateChangeActivity.class);
            intent.putExtra("BirthDate", user.getBirthDate());
            startActivityForResult(intent, BIRTHDATE);
        });

        whatsup = findViewById(R.id.MyprofileActivity_WhatsUp);
        whatsup.setText(user.getWhatsUp());
        View whatsupLayout = findViewById(R.id.MyprofileActivity_Layout6);
        whatsupLayout.setOnClickListener(v -> {
            Intent intent = new Intent(MyprofileActivity.this, WhatsUpChangeActivity.class);
            intent.putExtra("WhatsUp", user.getWhatsUp());
            startActivityForResult(intent, WHATSUP);
        });
    }
}