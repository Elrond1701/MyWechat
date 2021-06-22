package com.example.mywechat.ui.chats;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywechat.R;
import com.example.mywechat.data.Message;

import java.util.LinkedList;

public class MsgActivity extends AppCompatActivity {

    public static final int PICK_PHOTO = 101;

    private Button back;
    private EditText inputText;
    private Button send;
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
        initView();
        getDate();
        setAdapter();
    }

    public void initView() {
        Messages = new LinkedList<Message>();
        inputText = findViewById(R.id.chat_input);
//        back = findViewById(R.id.chat_back);
//        send = findViewById(R.id.chat_send);
//        msgListView = findViewById(R.id.chat_content);
        voice = findViewById(R.id.chat_voice);
        more = findViewById(R.id.chat_more);
        bottom_layout = findViewById(R.id.chat_bottom_layout);
        pic = findViewById(R.id.chat_photo_more);
        video = findViewById(R.id.chat_video_more);
        location = findViewById(R.id.chat_location_more);
        recyclerView = findViewById(R.id.chat_content);

//        send.setOnClickListener(v -> {
////            String content =  inputText.getText().toString();
////            if(!"".equals(content)){
////
////            }
////            bottom_layout.setVisibility(View.GONE);
//        });
        voice.setOnClickListener(v -> {

        });

        more.setOnClickListener(v -> {
            bottom_layout.setVisibility(View.VISIBLE);
        });

        pic.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MsgActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        101);
            }else {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                // 如果限制上传到服务器的intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/jpeg");
                startActivityForResult(intent, PICK_PHOTO);
            }
        });

        video.setOnClickListener(v -> {

        });

        location.setOnClickListener(v -> {

        });
    }

    public void getDate() {

    }

    public void setAdapter() {
        MsgAdapter msgAdapter = new MsgAdapter(Messages);
        msgAdapter.setParent(MsgActivity.this);
        recyclerView.setAdapter(msgAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MsgActivity.this, RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void sendText(){

    }

    public void sendImage() {

    }

    public void sendVoice() {

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
//                        //该uri是上一个Activity返回的
//                        imageUri = data.getData();
//                        if(imageUri!=null) {
//                            Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
////                            Log.i("bit", String.valueOf(bit));
//                            pic1.setVisibility(View.VISIBLE);
//                            pic1.setImageBitmap(bit);
//                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }
}
