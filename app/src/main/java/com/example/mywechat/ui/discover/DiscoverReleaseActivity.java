package com.example.mywechat.ui.discover;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywechat.MainActivity;
import com.example.mywechat.R;
import com.example.mywechat.ui.login.LoginActivity;
import com.example.mywechat.ui.me.myprofile.change.ProfileChangeActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DiscoverReleaseActivity extends AppCompatActivity {

    private ImageView profile;
    private Uri image;
    public static final int PICK_PHOTO = 101;

    Uri imageUri;
    TextView discoverCancel;
    TextView discoverSend;
    EditText text;
    ImageView img_chosen1;
    ImageView img_chosen2;
    ImageView img_chosen3;
    ImageView add;
    private ArrayList<Bitmap> img_chosen_list;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_release);
        initView();
    }

    private void initView(){
        img_chosen_list = new ArrayList<Bitmap>();
        text = findViewById(R.id.discover_release_text);
        img_chosen1 = findViewById(R.id.discover_img_chosen1);
        img_chosen2 = findViewById(R.id.discover_img_chosen2);
        img_chosen3 = findViewById(R.id.discover_img_chosen3);
        discoverCancel = findViewById(R.id.discover_release_cancel);
        discoverSend = findViewById(R.id.discover_release_send);
        add = findViewById(R.id.discover_img_add);
        discoverCancel.setOnClickListener(v -> {

        });
        discoverSend.setOnClickListener(v -> {
            discoverRelease();
        });
        add.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(DiscoverReleaseActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(DiscoverReleaseActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
            }

            Intent intent = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_PHOTO);
        });
    }

//    private void choosePhoto(){
//        Intent intent = new Intent(Intent.ACTION_PICK, null);
//        // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型" 所有类型则写 "image/*"
//        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/jpeg");
//        startActivityForResult(intent, PICK_PHOTO);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        ImageView pic1 = findViewById(R.id.discover_img_chosen1);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PICK_PHOTO: {
                    // 获取图片
                    try {
                        //该uri是上一个Activity返回的
                        imageUri = data.getData();
                        if(imageUri != null) {
                            Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                            Log.i("bit", String.valueOf(bit));
//                            img_chosen1.setVisibility(View.VISIBLE);
//                            img_chosen1.setImageBitmap(bit);
                            img_chosen_list.add(bit);
                            if(img_chosen_list.size()==1){
                                img_chosen1.setVisibility(View.VISIBLE);
                                img_chosen1.setImageBitmap(bit);
                            }
                            else if(img_chosen_list.size()==2){
                                img_chosen2.setVisibility(View.VISIBLE);
                                img_chosen2.setImageBitmap(bit);
                            }
                            else if(img_chosen_list.size()==3){
                                img_chosen3.setVisibility(View.VISIBLE);
                                img_chosen3.setImageBitmap(bit);
                                add.setVisibility(View.GONE);
                            }
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
        RequestBody requestBody = new FormBody.Builder().add("text", text.getText().toString())
                                                        .add("pictures", String.valueOf(img_chosen_list)).build();
        final Request request = new Request.Builder().url("https://test.extern.azusa.one:7541/moment").post(requestBody).build();
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



//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        if (requestCode == GlobalVariable.GALLERY_REQUEST_CODE)
//        {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                choosePhoto();
//            }
//            else {
//                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
//            }
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
}
