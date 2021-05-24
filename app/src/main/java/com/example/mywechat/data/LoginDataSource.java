package com.example.mywechat.data;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.mywechat.R;
import com.example.mywechat.data.model.LoggedInUser;
import com.example.mywechat.ui.contacts.newfriend.NewfriendActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.ContentValues.TAG;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    public static final int SUCCESS = 1;
    public static final int NETWORK_ERROR = 2;
    public static final int SERVER_ERROR = 3;

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            if (InternetLogin(username, password)) {
                LoggedInUser fakeUser =
                        new LoggedInUser(
                                java.util.UUID.randomUUID().toString(),
                                username);
                return new Result.Success<>(fakeUser);
            }
            else {
                return new Result.Error(new IOException("Error logging in"));
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }

    private boolean InternetLogin(String username, String password) {
        new Thread(() -> {
            URL url;
            try {
                String string = "http://" + "8.140.133.34:7541/user/login?username="
                        + username + "&password=" + password;
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
                    String string;
                    InputStream inputStream = connection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    Message msg = new Message();
                    msg.what = SUCCESS;
                    handler.sendEmptyMessage(SUCCESS);

                    inputStream.close();
                } else {
                    handler.sendEmptyMessage(SERVER_ERROR);
                }
            } catch (IOException e) {

            }


        }).start();
        return true;
    }

    private final static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == SUCCESS)
            {

            }
            else if (msg.what == NETWORK_ERROR)
            {

            }
            else if (msg.what == SERVER_ERROR)
            {

            }
            else
            {
                throw new RuntimeException("Unkonown Wrong");
            }
        }
    };
}