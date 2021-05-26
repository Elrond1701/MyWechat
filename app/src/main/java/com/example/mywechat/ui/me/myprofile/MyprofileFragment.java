package com.example.mywechat.ui.me.myprofile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mywechat.R;

public class MyprofileFragment extends Fragment {

    Intent intent;

    ImageView profile;
    ImageView gender;
    TextView nickname;
    TextView id;

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
        return inflater.inflate(R.layout.fragment_myprofile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profile = getActivity().findViewById(R.id.MyprofileFragment_Profile);
        gender = getActivity().findViewById(R.id.MyprofileFragment_Gender);
        nickname = getActivity().findViewById(R.id.MyprofileFragment_Nickname);
        id = getActivity().findViewById(R.id.MyprofileFragment_PhoneNumber);
    }

    public void setProfile(Bitmap profile) {
        this.profile.setImageBitmap(profile);
    }

    public void setNickname(String nickname) {
        this.nickname.setText(nickname);
    }

    public void setID(String id) {
        this.id.setText(id);
    }

    public void setGender(String gender) {
        if (gender.equals("male")) {
            this.gender.setImageResource(R.drawable.ic_male_blue_25dp);
        } else if (gender.equals("female")) {
            this.gender.setImageResource(R.drawable.ic_female_blue_25dp);
        }
    }
}