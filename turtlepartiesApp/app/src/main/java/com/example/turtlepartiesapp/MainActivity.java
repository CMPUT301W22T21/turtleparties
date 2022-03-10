package com.example.turtlepartiesapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class MainActivity extends AppCompatActivity implements QRDeleteFragment.OnFragmentInteractionListener{

    public static final String EXTRA_QR = "com.example.assignment1.MESSAGE";
    final String TAG = "firebase";
    FirebaseFirestore db;
    final String username = "test1";
    CollectionReference collectionReference;
    private ListView qrList;
    private ArrayAdapter<Qrcode> qrAdapter;
    private ArrayList<Qrcode> qrDataList;
    private int selectedPosition;
    private Qrcode currentQr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        qrList = findViewById(R.id.qr_list);
        qrDataList = new ArrayList<>();

        addQRToDatalist();
        try {
            qrDataList.add(new Qrcode("hi", 100, 53.521331248, -113.521331248, "comment goes here"));
        } catch (WriterException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Size: " + qrDataList.size());
        qrAdapter = new QRList(this, qrDataList);
        qrList.setAdapter(qrAdapter);

        qrList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                currentQr = (Qrcode) qrList.getItemAtPosition(position);
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
    }

    public void addQRToDatalist(){
        collectionReference = db.collection("Users").document(username).collection("qrcodes");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots)
                {
                    String qrstring = (String) doc.getData().get("qrcodestring");
                    Integer score = ((Number) doc.getData().get("score")).intValue();
                    Double latval = ((Number) doc.getData().get("latval")).doubleValue();
                    Double longval = ((Number) doc.getData().get("longval")).doubleValue();
                    String comment = (String) doc.getData().get("comment");

                    try {
                        qrDataList.add(new Qrcode(qrstring, score, latval, longval, comment));
                        Log.d(TAG, qrstring + "  " + comment + "  " + score);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onDeleteClicked(){
        qrDataList.remove(selectedPosition);
        qrAdapter.notifyDataSetChanged();
    }

    public void switchActivity(Qrcode qrToPass){
        Bundle args = new Bundle();
        args.putSerializable("qrcode", qrToPass);
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
        startActivity(leaderboardIntent);
    }

    public void scanQRActivity(View view) {
        // QR scanner goes here
    }

    public void profileSearchActivity(View view) {
        // User search goes here
    }

    public void profileQRActivity(View view) {
        Intent profileIntent = new Intent(this, ProfileQRActivity.class);
        startActivity(profileIntent);
    }
}