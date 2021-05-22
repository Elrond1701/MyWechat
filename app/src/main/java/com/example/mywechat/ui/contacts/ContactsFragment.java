package com.example.mywechat.ui.contacts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywechat.R;
import com.example.mywechat.data.Friend;
import com.example.mywechat.ui.chats.ChatsViewModel;
import com.example.mywechat.ui.contacts.groups.GroupsActivity;
import com.example.mywechat.ui.contacts.newfriend.NewfriendActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

import static android.content.ContentValues.TAG;

public class ContactsFragment extends Fragment {

    private ContactsViewModel contactsViewModel;
    private RecyclerView recyclerView;
    private View newfriend;
    private View groupchat;

    public interface OnItemClickListener{
        void onClick(Friend friend);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        contactsViewModel =
                new ViewModelProvider(this).get(ContactsViewModel.class);
        get();
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        newfriend = view.findViewById(R.id.ContactsFragment_Newfriend);
        newfriend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent NewfriendActivity_intent = new Intent(getActivity(), NewfriendActivity.class);
                startActivity(NewfriendActivity_intent);
            }
        });

        groupchat = view.findViewById(R.id.ContactsFragment_Groupchats);
        groupchat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent GroupsActivity_intent = new Intent(getActivity(), GroupsActivity.class);
                startActivity(GroupsActivity_intent);
            }
        });
        
        recyclerView = view.findViewById(R.id.contacts_recylerview);

        ContactAdapter contactAdapter = new ContactAdapter(contactsViewModel.getFriends());
        recyclerView.setAdapter(contactAdapter);
        LinearLayoutManager linearlayoutmanager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearlayoutmanager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void get() {
        int number = 0;
        Friend newfriend = Singleget(number);
        LinkedList<Friend> friends = new LinkedList<>();
        while (newfriend != null) {
            friends.add(newfriend);
            number++;
            newfriend = Singleget(number);
        }
        contactsViewModel.setFriends(friends);
    }

    private Friend Singleget(int number) {
        String nickname = null;
        String phonenumber = null;
        Bitmap profile = null;

        File BitmapFile = new File(getContext().getFilesDir(), "Friend" + Integer.toString(number) + "Bitmap");
        File XmlFile = new File(getContext().getFilesDir(), "Friend" + Integer.toString(number) + "Xml");

        try {
            FileInputStream fileInputStream = new FileInputStream(BitmapFile);
            profile = BitmapFactory.decodeStream(fileInputStream);
        } catch (IOException e) {
            Log.d(TAG, "file input err:" + e.getMessage());
            return null;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(XmlFile);
            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(fileInputStream, "utf-8");
                int eventType = parser.getEventType(); // 获得事件类型

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String tagName = parser.getName(); // 获得当前节点的名称

                    switch (eventType) {
                        case XmlPullParser.START_TAG: // 当前等于开始节点 <person>
                            if (("Friend" + Integer.toString(number)).equals(tagName)) {

                            } else if ("Nickname".equals(tagName)) {
                                nickname = parser.getAttributeValue(null, "Nickname");
                            } else if ("PhoneNumber".equals(tagName)) { // <name>
                                phonenumber = parser.nextText();
                            }
                            break;
                        case XmlPullParser.END_TAG: // </persons>
                            if ("person".equals(tagName)) {
                                Log.i(TAG, "Nickname---" + nickname);
                                Log.i(TAG, "PhoneNumber---" + phonenumber);
                            }
                            break;
                        default:
                            break;
                    }
                }
            } catch (XmlPullParserException e) {
                Log.d(TAG, "xml pull parse err:" + e.getMessage());
                return null;
            }
        } catch (IOException e) {
            Log.d(TAG, "file input err:" + e.getMessage());
            return null;
        }

        if (nickname != null && phonenumber != null && profile != null) {
            Friend friend = new Friend(number, nickname, phonenumber, profile);
            return friend;
        } else {
            return null;
        }
    }
}