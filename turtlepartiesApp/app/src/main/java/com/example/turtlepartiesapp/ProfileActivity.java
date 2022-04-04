package com.example.turtlepartiesapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import java.io.Serializable;

/**
 * Profile activity
 * Displays current players information
 */

public class ProfileActivity extends AppCompatActivity implements LoginQRFragment.OnFragmentInteractionListener, GoalsFragment.OnFragmentInteractionListener, FriendQRFragment.OnFragmentInteractionListener {


    TextView text_name, text_userName;
    EditText editText_name, editText_email, editText_phoneNumber;
    Button saveButton, showLoginQRButton, showFriendQRButton, cameraLogin, galleryLogin;
    Button darkModeButton;
    ImageButton goalsButton;
    Player player;

    PlayerController playerControl;
    final String TAG = "ProfileActivity";


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_screen);

        text_name = findViewById(R.id.other_player_name_textview);
        text_userName = findViewById(R.id.other_player_username_textview);
        editText_name = findViewById(R.id.editName);
        editText_email = findViewById(R.id.editEmail);
        editText_phoneNumber = findViewById(R.id.editPhoneNumber);
        saveButton = findViewById(R.id.saveChangesButton);
        showLoginQRButton = findViewById(R.id.showLoginQRButton);
        showFriendQRButton = findViewById(R.id.showFriendQRButton);
        goalsButton = findViewById(R.id.goalsButton);
        darkModeButton = findViewById(R.id.toggleDarkModeButton);
        cameraLogin = findViewById(R.id.CameraLogin);
        galleryLogin = findViewById(R.id.GalleryLogin);


        Intent intent = getIntent();
        Bundle userBundle = intent.getBundleExtra(MainActivity.EXTRA_USER);
        player = (Player) userBundle.getSerializable("usr");

        playerControl = new PlayerController();

        // sets info and apply darkmode
        setInfo();
        SharedPreferences appSettings = getSharedPreferences("AppSettings",0);
        SharedPreferences.Editor shEditor = appSettings.edit();
        Boolean nightmode = appSettings.getBoolean("NightMode",false);
        if(nightmode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            FrameLayout f1 = findViewById(R.id.frameLayout);
            f1.setBackgroundColor(Color.parseColor("#121212"));
            goalsButton.setBackgroundColor(Color.parseColor("#121212"));
            darkModeButton.setText("Disable Dark Mode");
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            darkModeButton.setText("Enable Dark Mode");
        }

        /**
         * This button will apply darkmode to the app
         */
        darkModeButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  if(nightmode){
                      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                      shEditor.putBoolean("NightMode", false);
                      shEditor.apply();
                      darkModeButton.setText("Enable Dark Mode");
                  }else{
                      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                      shEditor.putBoolean("NightMode", true);
                      shEditor.apply();
                      darkModeButton.setText("Disable Dark Mode");
                  }
              }
          }
        );

        /**
         * This button will open the camera for the player to scan a QRcode
         */
        cameraLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                IntentIntegrator integrator = new IntentIntegrator(ProfileActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.setCaptureActivity(PortraitMode.class);
                integrator.setPrompt("Scan LoginQR code");
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

        galleryLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent opengallery = new Intent();
                opengallery.setType("image/*");
                opengallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(opengallery, "Select LoginQR code"), 1);
            }
        });


    }


    /**
     * Sets the players info to the screen
     */
    public void setInfo(){
        text_name.setText(player.getName());
        text_userName.setText(player.getUsername());
        editText_name.setText(player.getName());
        editText_email.setText(player.getEmail());
        editText_phoneNumber.setText(player.getPhoneNumber());
        Log.d(TAG, player.getEmail() + "  " + player.getEmail());
    }


    /**
     * Saves changes to players information to the database
     * @param view
     */
    public void onSaveButtonClicked(View view){
        player.setName(String.valueOf(editText_name.getText()));
        player.setEmail(String.valueOf(editText_email.getText()));
        player.setPhoneNumber(String.valueOf(editText_phoneNumber.getText()));
        playerControl.savePlayer(player);
        setInfo();
    }


    /**
     * Shows the players Login Qrcode that can be used to login
     * @param view
     */
    public void onShowLoginQRButtonClicked(View view){
        Bundle userBundle = new Bundle();
        userBundle.putSerializable("player", player);

        LoginQRFragment newFragment = new LoginQRFragment();
        newFragment.setArguments(userBundle);
        newFragment.show(getSupportFragmentManager(),"SHOWLOGIN");

    }

    /**
     * Shows the goal feature
     * @param view
     */
    public void onGoalsButtonClicked(View view){
        Bundle userBundle = new Bundle();
        userBundle.putSerializable("player", player);

        GoalsFragment newFragment = new GoalsFragment();
        newFragment.setArguments(userBundle);
        newFragment.show(getSupportFragmentManager(), "GOALS");
    }


    /**
     * Shows the players profile QRcode
     * @param view
     */
    public void onShowFriendQRButtonClicked(View view){


        Bundle userBundle = new Bundle();
        userBundle.putSerializable("player", player);

        FriendQRFragment newFragment = new FriendQRFragment();
        newFragment.setArguments(userBundle);
        newFragment.show(getSupportFragmentManager(),"SHOWNAME");
    }





    @Override
    public void onclosePressed() {;}



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
                String code;
                code = scanresult.getContents();

                // QUERY GOES HERE to login player to new account and assign device to that account
                playerControl.updateRef(player.getUsername(), code);


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
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(ProfileActivity.this.getContentResolver(), data.getData());

                        int[] intArray = new int[bitmap.getWidth() * bitmap.getHeight()];

                        bitmap.getPixels(intArray, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

                        LuminanceSource source = new RGBLuminanceSource(bitmap.getWidth(), bitmap.getHeight(), intArray);
                        BinaryBitmap QRbitmap = new BinaryBitmap(new HybridBinarizer(source));


                        Reader reader = new MultiFormatReader();
                        Result result = reader.decode(QRbitmap);


                        if (result.getText() == null) {
                            Toast.makeText(this, "Scan failed", Toast.LENGTH_LONG).show();
                        } else {

                            String code;
                            code = result.getText();

                            // QUERY GOES HERE to login player to new account and assign device to that account
                            playerControl.updateRef(player.getUsername(), code);

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
                    Toast.makeText(ProfileActivity.this, "No photo selected", Toast.LENGTH_LONG).show();
                }

            }
        }


    }


}
