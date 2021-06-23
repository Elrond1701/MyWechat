package com.example.mywechat.ui.chats;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywechat.MainActivity;
import com.example.mywechat.R;
import com.example.mywechat.data.Message;
import com.example.mywechat.ui.discover.DiscoverReleaseActivity;
import com.example.mywechat.ui.discover.DiscoverVideoReleaseActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MsgActivity extends AppCompatActivity {

    public static final int PICK_PHOTO = 101;
    public static final int PICK_VIDEO = 102;


    Intent intent;
    SQLiteDatabase db;
    private Uri imageUri;
    private Uri videoUri;

    private String cookie;
    private String chatId;
    private String username;
    private Button back;
    private EditText inputText;
    private ImageView send;
//    private ListView msgListView;
    private ImageView voice;
    private ImageView more;
    private RelativeLayout pic;
    private RelativeLayout video;
    private RelativeLayout location;
    private LinearLayout bottom_layout;
    private RecyclerView recyclerView;
    private LinkedList<Message> Messages;;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_interface);
        intent = getIntent();
        cookie = intent.getStringExtra("cookie");
        chatId = intent.getStringExtra("chatId");
        username = intent.getStringExtra("username");
        initView();
        getDate();
        setAdapter();
    }

    public void initView() {
        Messages = new LinkedList<Message>();
        inputText = findViewById(R.id.chat_input);
//        back = findViewById(R.id.chat_back);
        send = findViewById(R.id.chat_send);
//        msgListView = findViewById(R.id.chat_content);
        voice = findViewById(R.id.chat_voice);
        more = findViewById(R.id.chat_more);
        bottom_layout = findViewById(R.id.chat_bottom_layout);
        pic = findViewById(R.id.chat_photo_more);
        video = findViewById(R.id.chat_video_more);
        location = findViewById(R.id.chat_location_more);
        recyclerView = findViewById(R.id.chat_content);

        send.setOnClickListener(v -> {
            String content =  inputText.getText().toString();
            if(!"".equals(content)){
                sendText();
            }
            bottom_layout.setVisibility(View.GONE);
        });

        pic.setOnClickListener(v -> {
            sendImage();
        });

        voice.setOnClickListener(v -> {

        });

        more.setOnClickListener(v -> {
            bottom_layout.setVisibility(View.VISIBLE);
        });

        pic.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
            }

            Intent intent = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_PHOTO);
        });

        video.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
            }

            Intent intent = new Intent(
                    Intent.ACTION_PICK,
                    null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"video/*");
            startActivityForResult(intent, PICK_VIDEO);
        });

        location.setOnClickListener(v -> {

        });
    }

    public void getDate() {
        final Request request = new Request.Builder().url("https://test.extern.azusa.one:7541/chat?chatId="+chatId).header("cookie",cookie).get().build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);
//        String responseData = call.execute().string();
//        call.enqueue(new Callback() {
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String responseData = null;
        try {
            responseData = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            System.out.println(jsonObject);
            if (jsonObject.getBoolean("success")){
                JSONArray jsonArray = jsonObject.getJSONArray("messages");
                for(int i= 0; i<jsonArray.length();i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String content = jsonObject1.getString("content");
                    String type = jsonObject1.getString("contentType");
                    String speaker = jsonObject1.getString("speaker");
                    Message message = null;
                    System.out.println(jsonObject1);
                    if (type.equals("TEXT") && speaker.equals(username)){
                        message = new Message(content, 102);
                    }
                    else if (type.equals("TEXT") && !speaker.equals(username)){
                        message = new Message(content, 101);
                    }
                    if (type.equals("PICTURE") && speaker.equals(username)){
                        message = new Message(content, 202);
                    }
                    else if (type.equals("PICTURE") && !speaker.equals(username)){
                        message = new Message(content, 201);
                    }
                    if (type.equals("VIDEO") && speaker.equals(username)){
                        message = new Message(content, 302);
                    }
                    else if (type.equals("VIDEO") && !speaker.equals(username)){
                        message = new Message(content, 301);
                    }
                    System.out.println(message);
                    Messages.add(message);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setAdapter() {
        MsgAdapter msgAdapter = new MsgAdapter(Messages);
        msgAdapter.setParent(MsgActivity.this);
        recyclerView.setAdapter(msgAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MsgActivity.this, RecyclerView.VERTICAL,false);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void sendText(){
        RequestBody requestBody = new FormBody.Builder().add("chatId", chatId)
                .add("content",inputText.getText().toString()).build();
        final Request request = new Request.Builder().url("https://test.extern.azusa.one:7541/chat/text").header("cookie",cookie).post(requestBody).build();
        System.out.println(chatId);
        System.out.println(cookie);
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
                    Log.d("chat", responseData);
                    if (jsonObject.getBoolean("success")) {
                    }
                } catch (JSONException e) {
                    Log.d("CHAT ERROR", e.getMessage());
                }
            }
        });
    }

    public void sendImage() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
        }

        Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_PHOTO);
    }

    public void sendVoice() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
        }

        Intent intent = new Intent(
                Intent.ACTION_PICK,
                null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"video/*");
        startActivityForResult(intent, PICK_VIDEO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView pic1 = findViewById(R.id.discover_img_chosen1);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PICK_PHOTO: {
                    // 获取图片
                    try {
                        //该uri是上一个Activity返回的
                        imageUri = data.getData();
//                        if(imageUri!=null) {
//                            Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
////                            Log.i("bit", String.valueOf(bit));
//                            pic1.setVisibility(View.VISIBLE);
//                            pic1.setImageBitmap(bit);
//                        }
                        String[] proj = {MediaStore.Images.Media.DATA};
                        Cursor actualimagecursor = managedQuery(imageUri,proj,null,null,null);
                        int index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        actualimagecursor.moveToFirst();
                        String img_path = actualimagecursor.getString(index);
                        File file = new File(img_path);
                        System.out.println(file);
//        System.out.println(file);
//        File file = new File(this.getFilesDir(),"UserProfile");
//        File file = new File(new URI(imageUri.toString()));
                        MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
                        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                                .addFormDataPart("chatId",chatId)
                                .addFormDataPart("contentType","picture")
                                .addFormDataPart("file","file",RequestBody.create(MEDIA_TYPE_PNG,file))
                                .build();
//                        System.out.println(requestBody);
//        RequestBody requestBody = new FormBody.Builder().add("text", text.getText().toString()).build();
                        final Request request = new Request.Builder().url("https://test.extern.azusa.one:7541/chat/file").header("cookie",cookie).post(requestBody).build();
                        OkHttpClient okHttpClient = new OkHttpClient();
                        Call call = okHttpClient.newCall(request);

                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Error:" + e.getMessage(), Toast.LENGTH_LONG).show());
                            }

                            @SuppressLint("LongLogTag")
                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                String responseData = Objects.requireNonNull(response.body()).string();
                                try {
                                    JSONObject jsonObject= new JSONObject(responseData);
                                    Log.d("Login", responseData);
                                    if (jsonObject.getBoolean("success")) {
                                        runOnUiThread(() -> {
                                            Toast.makeText(getApplicationContext(), "Release your discover!", Toast.LENGTH_LONG).show();
                                        });
                                    } else {
                                        runOnUiThread(() -> {
                                            try {
                                                Toast.makeText(getApplicationContext(), "ERROR:" + jsonObject.getString("msg"), Toast.LENGTH_LONG).show();
                                            } catch (JSONException e) {
                                                Log.d("DiscoverReleaseActivity ERROR", e.getMessage());
                                            }
                                        });
                                    }
                                } catch (JSONException e) {
                                    Log.d("DiscoverReleaseActivity ERROR", e.getMessage());
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case PICK_VIDEO:{
                    try {
                        //该uri是上一个Activity返回的
                        videoUri = data.getData();
//                        System.out.println(videoUri);
//                        if(videoUri != null) {
//                            video.setVisibility(View.VISIBLE);
//                            video.setVideoURI(videoUri);
//                            video.start();
//                            add.setVisibility(View.GONE);

//                        }
                        String[] proj = {MediaStore.Images.Media.DATA};
                        Cursor actualimagecursor = managedQuery(videoUri,proj,null,null,null);
                        int index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        actualimagecursor.moveToFirst();
                        String img_path = actualimagecursor.getString(index);
                        File file = new File(img_path);
                        System.out.println(file);
//        System.out.println(file);
//        File file = new File(this.getFilesDir(),"UserProfile");
//        File file = new File(new URI(imageUri.toString()));
                        MediaType MEDIA_TYPE = MediaType.parse("video/mp4");
                        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                                .addFormDataPart("chatId",chatId)
                                .addFormDataPart("contentType","video")
                                .addFormDataPart("file","file",RequestBody.create(MEDIA_TYPE,file))
                                .build();
                        System.out.println(requestBody);
//        RequestBody requestBody = new FormBody.Builder().add("text", text.getText().toString()).build();
                        final Request request = new Request.Builder().url("https://test.extern.azusa.one:7541/chat/file").header("cookie",cookie).post(requestBody).build();
                        OkHttpClient okHttpClient = new OkHttpClient();
                        Call call = okHttpClient.newCall(request);

                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Error:" + e.getMessage(), Toast.LENGTH_LONG).show());
                            }

                            @SuppressLint("LongLogTag")
                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                String responseData = Objects.requireNonNull(response.body()).string();
                                try {
                                    JSONObject jsonObject= new JSONObject(responseData);
                                    Log.d("Login", responseData);
                                    if (jsonObject.getBoolean("success")) {
                                        runOnUiThread(() -> {
                                            Toast.makeText(getApplicationContext(), "Release your discover!", Toast.LENGTH_LONG).show();
                                        });
                                    } else {
                                        runOnUiThread(() -> {
                                            try {
                                                Toast.makeText(getApplicationContext(), "ERROR:" + jsonObject.getString("msg"), Toast.LENGTH_LONG).show();
                                            } catch (JSONException e) {
                                                Log.d("DiscoverReleaseActivity ERROR", e.getMessage());
                                            }
                                        });
                                    }
                                } catch (JSONException e) {
                                    Log.d("DiscoverReleaseActivity ERROR", e.getMessage());
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }
}
