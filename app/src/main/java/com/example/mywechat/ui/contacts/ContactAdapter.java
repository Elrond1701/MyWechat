package com.example.mywechat.ui.contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywechat.R;
import com.example.mywechat.data.Friend;

import java.util.LinkedList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private LinkedList<Friend> data;

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        ImageView Profile;
        TextView Nickname;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            Profile = itemView.findViewById(R.id.item_recycle_contact_Profile);
            Nickname = itemView.findViewById(R.id.item_recycle_contact_Nickname);
        }
    }

    public ContactAdapter(LinkedList<Friend> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ContactAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_contact, parent,false);
        ContactViewHolder holder = new ContactViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Friend friend = data.get(position);
        holder.Profile.setImageBitmap(friend.getProfile());
        holder.Nickname.setText(friend.getNickname());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
