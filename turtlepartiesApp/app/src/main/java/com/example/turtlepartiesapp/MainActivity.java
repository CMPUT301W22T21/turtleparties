package com.example.turtlepartiesapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.WriterException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final String TAG = "firebase";
    FirebaseFirestore db;
    final String username = "test1";
    CollectionReference collectionReference;
    private ListView qrList;
    private ArrayAdapter<Qrcode> qrAdapter;
    private ArrayList<Qrcode> qrDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        qrList = findViewById(R.id.qr_list);
        qrDataList = new ArrayList<>();

        addQRToDatalist();
        try {
            qrDataList.add(new Qrcode("hi"));
        } catch (WriterException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Size: " + qrDataList.size());
        qrAdapter = new QRList(this, qrDataList);
        qrList.setAdapter(qrAdapter);
    }

    public void addQRToDatalist(){
        collectionReference = db.collection("Users").document(username).collection("qrcodes");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots)
                {
                    String qrstring = (String) doc.getData().get("qrcodestring");
                    String comment = (String) doc.getData().get("comment");
                    Integer score = ((Number) doc.getData().get("score")).intValue();

                    try {
                        qrDataList.add(new Qrcode(qrstring));
                        Log.d(TAG, qrstring + "  " + comment + "  " + score);
                        Log.d(TAG, "Size: " + qrDataList.size());
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
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