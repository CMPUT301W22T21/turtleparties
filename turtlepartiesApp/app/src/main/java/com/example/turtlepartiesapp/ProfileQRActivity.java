package com.example.turtlepartiesapp;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.hardware.camera2.params.BlackLevelPattern;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class ProfileQRActivity extends AppCompatActivity {

    ImageView profileqr;
    Button genQR;
    String text;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_qr);

        profileqr = findViewById(R.id.personalqr);
        genQR = findViewById(R.id.generatepersonalQr);


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
                profileqr.setImageBitmap(QR.getMyBitmap());
            }
        });

    }
}