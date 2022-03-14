package com.example.turtlepartiesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements QRDeleteFragment.OnFragmentInteractionListener{

    public static final String EXTRA_QR = "com.example.assignment1.MESSAGE";
    final String TAG = "MainActivity";
    FirebaseFirestore db;
    private CollectionReference qrcodesRef;

    private LoggedInPlayer user;
    final String username = "test1";
    private ListView qrList;
    private ArrayAdapter<ScoreQrcode> qrAdapter;
    private ArrayList<ScoreQrcode> qrDataList;
    private int selectedPosition;
    private ScoreQrcode currentQr;
    View view;
    private TextView scanView;
    private TextView sumView;
    private TextView highestView;
    private TextView lowestView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // added temporary profile button
        Button profileButton = (Button) findViewById(R.id.test_btn);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

        context = this;
        checkAndRequestPermissions();
        user = new LoggedInPlayer(username);

        view = this.findViewById(android.R.id.content);
        db = FirebaseFirestore.getInstance();

        user = new LoggedInPlayer(username);
        qrcodesRef  = db.collection("Users").document(username).collection("qrcodes");

        qrList = findViewById(R.id.qr_list);
        qrDataList = new ArrayList<>();

        qrAdapter = new QRList(this, qrDataList);
        qrList.setAdapter(qrAdapter);

        qrList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                currentQr = (ScoreQrcode) qrList.getItemAtPosition(position);
                switchActivity(currentQr);
            }
        });

        qrList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                selectedPosition = position;
                new QRDeleteFragment().show(getSupportFragmentManager(), "DELETE_QR");
                return true;
            }
        });
        qrcodesRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
                    FirebaseFirestoreException error) {
                updateQRListview();
                updateInfo();
                qrAdapter.notifyDataSetChanged();
            }

        });
    }

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


    public void updateInfo(){
        highestView = view.findViewById(R.id.highest_qr_view);
        lowestView = view.findViewById(R.id.lowest_qr_view);
        scanView = view.findViewById(R.id.scan_count_view);
        sumView = view.findViewById(R.id.sum_view);

        db.collection("Users").document(username).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    Long highestqr = (long) ((Number) doc.getData().get("highestqr")).intValue();
                    Long lowestqr = (long) ((Number) doc.getData().get("lowestqr")).intValue();
                    Long countqr = (long) ((Number) doc.getData().get("qrcount")).intValue();
                    Long sumqr = (long) ((Number) doc.getData().get("qrsum")).intValue();

                    user.setQrHighest(highestqr);
                    user.setQrLowest(lowestqr);
                    user.setQrCount(countqr);
                    user.setQrSum(sumqr);

                    Log.d(TAG, "updateInfo: "+highestqr + "  " + lowestqr + "  " + countqr + "  " + sumqr);

                    highestView.setText(String.valueOf(highestqr));
                    lowestView.setText(String.valueOf(lowestqr));
                    scanView.setText(String.valueOf(countqr));
                    sumView.setText(String.valueOf(sumqr));
                }
            }
        });

    }

    public void updateQRListview(){
        qrDataList.clear();
        db.collection("Users").document(username).collection("qrcodes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        String qrname = doc.getId();
                        String comment = (String) doc.getData().get("comment");
                        db.collection("QR codes").document(qrname).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot doc = task.getResult();
                                    String qrtext = null;
                                    GeoPoint qrGeo = null;
                                    boolean qrDraw = true;
                                    try {
                                        qrtext = (String) doc.getData().get("qrText");
                                        qrDraw = (boolean) doc.getData().get("toShow");
                                        qrGeo = (GeoPoint) doc.getData().get("geolocation");
                                    }catch (Exception e){
                                        Log.d(TAG, "QRTEXT DATA ISSUE");
                                    }

                                    try {
                                        ScoreQrcode thisQR = new ScoreQrcode(qrname);
                                        thisQR.setQrName(qrtext);
                                        thisQR.setQrName(qrname);
                                        thisQR.setToShow(qrDraw);
                                        thisQR.setGeolocation(qrGeo);
                                        thisQR.setComment(comment);
                                        qrDataList.add(thisQR);
                                        user.addQrCode(thisQR);
                                        Log.d(TAG, "UpdateQRCode: "+qrname + "  " + comment + "  " + qrtext);
                                    } catch (Exception e) {
                                        Log.d(TAG, "NOT ADDED TO QR DATA LIST");
                                        e.printStackTrace();
                                    }
                                }
                                qrAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    @Override
    public void onDeleteClicked(){
        ScoreQrcode deleteQR = (ScoreQrcode) qrList.getItemAtPosition(selectedPosition);
        CollectionReference collectionReference = db.collection("Users").document(username).collection("qrcodes");
        if(deleteQR != null) {
            collectionReference.document(deleteQR.getQrName())
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error deleting document", e);
                        }
                    });
        }
    }

    public void switchActivity(ScoreQrcode qrToPass){
        Bundle args = new Bundle();
        args.putSerializable("qrcode", qrToPass);

        try {
            args.putSerializable("lat", qrToPass.getGeolocation().getLatitude());
            args.putSerializable("lon", qrToPass.getGeolocation().getLongitude());
        }catch (Exception e){
            args.putSerializable("lat", 0.0);
            args.putSerializable("lon", 0.0);
        }

        Intent qrinfoIntent = new Intent (this, QRInfo.class);
        qrinfoIntent.putExtra(EXTRA_QR, args);
        startActivity(qrinfoIntent);
    }

    public void mapActivity(View view) {
        Intent mapIntent = new Intent(this, MapsActivity.class);
        startActivity(mapIntent);
    }

    public void leaderboardActivity(View view){
        Intent leaderboardIntent = new Intent(this, LeaderboardActivity.class);
        leaderboardIntent.putExtra("USER_IDENTIFIER",username);

        startActivity(leaderboardIntent);
    }

    public void scanQRActivity(View view) {
        // QR scanner goes here
        Intent scanQRIntent = new Intent(this, ScanQRActivity.class);
        startActivity(scanQRIntent);
    }

    public void profileSearchActivity(View view) {
        // User search goes here
        Intent playerSearchIntent = new Intent(this, PlayerSearchActivity.class);
        startActivity(playerSearchIntent);
    }


    public void profileQRActivity(View view) {
        Intent profileIntent = new Intent(this, ProfileQRActivity.class);
        startActivity(profileIntent);
    }
}