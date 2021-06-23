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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

public class MyprofileFragment extends Fragment {


    ImageView profile;
    String ProfileDir;
    ImageView gender;
    String Gender;
    TextView nickname;
    String Nickname;
    TextView id;
    String ID;

    public MyprofileFragment() {
        // Required empty public constructor
    }

    public static MyprofileFragment newInstance(String profiledir, String nickname, String gender, String id) {
        MyprofileFragment fragment = new MyprofileFragment();
        Bundle args = new Bundle();
        args.putString("ProfileDir", profiledir);
        args.putString("Nickname", nickname);
        args.putString("Gender", gender);
        args.putString("ID", id);
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
        ProfileDir = bundle.getString("UserProfile");
        Gender = bundle.getString("Gender");
        Nickname = bundle.getString("Nickname");
        ID = bundle.getString("ID");
        return inflater.inflate(R.layout.fragment_myprofile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profile = requireActivity().findViewById(R.id.MyprofileFragment_Profile);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.unnamed);
        File file = new File(requireContext().getFilesDir(), ProfileDir);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(fileInputStream);
        } catch (FileNotFoundException e) {
            Toast.makeText(getContext(), "FileNotFoundException" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        profile.setImageBitmap(bitmap);

        gender = requireActivity().findViewById(R.id.MyprofileFragment_Gender);
        if (Gender.equals("male")) {
            gender.setImageResource(R.drawable.ic_male_blue_25dp);
        } else if (Gender.equals("female")) {
            gender.setImageResource(R.drawable.ic_female_blue_25dp);
        }

        nickname = requireActivity().findViewById(R.id.MyprofileFragment_Nickname);
        nickname.setText(Nickname);

        id = requireActivity().findViewById(R.id.MyprofileFragment_PhoneNumber);
        id.setText(ID);
    }
}