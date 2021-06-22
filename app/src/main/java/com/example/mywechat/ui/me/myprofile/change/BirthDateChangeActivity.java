package com.example.mywechat.ui.me.myprofile.change;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.mywechat.R;

public class BirthDateChangeActivity extends AppCompatActivity {

    Intent intent;

    private String birthdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthdate_change);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        intent = getIntent();
        birthdate = intent.getStringExtra("BirthDate");

        Button button = findViewById(R.id.BirthDateChangeActivity_Button);
        DatePicker datePicker = findViewById(R.id.BirthDateChangeActivity_DatePicker);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("BirthDate", birthdate);
                BirthDateChangeActivity.this.setResult(1, intent);
                BirthDateChangeActivity.this.finish();
            }
        });
        datePicker.init(getYear(), getMonth(), getDay(), (view, year, monthOfYear, dayOfMonth) -> setBirthDate(year, monthOfYear, dayOfMonth));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.setResult(0, intent);
            this.finish(); // back button
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private int getYear() {
        int year = 1970;
        for (int i = 0; i < birthdate.length(); i++) {
            if (birthdate.substring(i, i + 1).equals("/")) {
                Log.d("Hello", "Hello");
                year = Integer.parseInt(birthdate.substring(0, i - 1));
                break;
            }
        }
        return year;
    }

    private int getMonth() {
        int month = 1;
        int count = 0;
        int pre = 0;
        for (int i = 0; i < birthdate.length(); i++) {
            if (birthdate.substring(i, i + 1).equals("/")) {
                count++;
                if (count == 1) {
                    pre = i;
                }
                else if (count == 2) {
                    month = Integer.parseInt(birthdate.substring(pre, i - 1));
                    break;
                }
            }
        }
        return month;
    }

    private int getDay() {
        int day = 1;
        int  count = 0;
        for (int i = 0; i < birthdate.length(); i++) {
            if (birthdate.substring(i, i + 1).equals("/")) {
                count++;
                if (count == 2) {
                    day = Integer.parseInt(birthdate.substring(i + 1, birthdate.length() - 1));
                    break;
                }
            }
        }
        return day;
    }

    private void setBirthDate(int year, int month, int day) {
        birthdate = year + "/" + month + "/" + day;
    }
}