package com.example.mywechat.data;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class User extends Friend{

    public static final boolean USER = true;
    private String password;
    private String email;
    private String cookie;

    public User() {
        password = null;
        email = null;
    }

    public void get(File UserJsonFile) {
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
        setProfileDir("UserBitmap");
    }

    public void save(File UserJsonFile) {
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
        } catch (JSONException e) {
            Log.d("User Save ERROR", e.getMessage());
        }
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
