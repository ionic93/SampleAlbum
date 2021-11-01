package com.example.samplealbum;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    Button button;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        imageView = findViewById(R.id.imageView);

        button.setOnClickListener(v ->{
            openGallery();
        });
    }

    private void openGallery() {
        Intent intent = new Intent();
        //이미지 모든 타입
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //인텐트가 갔다가 돌아옴(101 코드 남김)
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            Uri uri = data.getData();
            ContentResolver resolver = getContentResolver();
            try {
                //안드로이드에서 이미지를 열고 닫을 때 사용
                InputStream in = resolver.openInputStream(uri);
                //이미지를 비트맵으로 변경
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                imageView.setImageBitmap(bitmap);
                in.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}