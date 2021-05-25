package com.example.mywechat.ui.contacts.groups;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywechat.R;
import com.example.mywechat.data.Friend;
import com.example.mywechat.data.Group;

import java.util.LinkedList;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {
    private LinkedList<Group> data;

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

    @NonNull
    @Override
    public GroupAdapter.GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_group, parent,false);
        GroupAdapter.GroupViewHolder holder = new GroupAdapter.GroupViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GroupAdapter.GroupViewHolder holder, int position) {
        Group group = data.get(position);
        holder.Profile.setImageBitmap(group.getProfile());
        holder.Name.setText(group.getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
