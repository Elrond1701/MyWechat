package com.example.mywechat.ui.chats;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywechat.R;
import com.example.mywechat.data.Message;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.MsgViewHolder> {
    private LinkedList<Message> data;
    private LinkedList<Intent> intents;
    private Context parent;


    @NotNull
    @Override
    public MsgAdapter.MsgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_message,parent,false);
        MsgViewHolder holder = new MsgViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MsgAdapter.MsgViewHolder holder, int position) {
        Message message = data.get(position);
        int type = message.getAsgType();
        if (type == 101){
            holder.profile_left.setVisibility(View.VISIBLE);
            holder.text_left.setVisibility(View.VISIBLE);
            holder.text_left.setText(message.getText());
        }
        else if (type == 102){
            holder.profile_right.setVisibility(View.VISIBLE);
            holder.text_right.setVisibility(View.VISIBLE);
            holder.text_right.setText(message.getText());
        }
        else if (type == 201){
            holder.voice_left.setVisibility(View.VISIBLE);
        }
        else if (type == 202){
            holder.voice_right.setVisibility(View.VISIBLE);
        }
        else if (type == 301){
            holder.photo_left.setVisibility(View.VISIBLE);
        }
        else if (type == 302){
            holder.photo_right.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getAsgType();
    }

    public MsgAdapter(LinkedList<Message> data) {this.data = data;}

    public void setParent(Context parent) {this.parent = parent;}

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class MsgViewHolder extends RecyclerView.ViewHolder {

        public String Type;
        ImageView profile_left;
        ImageView profile_right;
        TextView text_left;
        TextView text_right;
        ImageView voice_left;
        ImageView voice_right;
        ImageView photo_left;
        ImageView photo_right;

        public MsgViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_left = itemView.findViewById(R.id.chat_msg_profile_left);
            profile_right = itemView.findViewById(R.id.chat_msg_profile_right);
            text_left = itemView.findViewById(R.id.chat_msg_text_left);
            text_right = itemView.findViewById(R.id.chat_msg_text_right);
            voice_left = itemView.findViewById(R.id.chat_msg_voice_left);
            voice_right = itemView.findViewById(R.id.chat_msg_voice_right);
            photo_left = itemView.findViewById(R.id.chat_msg_photo_left);
            photo_right = itemView.findViewById(R.id.chat_msg_photo_right);
        }
    }


}
