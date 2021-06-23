package com.example.mywechat.ui.me.myprofile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mywechat.R;
import com.example.mywechat.data.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

public class MyprofileFragment extends Fragment {


    ImageView profile;
    ImageView gender;
    TextView nickname;
    TextView id;

    User user;

    public MyprofileFragment() {
        // Required empty public constructor
    }

    public static MyprofileFragment newInstance() {
        MyprofileFragment fragment = new MyprofileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle = getArguments();
        return inflater.inflate(R.layout.fragment_myprofile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        user = new User();
        user.get(requireActivity().getFilesDir());
        if (user.getProfile() == null) {
            Log.d("BAD", "BAD");
        } else {
            profile = requireActivity().findViewById(R.id.MyprofileFragment_Profile);
            profile.setImageBitmap(user.getProfile());
            Log.d("GOOD", "GOOD");
        }

        gender = requireActivity().findViewById(R.id.MyprofileFragment_Gender);
        if (user.getGender().equals("male")) {
            gender.setImageResource(R.drawable.ic_male_blue_25dp);
        } else if (user.getGender().equals("female")) {
            gender.setImageResource(R.drawable.ic_female_blue_25dp);
        }

        nickname = requireActivity().findViewById(R.id.MyprofileFragment_Nickname);
        nickname.setText(user.getNickname());

        id = requireActivity().findViewById(R.id.MyprofileFragment_PhoneNumber);
        id.setText(user.getID());
    }
}