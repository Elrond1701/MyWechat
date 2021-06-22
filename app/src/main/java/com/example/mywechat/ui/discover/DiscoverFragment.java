package com.example.mywechat.ui.discover;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywechat.R;
import com.example.mywechat.data.Discover;
import com.example.mywechat.data.Friend;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    private String cookie;
    private String id;
    private DiscoverViewModel discoViewModel;
    private RecyclerView recyclerView;
    private LinkedList<Discover> discovers;
    private DiscoverAdapter discoverAdapter;


    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
//        discovers = new LinkedList<>();
//        discoViewModel = new ViewModelProvider(this).get(DiscoverViewModel.class);
//        getCookie();
//        getData();
        super.onCreate(savedInstanceState);
    }

//    public void getCookie() {
//        File UserJsonFile = new File(getActivity().getFilesDir(), "UserJson");
//        FileInputStream in;
//        String JsonData;
//        JSONObject user_get;
//        try {
//            in = new FileInputStream(UserJsonFile);
//            byte[] bytes = new byte[in.available()];
//            in.read(bytes);
//            JsonData = new String(bytes);
//        } catch (FileNotFoundException e) {
//            Log.d("FileNotFoundERROR", e.getMessage());
//            JsonData = null;
//        } catch (IOException e) {
//            Log.d("IOERROR", e.getMessage());
//            JsonData = null;
//        }
//        if (JsonData != null) {
//            try {
//                user_get = new JSONObject(JsonData);
//            } catch (JSONException e) {
//                user_get = null;
//                Log.d("JSONERROR", e.getMessage());
//            }
//            try {
//                cookie = user_get.getString("Cookie");
//            } catch (JSONException e) {
//                Log.d("GET COOKIE ERROR", e.getMessage());
//            }
//        }
//    }
//
    public void getData() throws IOException {
        File UserJsonFile = new File(getActivity().getFilesDir(), "UserJson");
        FileInputStream in;
        String JsonData;
        JSONObject user_get;
        try {
            in = new FileInputStream(UserJsonFile);
            byte[] bytes = new byte[in.available()];
            in.read(bytes);
            JsonData = new String(bytes);
        } catch (FileNotFoundException e) {
            Log.d("FileNotFoundERROR", e.getMessage());
            JsonData = null;
        } catch (IOException e) {
            Log.d("IOERROR", e.getMessage());
            JsonData = null;
        }
        if (JsonData != null) {
            try {
                user_get = new JSONObject(JsonData);
                System.out.println(user_get);
            } catch (JSONException e) {
                user_get = null;
                Log.d("JSONERROR", e.getMessage());
            }
            try {
                cookie = user_get.getString("Cookie");
            } catch (JSONException e) {
                Log.d("GET COOKIE ERROR", e.getMessage());
            }
            try {
                id = user_get.getString("UserName");
            } catch (JSONException e) {
                Log.d("GET ID ERROR", e.getMessage());
            }
        }
        final Request request = new Request.Builder().url("https://test.extern.azusa.one:7541/moment").header("cookie",cookie).get().build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);
//        String responseData = call.execute().string();
//        call.enqueue(new Callback() {
        Response response = call.execute();
        String responseData = response.body().string();
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            if (jsonObject.getBoolean("success")){
                JSONArray jsonArray = jsonObject.getJSONArray("moments");
                for(int i = 0; i<jsonArray.length();i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    System.out.println(jsonObject1);
                    String id = jsonObject1.getString("id");
                    String username = jsonObject1.getString("username");
                    String text = "";
                    try {
                        text = jsonObject1.getString("text");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ArrayList<String> likeList = new ArrayList<>();
                    try {
                        JSONArray jsonArray2 = jsonObject1.getJSONArray("like");
                        for( int j = 0; j<jsonArray2.length(); j++){
                            likeList.add((String) jsonArray2.get(i));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println(likeList.size());
                    Discover discover = new Discover(id, username);
                    getCommentList(discover);
                    discover.setText(text);
                    discover.setLikeList(likeList);
                    System.out.println(discover.getCommentList().size());
                    discovers.add(discover);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //        call.enqueue(new Callback() {
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                String responseData = Objects.requireNonNull(response.body()).string();
//                try {
//                    JSONObject jsonObject = new JSONObject(responseData);
//                    if (jsonObject.getBoolean("success")){
//                        JSONArray jsonArray = jsonObject.getJSONArray("moments");
//                        for(int i = 0; i<jsonArray.length();i++){
//                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                            String id = jsonObject1.getString("id");
//                            String username = jsonObject1.getString("username");
//                            String text = jsonObject1.getString("text");
//                            Discover discover = new Discover(id, username);
//                            discover.setText(text);
//                            discovers.add(discover);
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) { }
//        });
    }

    public void getCommentList(Discover discover) throws IOException {
        ArrayList<String> commentList = new ArrayList<>();
        final Request request = new Request.Builder().url("https://test.extern.azusa.one:7541/moment/comment?momentId="+discover.getId()).header("cookie",cookie).get().build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);
//        String responseData = call.execute().string();
//        call.enqueue(new Callback() {
        Response response = call.execute();
        String responseData = response.body().string();
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            System.out.println(jsonObject);
            if (jsonObject.getBoolean("success")){
                JSONArray jsonArray = jsonObject.getJSONArray("comments");
                for(int i = 0; i<jsonArray.length();i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                    System.out.println(jsonObject1);
//                    String id = jsonObject1.getString("id");
                    String username = jsonObject1.getString("username");
                    String content = jsonObject1.getString("content");;
                    commentList.add(username+'：'+content);
                }
                System.out.println(commentList.size());
                discover.setCommentList(commentList);
            }

//            return commentList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public Friend getFriend(String username) {
        Friend friend = new Friend();
        RequestBody requestBody = new FormBody.Builder().build();
        final Request request = new Request.Builder().url("https://test.extern.azusa.one:7541/moment").post(requestBody).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = Objects.requireNonNull(response.body()).string();
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    Log.d("Login", responseData);
                    if (jsonObject.getBoolean("success")) {

                    } else {

                    }
                } catch (JSONException e) {
                    Log.d("LoginActivity ERROR", e.getMessage());
                }
            }
        });
        return friend;
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        discovers = new LinkedList<>();
        discoViewModel = new ViewModelProvider(this).get(DiscoverViewModel.class);
        try {
            getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            builder.show();
        });
        recyclerView = view.findViewById(R.id.discover_recyclerview);
        discoViewModel = new ViewModelProvider(this).get(DiscoverViewModel.class);
        discoViewModel.setDiscovers(discovers);
        DiscoverAdapter discoverAdapter = new DiscoverAdapter(discovers);
        discoverAdapter.setCookie(cookie);
        System.out.println(id);
        discoverAdapter.setId(id);
        recyclerView.setAdapter(discoverAdapter);
        LinearLayoutManager linearlayoutmanager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearlayoutmanager);
    }
}