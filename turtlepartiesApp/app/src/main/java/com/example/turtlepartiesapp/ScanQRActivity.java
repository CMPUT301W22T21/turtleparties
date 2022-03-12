package com.example.turtlepartiesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanQRActivity extends AppCompatActivity {



    private ImageView qrview;
    private Button openCameraButton;
    final Activity myactivity = this;
    private String mystring;
    private ScoreQrcode newQR;
    private TextView qrscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);
        openCameraButton = findViewById(R.id.openCamera);
        qrview = findViewById(R.id.scannedQRview);
        qrscore = findViewById(R.id.score);

        openCameraButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(myactivity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();

            }
        });


    }


    /**
     * This is the camera activity which saves the result of score qrcode
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult scanresult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (scanresult!=null){
            if (scanresult.getContents()==null){
                Toast.makeText(this,"Scan failed",Toast.LENGTH_LONG).show();
            }
            else{
                mystring = scanresult.getContents();
                // create QR code object and display the code on the screen with the score
                newQR = new ScoreQrcode(mystring);
                newQR.generateQRimage();
                qrview.setImageBitmap(newQR.getMyBitmap());
                qrscore.setText("Score:"+ newQR.getScore());
            }
        }
        else{
            super.onActivityResult(requestCode,resultCode,data);
        }

    }




}