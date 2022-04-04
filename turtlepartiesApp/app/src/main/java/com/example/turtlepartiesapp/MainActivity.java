package com.example.turtlepartiesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
// Main activity
// Controls all acitivites
// To do: Decide how to implement it with logging in
public class MainActivity extends AppCompatActivity implements QRDeleteFragment.OnFragmentInteractionListener {

    public static final String EXTRA_QR = "com.example.turtlepartiesapp.MESSAGE";
    public static final String EXTRA_USER = "com.example.turtlepartiesapp.MESSAGE";
    final String TAG = "MainActivity";
    FirebaseFirestore db;
    private DocumentReference userRef;

    private Player user;
    private PlayerController playerControl;
    private String username;
    private ListView qrList;
    private ArrayAdapter<ScoreQrcode> qrAdapter;
    private ArrayList<ScoreQrcode> qrDataList;
    private int selectedPosition;
    ConstraintLayout infoConLayout;
    LinearLayout taskLinLayout;
    private ScoreQrcode currentQr;
    View view;
    private TextView scanView;
    private TextView sumView;
    private TextView highestView;
    private TextView lowestView;
    Context context;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String uniqueID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d(TAG, "My Unique ID: "+uniqueID);
        username = uniqueID;
        //username = "Owner";
        if(username.equals("Owner")){
            Intent mapIntent = new Intent(MainActivity.this, OwnerActivity.class);
            startActivity(mapIntent);

            return;
        }

        setContentView(R.layout.activity_main);
        Log.d(TAG, uniqueID);
        db = FirebaseFirestore.getInstance();
        playerControl = new PlayerController();

        final DocumentReference uniqueId  = db.collection("DeviceId").document(username);
        uniqueId.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        Log.d(TAG, "Device ID already exists" + document.getData());
                        username = document.getString("ref");
                    }
                    else{
                        user = new Player(username);
                        playerControl.savePlayer(user);
                        HashMap<String, String> deviceIdMap = new HashMap<>();
                        deviceIdMap.put("ref", username);
                        db.collection("DeviceId").document(username).set(deviceIdMap);
                        Log.d(TAG, "added Device ID");
                    }
                    Login();
                }
                else{
                    Log.d(TAG, "get failed", task.getException());
                }
            }
        });

        context = this;
        checkAndRequestPermissions();
        view = this.findViewById(android.R.id.content);
        qrList = findViewById(R.id.other_player_qr_list);
        context = this;



        infoConLayout = findViewById(R.id.infobarConstraintLayout);
        taskLinLayout = findViewById(R.id.taskbarLinearLayout);

        //Night mode handeling

        SharedPreferences appSettings = getSharedPreferences("AppSettings",0);

        Boolean nightmode = appSettings.getBoolean("NightMode",false);
        if(nightmode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            ConstraintLayout cl = findViewById(R.id.infobarConstraintLayout);
            LinearLayout l1 = findViewById(R.id.taskbarLinearLayout);
            l1.setBackgroundColor(Color.parseColor("#121212"));
            cl.setBackgroundColor(Color.parseColor("#121212"));
            infoConLayout.setBackgroundResource(R.drawable.infobarborder_dark);
            taskLinLayout.setBackgroundResource(R.drawable.taskbarborder_dark);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            infoConLayout.setBackgroundResource(R.drawable.infobarborder);
            taskLinLayout.setBackgroundResource(R.drawable.taskbarborder);
        }
    }

    // logins user to profile
    public void Login(){
        userRef = db.collection("Users").document(username);


        ResultHandler handler = new ResultHandler() {
            @Override
            public void handleResult(Object data) {
                try{
                    user = (Player) data;
                    if(user == null){
                        user = new Player(username);
                        playerControl.savePlayer(user);
                    }
                    qrDataList = user.qrCodes;
                    qrAdapter = new QRList(context, qrDataList);
                    qrList.setAdapter(qrAdapter);

                    qrList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                            currentQr = (ScoreQrcode) qrList.getItemAtPosition(position);
                            qrInfoActivity(currentQr);
                        }
                    });

                    qrList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
                        public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                            selectedPosition = position;
                            new QRDeleteFragment().show(getSupportFragmentManager(), "DELETE_QR");
                            return true;
                        }
                    });

                    userRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            //updateQRListview();
                            updateInfo(value);
                            qrAdapter.notifyDataSetChanged();
                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        playerControl.getPlayer(username, handler);
    }


    //Gets permissions from user for device things
    public boolean checkAndRequestPermissions() {
        int internet = ContextCompat.checkSelfPermission(context,
                Manifest.permission.INTERNET);
        int loc = ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        int loc2 = ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (internet != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.INTERNET);
        }
        if (loc != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (loc2 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions((Activity) context, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), 1);
            return false;
        }
        return true;
    }

    public void updateInfo(DocumentSnapshot value){

        try{
            highestView = view.findViewById(R.id.highest_qr_view);
            lowestView = view.findViewById(R.id.lowest_qr_view);
            scanView = view.findViewById(R.id.scan_count_view);
            sumView = view.findViewById(R.id.sum_view);
            user = value.toObject(Player.class);
            qrDataList.clear();
            qrDataList.addAll(user.qrCodes);
            highestView.setText(String.valueOf(user.getQrHighest()));
            lowestView.setText(String.valueOf(user.getQrLowest()));
            scanView.setText(String.valueOf(user.getQrCount()));
            sumView.setText(String.valueOf(user.getQrSum()));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return;
    }


    //When delete is clicked on a qr code it is remove form DB
    @Override
    public void onDeleteClicked(){
        ScoreQrcode deleteQR = (ScoreQrcode) qrList.getItemAtPosition(selectedPosition);
        playerControl.removeQrFromPlayer(user, deleteQR);
        return;
    }

    // Goes to QRInfo acitivty with intent
    public void qrInfoActivity(ScoreQrcode qrToPass){
        Bundle args = new Bundle();
        args.putSerializable("qrcode", qrToPass);
        args.putSerializable("user", user);
        args.putSerializable("showDeleteButton", true);

        try {
            args.putSerializable("lat", qrToPass.getGeolocation().getLatitude());
            args.putSerializable("lon", qrToPass.getGeolocation().getLongitude());
        }catch (Exception e){
            args.putSerializable("lat", null);
            args.putSerializable("lon", null);
        }

        Intent qrinfoIntent = new Intent (this, QRInfo.class);
        qrinfoIntent.putExtra(EXTRA_QR, args);
        startActivity(qrinfoIntent);
    }
    
    //Goes to map activty
    public void mapActivity(View view) {
        Intent mapIntent = new Intent(this, MapsActivity.class);
        startActivity(mapIntent);
    }
    //Goes to leaderboard acitivty
    public void leaderboardActivity(View view){
        Intent leaderboardIntent = new Intent(this, LeaderboardActivity.class);
        leaderboardIntent.putExtra("USER_IDENTIFIER",username);

        startActivity(leaderboardIntent);
    }
    //Goes to scan qr acitivty
    public void scanQRActivity(View view) {
        // QR scanner goes here
        Intent scanQRIntent = new Intent(this, ScanQRActivity.class);
        scanQRIntent.putExtra("USER_ID", username);
        startActivity(scanQRIntent);
    }
    //Goes to profile search acitivy
    public void profileSearchActivity(View view) {
        Bundle args = new Bundle();
        args.putSerializable("mainUser", user);
        Intent playerSearchIntent = new Intent(this, PlayerSearchActivity.class);
        playerSearchIntent.putExtra(EXTRA_USER, args);
        startActivity(playerSearchIntent);
    }

    //Goes to profile Qr Acitivty
    public void profileQRActivity(View view) {
        Bundle args = new Bundle();
        args.putSerializable("usr", user);
        Intent profileIntent  = new Intent(this, ProfileActivity.class);
        profileIntent.putExtra(EXTRA_USER, args);
        startActivity(profileIntent);
    }
}