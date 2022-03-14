package com.example.turtlepartiesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;

public class ScanQRActivity extends AppCompatActivity {



    private ImageView qrview;
    private Button openCameraButton;
    private Button openGalleryButton;
    private Button addQRCode;
    final Activity myactivity = this;
    private String mystring;
    private ScoreQrcode scannedQR;
    private TextView qrscore;
    private EditText comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);
        openCameraButton = findViewById(R.id.openCamera);
        openGalleryButton = findViewById(R.id.openGallery);
        qrview = findViewById(R.id.scannedQRview);
        qrscore = findViewById(R.id.score);
        addQRCode = findViewById(R.id.addQR);
        comment = findViewById(R.id.QRcomment);

        openCameraButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(myactivity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.setPrompt("Scan QR code");
                integrator.setCameraId(0);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();

            }
        });


        /*https://stackoverflow.com/questions/6016000/how-to-open-phones-gallery-through-code
         author: Jyonsa on May 16, 2011
         answer author: Niranj Patel on May 16, 2011*/

        openGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent opengallery = new Intent();
                opengallery.setType("image/*");
                opengallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(opengallery, "Select Picture"), 1);

            }
        });



        addQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mycomment = comment.getText().toString();
                scannedQR.setComment(mycomment);

                // implementation for adding the qr to the database should go here

                finish();
            }
        });


    }






    /**
     * This is the onActivity result method which will open either the camera or gallery depending on which button the user clicked
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanresult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanresult != null) {
            if (scanresult.getContents() == null) {
                Toast.makeText(this, "Scan failed", Toast.LENGTH_LONG).show();
            } else {
                mystring = scanresult.getContents();
                // create QR code object and display the code on the screen with the score
                scannedQR = new ScoreQrcode(mystring);
                scannedQR.generateQRimage();
                qrview.setImageBitmap(scannedQR.getMyBitmap());
                qrscore.setText("Score:" + scannedQR.getScore());
                addQRCode.setVisibility(View.VISIBLE);
                comment.setVisibility(View.VISIBLE);

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }



        // this part checks if the selected activity was for reading a qr from image //

        /*https://stackoverflow.com/questions/6016000/how-to-open-phones-gallery-through-code
         author: Jyonsa on May 16, 2011
         answer author: Niranj Patel on May 16, 2011*/


        /*https://stackoverflow.com/questions/29649673/scan-barcode-from-an-image-in-gallery-android
          author: MrSiro on Apr 15, 2015
          answer author: LaurentY on Apr 15, 2015*/

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(ScanQRActivity.this.getContentResolver(), data.getData());

                        int[] intArray = new int[bitmap.getWidth() * bitmap.getHeight()];

                        bitmap.getPixels(intArray, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

                        LuminanceSource source = new RGBLuminanceSource(bitmap.getWidth(), bitmap.getHeight(), intArray);
                        BinaryBitmap QRbitmap = new BinaryBitmap(new HybridBinarizer(source));


                        Reader reader = new MultiFormatReader();
                        Result result = reader.decode(QRbitmap);


                        if (result.getText() == null) {
                            Toast.makeText(this, "Scan failed", Toast.LENGTH_LONG).show();
                        } else {
                            String contents = result.getText();
                            scannedQR = new ScoreQrcode(contents);
                            scannedQR.generateQRimage();
                            qrview.setImageBitmap(scannedQR.getMyBitmap());
                            qrscore.setText("Score:" + scannedQR.getScore());
                            addQRCode.setVisibility(View.VISIBLE);
                            comment.setVisibility(View.VISIBLE);

                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ChecksumException e) {
                        e.printStackTrace();
                    } catch (NotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "No QR code found", Toast.LENGTH_LONG).show();
                    } catch (FormatException e) {
                        e.printStackTrace();
                    }


                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(ScanQRActivity.this, "No photo selected", Toast.LENGTH_LONG).show();
                }

            }
        }
    }

}