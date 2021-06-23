package com.example.mywechat.ui.me.myprofile.change;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.mywechat.R;
import com.example.mywechat.data.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ProfileChangeActivity extends AppCompatActivity {

    Intent intent;

    private ImageView profile;

    User user;

    public static final int TAKE_CAMERA = 101;
    public static final int PICK_PHOTO = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_change);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        user = new User();
        user.get(getFilesDir());

        intent = getIntent();
        profile = findViewById(R.id.ProfileChangeActivity_Profile);
        Bitmap bitmap = null;
        File file = new File(this.getFilesDir(), intent.getStringExtra("ProfileDir"));
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(fileInputStream);
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "FileNotFoundException" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        profile.setImageBitmap(bitmap);

        new Thread(() -> {
            Bitmap new_bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.avatar1);
            File file1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath(),
                    "avatar1.png");
            try {
                FileOutputStream out = new FileOutputStream(file1);
                new_bitmap1.compress(Bitmap.CompressFormat.PNG, 100, out);
                Log.d("GOOD", "GOOD");
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    Log.d("IOException", e.getMessage());
                }
            } catch (FileNotFoundException e) {
                Log.d("FileNotFoundException", e.getMessage());
            }

            Intent intent1 = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri1 = Uri.fromFile(file1);
            intent1.setData(uri1);
            sendBroadcast(intent1);

            Bitmap new_bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.avatar2);
            File file2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath(),
                    "avatar2.png");
            try {
                FileOutputStream out = new FileOutputStream(file2);
                new_bitmap2.compress(Bitmap.CompressFormat.PNG, 100, out);
                Log.d("GOOD", "GOOD");
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    Log.d("IOException", e.getMessage());
                }
            } catch (FileNotFoundException e) {
                Log.d("FileNotFoundException", e.getMessage());
            }

            Intent intent2 = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri2 = Uri.fromFile(file2);
            intent.setData(uri2);
            sendBroadcast(intent2);
        }).start();

        Button album = findViewById(R.id.ProfileChangeActivity_Album);
        album.setOnClickListener(v -> {

            if (ContextCompat.checkSelfPermission(ProfileChangeActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ProfileChangeActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
            }

            Intent intent = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_PHOTO);
        });

        Button camera = findViewById(R.id.ProfileChangeActivity_Camera);
        camera.setOnClickListener(v -> {

        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            intent.putExtra("ProfileDir", "UserBitmap");
            this.setResult(0, intent);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_CAMERA:
                break;
            case PICK_PHOTO:
                if (resultCode == RESULT_OK) {
                    assert data != null;
                    Uri uri = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(uri,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    Bitmap bitmap = BitmapFactory.decodeFile(picturePath);

                    new Thread(() -> {
                        user.setProfile(bitmap);
                        user.save(getFilesDir());
                    }).start();
                    profile.setImageBitmap(bitmap);
                }
                break;
        }
    }
}