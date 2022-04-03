package com.example.turtlepartiesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.GeoPoint;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class ScanQRActivity extends AppCompatActivity implements GeolocationFragment.OnFragmentInteractionListener, AddImageFragment.OnFragmentInteractionListener{

    private Player player;
    private PlayerController playerController;

    private ImageView qrview;
    private Button openCameraButton;
    private Button openGalleryButton;
    private Button addQRCode;
    private Button showImage;
    final Activity myactivity = this;
    private String mystring;
    private ScoreQrcode scannedQR;
    private TextView qrscore;
    private EditText comment;
    private LocationManager locationManager;
    double latitude;
    double longitude;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);

        geoLocation();
        /*get player async*/
        Bundle b = getIntent().getExtras();
        playerController = new PlayerController();
        if(b != null){
            ResultHandler handler = new ResultHandler() {
                @Override
                public void handleResult(Object data) {
                    try{
                        player = (Player) data;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            playerController.getPlayer(b.get("USER_ID").toString(), handler);
        }

        openCameraButton = findViewById(R.id.openCamera);
        openGalleryButton = findViewById(R.id.openGallery);
        qrview = findViewById(R.id.scannedQRview);
        qrscore = findViewById(R.id.score);
        addQRCode = findViewById(R.id.addQR);
        comment = findViewById(R.id.QRcomment);
        showImage = findViewById(R.id.showimage);

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


        // button for adding qr code //
        addQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mycomment = comment.getText().toString();
                scannedQR.setComment(mycomment);

                // implementation for adding the qr to the database should go here
                if(player != null){
                    playerController.addQrToPlayer(player, scannedQR);
                }

                finish();
            }
        });



        showImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),TakenPictureActivity.class);
                intent.putExtra("Bitmap",scannedQR.getPicture());
                startActivity(intent);


                /*
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                scannedQR.getPicture().compress(Bitmap.CompressFormat.JPEG,100,bytes);
                String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), scannedQR.getPicture(),"title",null);
                userBundle.putString("key",path); */



                /*userBundle.putParcelable("BMP", scannedQR.getPicture());
                newFragment.setArguments(userBundle);
                newFragment.show(getSupportFragmentManager(),"SHOWPICTURE");*/
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
                showImage.setVisibility(View.INVISIBLE);
                new AddImageFragment().show(getSupportFragmentManager(),"ADD_IMAGE");
                new GeolocationFragment().show(getSupportFragmentManager(), "ADD_GEOLOCATION");

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
                            showImage.setVisibility(View.INVISIBLE);

                            new AddImageFragment().show(getSupportFragmentManager(),"ADD_IMAGE");
                            new GeolocationFragment().show(getSupportFragmentManager(), "ADD_GEOLOCATION");


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

        // checks if selected activity is taking a picture of the location //
        if(requestCode==2){
           // photo = (Bitmap) data.getExtras().get("data");
            if((Bitmap) data.getExtras().get("data")!=null) {
                scannedQR.setPicture((Bitmap) data.getExtras().get("data")); // sets the picture to the qrcode
            }
            showImage.setVisibility(View.VISIBLE);


        }

    }




    public void geoLocation(){
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ScanQRActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,}, 1);
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 15, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                latitude = Double.parseDouble(df.format(location.getLatitude()));
                longitude = Double.parseDouble(df.format(location.getLongitude()));
                Log.d("GEO", "My location: " + latitude + "   " + longitude);
            }
            @Override
            public void onProviderEnabled(@NonNull String provider) {
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
        });
    }


    @Override
    public double[] getGeoLocation() {
        double[] geoLoc = new double[2];

        geoLoc[0] = latitude;
        geoLoc[1] = longitude;

        return geoLoc;
    }

    public void setLocation(){
        if (scannedQR != null){
            Log.d("GEOERR", latitude + "   " + longitude);
            GeoPoint newGP = new GeoPoint((latitude), (longitude));
            scannedQR.setGeolocation(newGP);
        }
    }

    @Override
    public void OnOpenCamera() {
        // opens camera when user prompts
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,2);




    }


}