package com.example.mywechat.ui.contacts;

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

import java.util.LinkedList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder>{
    private LinkedList<Friend> data;
    private Context parent;

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

    public void setParent(Context parent) {
        this.parent = parent;
    }


    @NonNull
    @Override
    public ContactAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_contact, parent,false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Friend friend = data.get(position);
        holder.Profile.setImageBitmap(friend.getProfile());
        holder.Nickname.setText(friend.getNickname());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(parent, ContactActivity.class);
            intent.putExtra("ProfileDir", friend.getProfileDir());
            intent.putExtra("Nickname", friend.getNickname());
            intent.putExtra("ID", friend.getID());
            intent.putExtra("Gender", friend.getGender());
            intent.putExtra("Region", friend.getRegion());
            intent.putExtra("WhatsUp", friend.getWhatsUp());
            parent.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }
}
