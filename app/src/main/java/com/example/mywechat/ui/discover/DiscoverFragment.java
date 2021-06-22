package com.example.mywechat.ui.discover;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywechat.R;
import com.example.mywechat.data.Comment;
import com.example.mywechat.data.Discover;
import com.example.mywechat.data.Friend;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
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

public class DiscoverFragment extends Fragment {

//    private ChatsViewModel discoViewModel;
    ImageView discover_type;
    TextView discover_video;
    private DiscoverViewModel discoViewModel;
    private RecyclerView recyclerView;


    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    public void getData() {
//        RequestBody requestBody = new FormBody.Builder().build();
//        final Request request = new Request.Builder().url("https://test.extern.azusa.one:7541/moment").post(requestBody).build();
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Call call = okHttpClient.newCall(request);
//
//        call.enqueue(new Callback() {
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//
//            }
//
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//
//            }
//        })
//    }
//
//    public Friend getFriend(String username) {
//        Friend friend = new Friend();
//        RequestBody requestBody = new FormBody.Builder().build();
//        final Request request = new Request.Builder().url("https://test.extern.azusa.one:7541/moment").post(requestBody).build();
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Call call = okHttpClient.newCall(request);
//
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Error:" + e.getMessage(), Toast.LENGTH_LONG).show());
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                String responseData = Objects.requireNonNull(response.body()).string();
//                try {
//                    JSONObject jsonObject = new JSONObject(responseData);
//                    Log.d("Login", responseData);
//                    if (jsonObject.getBoolean("success")) {
//                        runOnUiThread(() -> {
//                            friend.setNickname(jsonObject.getString("nickname"));
//                            friend.setProfile();
//                        });
//                    } else {
//                        runOnUiThread(() -> {
//                            try {
//                                Toast.makeText(getApplicationContext(), "ERROR:" + jsonObject.getString("msg"), Toast.LENGTH_LONG).show();
//                            } catch (JSONException e) {
//                                Log.d("LoginActivity ERROR", e.getMessage());
//                            }
//                        });
//                    }
//                } catch (JSONException e) {
//                    Log.d("LoginActivity ERROR", e.getMessage());
//                }
//            }
//        });
//        return friend;
//    }

//    public ArrayList<Comment> get(String moment){
//
//    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LinkedList<Discover> discovers=new LinkedList<>();
        discoViewModel = new ViewModelProvider(this).get(DiscoverViewModel.class);
        discoViewModel.setDiscovers(discovers);
        discover_type = view.findViewById(R.id.discover_type_choose);
        discover_type.setOnClickListener(v -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("选择动态发布类型");
                    builder.setSingleChoiceItems(new String[]{"图文动态", "视频动态"}, 0, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent;
                            if(which == 0){
                                intent = new Intent(getActivity(), DiscoverReleaseActivity.class);
                            }
                            else {
                                intent = new Intent(getActivity(), DiscoverVideoReleaseActivity.class);
                            }
                            getActivity().startActivity(intent);
                            dialog.dismiss();
                        }
                    });
//            builder.setSingleChoiceItems(new String[]{"图文动态", "视频动态"}, 0,
//                    (dialog, which) -> {
//                        switch (which) {
//                            case 0: {
//                                Intent intent = new Intent(getActivity(), DiscoverReleaseActivity.class);
//                                getActivity().startActivity(intent);
//                            }
//                            case 1: {
//                                Intent intent = new Intent(getActivity(), DiscoverVideoReleaseActivity.class);
//                                getActivity().startActivity(intent);
//                            }
//                        }
//                        dialog.dismiss();
//                        });
            builder.show();
        });
        recyclerView = view.findViewById(R.id.discover_recyclerview);
        DiscoverAdapter discoverAdapter = new DiscoverAdapter(discoViewModel.getDiscovers());
        recyclerView.setAdapter(discoverAdapter);
        LinearLayoutManager linearlayoutmanager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearlayoutmanager);
    }
}