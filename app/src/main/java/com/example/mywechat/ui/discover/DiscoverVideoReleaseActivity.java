package com.example.mywechat.ui.discover;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywechat.MainActivity;
import com.example.mywechat.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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

public class DiscoverVideoReleaseActivity extends AppCompatActivity {

//    private ImageView profile;
//    private Uri image;
    String cookie;
    public static final int PICK_VIDEO = 101;

    Uri videoUri;
    TextView discoverCancel;
    TextView discoverSend;
    VideoView video;
    ImageView add;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_video_release);
        initView();
    }

    private void initView(){
        File UserJsonFile = new File(this.getFilesDir(), "UserJson");
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
            } catch (JSONException e) {
                user_get = null;
                Log.d("JSONERROR", e.getMessage());
            }
            try {
                cookie = user_get.getString("Cookie");
            } catch (JSONException e) {
                Log.d("GET COOKIE ERROR", e.getMessage());
            }
        }
        discoverCancel = findViewById(R.id.discover_video_release_cancel);
        discoverSend = findViewById(R.id.discover_video_release_send);
        add = findViewById(R.id.discover_video_add);
        video = findViewById(R.id.discover_video_chosen);
        discoverCancel.setOnClickListener(v -> {

        });
        discoverSend.setOnClickListener(v -> {
            discoverRelease();
        });
        add.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(DiscoverVideoReleaseActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(DiscoverVideoReleaseActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
            }

            Intent intent = new Intent(
                    Intent.ACTION_PICK,
                    null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"video/*");
            startActivityForResult(intent, PICK_VIDEO);
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        ImageView pic1 = findViewById(R.id.discover_img_chosen1);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PICK_VIDEO: {
                    try {
                        //该uri是上一个Activity返回的
                        videoUri = data.getData();
                        System.out.println(videoUri);
                        if(videoUri != null) {
                            video.setVisibility(View.VISIBLE);
                            video.setVideoURI(videoUri);
                            video.start();
                            add.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    public void discoverRelease (){

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
                .addFormDataPart("vedios","file",RequestBody.create(MEDIA_TYPE,file))
                .build();
        System.out.println(requestBody);
//        RequestBody requestBody = new FormBody.Builder().add("text", text.getText().toString()).build();
        final Request request = new Request.Builder().url("https://test.extern.azusa.one:7541/moment").header("cookie",cookie).post(requestBody).build();
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
    }

}
