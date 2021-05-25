package com.example.mywechat.ui.discover;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywechat.R;
import com.example.mywechat.data.Discover;

import java.util.LinkedList;

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.DiscoverViewHolder> {
    private LinkedList<Discover> data;

    public static class DiscoverViewHolder extends RecyclerView.ViewHolder {
//        ImageView Profile;
//        TextView Nickname;

        public DiscoverViewHolder(@NonNull View itemView) {
            super(itemView);
//            Profile = itemView.findViewById(R.id.item_recycle_contact_Profile);
//            Nickname = itemView.findViewById(R.id.item_recycle_contact_Nickname);
        }
    }

    public DiscoverAdapter(LinkedList<Discover> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public DiscoverAdapter.DiscoverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_discover, parent,false);
        DiscoverViewHolder holder = new DiscoverViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DiscoverViewHolder holder, int position) {
//        Friend friend = data.get(position);
//        holder.Profile.setImageResource(friend.getProfile());
//        holder.Nickname.setText(friend.getNickname());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
