package com.example.mywechat;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mywechat.data.User;
import com.example.mywechat.ui.chats.ChatsFragment;
import com.example.mywechat.ui.contacts.ContactsFragment;
import com.example.mywechat.ui.discover.DiscoverFragment;
import com.example.mywechat.ui.me.MeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.util.Objects;

import javax.net.SocketFactory;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class MainActivity extends AppCompatActivity {

    ChatsFragment chatsFragment;
    ContactsFragment contactsFragment;
    DiscoverFragment discoverFragment;
    MeFragment meFragment;
    SQLiteDatabase db;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.MainActivity_NavView);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.Navigation_Chats, R.id.Navigation_Contacts, R.id.Navigation_Discover, R.id.Navigation_Me)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.MainActivity_NavHostFragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        db = openOrCreateDatabase("test", Context.MODE_PRIVATE,null);
        String sql="CREATE TABLE IF NOT EXISTS chatlist (Nickname VARCHAR(32),LastSpeak VARCHAR(1024),LastSpeakTime VARCHAR(1024),chatId VARCHAR(128), isGroupChat Integer)";
        db.execSQL(sql);

        intent = getIntent();
        User user = new User();
        user.setID(intent.getStringExtra("UserName"));
        user.setPassword(intent.getStringExtra("Password"));
        user.setNickname(intent.getStringExtra("Nickname"));
        user.setBirthDate(intent.getStringExtra("BirthDate"));
        user.setGender(intent.getStringExtra("Gender"));
        user.setWhatsUp(intent.getStringExtra("WhatsUp"));
        user.setCookie(intent.getStringExtra("Cookie"));

        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url("wss://test.extern.azusa.one:7542/ws").build();
        WebSocket webSocket = okHttpClient.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                super.onClosed(webSocket, code, reason);
                Log.d("onClosed:", reason);
            }

            @Override
            public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                super.onClosing(webSocket, code, reason);
                Log.d("onClosed:", reason);
            }

            @Override
            public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
                super.onFailure(webSocket, t, response);
                runOnUiThread(() -> {
                    try {
                        Log.d("onFailure", response.body().string());
                    } catch (IOException e) {
                        Log.d("onFailure", e.getMessage());
                    }
                });
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
                super.onMessage(webSocket, text);
                try {
                    JSONObject jsonObject = new JSONObject(text);
                    if (jsonObject.getString("messageType").equals("chat")){
                        JSONObject message = jsonObject.getJSONObject("message");
                        String chatId = message.getString("chatId");
                        String delete_sql = "DELETE FROM chatlist where chatId = " + chatId;
                        db.execSQL(delete_sql);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("LastSpeak",jsonObject.getString("content"));
                        contentValues.put("LastSpeakTime",message.getString("time"));
                        contentValues.put("chatId",chatId);
                        String groupchatName = jsonObject.getString("groupchatName");
                        if (groupchatName == null){
                            contentValues.put("Nickname",message.getString("speaker"));
                            contentValues.put("isGroupChat",0);
                        }
                        else{
                            contentValues.put("Nickname",groupchatName);
                            contentValues.put("isGroupChat",1);
                        }
                        db.insert("chatlist",null,contentValues);
                    }
                    Log.d("onMessage", jsonObject.toString());
                } catch (JSONException e) {
                    Log.d("onMessage", e.getMessage());
                }
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
                super.onMessage(webSocket, bytes);
                Log.d("onMessage", String.valueOf(bytes));
            }

            @Override
            public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                super.onOpen(webSocket, response);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("bizType", "USER_LOGIN");
                    jsonObject.put("username", user.getID());
                    jsonObject.put("password", user.getPassword());
                    Log.d("onOpen", jsonObject.toString());
                } catch (JSONException e) {
                    Log.d("onOpen", e.getMessage());
                }
                webSocket.send(jsonObject.toString());
            }
        });

        final Request request1 = new Request.Builder().url("https://test.extern.azusa.one:7541/user/advatar")
                .header("Cookie", user.getCookie()).get().build();
        OkHttpClient okHttpClient1 = new OkHttpClient();
        Call call = okHttpClient1.newCall(request1);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("onFailureDownloadAvatar", e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = Objects.requireNonNull(response.body()).string();
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    Log.d("DOWN", jsonObject.toString());
                    if (jsonObject.getBoolean("success")) {
                        runOnUiThread(() -> {
                            try {
                                Toast.makeText(getApplicationContext(), "ERROR:" + jsonObject.getString("msg"), Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), "ERROR:" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        runOnUiThread(() -> {
                            try {
                                Toast.makeText(getApplicationContext(), "ERROR:" + jsonObject.getString("msg"), Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), "ERROR:" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (JSONException e) {
                    Log.d("onResponse", e.getMessage());
                }
            }
        });

        user.setProfileDir("");
        JSONObject user_save = new JSONObject();
        try {
            user_save.put("UserName", user.getID());
            user_save.put("Password", user.getPassword());
            user_save.put("Nickname", user.getNickname());
            user_save.put("Gender", user.getGender());
            user_save.put("BirthDate", user.getBirthDate());
            user_save.put("WhatsUp", user.getWhatsUp());
            user_save.put("ProfileDir", user.getProfileDir());
            user_save.put("Cookie", user.getCookie());
        } catch (JSONException e) {
            Log.d("User Save ERROR", e.getMessage());
        }
        final File UserJsonFile = new File(getFilesDir(), "UserJson");
        final FileOutputStream out;
        try {
            out = new FileOutputStream(UserJsonFile);
            out.write(user_save.toString().getBytes());
        } catch (FileNotFoundException e) {
            Log.d("FileNotFound ERROR", e.getMessage());
        } catch (IOException e) {
            Log.d("IO ERROR", e.getMessage());
        }
    }
}