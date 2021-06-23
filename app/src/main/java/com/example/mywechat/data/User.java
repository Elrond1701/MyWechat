package com.example.mywechat.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.example.mywechat.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class User extends Friend{

    public static final boolean USER = true;
    private String password;
    private String email;
    private String cookie;

    public User() {
        password = null;
        email = null;
    }

    public void get(File FilesDir) {
        File UserJsonFile = new File(FilesDir, "UserJson");
        FileInputStream in;
        String JsonData;
        JSONObject user_get;
        try {
            in = new FileInputStream(UserJsonFile);
            byte[] bytes = new byte[in.available()];
            in.read(bytes);
            JsonData = new String(bytes);
        } catch (FileNotFoundException e) {
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
            if (user_get == null) {
                setProfileDir("");
                setGender("");
                setNickname("");
                setID("");
                setBirthDate("");
                setWhatsUp("");
            } else {
                try {
                    String Nickname = user_get.getString("Nickname");
                    setNickname(Nickname);
                } catch (JSONException e) {
                    Log.d("LoginActivity ERROR", e.getMessage());
                    setNickname("");
                }
                try {
                    String Gender = user_get.getString("Gender");
                    setGender(Gender);
                } catch (JSONException e) {
                    Log.d("LoginActivity ERROR", e.getMessage());
                    setGender("");
                }
                try {
                    String WhatsUp = user_get.getString("WhatsUp");
                    setWhatsUp(WhatsUp);
                } catch (JSONException e) {
                    Log.d("LoginActivity ERROR", e.getMessage());
                    setWhatsUp("");
                }
                try {
                    String BirthDate = user_get.getString("BirthDate");
                    setBirthDate(BirthDate);
                } catch (JSONException e) {
                    Log.d("LoginActivity ERROR", e.getMessage());
                    setBirthDate("");
                }
                try {
                    String UserName = user_get.getString("UserName");
                    setID(UserName);
                } catch (JSONException e) {
                    Log.d("LoginActivity ERROR", e.getMessage());
                    setID("");
                }
                try {
                    String Cookie = user_get.getString("Cookie");
                    setCookie(Cookie);
                } catch (JSONException e) {
                    Log.d("JSONException", e.getMessage());
                    setCookie("");
                }
            }
        }
        setProfileDir("UserProfile");

        File UserProfileFile = new File(FilesDir, "UserProfile");
        if (UserProfileFile.exists()) {
            try {
                FileInputStream in1 = new FileInputStream(UserProfileFile);
                setProfile(BitmapFactory.decodeStream(in1));
            } catch (FileNotFoundException e) {
                Log.d("FileNotFoundException", e.getMessage());
            }
        }

    }

    public void save(File FilesDir) {
        JSONObject user_save = new JSONObject();
        try {
            user_save.put("UserName", getID());
            user_save.put("Password", getPassword());
            user_save.put("Nickname", getNickname());
            user_save.put("Gender", getGender());
            user_save.put("BirthDate", getBirthDate());
            user_save.put("WhatsUp", getWhatsUp());
            user_save.put("ProfileDir", getProfileDir());
            user_save.put("Cookie", getCookie());
            user_save.put("ProfileDir", getProfileDir());
        } catch (JSONException e) {
            Log.d("User Save ERROR", e.getMessage());
        }

        File UserJsonFile = new File(FilesDir, "UserJson");
        final FileOutputStream out;
        try {
            out = new FileOutputStream(UserJsonFile);
            out.write(user_save.toString().getBytes());
        } catch (FileNotFoundException e) {
            Log.d("FileNotFound ERROR", e.getMessage());
        } catch (IOException e) {
            Log.d("IO ERROR", e.getMessage());
        }

        File UserProfileFile = new File(FilesDir, "UserProfile");

        new Thread(() -> {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(UserProfileFile);
                getProfile().compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                try {
                    fileOutputStream.close();
                    Log.d("Profile", "SAVE");
                } catch (IOException e) {
                    Log.d("IOException", e.getMessage());
                }
            } catch (FileNotFoundException e) {
                Log.d("FileNotFoundException", e.getMessage());
            }
        }).start();
    }

    public void delete(File FilesDir) {
        File UserJsonFile = new File(FilesDir, "UserJson");
        if (UserJsonFile.exists()) {
            UserJsonFile.delete();
        }

        File UserProfileFile = new File(FilesDir, "UserProfile");
        if (UserProfileFile.exists()) {
            UserProfileFile.delete();
        }
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getCookie() {
        return cookie;
    }
}
