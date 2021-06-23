//package com.example.mywechat.util;
//
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
//import com.example.mywechat.data.Chat;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class chatList {
//    public List<Chat> getChatList(SQLiteDatabase db){
//        List<Chat> res = new ArrayList<>();
//        Cursor cursor = db.rawQuery("SELECT Nickname,chatId,LastSpeak,LastSpeakTime,isGroupchat FROM chatlist ORDER BY LastSpeakTime",null);
//        if (cursor != null){
//            while (cursor.moveToNext()){
//                res.add(new Chat(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4)));
//            }
//            cursor.close();
//        }
//        return res;
//    }
//}
