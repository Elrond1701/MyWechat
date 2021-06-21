package com.example.mywechat.ui.me;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.IpSecManager;
import android.os.Bundle;
import android.provider.MediaStore;
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
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class MeFragment extends Fragment {

    private User user;

    MyprofileFragment myprofileFragment;

    WebSocket webSocket;

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

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.myjpg);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.myjpg2);
        File file = new File(requireContext().getFilesDir(), "UserBitmap");
        MediaStore.Images.Media.insertImage(requireContext().getContentResolver(), bitmap2, "title", "description");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            new Thread(() -> bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)).start();
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
        user.setBirthDate("2021/6/21");
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
            intent.putExtra("BirthDate", user.getBirthDate());
            intent.putExtra("WhatsUp", user.getWhatsUp());
            startActivityForResult(intent, MYPROFILE);
        });

        Button logout = view.findViewById(R.id.MeFragment_LogOut);
        logout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });

        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url("wss://" + "8.140.133.34:7542" + "/").build();
        webSocket = okHttpClient.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                super.onClosed(webSocket, code, reason);
            }

            @Override
            public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                super.onClosing(webSocket, code, reason);
            }

            @Override
            public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @org.jetbrains.annotations.Nullable Response response) {
                super.onFailure(webSocket, t, response);
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
                super.onMessage(webSocket, text);
                requireActivity().runOnUiThread(() -> Toast.makeText(requireActivity().getApplicationContext(), "onMessage:" + text, Toast.LENGTH_LONG).show());
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
                super.onMessage(webSocket, bytes);
                requireActivity().runOnUiThread(() -> Toast.makeText(requireActivity().getApplicationContext(), "onMessage:" + bytes.toString(), Toast.LENGTH_LONG).show());
            }

            @Override
            public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                super.onOpen(webSocket, response);
                requireActivity().runOnUiThread(() -> {
                    try {
                        Toast.makeText(requireActivity().getApplicationContext(), "onOpen:" + Objects.requireNonNull(response.body()).string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        Toast.makeText(requireActivity().getApplicationContext(), "ERROR:" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("bizType", "USER_LOGIN");
            jsonObject.put("username", user.getID());
            jsonObject.put("password", user.getPassword());
            Log.d("Response:", jsonObject.toString());
        } catch (JSONException e) {
            Toast.makeText(requireActivity().getApplicationContext(), "ERROR:" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        webSocket.send(jsonObject.toString());
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