package com.example.mywechat.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mywechat.MainActivity;
import com.example.mywechat.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        intent = getIntent();

        final EditText usernameEditText = findViewById(R.id.RegisterActivity_Username);
        final EditText passwordEditText = findViewById(R.id.RegisterActivity_Password);
        final EditText emailEditText = findViewById(R.id.RegisterActivity_Email);
        final EditText verifycodeEditText = findViewById(R.id.RegisterActivity_VerifyCode);
        final EditText nicknameEditText = findViewById(R.id.RegisterActivity_Nickname);
        final Button getverifycodeButton = findViewById(R.id.RegisterActivity_GetVerifyCode);
        final Button registerButton = findViewById(R.id.RegisterActivity_Register);

        usernameEditText.setText(intent.getStringExtra("ID"));
        passwordEditText.setText(intent.getStringExtra("Password"));

        getverifycodeButton.setOnClickListener(v -> {
            RequestBody requestBody = new FormBody.Builder().add("email", emailEditText.getText().toString()).build();
            final Request request = new Request.Builder().url("https://test.extern.azusa.one:7541/user/verify").post(requestBody).build();
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
                        JSONObject jsonObject = new JSONObject(responseData);
                        if (jsonObject.getBoolean("success")) {
                            runOnUiThread(() -> Toast.makeText(getApplicationContext(), "VerifyCode has been sent.", Toast.LENGTH_LONG).show());
                        } else {
                            runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Cannot send VerifyCode", Toast.LENGTH_LONG).show());
                        }
                    } catch (JSONException e) {
                        runOnUiThread(() -> Toast.makeText(getApplicationContext(), "ERROR:" + e.getMessage(), Toast.LENGTH_LONG).show());
                    }
                }
            });
        });

        registerButton.setOnClickListener(v -> {
            RequestBody requestBody = new FormBody.Builder().add("uname", usernameEditText.getText().toString())
                                                            .add("password", passwordEditText.getText().toString())
                                                            .add("email", emailEditText.getText().toString())
                                                            .add("verifyCode", verifycodeEditText.getText().toString())
                                                            .add("nickname", nicknameEditText.getText().toString()).build();
            final Request request = new Request.Builder().url("https://test.extern.azusa.one:7541/user/register").post(requestBody).build();
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
                    Log.d("Response", responseData);
                    try {
                        JSONObject jsonObject = new JSONObject(responseData);
                        if (jsonObject.getBoolean("success")) {
                            runOnUiThread(() -> {
                                Toast.makeText(getApplicationContext(), "Register and Login!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                intent.putExtra("UserName", usernameEditText.getText().toString());
                                intent.putExtra("Password", passwordEditText.getText().toString());
                                intent.putExtra("Nickname", nicknameEditText.getText().toString());
                                intent.putExtra("Gender", "");
                                intent.putExtra("BirthDate", "");
                                intent.putExtra("WhatsUp", "");
                                startActivity(intent);
                            });
                        } else {
                            runOnUiThread(() -> {
                                try {
                                    Toast.makeText(getApplicationContext(), "ERROR:" + jsonObject.getString("msg"), Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    Toast.makeText(getApplicationContext(), "ERROR:" + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        runOnUiThread(() -> Toast.makeText(getApplicationContext(), "ERROR:" + e.getMessage(), Toast.LENGTH_LONG).show());
                    }
                }
            });
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.setResult(0, intent);
            this.finish(); // back button
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}