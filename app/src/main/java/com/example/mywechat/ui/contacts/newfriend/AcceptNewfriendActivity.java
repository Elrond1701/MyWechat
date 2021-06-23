package com.example.mywechat.ui.contacts.newfriend;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mywechat.R;
import com.example.mywechat.data.Friend;
import com.example.mywechat.data.Newfriend;
import com.example.mywechat.data.User;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AcceptNewfriendActivity extends AppCompatActivity {

    Intent intent;

    Newfriend newfriend;
    User user;

    TextView username;
    ImageView profile;
    TextView nickname;
    TextView gender;
    TextView birthdate;
    TextView whatsup;

    Button accept;
    Button deny;

    int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_newfriend);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        intent = getIntent();
        number = intent.getIntExtra("number", 0);

        username = findViewById(R.id.AcceptNewfriendActivity_Username);
        profile = findViewById(R.id.AcceptNewfriendActivity_Profile);
        nickname = findViewById(R.id.AcceptNewfriendActivity_Nickname);
        gender = findViewById(R.id.AcceptNewfriendActivity_Gender);
        birthdate = findViewById(R.id.AcceptNewfriendActivity_Region);
        whatsup = findViewById(R.id.AcceptNewfriendActivity_WhatsUp);

        newfriend = new Newfriend();
        newfriend.setNumber(number);
        newfriend.get(getFilesDir());

        username.setText(newfriend.getID());
        nickname.setText(newfriend.getNickname());
        gender.setText(newfriend.getGender());
        birthdate.setText(newfriend.getBirthDate());
        whatsup.setText(newfriend.getWhatsUp());
        profile.setImageBitmap(newfriend.getProfile());

        user = new User();
        user.get(getFilesDir());

        accept = findViewById(R.id.AcceptNewfriendActivity_Accept);
        accept.setOnClickListener(v -> {
            RequestBody requestBody = new FormBody.Builder()
                    .add("ContactApplyId", newfriend.getContactapplyId())
                    .add("Note", "Very Good").build();

            Request request = new Request.Builder().url("https://test.extern.azusa.one:7541/user/contact/approve")
                    .header("Cookie", user.getCookie()).post(requestBody).build();
            OkHttpClient okHttpClient = new OkHttpClient();
            Call call = okHttpClient.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.d("onFailure", e.getMessage());
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String responseData = Objects.requireNonNull(response.body()).string();
                    Log.d("onResponse", responseData);
                    File NewfriendJsonFile = new File(getFilesDir(), "NewfriendJson" + number);
                    if (NewfriendJsonFile.exists()) {
                        NewfriendJsonFile.delete();
                    }

                    File FriendJsonFile = null;
                    for (int i = 0; ; i++) {
                        FriendJsonFile = new File(getFilesDir(), "FriendJson" + i);
                        if (!FriendJsonFile.exists()) {
                            Friend friend = new Friend();
                            friend.setBirthDate(newfriend.getBirthDate());
                            friend.setID(newfriend.getID());
                            friend.setNickname(newfriend.getNickname());
                            friend.setGender(newfriend.getGender());
                            friend.setWhatsUp(newfriend.getWhatsUp());
                            friend.setProfileDir(newfriend.getProfileDir());
                            friend.setProfile(newfriend.getProfile());
                            friend.setContactapplyId(newfriend.getContactapplyId());
                            friend.setNumber(i);
                            friend.save(FriendJsonFile);
                            break;
                        }
                    }
                }
            });
            AcceptNewfriendActivity.this.finish();
        });

        deny = findViewById(R.id.AcceptNewfriendActivity_Reject);
        deny.setOnClickListener(v -> {
            RequestBody requestBody = new FormBody.Builder()
                    .add("ContactApplyId", newfriend.getContactapplyId()).build();

            Request request = new Request.Builder().url("https://test.extern.azusa.one:7541/user/contact/reject")
                    .header("Cookie", user.getCookie()).post(requestBody).build();
            OkHttpClient okHttpClient = new OkHttpClient();
            Call call = okHttpClient.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.d("onFailure", e.getMessage());
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String responseData = Objects.requireNonNull(response.body()).string();
                    Log.d("onResponse", responseData);
                    File NewfriendJsonFile = new File(getFilesDir(), "NewfriendJson" + number);
                    if (NewfriendJsonFile.exists()) {
                        NewfriendJsonFile.delete();
                    }
                }
            });
            AcceptNewfriendActivity.this.finish();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}