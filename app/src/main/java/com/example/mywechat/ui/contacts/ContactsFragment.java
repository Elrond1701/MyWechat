package com.example.mywechat.ui.contacts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywechat.R;
import com.example.mywechat.data.Friend;
import com.example.mywechat.data.Newfriend;
import com.example.mywechat.ui.contacts.groups.GroupsActivity;
import com.example.mywechat.ui.contacts.newfriend.NewfriendActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class ContactsFragment extends Fragment {

    private ContactsViewModel contactsViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        contactsViewModel =
                new ViewModelProvider(this).get(ContactsViewModel.class);

        LinkedList<Friend> friends = new LinkedList<>();
        File JsonFriendFile;
        int i;
        for (i = 0; ; i++) {
            JsonFriendFile = new File(requireActivity().getFilesDir(), "FriendJson" + i);
            if (JsonFriendFile.exists()) {
                Friend friend = new Friend();
                friend.setNumber(i);
                friend.get(requireActivity().getFilesDir());
                Log.d(Integer.toString(i), JsonFriendFile.getName());
                Log.d(Integer.toString(i), friend.getNickname());
                friends.add(friend);
            } else {
                break;
            }
        }
        contactsViewModel.setFriends(friends);

        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        View newfriend = view.findViewById(R.id.ContactsFragment_Newfriend);
        newfriend.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), NewfriendActivity.class);
            startActivity(intent);
        });

        View groupchat = view.findViewById(R.id.ContactsFragment_Groupchats);
        groupchat.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), GroupsActivity.class);
            startActivity(intent);
        });

        RecyclerView recyclerView = view.findViewById(R.id.contacts_recylerview);

        ContactAdapter contactAdapter = new ContactAdapter(contactsViewModel.getFriends());
        contactAdapter.setParent(getActivity());
        recyclerView.setAdapter(contactAdapter);
        LinearLayoutManager linearlayoutmanager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearlayoutmanager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}