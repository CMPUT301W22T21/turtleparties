package com.example.turtlepartiesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

// this activity shows the owners screen

public class OwnerActivity extends AppCompatActivity implements QRDeleteFragment.OnFragmentInteractionListener{

    private static final String TAG = "QRCODE";
    public static final String EXTRA_QR = "com.example.turtlepartiesapp.MESSAGE";
    FirebaseFirestore db;

    private ListView qrList;
    private ArrayAdapter<ScoreQrcode> qrAdapter;
    private ArrayList<ScoreQrcode> qrDataList;

    private ScoreQrcode currentQr;
    private int selectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);
        //setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        qrList = findViewById(R.id.other_player_qr_list);

        qrDataList = new ArrayList<ScoreQrcode>();
        qrAdapter = new QRList(this, qrDataList);
        qrList.setAdapter(qrAdapter);
        addQrListListeners();

        getQrCodes();
    }

    void addQrListListeners(){
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
    }

    void getQrCodes(){
        final CollectionReference QrCodesRef = db.collection("QR codes");

        QrCodesRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                qrDataList.clear();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        ScoreQrcode qrcode = new ScoreQrcode(document.getId());

                        qrcode.setQrName((String) document.getData().get("qrText"));
                        qrcode.setGeolocation((GeoPoint) document.getData().get("geolocation"));
                        if(document.getData().get("toShow") != null){
                            qrcode.setToShow((boolean) document.getData().get("toShow"));
                        }

                        qrDataList.add(qrcode);

                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
                qrAdapter.notifyDataSetChanged();
            }
        });

    }

    // Goes to QRInfo acitivty with intent
    public void qrInfoActivity(ScoreQrcode qrToPass){
        Bundle args = new Bundle();
        args.putSerializable("qrcode", qrToPass);
        args.putSerializable("user", "Owner");
        args.putSerializable("showDeleteButton", true);

        try {
            args.putSerializable("lat", qrToPass.getGeolocation().getLatitude());
            args.putSerializable("lon", qrToPass.getGeolocation().getLongitude());
        }catch (Exception e){
            args.putSerializable("lat", 0.0);
            args.putSerializable("lon", 0.0);
        }

        Intent qrinfoIntent = new Intent (this, QRInfoOwner.class);
        qrinfoIntent.putExtra(EXTRA_QR, args);
        startActivity(qrinfoIntent);
    }

    @Override
    public void onDeleteClicked(){
        ScoreQrcode deleteQR = (ScoreQrcode) qrList.getItemAtPosition(selectedPosition);
        //playerControl.removeQrFromPlayer(user, deleteQR);
        return;
    }

    //Goes to profile search acitivy
    public void profileSearchActivity(View view) {
        //Bundle args = new Bundle();
        //args.putSerializable("mainUser", user);
        Intent playerSearchIntent = new Intent(this, OwnerPlayerSearchActivity.class);
        //playerSearchIntent.putExtra(EXTRA_USER, args);
        startActivity(playerSearchIntent);
    }

}