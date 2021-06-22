package com.example.mywechat.ui.discover;

import android.Manifest;
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
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywechat.MainActivity;
import com.example.mywechat.R;

import java.util.ArrayList;

public class DiscoverVideoReleaseActivity extends AppCompatActivity {

//    private ImageView profile;
//    private Uri image;
    public static final int PICK_VIDEO = 101;

    Uri videoUri;
    TextView discoverCancel;
    TextView discoverSend;
    ImageView add;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_video_release);
        initView();
    }

    private void initView(){
        discoverCancel = findViewById(R.id.discover_video_release_cancel);
        discoverSend = findViewById(R.id.discover_video_release_send);
        add = findViewById(R.id.discover_video_add);
        discoverCancel.setOnClickListener(v -> {

        });
        discoverSend.setOnClickListener(v -> {

        });
        add.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(DiscoverVideoReleaseActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(DiscoverVideoReleaseActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
            }

            Intent intent = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
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
                    // 获取图片
                    try {
                        //该uri是上一个Activity返回的
                        videoUri = data.getData();
                        if(videoUri != null) {
//                            Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
//                            Log.i("bit", String.valueOf(bit));
////                            img_chosen1.setVisibility(View.VISIBLE);
////                            img_chosen1.setImageBitmap(bit);
//                            img_chosen_list.add(imageUri);
//                            if(img_chosen_list.size()==1){
//                                img_chosen1.setVisibility(View.VISIBLE);
//                                img_chosen1.setImageBitmap(bit);
//                            }
//                            else if(img_chosen_list.size()==2){
//                                img_chosen2.setVisibility(View.VISIBLE);
//                                img_chosen2.setImageBitmap(bit);
//                            }
//                            else if(img_chosen_list.size()==3){
//                                img_chosen3.setVisibility(View.VISIBLE);
//                                img_chosen3.setImageBitmap(bit);
//                                add.setVisibility(View.GONE);
//                            }

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
}
