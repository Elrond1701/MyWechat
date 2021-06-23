package com.example.mywechat.util;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CommonDatabase extends SQLiteOpenHelper {
    public CommonDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE IF NOT EXISTS chatlist (Nickname VARCHAR(32),LastSpeak VARCHAR(1024),LastSpeakTime VARCHAR(1024),chatId VARCHAR(128), isGroupChat Integer)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
