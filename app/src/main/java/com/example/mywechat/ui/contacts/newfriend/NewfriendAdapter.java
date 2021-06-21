package com.example.mywechat.ui.contacts.newfriend;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywechat.contact.ContactActivity;
import com.example.mywechat.R;
import com.example.mywechat.data.Friend;
import com.example.mywechat.data.Group;

import java.util.LinkedList;

public class NewfriendAdapter extends RecyclerView.Adapter<com.example.mywechat.ui.contacts.groups.GroupAdapter.GroupViewHolder> {
    private final LinkedList<Friend> data;
    private Context parent;

    public static class NewfriendViewHolder extends RecyclerView.ViewHolder {
        ImageView Profile;
        TextView Name;

        public NewfriendViewHolder(@NonNull View itemView) {
            super(itemView);
            Profile = itemView.findViewById(R.id.item_recycle_group_Profile);
            Name = itemView.findViewById(R.id.item_recycle_group_Name);
        }
    }

    public NewfriendAdapter(LinkedList<Friend> data) {
        this.data = data;
    }

    public void setParent(Context parent) {
        this.parent = parent;
    }

    @NonNull
    @Override
    public com.example.mywechat.ui.contacts.groups.GroupAdapter.GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_group, parent,false);
        return new com.example.mywechat.ui.contacts.groups.GroupAdapter.GroupViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.mywechat.ui.contacts.groups.GroupAdapter.GroupViewHolder holder, int position) {
        Friend friend = data.get(position);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(parent, ContactActivity.class);
            parent.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

