package com.example.turtlepartiesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.zxing.WriterException;

public class LoginQRActivity extends AppCompatActivity {

    ImageView loginqr;
    String text;

    // this is the activity that shows our login qr code
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_qr);

        loginqr = findViewById(R.id.loginqr);
        text = "helloworld";
        LoginQrcode QR = null;
        QR = new LoginQrcode(text);
        QR.generateQRimage();
        loginqr.setImageBitmap(QR.getMyBitmap());

    }
}