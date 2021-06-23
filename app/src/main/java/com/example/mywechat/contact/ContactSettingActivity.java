package com.example.mywechat.contact;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mywechat.R;
import com.example.mywechat.data.Friend;
import com.example.mywechat.data.User;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

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

public class ContactSettingActivity extends AppCompatActivity {

    Button delete;

    User user;
    Friend friend;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_setting);

        user = new User();
        user.get(getFilesDir());

        intent = getIntent();
        friend = new Friend();
        friend.setNumber(intent.getIntExtra("Number", 0));
        friend.get(getFilesDir());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        delete = findViewById(R.id.ContactSettingActivity_Delete);
        delete.setOnClickListener(v -> {
            RequestBody requestBody = new FormBody.Builder()
                    .add("contact", friend.getID()).build();

            Request request = new Request.Builder().url("https://test.extern.azusa.one:7541/user/contact")
                    .header("Cookie", user.getCookie()).delete(requestBody).build();
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

                    try {
                        JSONObject jsonObject = new JSONObject(responseData);
                        if (jsonObject.getBoolean("success")) {
                            runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Delete Success!", Toast.LENGTH_LONG).show());
                            File FrienddeleteJson = new File(getFilesDir(), "FriendJson" + friend.getNumber());
                            if (FrienddeleteJson.exists()) {
                                FrienddeleteJson.delete();
                            }
                        }
                    } catch (JSONException e) {
                        Log.d("JSONException", e.getMessage());
                    }

                }
            });
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish(); // back button
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}