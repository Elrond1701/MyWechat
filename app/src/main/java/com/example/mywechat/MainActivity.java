package com.example.mywechat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.mywechat.data.User;
import com.example.mywechat.ui.chats.ChatsFragment;
import com.example.mywechat.ui.contacts.ContactsFragment;
import com.example.mywechat.ui.discover.DiscoverFragment;
import com.example.mywechat.ui.me.MeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.text.Normalizer;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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

        intent = getIntent();
        User user = new User();
        user.setID(intent.getStringExtra("Username"));
        user.setPassword(intent.getStringExtra("Password"));
        user.setNickname(intent.getStringExtra("Nickname"));

        OkHttpClient okHttpClient = new OkHttpClient.Builder().pingInterval(500, TimeUnit.MILLISECONDS).build();
        Request request = new Request.Builder().url("wss://" + "8.140.133.34:7542" + "/").build();
        WebSocket webSocket = okHttpClient.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                super.onClosed(webSocket, code, reason);
            }

            @Override
            public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                super.onClosing(webSocket, code, reason);
            }

            @Override
            public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
                super.onFailure(webSocket, t, response);
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
                super.onMessage(webSocket, text);
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "onMessage:" + text, Toast.LENGTH_LONG).show());
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
                super.onMessage(webSocket, bytes);
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "onMessage:" + bytes.toString(), Toast.LENGTH_LONG).show());
            }

            @Override
            public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                super.onOpen(webSocket, response);
                runOnUiThread(() -> {
                    try {
                        Toast.makeText(getApplicationContext(), "onOpen:" + Objects.requireNonNull(response.body()).string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), "ERROR:" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("bizType", "USER_LOGIN");
            jsonObject.put("username", user.getID());
            jsonObject.put("password", user.getPassword());
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "ERROR:" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        boolean isSendSuccess = webSocket.send(jsonObject.toString());
        if (isSendSuccess) {
            Toast.makeText(getApplicationContext(), "Response:GOOD", Toast.LENGTH_LONG).show();
        }


    }
}