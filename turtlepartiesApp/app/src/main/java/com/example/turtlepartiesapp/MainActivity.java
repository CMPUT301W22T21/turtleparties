package com.example.turtlepartiesapp;

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

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final String TAG = "firebase";
    FirebaseFirestore db;
    final String username = "test1";
    CollectionReference collectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        displayQRListview();
    }

    public void displayQRListview(){
        collectionReference = db.collection("Users").document(username).collection("qrcodes");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots)
                {
                    String qrstring = (String) doc.getData().get("qrcodestring");
                    String comment = (String) doc.getData().get("comment");
                    Integer score = ((Number) doc.getData().get("score")).intValue();

                    Log.d(TAG, qrstring + "  " + comment + "  " + score);
                }
            }
        });
    }

    public void mapActivity(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void leaderboardActivity(View view){
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
    }

    public void scanQRActivity(View view) {
        // QR scanner goes here
    }

    public void profileSearchActivity(View view) {
        // User search goes here
    }

    public void profileQRActivity(View view) {
        Intent intent = new Intent(this, ProfileQRActivity.class);
        startActivity(intent);
    }
}