package com.example.mywechat.ui.me;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import com.example.mywechat.R;
import com.example.mywechat.data.User;
import com.example.mywechat.ui.login.LoginActivity;
import com.example.mywechat.ui.me.myprofile.MyprofileActivity;
import com.example.mywechat.ui.me.myprofile.MyprofileFragment;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MeFragment extends Fragment {

    private User user;

    MyprofileFragment myprofileFragment;


    public static final int MYPROFILE = 101;

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

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.unnamed);
        //File file = null;
        //new Thread(new Runnable() {
        //    @Override
        //    public void run() {
        //        File file = new File(requireContext().getFilesDir(), "UserBitmap");
        //        try {
        //            FileOutputStream fileOutputStream = new FileOutputStream(file);
        //            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
        //            try {
        //                fileOutputStream.close();
        //            } catch (IOException e) {
        //                Toast.makeText(getActivity(), "ERROR:" + e.getMessage(), Toast.LENGTH_LONG).show();
        //            }
        //        } catch (FileNotFoundException e) {
        //            Toast.makeText(getActivity(), "ERROR:" + e.getMessage(), Toast.LENGTH_LONG).show();
        //        }
        //    }
        //}).start();
        user = new User();

        File UserJsonFile = new File(getActivity().getFilesDir(), "UserJson");
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
            if (user_get == null) {
                user.setProfileDir("");
                user.setGender("");
                user.setNickname("");
                user.setID("");
                user.setBirthDate("");
                user.setWhatsUp("");
            } else {
                try {
                    String Nickname = user_get.getString("Nickname");
                    user.setNickname(Nickname);
                } catch (JSONException e) {
                    Log.d("LoginActivity ERROR", e.getMessage());
                    user.setNickname("");
                }
                try {
                    String Gender = user_get.getString("Gender");
                    user.setGender(Gender);
                } catch (JSONException e) {
                    Log.d("LoginActivity ERROR", e.getMessage());
                    user.setGender("");
                }
                try {
                    String WhatsUp = user_get.getString("WhatsUp");
                    user.setWhatsUp(WhatsUp);
                } catch (JSONException e) {
                    Log.d("LoginActivity ERROR", e.getMessage());
                    user.setWhatsUp("");
                }
                try {
                    String BirthDate = user_get.getString("BirthDate");
                    user.setBirthDate(BirthDate);
                } catch (JSONException e) {
                    Log.d("LoginActivity ERROR", e.getMessage());
                    user.setBirthDate("");
                }
                try {
                    String UserName = user_get.getString("UserName");
                    user.setID(UserName);
                } catch (JSONException e) {
                    Log.d("LoginActivity ERROR", e.getMessage());
                    user.setID("");
                }
                try {
                    String Cookie = user_get.getString("Cookie");
                    user.setCookie(Cookie);
                } catch (JSONException e) {
                    Log.d("JSONException", e.getMessage());
                    user.setCookie("");
                }
            }
        }
        user.setProfileDir("UserBitmap");
        user.setProfile(bitmap);

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
            intent.putExtra("BirthDate", user.getBirthDate());
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
                user.setBirthDate(data.getStringExtra("BirthDate"));
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

    @Override
    public void onStop() {
        super.onStop();


        new Thread(() -> {
            RequestBody requestBody = new FormBody.Builder().add("nickname", user.getNickname())
                    .add("sex", user.getGender())
                    .add("birthdate", user.getBirthDate())
                    .add("sign", user.getWhatsUp()).build();
            final Request request = new Request.Builder().url("https://test.extern.azusa.one:7541/user/info").post(requestBody).build();
            OkHttpClient okHttpClient = new OkHttpClient();
            Call call = okHttpClient.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    requireActivity().runOnUiThread(() -> Toast.makeText(requireActivity().getApplicationContext(), "ERROR:" + e.getMessage(), Toast.LENGTH_LONG).show());
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String responseData = Objects.requireNonNull(response.body()).string();
                    Log.d("Response:HELLO", responseData);
                    try {
                        JSONObject jsonObject = new JSONObject(responseData);
                    } catch (JSONException e) {
                        requireActivity().runOnUiThread(() -> Toast.makeText(requireContext().getApplicationContext(), "ERROR:" + e.getMessage(), Toast.LENGTH_LONG).show());
                    }
                }
            });
        }).start();
    }
}