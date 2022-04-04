package com.example.turtlepartiesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;


// activity for showing the picture of the location the qr code was taken
public class TakenPictureActivity extends AppCompatActivity {

    private ImageView Locationpic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taken_picture);
        Locationpic = findViewById(R.id.TakenPicture);


        Intent intent = getIntent();
        Bitmap bitmap = (Bitmap) intent.getParcelableExtra("Bitmap");
        Locationpic.setImageBitmap(bitmap);






    }
}