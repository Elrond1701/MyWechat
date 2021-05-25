package com.example.mywechat.ui.contacts.newfriend;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.mywechat.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.ContentValues.TAG;

public class NewfriendActivity extends AppCompatActivity {
    public static final int SUCCESS = 1;
    public static final int NETWORK_ERROR = 2;
    public static final int SERVER_ERROR = 3;

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newfriend);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        editText = findViewById(R.id.NewfriendActivity_EditText);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void searchFriend(android.view.View View) {
        String phonenumber = editText.toString();
        new Thread(() -> {
            URL url;
            try {
                String string = "http://" + getString(R.string.server) + "/user/get?username=" + phonenumber;
                url = new URL(string);
            } catch (MalformedURLException e) {
                Log.d(TAG, "Malformed URL err:" + e.getMessage());
                return;
            }
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000);
                int code = connection.getResponseCode();
                if (code == 200) {
                    InputStream inputStream = connection.getInputStream();
                    Message msg = new Message();
                    msg.what = SUCCESS;
                    handler.sendEmptyMessage(SUCCESS);

                    inputStream.close();
                } else {
                    handler.sendEmptyMessage(SERVER_ERROR);
                }
            } catch (IOException e) {
                Log.d(TAG, "HTTP IO err:" + e.getMessage());
                handler.sendEmptyMessage(NETWORK_ERROR);
            }
        }).start();
    }

    private final static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == SUCCESS) {

            } else if (msg.what == NETWORK_ERROR) {

            } else if (msg.what == SERVER_ERROR) {

            } else {
                throw new RuntimeException("Unkonown Wrong");
            }
        }
    };

}