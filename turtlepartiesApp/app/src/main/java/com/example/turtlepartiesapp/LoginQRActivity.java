package com.example.turtlepartiesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.zxing.WriterException;

public class LoginQRActivity extends AppCompatActivity {

    ImageView loginqr;
    Button genQR;
    String text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_qr);

        loginqr = findViewById(R.id.loginqr);
        genQR = findViewById(R.id.generateloginQr);
        text = "helloworld";





        // Generates QR code on Button press based On players User name to add later //
        genQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Qrcode QR = null;
                try {
                    QR = new Qrcode(text);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                QR.generateQRimage();
                loginqr.setImageBitmap(QR.getMyBitmap());
            }
        });

    }
}