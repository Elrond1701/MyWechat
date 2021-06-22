package com.example.mywechat.ui.discover;

import android.annotation.SuppressLint;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywechat.R;
import com.example.mywechat.data.Discover;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
        TextView CommentList;
        EditText CommentText;
        ImageView CommentSend;

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
            CommentText = itemView.findViewById(R.id.comment_input);
            CommentSend = itemView.findViewById(R.id.comment_send);
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
        holder.Like.setOnClickListener(v -> {
//            holder.LikeList.setText();
            holder.LikeList.setVisibility(View.VISIBLE);

        });
//        holder.LikeList.setText(discover.getLikeList());
        holder.Comment.setOnClickListener(v -> {
            holder.InputLayout.setVisibility(View.VISIBLE);
        });
        holder.CommentSend.setOnClickListener(v -> {

            String CommentList = (String) holder.CommentList.getText();
            holder.CommentText.getText();
            holder.CommentList.setText((String) holder.CommentList.getText()+'\n'+holder.CommentText.getText());
            holder.CommentList.setVisibility(View.VISIBLE);
            holder.InputLayout.setVisibility(View.GONE);
        });
    }

    public void discoverLike(String Id) {
        RequestBody requestBody = new FormBody.Builder().add("momentId", Id).build();
        final Request request = new Request.Builder().url("https://test.extern.azusa.one:7541/moment").post(requestBody).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = Objects.requireNonNull(response.body()).string();
                try {
                    JSONObject jsonObject= new JSONObject(responseData);
                    Log.d("Login", responseData);
                    if (jsonObject.getBoolean("success")) {
                    } else {
                    }
                } catch (JSONException e) {
                    Log.d("DiscoverReleaseActivity ERROR", e.getMessage());
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return data.size();
    }
}
