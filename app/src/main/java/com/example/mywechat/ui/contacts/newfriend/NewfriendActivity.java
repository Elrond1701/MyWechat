package com.example.mywechat.ui.contacts.newfriend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mywechat.R;
import com.example.mywechat.data.Friend;
import com.example.mywechat.data.User;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewfriendActivity extends AppCompatActivity {

    private EditText editText;
    private Friend friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newfriend);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        editText = findViewById(R.id.NewfriendActivity_EditText);

        Button button = findViewById(R.id.NewfriendActivity_Button);
        button.setOnClickListener(v -> {
            User user = new User();
            user.get(getFilesDir());

            String Username = editText.getText().toString();
            final Request request = new Request.Builder().url("https://test.extern.azusa.one:7541/user?uname=" + Username)
                    .header("Cookie", user.getCookie()).get().build();
            OkHttpClient okHttpClient = new OkHttpClient();
            Call call = okHttpClient.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "ERROR:" + e.getMessage(), Toast.LENGTH_LONG).show());
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String responseData = Objects.requireNonNull(response.body()).string();
                    Log.d("ResponseHappy", responseData);
                    try {
                        JSONObject jsonObject = new JSONObject(responseData);
                        if (jsonObject.getBoolean("success")) {
                            friend = new Friend();
                            friend.setBirthDate(jsonObject.getString("birthdate"));
                            friend.setID(jsonObject.getString("username"));
                            friend.setNickname(jsonObject.getString("nickname"));
                            friend.setGender(jsonObject.getString("sex"));
                            friend.setWhatsUp(jsonObject.getString("sign"));
                            Intent intent = new Intent(NewfriendActivity.this, AddNewfriendActivity.class);
                            intent.putExtra("BirthDate", friend.getBirthDate());
                            intent.putExtra("ID", friend.getID());
                            intent.putExtra("Nickname", friend.getNickname());
                            intent.putExtra("Gender", friend.getGender());
                            intent.putExtra("WhatsUp", friend.getWhatsUp());
                            startActivity(intent);
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

        Button see = findViewById(R.id.NewfriendActivity_See);
        see.setOnClickListener(v -> {
            Intent intent = new Intent(NewfriendActivity.this, AddyouActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}