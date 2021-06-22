package com.example.mywechat.ui.discover;

import android.annotation.SuppressLint;
import android.content.Context;
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
import java.util.ArrayList;
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
    private Context parent;
    private String cookie;
    private String id;
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

    public void setParent(Context parent) {this.parent =  parent;}

    public void setCookie(String cookie) {this.cookie = cookie;}

    public void setId(String id){this.id = id;}

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
//        ArrayList<String> LikeList;
        if(discover.getLikeListLen()!=0){
            ArrayList<String> LikeList = discover.getLikeList();
            if(discover.getLikeListLen()!=0) {
                boolean flag = false;
//                ArrayList<String> LikeList = discover.getLikeList();
                for (int i = 0; i < LikeList.size(); i++) {
                    if (id.equals(LikeList.get(i))) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    holder.Like.setText("取消");
                } else {
                    holder.Like.setText("点赞");
                }
                holder.LikeList.setText(discover.getLikeListStr());
                holder.LikeList.setVisibility(View.VISIBLE);
            }
        }
        else {
            holder.Like.setText("点赞");
        }
////        holder.PublishedTime.setText(discover.getPublishedTime());
        holder.Like.setOnClickListener(v -> {
//            System.out.println(id);
            ArrayList<String> LikeList = discover.getLikeList();
            if(discover.getLikeListLen()!=0){
                boolean flag = false;
                int index = 0;
//                ArrayList<String> LikeList = discover.getLikeList();
                for(int i = 0;i < LikeList.size();i++){
                    if (id.equals(LikeList.get(i))){
                        flag = true;
                        index = i;
                        break;
                    }
                }
                if(flag){
                    LikeList.remove(index);
                }
                else {
                    LikeList.add(id);
                    holder.Like.setText("取消");
                }
            }
            else {
                LikeList.add(id);
                holder.Like.setText("取消");
            }
            if (LikeList.size() == 0){
                holder.LikeList.setVisibility(View.GONE);
            }
            else {
                holder.LikeList.setVisibility(View.VISIBLE);
                discover.setLikeList(LikeList);
            }
//            discover.setLikeList(LikeList);
            holder.LikeList.setText(discover.getLikeListStr());
            discoverLike(discover.getId());
        });
////        holder.LikeList.setText(discover.getLikeList());
        holder.Comment.setOnClickListener(v -> {
            holder.InputLayout.setVisibility(View.VISIBLE);
        });
        holder.CommentSend.setOnClickListener(v -> {
//            String CommentList = (String) holder.CommentList.getText();
//            holder.CommentText.getText();
//            holder.CommentList.setText((String) holder.CommentList.getText()+'\n'+holder.CommentText.getText());
//            holder.CommentList.setVisibility(View.VISIBLE);
//            holder.InputLayout.setVisibility(View.GONE);
            holder.InputLayout.setVisibility(View.GONE);
            discoverComment(discover.getId(), (String) holder.Comment.getText());
        });
    }

    public void discoverLike(String Id) {

        RequestBody requestBody = new FormBody.Builder().add("momentId", Id).build();
        final Request request = new Request.Builder().url("https://test.extern.azusa.one:7541/moment/like").header("cookie",cookie).post(requestBody).build();
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
                    Log.d("Like", responseData);
                    if (jsonObject.getBoolean("success")) {
                    }
                } catch (JSONException e) {
                    Log.d("LIKE ERROR", e.getMessage());
                }
            }
        });
    }

    public void discoverComment(String Id, String Content){
        RequestBody requestBody = new FormBody.Builder().add("momentId", Id)
                .add("content",Content).build();
        final Request request = new Request.Builder().url("https://test.extern.azusa.one:7541/moment/comment").header("cookie",cookie).post(requestBody).build();
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
                    Log.d("Like", responseData);
                    if (jsonObject.getBoolean("success")) {
                    }
                } catch (JSONException e) {
                    Log.d("LIKE ERROR", e.getMessage());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

