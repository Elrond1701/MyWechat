package com.example.mywechat.ui.contacts.newfriend;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.mywechat.R;
import com.example.mywechat.data.Group;
import com.example.mywechat.data.Newfriend;
import com.example.mywechat.ui.contacts.groups.GroupAdapter;

import java.io.File;
import java.util.LinkedList;

public class AddyouActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addyou);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        LinkedList<Newfriend> newfriends = new LinkedList<>();
        File JsonNewfriendFile = null;
        int i;
        for (i = 0; ; i++) {
            JsonNewfriendFile = new File(getFilesDir(), "NewfriendJson" + i);
            if (JsonNewfriendFile.exists()) {
                Newfriend newfriend = new Newfriend();
                newfriend.get(JsonNewfriendFile);
                Log.d(Integer.toString(i), JsonNewfriendFile.getName());
                Log.d(Integer.toString(i), newfriend.getNickname());
                newfriends.add(newfriend);
            } else {
                break;
            }
        }

        RecyclerView recyclerView = findViewById(R.id.groups_recylerview);

        NewfriendAdapter newfriendAdapter = new NewfriendAdapter(newfriends);
        newfriendAdapter.setParent(AddyouActivity.this);
        recyclerView.setAdapter(newfriendAdapter);
        LinearLayoutManager linearlayoutmanager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearlayoutmanager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}