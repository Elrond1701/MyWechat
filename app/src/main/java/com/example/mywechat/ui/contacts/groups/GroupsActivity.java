package com.example.mywechat.ui.contacts.groups;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.MenuItem;
import android.view.View;

import com.example.mywechat.R;
import com.example.mywechat.data.Friend;
import com.example.mywechat.data.Group;
import com.example.mywechat.ui.contacts.ContactAdapter;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

import static android.content.ContentValues.TAG;

public class GroupsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinkedList<Group> groups;
    private GroupAdapter groupAdapter;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        get();

        recyclerView = findViewById(R.id.groups_recylerview);

        GroupAdapter groupAdapter = new GroupAdapter(groups);
        recyclerView.setAdapter(groupAdapter);
        LinearLayoutManager linearlayoutmanager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearlayoutmanager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void get() {
        int number = 0;
        Group group = Singleget(number);
        groups = new LinkedList<>();
        while (group != null) {
            groups.add(group);
            number++;
            group = Singleget(number);
        }
        Group newGroup = new Group();
        newGroup.setNumber(number);
        newGroup.setName("Group");
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.myjpg);
        newGroup.setProfile(bitmap);
        groups.add(newGroup);
    }

    private Group Singleget(int number) {
        String name = null;
        Bitmap profile = null;

        File BitmapFile = new File(getFilesDir(), "Group" + Integer.toString(number) + "Bitmap");
        File XmlFile = new File(getFilesDir(), "Group" + Integer.toString(number) + "Xml");

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

                            }
                            else if ("Name".equals(tagName)) {
                                name = parser.getAttributeValue(null, "Nickname");
                            }
                            break;
                        case XmlPullParser.END_TAG: // </persons>
                            if ("person".equals(tagName)) {
                                Log.i(TAG, "Nickname---" + name);
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

        if (name != null && profile != null) {
            Group group = new Group();
            group.setNumber(number);
            group.setName(name);
            group.setProfile(profile);
            group.setGroupmembers(null);
            return group;
        }
        else {
            return null;
        }
    }
}