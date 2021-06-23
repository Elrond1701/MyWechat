package com.example.mywechat;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.example.mywechat.data.Friend;
import com.example.mywechat.data.Newfriend;
import com.example.mywechat.data.User;
import com.example.mywechat.ui.chats.ChatsFragment;
import com.example.mywechat.ui.contacts.ContactsFragment;
import com.example.mywechat.ui.contacts.newfriend.AcceptNewfriendActivity;
import com.example.mywechat.ui.contacts.newfriend.AddNewfriendActivity;
import com.example.mywechat.ui.contacts.newfriend.NewfriendActivity;
import com.example.mywechat.ui.discover.DiscoverFragment;
import com.example.mywechat.ui.me.MeFragment;
import com.example.mywechat.util.CommonDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Objects;
import java.util.TimerTask;

import javax.net.SocketFactory;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
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
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        BottomNavigationView navView = findViewById(R.id.MainActivity_NavView);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.Navigation_Chats, R.id.Navigation_Contacts, R.id.Navigation_Discover, R.id.Navigation_Me)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.MainActivity_NavHostFragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        CommonDatabase commonDatabase = new CommonDatabase(this,"chatlist",null,1);
        db = commonDatabase.getWritableDatabase();

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
                        assert response != null;
                        Log.d("onFailure", Objects.requireNonNull(response.body()).string());
                    } catch (IOException e) {
                        Log.d("onFailure", e.getMessage());
                    }
                });
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
                super.onMessage(webSocket, text);
                try {
                    JSONArray jsonArray = new JSONArray(text);
                    JSONObject jsonObject = (JSONObject) jsonArray.get(0);
                    if (jsonObject.getString("messageType").equals("CONTACT_APPLY")) {
                        Newfriend newfriend = new Newfriend();
                        String contactApply = jsonObject.getString("contactApply");
                        JSONObject contactApplyData = new JSONObject(contactApply);
                        if (contactApplyData.getString("contact").equals(user.getID())) {
                            newfriend.setContactapplyId(contactApplyData.getString("id"));
                            newfriend.setNote(contactApplyData.getString("note"));
                            newfriend.setID(contactApplyData.getString("username"));

                            final Request request = new Request.Builder().url("https://test.extern.azusa.one:7541/user?uname=" + newfriend.getID())
                                    .header("Cookie", user.getCookie()).get().build();
                            OkHttpClient okHttpClient = new OkHttpClient();
                            Call call = okHttpClient.newCall(request);

                            call.enqueue(new Callback() {
                                @Override
                                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "ERROR:" + e.getMessage(), Toast.LENGTH_LONG).show());
                                }

                                @Override
                                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                    String responseData = Objects.requireNonNull(response.body()).string();
                                    Log.d("ResponseHappy", responseData);
                                    try {
                                        JSONObject jsonObject_user = new JSONObject(responseData);
                                        if (jsonObject_user.getBoolean("success")) {
                                            newfriend.setBirthDate(jsonObject_user.getString("birthdate"));
                                            newfriend.setID(jsonObject_user.getString("username"));
                                            newfriend.setNickname(jsonObject_user.getString("nickname"));
                                            newfriend.setGender(jsonObject_user.getString("sex"));
                                            newfriend.setWhatsUp(jsonObject_user.getString("sign"));

                                            File JsonNewfriendFile;
                                            for (int i = 0; ; i++) {
                                                JsonNewfriendFile = new File(getFilesDir(), "NewfriendJson" + i);
                                                newfriend.setNumber(0);
                                                if (!JsonNewfriendFile.exists()) {
                                                    Log.d("BREAK", "BREAK");
                                                    break;
                                                }
                                            }

                                            Bitmap bitmap = null;
                                            OkHttpClient okHttpClientMAP = new OkHttpClient();
                                            Request requestMAP = new Request.Builder()
                                                    .url("https://test.extern.azusa.one:7543/target/avatar/" + newfriend.getID() + ".png")
                                                    .build();
                                            Response responseMAP = okHttpClientMAP.newCall(requestMAP).execute();
                                            byte[] bytes = Objects.requireNonNull(responseMAP.body()).bytes();
                                            Log.d("GOOD", newfriend.getID());
                                            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                            newfriend.setProfile(bitmap);
                                            Log.d("ABC", "ABD");
                                            newfriend.setProfile(bitmap);
                                            newfriend.save(getFilesDir());
                                            Log.d("HELLO", "HELLO");
                                        } else {
                                            runOnUiThread(() -> {
                                                try {
                                                    Toast.makeText(getApplicationContext(), "ERROR:" + jsonObject_user.getString("msg"), Toast.LENGTH_LONG).show();
                                                } catch (JSONException e) {
                                                    Toast.makeText(getApplicationContext(), "ERROR:" + e.getMessage(), Toast.LENGTH_LONG).show();
                                                }
                                            });
                                        }
                                    } catch (JSONException e) {
                                        runOnUiThread(() -> Toast.makeText(getApplicationContext(), "ERROR:" + e.getMessage(), Toast.LENGTH_LONG).show());
                                    }
                                }
                            });
                        }

                    }
                    if (jsonObject.getString("messageType").equals("chat")) {
                        JSONObject message = jsonObject.getJSONObject("message");
                        String chatId = message.getString("chatId");
                        String delete_sql = "DELETE FROM chatlist where chatId = " + chatId;
                        db.execSQL(delete_sql);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("LastSpeak", jsonObject.getString("content"));
                        contentValues.put("LastSpeakTime", message.getString("time"));
                        contentValues.put("chatId", chatId);
                        String groupchatName = jsonObject.getString("groupchatName");
                        contentValues.put("Nickname", groupchatName);
                        contentValues.put("isGroupChat", 1);
                        db.insert("chatlist", null, contentValues);
                    }
                    Log.d("onMessage", jsonArray.toString());
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

        final Request request1 = new Request.Builder().url("https://test.extern.azusa.one:7541/user/avatar")
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

        Request request3 = new Request.Builder()
                .url("https://test.extern.azusa.one:7543/avatar/" + user.getID() + ".png")
                .build();
        OkHttpClient okHttpClient3 = new OkHttpClient();
        try {
            ResponseBody body = okHttpClient3.newCall(request3).execute().body();
            assert body != null;
            InputStream in = body.byteStream();

            Bitmap bitmap = null;
            OkHttpClient okHttpClientMAP = new OkHttpClient();
            Request requestMAP = new Request.Builder()
                    .url("https://test.extern.azusa.one:7543/target/avatar/" + user.getID() + ".png")
                    .build();
            Response responseMAP = okHttpClientMAP.newCall(requestMAP).execute();
            byte[] bytes = Objects.requireNonNull(responseMAP.body()).bytes();
            Log.d("GOOD", bytes.toString());
            Log.d("GOOD", user.getID());
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            if (bitmap != null) {
                user.setProfile(bitmap);
            }
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avatar1);
            user.setProfile(bitmap);
            user.save(getFilesDir());
        } catch (IOException e) {
            Log.d("IOException", e.getMessage());
        }

        final Request request2 = new Request.Builder().url("https://test.extern.azusa.one:7541/user/contact")
                .header("Cookie", user.getCookie()).get().build();
        OkHttpClient okHttpClient2 = new OkHttpClient();
        Call call1 = okHttpClient2.newCall(request2);

        call1.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("onFailure", e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = Objects.requireNonNull(response.body()).string();
                Log.d("onResponseHIHIHI", responseData);
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    String Contacts = jsonObject.getString("contacts");

                    JSONArray jsonArray = new JSONArray(Contacts);

                    for (int i = 0; ; i++) {
                        File JsonFriend = new File(getFilesDir(), "FriendJson" + i);
                        File ProfileFriend = new File(getFilesDir(), "FriendProfile" + i);
                        if (JsonFriend.exists()) {
                            JsonFriend.delete();
                            if (ProfileFriend.exists()) {
                                ProfileFriend.delete();
                            }
                        } else {
                            break;
                        }
                    }

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject contact = (JSONObject) jsonArray.get(i);
                        if (contact.getString("username").equals(user.getID())) {
                            Friend friend = new Friend();
                            friend.setNumber(i);
                            friend.setContactapplyId(contact.getString("chatId"));
                            friend.setID(contact.getString("contact"));

                            Request request = new Request.Builder().url("https://test.extern.azusa.one:7541/user?uname=" + friend.getID())
                                    .header("Cookie", user.getCookie()).get().build();
                            OkHttpClient okHttpClient = new OkHttpClient();
                            Call call_1 = okHttpClient.newCall(request);

                            call_1.enqueue(new Callback() {
                                @Override
                                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                    Log.d("onFailure", e.getMessage());
                                }

                                @Override
                                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                    String reponseData = Objects.requireNonNull(response.body()).string();
                                    Log.d("onResponse", reponseData);
                                    try {
                                        JSONObject jsonObject1 = new JSONObject(reponseData);
                                        if (jsonObject1.getBoolean("success")) {
                                            friend.setBirthDate(jsonObject1.getString("birthdate"));
                                            friend.setNickname(jsonObject1.getString("nickname"));
                                            friend.setGender(jsonObject1.getString("sex"));
                                            friend.setWhatsUp(jsonObject1.getString("sign"));
                                            friend.setProfileDir("UserProfile");
                                            friend.save(getFilesDir());
                                        }
                                    } catch (JSONException e) {
                                        Log.d("JSONException", e.getMessage());
                                    }
                                }
                            });
                        }
                    }
                } catch (JSONException e) {
                    Log.d("JSONException", e.getMessage());
                }
            }
        });
    }
}