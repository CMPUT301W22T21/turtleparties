package com.example.turtlepartiesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.protobuf.StringValue;
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
import java.util.ArrayList;

/**
 * This is a class that provides a UI for searching other players
 */
public class PlayerSearchActivity extends AppCompatActivity {

    public static final String EXTRA_OTHER_PLAYER = "com.example.turtlepartiesapp.MESSAGE";

    ListView playerList;
    ArrayAdapter<Player> playerAdapter;
    ArrayList<Player> players;

    Player mainUser;
    PlayerSearcherController searcherController;

    ResultHandler handler;

    Button addbyCamera;
    Button addbyGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_search);

        Intent mainIntent = getIntent();

        handleBundle(mainIntent);
        
        handler = new ResultHandler() {
            @Override
            public void handleResult(Object data) {
                playerAdapter.notifyDataSetChanged();
            }
        };

        addbyCamera = findViewById(R.id.ScanbyCameraps);
        addbyGallery = findViewById(R.id.ScanbyGalleryps);


        playerList = findViewById(R.id.playerListView);
        players = new ArrayList<Player>();
        playerAdapter= new PlayerListAdapter(this, players);
        playerList.setAdapter(playerAdapter);

        searcherController = new PlayerSearcherController();

        playerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Player thisPlayer = (Player) playerList.getItemAtPosition(position);
                initiateOtherPlayerActivity(thisPlayer);
            }
        });

        /**
         * This button will open the camera for the player to scan a QRcode
         */
        addbyCamera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                IntentIntegrator integrator = new IntentIntegrator(PlayerSearchActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.setCaptureActivity(PortraitMode.class);
                integrator.setPrompt("Scan ProfileQR code");
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

        addbyGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent opengallery = new Intent();
                opengallery.setType("image/*");
                opengallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(opengallery, "Select ProfileQR code"), 1);
            }
        });





    }

    void handleBundle(Intent mainIntent){
        Bundle playerBundle = mainIntent.getBundleExtra(MainActivity.EXTRA_USER);
        mainUser = (Player) playerBundle.getSerializable("mainUser");
        Log.d("SearchActivity", mainUser.getUsername());
    }


    /**
     * Searches db for other players with usernames that contain name
     * @param view
     * view is an EditText containing the search word
     */
    public void onClickSearch(View view){
        EditText editText = (EditText) findViewById(R.id.search_text);
        String name = editText.getText().toString();

        searcherController.searchByName(name, players,20, handler);
    }


    public void initiateOtherPlayerActivity(Player thisPlayer){
        Bundle args = new Bundle();
        args.putSerializable("thisPlayer", thisPlayer);
        args.putSerializable("mainPlayer", mainUser);
        Intent showOtherProfileIntent = new Intent (this, OtherPlayerProfileActivity.class);
        showOtherProfileIntent.putExtra(EXTRA_OTHER_PLAYER, args);
        startActivity(showOtherProfileIntent);
    }


    ResultHandler handleCodeSearch = new ResultHandler() {
        @Override
        public void handleResult(Object data) {
            players.clear();
            if(data != null) {
                Player player = (Player) data;
                players.add(player);
            }
            playerAdapter.notifyDataSetChanged();
        }
    };

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

                searcherController.searchByCode(code, handleCodeSearch);

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
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(PlayerSearchActivity.this.getContentResolver(), data.getData());

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

                            searcherController.searchByCode(code, handleCodeSearch);

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
                    Toast.makeText(PlayerSearchActivity.this, "No photo selected", Toast.LENGTH_LONG).show();
                }

            }
        }


    }

}