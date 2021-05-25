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
import com.example.mywechat.ui.me.myprofile.change.IDChangeActivity;
import com.example.mywechat.ui.me.myprofile.change.NicknameChangeActivity;
import com.example.mywechat.ui.me.myprofile.change.ProfileChangeActivity;
import com.example.mywechat.ui.me.myprofile.change.RegionChangeActivity;
import com.example.mywechat.ui.me.myprofile.change.WhatsUpChangeActivity;

import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MyprofileActivity extends AppCompatActivity {
    private ActionBar actionBar;

    Intent intent;

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

    public static final int PROFILE = 101;
    public static final int NICKNAME = 102;
    public static final int ID = 103;
    public static final int GENDER = 104;
    public static final int REGION = 105;
    public static final int WHATSUP = 106;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

        actionBar = getSupportActionBar();
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
        friend.setPhoneNumber(intent.getStringExtra("ID"));
        friend.setRegion(intent.getStringExtra("Region"));
        friend.setWhatsUp(intent.getStringExtra("WhatsUp"));

        profile = findViewById(R.id.MyprofileActivity_Profile);
        profile.setImageBitmap(friend.getProfile());
        profileLayout = findViewById(R.id.MyprofileActivity_Layout1);
        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyprofileActivity.this, ProfileChangeActivity.class);
                intent.putExtra("ProfileDir", friend.getProfileDir());
                startActivityForResult(intent, PROFILE);
            }
        });

        nickname = findViewById(R.id.MyprofileActivity_Nickname);
        nickname.setText(friend.getNickname());
        nicknameLayout = findViewById(R.id.MyprofileActivity_Layout2);
        nicknameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyprofileActivity.this, NicknameChangeActivity.class);
                intent.putExtra("Nickname", friend.getNickname());
                startActivityForResult(intent, NICKNAME);
            }
        });

        id = findViewById(R.id.MyprofileActivity_PhoneNumber);
        id.setText(friend.getPhoneNumber());
        idLayout = findViewById(R.id.MyprofileActivity_Layout3);
        idLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyprofileActivity.this, IDChangeActivity.class);
                intent.putExtra("ID", friend.getPhoneNumber());
                startActivityForResult(intent, ID);
            }
        });

        gender = findViewById(R.id.MyprofileActivity_Gender);
        gender.setText(friend.getGender());
        genderLayout = findViewById(R.id.MyprofileActivity_Layout4);
        genderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyprofileActivity.this, GenderChangeActivity.class);
                intent.putExtra("Gender", friend.getGender());
                startActivityForResult(intent, GENDER);
            }
        });

        region = findViewById(R.id.MyprofileActivity_Region);
        region.setText(friend.getRegion());
        regionLayout = findViewById(R.id.MyprofileActivity_Layout5);
        regionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyprofileActivity.this, RegionChangeActivity.class);
                intent.putExtra("Region", friend.getRegion());
                startActivityForResult(intent, REGION);
            }
        });

        whatsup = findViewById(R.id.MyprofileActivity_WhatsUp);
        whatsup.setText(friend.getWhatsUp());
        whatsupLayout = findViewById(R.id.MyprofileActivity_Layout6);
        whatsupLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyprofileActivity.this, WhatsUpChangeActivity.class);
                intent.putExtra("WhatsUp", friend.getWhatsUp());
                startActivityForResult(intent, WHATSUP);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                intent.putExtra("Nickname", friend.getNickname());
                intent.putExtra("ID", friend.getPhoneNumber());
                intent.putExtra("Gender", friend.getGender());
                intent.putExtra("Region", friend.getRegion());
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
                switch (resultCode) {
                    case 0:
                        break;
                    case 1:
                        if (data.getByteArrayExtra("Profile") != null) {
                            byte[] temp = data.getByteArrayExtra("Profile");
                            Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
                            friend.setProfile(bitmap);
                            profile.setImageBitmap(bitmap);
                        }
                    default:
                        Toast.makeText(this, "Profile Wrong set", Toast.LENGTH_LONG).show();
                        break;
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
            case ID:
                switch (resultCode) {
                    case 0:
                        break;
                    case 1:
                        friend.setPhoneNumber(data.getStringExtra("ID"));
                        id.setText(friend.getPhoneNumber());
                        break;
                    default:
                        Toast.makeText(this, "ID Wrong set", Toast.LENGTH_LONG).show();
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
            case REGION:
                switch (resultCode) {
                    case 0:
                        break;
                    case 1:
                        friend.setRegion(data.getStringExtra("Region"));
                        region.setText(friend.getRegion());
                        break;
                    default:
                        Toast.makeText(this, "Region Wrong set", Toast.LENGTH_LONG).show();
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