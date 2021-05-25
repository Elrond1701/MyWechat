package com.example.mywechat.ui.me;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mywechat.R;
import com.example.mywechat.data.Friend;
import com.example.mywechat.ui.login.LoginActivity;
import com.example.mywechat.ui.me.myprofile.MyprofileActivity;
import com.example.mywechat.ui.me.myprofile.MyprofileFragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MeFragment extends Fragment {

    private Friend friend;

    private FrameLayout myprofile;
    private Button logout;

    public static final int MYPROFILE = 101;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.myjpg);
        File file = new File(getContext().getFilesDir(), "UserBitmap");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fileOutputStream);
            try {
                fileOutputStream.close();
            } catch (IOException e) {

            }
        } catch (FileNotFoundException e) {

        }
        friend = new Friend();
        friend.setProfile(bitmap);
        friend.setProfileDir("UserBitmap");
        friend.setGender("male");
        friend.setNickname("HIHI");
        friend.setNumber(0);
        friend.setPhoneNumber("12344");
        friend.setRegion("北京");
        friend.setWhatsUp("Very Good");

        myprofile = view.findViewById(R.id.MeFragment_MyProfile);
        myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyprofileActivity.class);
                intent.putExtra("ProfileDir", friend.getProfileDir());
                intent.putExtra("Nickname", friend.getNickname());
                intent.putExtra("ID", friend.getPhoneNumber());
                intent.putExtra("Gender", friend.getGender());
                intent.putExtra("Region", friend.getRegion());
                intent.putExtra("WhatsUp", friend.getWhatsUp());
                startActivityForResult(intent, MYPROFILE);
            }
        });

        logout = view.findViewById(R.id.MeFragment_LogOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 101:
                switch (resultCode) {
                    case 0:
                        Bitmap bitmap = BitmapFactory.decodeFile(getContext().getFilesDir().toString() + "/UserBitmap");
                        friend.setProfile(bitmap);
                        friend.setNickname(data.getStringExtra("Nickname"));
                        friend.setPhoneNumber(data.getStringExtra("ID"));
                        friend.setGender(data.getStringExtra("Gender"));
                        friend.setRegion(data.getStringExtra("Region"));
                        friend.setWhatsUp(data.getStringExtra("WhatsUp"));
                        updateProfile(friend.getProfile(), friend.getNickname(), friend.getPhoneNumber(), friend.getGender());
                        break;
                    default:
                        Toast.makeText(getActivity(), "Myprofile Wrong set", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                Toast.makeText(getActivity(), "Myprofile Wrong set", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void updateProfile(Bitmap profile, String nickname, String id, String gender) {

    }
}