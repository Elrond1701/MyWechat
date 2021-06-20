package com.example.mywechat.ui.me;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mywechat.R;
import com.example.mywechat.data.Friend;
import com.example.mywechat.data.User;
import com.example.mywechat.ui.login.LoginActivity;
import com.example.mywechat.ui.me.myprofile.MyprofileActivity;
import com.example.mywechat.ui.me.myprofile.MyprofileFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class MeFragment extends Fragment {

    private User user;

    MyprofileFragment myprofileFragment;

    public static final int MYPROFILE = 101;

    public static MeFragment newInstance(User user) {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        args.putString("Gender", user.getGender());
        args.putString("Nickname", user.getNickname());
        args.putString("ProfileDir", user.getProfileDir());
        args.putString("Password", user.getPassword());
        args.putString("ID", user.getID());
        args.putString("Region", user.getRegion());
        args.putString("WhatsUp", user.getWhatsUp());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myprofileFragment = new MyprofileFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.myjpg);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.myjpg2);
        File file = new File(requireContext().getFilesDir(), "UserBitmap");
        MediaStore.Images.Media.insertImage(requireContext().getContentResolver(), bitmap2, "title", "description");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                }
            }).start();
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                Toast.makeText(getActivity(), "Profile Wrong get" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(getActivity(), "Profile Wrong get" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        user = new User();
        user.setProfile(bitmap);
        user.setProfileDir("UserBitmap");
        user.setGender("male");
        user.setNickname("HIHI");
        user.setID("12344");
        user.setRegion("北京");
        user.setWhatsUp("Very Good");

        FragmentContainerView myprofile = view.findViewById(R.id.MeFragment_MyProfile);
        requireActivity().getSupportFragmentManager().beginTransaction().add(R.id.MeFragment_MyProfile,
                MyprofileFragment.newInstance(user.getProfileDir(), user.getNickname(), user.getGender(),
                        user.getID())).commit();

        myprofile.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MyprofileActivity.class);
            intent.putExtra("ProfileDir", user.getProfileDir());
            intent.putExtra("Nickname", user.getNickname());
            intent.putExtra("ID", user.getID());
            intent.putExtra("Gender", user.getGender());
            intent.putExtra("Region", user.getRegion());
            intent.putExtra("WhatsUp", user.getWhatsUp());
            startActivityForResult(intent, MYPROFILE);
        });

        Button logout = view.findViewById(R.id.MeFragment_LogOut);
        logout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == 0) {
                Bitmap bitmap = BitmapFactory.decodeFile(requireContext().getFilesDir().toString() + "/UserBitmap");
                user.setProfile(bitmap);
                assert data != null;
                user.setNickname(data.getStringExtra("Nickname"));
                user.setID(data.getStringExtra("ID"));
                user.setGender(data.getStringExtra("Gender"));
                user.setRegion(data.getStringExtra("Region"));
                user.setWhatsUp(data.getStringExtra("WhatsUp"));
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.MeFragment_MyProfile,
                        MyprofileFragment.newInstance(user.getProfileDir(), user.getNickname(),
                                user.getGender(), user.getID())).commit();
            } else {
                Toast.makeText(getActivity(), "Myprofile Wrong set", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getActivity(), "Myprofile Wrong set", Toast.LENGTH_LONG).show();
        }
    }
}