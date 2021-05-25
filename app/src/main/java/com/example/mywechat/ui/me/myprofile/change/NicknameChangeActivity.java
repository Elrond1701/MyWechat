package com.example.mywechat.ui.me.myprofile.change;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mywechat.R;

public class NicknameChangeActivity extends AppCompatActivity {

    Intent intent;

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname_change);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        intent = getIntent();

        editText = findViewById(R.id.NicknameChangeActivity_EditText);
        editText.setText(intent.getStringExtra("Nickname"));

        Button done = findViewById(R.id.NicknameChangeActivity_Done);
        done.setOnClickListener(v -> {
            intent.putExtra("Nickname", editText.getText().toString());
            NicknameChangeActivity.this.setResult(1, intent);
            NicknameChangeActivity.this.finish();
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