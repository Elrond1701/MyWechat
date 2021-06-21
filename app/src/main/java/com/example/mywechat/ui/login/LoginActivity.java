package com.example.mywechat.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mywechat.MainActivity;
import com.example.mywechat.R;
import com.example.mywechat.ui.register.RegisterActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
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

        loginButton.setOnClickListener(v -> {
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
                    String responseData = Objects.requireNonNull(response.body()).string();
                    try {
                        JSONObject jsonObject= new JSONObject(responseData);
                        Log.d("Login", responseData);
                        if (jsonObject.getBoolean("success")) {
                            Headers headers = response.headers();
                            HttpUrl loginUrl = request.url();
                            List<Cookie> cookies = Cookie.parseAll(loginUrl, headers);
                            StringBuilder cookieStr = new StringBuilder();
                            for (int i = 0; i < cookies.size(); i++) {
                                Cookie cookie = cookies.get(i);
                                cookieStr.append(cookie.name()).append("=").append(cookie.value()).append(";");
                            }
                            String result = cookieStr.toString();
                            runOnUiThread(() -> {
                                Toast.makeText(getApplicationContext(), "Login!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                                try {
                                    String Nickname = jsonObject.getString("nickname");
                                    intent.putExtra("Nickname", Nickname);
                                } catch (JSONException e) {
                                    Log.d("LoginActivity ERROR", e.getMessage());
                                    intent.putExtra("Nickname", "");
                                }
                                try {
                                    String Gender = jsonObject.getString("sex");
                                    intent.putExtra("Gender", Gender);
                                } catch (JSONException e) {
                                    Log.d("LoginActivity ERROR", e.getMessage());
                                    intent.putExtra("Gender", "");
                                }
                                try {
                                    String WhatsUp = jsonObject.getString("sign");
                                    intent.putExtra("WhatsUp", WhatsUp);
                                } catch (JSONException e) {
                                    Log.d("LoginActivity ERROR", e.getMessage());
                                    intent.putExtra("WhatsUp", "");
                                }
                                try {
                                    String BirthDate = jsonObject.getString("birthdate");
                                    intent.putExtra("BirthDate", BirthDate);
                                } catch (JSONException e) {
                                    Log.d("LoginActivity ERROR", e.getMessage());
                                    intent.putExtra("BirthDate", "");
                                }
                                try {
                                    String UserName = jsonObject.getString("username");
                                    intent.putExtra("UserName", UserName);
                                } catch (JSONException e) {
                                    Log.d("LoginActivity ERROR", e.getMessage());
                                    intent.putExtra("UserName", "");
                                }
                                intent.putExtra("Password", passwordEditText.getText().toString());
                                intent.putExtra("COOKIE", result);
                                startActivity(intent);
                            });
                        } else {
                            runOnUiThread(() -> {
                                try {
                                    Toast.makeText(getApplicationContext(), "ERROR:" + jsonObject.getString("msg"), Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    Log.d("LoginActivity ERROR", e.getMessage());
                                }
                            });
                        }
                    } catch (JSONException e) {
                        Log.d("LoginActivity ERROR", e.getMessage());
                    }

                }
            });
        });

        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            intent.putExtra("ID", usernameEditText.getText().toString());
            intent.putExtra("Password", passwordEditText.getText().toString());
            startActivity(intent);
        });
    }
}