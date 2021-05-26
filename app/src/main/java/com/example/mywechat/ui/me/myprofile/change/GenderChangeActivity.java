package com.example.mywechat.ui.me.myprofile.change;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.mywechat.R;

public class GenderChangeActivity extends AppCompatActivity {

    Intent intent;

    private RadioButton male;
    private RadioButton female;

    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender_change);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        intent = getIntent();
        gender = intent.getStringExtra("Gender");

        male = findViewById(R.id.GenderChangeActivity_Male);
        female = findViewById(R.id.GenderChangeActivity_Female);
        RadioButton invisible = findViewById(R.id.GenderChangeActivity_Invisible);

        if (gender.equals("male")) {
            male.setChecked(true);
        } else if (gender.equals("female")) {
            female.setChecked(true);
        } else {
            invisible.setChecked(true);
        }

        Button done = findViewById(R.id.GenderChangeActivity_Done);
        done.setOnClickListener(v -> {
            if (male.isChecked()) {
                gender = "male";
            } else if (female.isChecked()) {
                gender = "female";
            } else {
                gender = "invisible";
            }
            intent.putExtra("Gender", gender);
            GenderChangeActivity.this.setResult(1, intent);
            GenderChangeActivity.this.finish();
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