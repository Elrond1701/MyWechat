package com.example.mywechat.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Newfriend extends Friend{
    String note;

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return  note;
    }

    public void get(File FilesDir) {
        File JsonNewfriendFile = new File(FilesDir, "NewfriendJson" + getNumber());
        FileInputStream in;
        String JsonData;
        JSONObject user_get;
        try {
            in = new FileInputStream(JsonNewfriendFile);
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
                    String Note = user_get.getString("Note");
                    setNote(Note);
                } catch (JSONException e) {
                    Log.d("LoginActivity ERROR", e.getMessage());
                    setNote("");
                }
                try {
                    String ContactapplyId = user_get.getString("ContactapplyId");
                    setContactapplyId(ContactapplyId);
                } catch (JSONException e) {
                    Log.d("LoginActivity ERROR", e.getMessage());
                    setContactapplyId("");
                }
            }
        }

        setProfileDir("NewfriendProfile" + getNumber());

        File UserProfileFile = new File(FilesDir, "NewfriendProfile" + getNumber());
        if (UserProfileFile.exists()) {
            try {
                FileInputStream in1 = new FileInputStream(UserProfileFile);
                setProfile(BitmapFactory.decodeStream(in1));
                Log.d("Profile", "GET");
            } catch (FileNotFoundException e) {
                Log.d("FileNotFoundException", e.getMessage());
            }
        }
    }

    public void save(File FilesDir) {
        File JsonNewfriendFile = new File(FilesDir, "NewfriendJson" + getNumber());
        JSONObject user_save = new JSONObject();
        Log.d("SAVE", "SAVE");
        try {
            user_save.put("UserName", getID());
            user_save.put("Note", getNote());
            user_save.put("Nickname", getNickname());
            user_save.put("Gender", getGender());
            user_save.put("BirthDate", getBirthDate());
            user_save.put("WhatsUp", getWhatsUp());
            user_save.put("ProfileDir", getProfileDir());
            user_save.put("ContactapplyId", getContactapplyId());
        } catch (JSONException e) {
            Log.d("User Save ERROR", e.getMessage());
        }
        final FileOutputStream out;
        try {
            out = new FileOutputStream(JsonNewfriendFile);
            out.write(user_save.toString().getBytes());
            Log.d("JSONFile", "SAVE");
        } catch (FileNotFoundException e) {
            Log.d("FileNotFound ERROR", e.getMessage());
        } catch (IOException e) {
            Log.d("IO ERROR", e.getMessage());
        }

        File UserProfileFile = new File(FilesDir, "Newfriend" + getNumber());

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
    }
}
