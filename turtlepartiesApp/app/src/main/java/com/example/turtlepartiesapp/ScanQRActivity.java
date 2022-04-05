package com.example.turtlepartiesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.turtlepartiesapp.Controllers.QrScanController;
import com.example.turtlepartiesapp.Fragments.AddImageFragment;
import com.example.turtlepartiesapp.Fragments.GeolocationFragment;
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

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;

// activity for scanning qrcodes to score
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
    private TextView qrscore;
    private EditText comment;
    private LocationManager locationManager;
    double latitude;
    double longitude;
    QrScanController qrScanController;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);

        geoLocation();
        /*get player async*/
        Bundle b = getIntent().getExtras();
        qrScanController = new QrScanController();
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


        /**
         * This button will open the camera for the player to scan a QRcode
         */
        openCameraButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(myactivity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.setCaptureActivity(PortraitMode.class);
                integrator.setPrompt("Scan QR code");
                integrator.setCameraId(0);
                integrator.setBarcodeImageEnabled(false);
                integrator.setOrientationLocked(true);
                integrator.setBeepEnabled(false);
                integrator.initiateScan();

            }
        });


        /*https://stackoverflow.com/questions/6016000/how-to-open-phones-gallery-through-code
         author: Jyonsa on May 16, 2011
         answer author: Niranj Patel on May 16, 2011*/

        /**
         * This button will open the gallery for the player to upload a QRcode
         */

        openGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent opengallery = new Intent();
                opengallery.setType("image/*");
                opengallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(opengallery, "Select Picture"), 1);
            }
        });


        /**
         * This button will add the QRcode to the database and return the player to MainActivity
         */
        addQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mycomment = comment.getText().toString();
                qrScanController.addComment(mycomment);

                // implementation for adding the qr to the database should go here
                if(player != null){
                    playerController.addQrToPlayer(player, qrScanController.getQRcode());
                }

                finish();
            }
        });


        /**
         * This button will send the user to TakenPictureActivity to view the picture they have taken of the location
         */
        showImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qrScanController.getLocationPicture()!=null) {
                    Intent intent = new Intent(getApplicationContext(), TakenPictureActivity.class);
                    intent.putExtra("Bitmap", qrScanController.getLocationPicture());
                    startActivity(intent);
                }
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


        /**
         * 1. This part of the code checks and handles the result if the player scanned a QR from the camera
         */
        IntentResult scanresult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanresult != null) {
            if (scanresult.getContents() == null) {
                Toast.makeText(this, "Scan failed", Toast.LENGTH_LONG).show();
            } else {
                mystring = scanresult.getContents();
                // create QR code object and display the code on the screen with the score

                qrScanController.NewQR(mystring);
                qrScanController.createBitmap();
                qrview.setImageBitmap(qrScanController.getBitmap());
                qrscore.setText("Score:" + qrScanController.getScore());
                addQRCode.setVisibility(View.VISIBLE);
                comment.setVisibility(View.VISIBLE);
                showImage.setVisibility(View.INVISIBLE);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        new AddImageFragment().show(getSupportFragmentManager(),"ADD_IMAGE");
                        new GeolocationFragment().show(getSupportFragmentManager(), "ADD_GEOLOCATION");
                        addQRCode.setVisibility(View.VISIBLE);
                        comment.setVisibility(View.VISIBLE);
                        showImage.setVisibility(View.INVISIBLE);
                    }
                }, 500);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }



        /**
         * 2. This part of the code checks and handles the result if the player scanned a QR from the gallery
         */

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

                            mystring = result.getText();
                            qrScanController.NewQR(mystring);
                            qrScanController.createBitmap();
                            qrview.setImageBitmap(qrScanController.getBitmap());
                            qrscore.setText("Score:" + qrScanController.getScore());

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    new AddImageFragment().show(getSupportFragmentManager(),"ADD_IMAGE");
                                    new GeolocationFragment().show(getSupportFragmentManager(), "ADD_GEOLOCATION");
                                    addQRCode.setVisibility(View.VISIBLE);
                                    comment.setVisibility(View.VISIBLE);
                                    showImage.setVisibility(View.INVISIBLE);
                                }
                            }, 500);
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

        /**
         * 3. This part of the code checks and handles the result if the player is taking a picture of the location they found the code
         */
        if(requestCode==2){
           // photo = (Bitmap) data.getExtras().get("data");
            if((Bitmap) data.getExtras().get("data")!=null) {
                qrScanController.setLocationPicture((Bitmap) data.getExtras().get("data")); // sets the picture to the qrcode
            }
            showImage.setVisibility(View.VISIBLE);


        }

    }


    /**
     * gets the geolocation of the player at start
     */
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


    /**
     * Gets the players current geolocation and displays in fragment
     * @return geoLoc
     */
    @Override
    public double[] getGeoLocation() {
        double[] geoLoc = new double[2];

        geoLoc[0] = latitude;
        geoLoc[1] = longitude;

        return geoLoc;
    }


    /**
     * Sets the geolocation of the QRcode
     */
    public void setLocation(){
        if (qrScanController.getQRcode() != null){
            Log.d("GEOERR", latitude + "   " + longitude);
            GeoPoint newGP = new GeoPoint((latitude), (longitude));
            qrScanController.setGeo(newGP);
        }
    }


    /**
     * Launches the camera activity
     */
    @Override
    public void OnOpenCamera() {
        // opens camera when user prompts
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,2);
    }


}