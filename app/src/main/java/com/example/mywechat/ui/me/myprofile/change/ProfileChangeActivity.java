package com.example.mywechat.ui.me.myprofile.change;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mywechat.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ProfileChangeActivity extends AppCompatActivity {

    Intent intent;

    private ImageView profile;

    private Uri image;
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
            File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
            try {
                if (outputImage.exists()) {
                    outputImage.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            image = FileProvider.getUriForFile(ProfileChangeActivity.this, "com.feige.pickphoto.fileprovider", outputImage);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, image);
            startActivityForResult(intent, TAKE_CAMERA);
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
                        try {
                            File file = new File(ProfileChangeActivity.this.getFilesDir(), "UserBitmap");
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                            try {
                                fileOutputStream.close();
                            } catch (IOException e) {
                                Toast.makeText(ProfileChangeActivity.this, "Profile Wrong get" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        } catch (FileNotFoundException e) {
                            Toast.makeText(ProfileChangeActivity.this, "Profile Wrong get" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }).start();
                    profile.setImageBitmap(bitmap);
                }
                break;
        }
    }
}