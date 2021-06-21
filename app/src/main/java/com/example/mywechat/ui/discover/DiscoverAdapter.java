package com.example.mywechat.ui.discover;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
        TextView Nickname;
        TextView Text;
        TextView PublishedTime;
        TextView Like;
        TextView Comment;
        LinearLayout InputLayout;
        TextView LikeList;
        ListView CommentList;

        public DiscoverViewHolder(@NonNull View itemView) {
            super(itemView);
//            Profile = itemView.findViewById(R.id.item_recycle_contact_Profile);
            Nickname = itemView.findViewById(R.id.item_recycle_discover_Nickname);
            Text = itemView.findViewById(R.id.item_recycle_discover_Text);
            PublishedTime = itemView.findViewById(R.id.item_recycle_discover_Published_time);
            InputLayout = itemView.findViewById(R.id.comment_input_layout);
            Like = itemView.findViewById(R.id.discover_like);
            Comment = itemView.findViewById(R.id.discover_comment);
            LikeList = itemView.findViewById(R.id.discover_like_list);
            CommentList = itemView.findViewById(R.id.discover_comment_list);
            Like.setOnClickListener(v -> {
                LikeList.setVisibility(View.VISIBLE);
            });
//            Comment.setOnClickListener(v -> {
//                InputLayout.setVisibility(View.VISIBLE);
//            });
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
        Discover discover = data.get(position);
        holder.Nickname.setText(discover.getNickname());
        holder.Text.setText(discover.getText());
        holder.PublishedTime.setText(discover.getPublishedTime());
        holder.LikeList.setText("&#10084;HELLO");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
