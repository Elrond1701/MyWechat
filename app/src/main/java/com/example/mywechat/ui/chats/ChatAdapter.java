package com.example.mywechat.ui.chats;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywechat.R;
import com.example.mywechat.data.Chat;

import java.util.LinkedList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private LinkedList<Chat> data;

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
//        ImageView Profile;
        TextView Nickname;
        TextView LastSpeak;
        TextView LastSpeakTime;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
//            Profile = itemView.findViewById(R.id.item_recycle_chat_Profile);
            Nickname = itemView.findViewById(R.id.item_recycle_chat_Nickname);
            LastSpeak = itemView.findViewById(R.id.item_recycle_chat_LastSpeak);
            LastSpeakTime = itemView.findViewById(R.id.item_recycle_chat_LastSpeakTime);
        }
    }

    public ChatAdapter(LinkedList<Chat> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ChatAdapter.ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_chat, parent,false);
        ChatViewHolder holder = new ChatViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Chat chat = data.get(position);
//        holder.Profile.setImageResource(chat.getProfile());
        holder.Nickname.setText(chat.getNickname());
        holder.LastSpeak.setText(chat.getLastSpeak());
        holder.LastSpeakTime.setText((chat.getLastSpeakTime()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
