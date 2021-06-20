package com.example.mywechat.ui.login;

import android.app.Activity;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mywechat.MainActivity;
import com.example.mywechat.R;
import com.example.mywechat.ui.register.RegisterActivity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usernameEditText = findViewById(R.id.LoginActivity_ID);
        final EditText passwordEditText = findViewById(R.id.LoginActivity_Password);
        final Button loginButton = findViewById(R.id.LoginActivity_Login);
        final Button registerButton = findViewById(R.id.LoginActivity_Register);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestBody requestBody = new FormBody.Builder().add("username", usernameEditText.getText().toString())
                                                                .add("password", passwordEditText.getText().toString()).build();
                final Request request = new Request.Builder().url("https://test.extern.azusa.one:7541/user/login").post(requestBody).build();
                OkHttpClient okHttpClient = new OkHttpClient();
                Call call = okHttpClient.newCall(request);

                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Error:" + e.getMessage(), Toast.LENGTH_LONG).show());
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            runOnUiThread(() -> {
                                Toast.makeText(getApplicationContext(), "Login Successfully!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Cannot Login!", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                });
            }
        });

        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            intent.putExtra("ID", usernameEditText.getText().toString());
            intent.putExtra("Password", passwordEditText.getText().toString());
            startActivity(intent);
        });
    }
}