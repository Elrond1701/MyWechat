package com.example.mywechat.contact;

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

import com.example.mywechat.ChatActivity;
import com.example.mywechat.R;
import com.example.mywechat.data.Friend;
import com.example.mywechat.ui.chats.MsgActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avatar1);
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
            String cookie = "";
            String chatId = "";
            String username = "";
            File UserJsonFile = new File(this.getFilesDir(), "UserJson");
            FileInputStream in;
            String JsonData;
            JSONObject user_get;
            try {
                in = new FileInputStream(UserJsonFile);
                byte[] bytes = new byte[in.available()];
                in.read(bytes);
                JsonData = new String(bytes);
            } catch (FileNotFoundException e) {
                Log.d("FileNotFoundERROR", e.getMessage());
                JsonData = null;
            } catch (IOException e) {
                Log.d("IOERROR", e.getMessage());
                JsonData = null;
            }
            if (JsonData != null) {
                try {
                    user_get = new JSONObject(JsonData);
                } catch (JSONException e) {
                    user_get = null;
                    Log.d("JSONERROR", e.getMessage());
                }
                try {
                    cookie = user_get.getString("Cookie");
                } catch (JSONException e) {
                    Log.d("GET COOKIE ERROR", e.getMessage());
                }
                try {
                    username = user_get.getString("UserName");
                } catch (JSONException e) {
                    Log.d("GET USERNAME ERROR", e.getMessage());
                }

            }
            Intent newIntent = new Intent(ContactActivity.this, MsgActivity.class);
            // Add more information to intent
//            newIntent.putExtra("id",friend.getID());
            final Request request = new Request.Builder().url("https://test.extern.azusa.one:7541/chat/id?contact="+friend.getID()).header("cookie",cookie).get().build();
            OkHttpClient okHttpClient = new OkHttpClient();
            Call call = okHttpClient.newCall(request);
//        String responseData = call.execute().string();
//        call.enqueue(new Callback() {
            Response response = null;
            try {
                response = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String responseData = null;
            try {
                responseData = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                JSONObject jsonObject = new JSONObject(responseData);
                System.out.println(jsonObject);
                if (jsonObject.getBoolean("success")){
                    chatId = jsonObject.getString("chatId");
                }
//            return commentList;
            } catch (JSONException e) {
                e.printStackTrace();
            }

//            newIntent.putExtra("profile",friend.getProfile());
            newIntent.putExtra("cookie",cookie);
            newIntent.putExtra("chatId",chatId);
            newIntent.putExtra("username",username);
            System.out.println(chatId);
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