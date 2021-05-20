package com.example.mywechat.ui.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import com.example.mywechat.ui.contacts.groups.GroupsActivity;
import com.example.mywechat.ui.contacts.newfriend.NewfriendActivity;

import java.util.LinkedList;

public class ContactsFragment extends Fragment {

    private ContactsViewModel contactsViewModel;
    private RecyclerView recyclerView;
    private View newfriend;
    private View groupchat;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinkedList<Friend> friends = new LinkedList<>();
        friends.add(new Friend(0, "我", "123456", R.drawable.ic_newfriend_black_60dp));
        contactsViewModel =
                new ViewModelProvider(this).get(ContactsViewModel.class);
        contactsViewModel.setFriends(friends);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
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
}