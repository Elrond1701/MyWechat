package com.example.mywechat.ui.contacts.newfriend;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mywechat.R;
import com.example.mywechat.data.Friend;
import com.example.mywechat.data.User;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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

public class AddNewfriendActivity extends AppCompatActivity {

    Intent intent;

    Friend friend;
    User user;

    TextView username;
    ImageView profile;
    TextView nickname;
    TextView gender;
    TextView birthdate;
    TextView whatsup;

    Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_newfriend);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        intent = getIntent();

        friend = new Friend();
        friend.setWhatsUp(intent.getStringExtra("WhatsUp"));
        friend.setGender(intent.getStringExtra("Gender"));
        friend.setNickname(intent.getStringExtra("Nickname"));
        friend.setID(intent.getStringExtra("ID"));
        friend.setBirthDate(intent.getStringExtra("BirthDate"));

        user = new User();
        user.get(getFilesDir());

        username = findViewById(R.id.AddNewfriendActivity_Username);
        profile = findViewById(R.id.AddNewfriendActivity_Profile);
        nickname = findViewById(R.id.AddNewfriendActivity_Nickname);
        gender = findViewById(R.id.AddNewfriendActivity_Gender);
        birthdate = findViewById(R.id.AddNewfriendActivity_Region);
        whatsup = findViewById(R.id.AddNewfriendActivity_WhatsUp);

        username.setText(friend.getID());
        nickname.setText(friend.getNickname());
        gender.setText(friend.getGender());
        birthdate.setText(friend.getBirthDate());
        whatsup.setText(friend.getWhatsUp());

        done = findViewById(R.id.AddNewfriendActivity_Done);
        done.setOnClickListener(v -> {
            Log.d("Hello", friend.getID());
            RequestBody requestBody = new FormBody.Builder().add("contact", friend.getID())
                    .add("note", "GOOD").build();
            final Request request = new Request.Builder().url("https://test.extern.azusa.one:7541/user/contact")
                    .header("Cookie", user.getCookie()).post(requestBody).build();
            OkHttpClient okHttpClient = new OkHttpClient();
            Call call = okHttpClient.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.d("onFailure ERROR", e.getMessage());
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String responseData = Objects.requireNonNull(response.body()).string();
                    Log.d("UserJsonFile", responseData);
                    try {
                        JSONObject jsonObject = new JSONObject(responseData);
                        if (jsonObject.getBoolean("success")) {
                            Log.d("UserJsonFile", "success");
                        } else {
                            Log.d("UserJsonFile", "fail");
                        }
                    } catch (JSONException e) {
                        Log.d("JsonERROR", e.getMessage());
                    }
                }
            });

            AddNewfriendActivity.this.finish();
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