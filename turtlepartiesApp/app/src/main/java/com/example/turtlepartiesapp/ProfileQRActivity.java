package com.example.turtlepartiesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.zxing.WriterException;

public class ProfileQRActivity extends AppCompatActivity {

    ImageView profileqr;
    String text;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_qr);

        profileqr = findViewById(R.id.personalqr);
        text = "helloworld";
        ProfileQrcode QR = null;
        QR = new ProfileQrcode(text);
        QR.generateQRimage();
        profileqr.setImageBitmap(QR.getMyBitmap());


    }
}