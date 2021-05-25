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

public class WhatsUpChangeActivity extends AppCompatActivity {

    Intent intent;

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whatsup_change);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        intent = getIntent();

        editText = findViewById(R.id.WhatsUpChangeActivity_EditText);
        editText.setText(intent.getStringExtra("WhatsUp"));

        Button done = findViewById(R.id.WhatsUpChangeActivity_Done);
        done.setOnClickListener(v -> {
            intent.putExtra("WhatsUp", editText.getText().toString());
            WhatsUpChangeActivity.this.setResult(1, intent);
            WhatsUpChangeActivity.this.finish();

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