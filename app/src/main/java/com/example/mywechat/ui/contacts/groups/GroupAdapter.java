package com.example.mywechat.ui.contacts.groups;

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
import com.example.mywechat.data.Group;

import java.util.LinkedList;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {
    private final LinkedList<Group> data;
    private Context parent;

    public static class GroupViewHolder extends RecyclerView.ViewHolder {
        ImageView Profile;
        TextView Name;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            Profile = itemView.findViewById(R.id.item_recycle_group_Profile);
            Name = itemView.findViewById(R.id.item_recycle_group_Name);
        }
    }

    public GroupAdapter(LinkedList<Group> data) {
        this.data = data;
    }

    public void setParent(Context parent) {
        this.parent = parent;
    }

    @NonNull
    @Override
    public GroupAdapter.GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_group, parent,false);
        return new GroupViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupAdapter.GroupViewHolder holder, int position) {
        Group group = data.get(position);
        holder.Profile.setImageBitmap(group.getProfile());
        holder.Name.setText(group.getName());
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
