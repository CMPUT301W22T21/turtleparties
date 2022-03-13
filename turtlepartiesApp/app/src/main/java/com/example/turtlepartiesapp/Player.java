package com.example.turtlepartiesapp;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

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

/**
 * Todo: implement Qrcodes and db connection
 */
public class Player {
    private static final String TAG = "player";
    FirebaseFirestore db;
    private DocumentReference userRef;
    private CollectionReference qrcodesRef;
    //QRGenerator qrGenerator;

    String username;
    String password;
    String name;
    Long qrSum;
    Long qrCount;
    Long qrHighest;
    Long qrLowest;
    ArrayList<ScoreQrcode> qrCodes;

    public Player(String username) {
        this.username = username;
        this.qrSum = Long.valueOf(0);
        this.qrCount = Long.valueOf(0);
        this.qrHighest = Long.valueOf(0);
        this.qrLowest = Long.valueOf(0);
        this.name = "";
        this.qrCodes = new ArrayList<ScoreQrcode>();
        this.db = FirebaseFirestore.getInstance();

        userRef  = db.collection("Users").document(username);
        qrcodesRef  = db.collection("Users").document(username).collection("qrcodes");

        //add listener for changes to profile on db
        addProfileListener(userRef);
        //add listener for qrcode changes on db
        addQrCodeListener(qrcodesRef);
    }

    private void addQrCodeListener(final CollectionReference qrcodesRef){
        qrcodesRef.addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, "Listen failed.", error);
                    return;
                }

                if(value == null){
                    return;
                }

                qrSum = Long.valueOf(0);
                qrCodes.clear();
                for(QueryDocumentSnapshot doc: value)
                {
                    //String qrname = doc.getId();
                    //String qrcomment = doc.getString("comment");
                    //Float latval = (Float)doc.get("latval");
                    //Float longval = (Float)doc.get("longval");
                    String qrcodestring = doc.getString("qrcodestring");
                    Object qrscore = doc.get("score");
                    if(qrscore != null){
                        qrSum += (Long) qrscore;
                    }

                    try {
                        qrCodes.add(new ScoreQrcode(qrcodestring));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    private void addProfileListener(DocumentReference userRef){
        userRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, "Listen failed.", error);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {

                    name = snapshot.getString("name");
                }

            }
        });
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getQrSum() {
        return qrSum;
    }

    public void setQrSum(Long qrSum) {
        this.qrSum = qrSum;
    }

    public Long getQrCount() {
        return qrCount;
    }

    public void setQrCount(Long qrCount) {
        this.qrCount = qrCount;
    }

    public Long getQrHighest() {
        return qrHighest;
    }

    public void setQrHighest(Long qrHighest) {
        this.qrHighest = qrHighest;
    }

    public Long getQrLowest() {
        return qrLowest;
    }

    public void setQrLowest(Long qrLowest) {
        this.qrLowest = qrLowest;
    }

    public ArrayList<ScoreQrcode> getQrCodes() {
        return qrCodes;
    }

    public void setQrCodes(ArrayList<ScoreQrcode> qrCodes) {
        this.qrCodes = qrCodes;
    }

    public void addQrCode(ScoreQrcode qrCode){
        this.qrCodes.add(qrCode);
    }

}
