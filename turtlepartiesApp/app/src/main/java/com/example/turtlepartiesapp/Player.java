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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class Player implements Serializable {
    private static final String TAG = "player";
    private transient FirebaseFirestore db;
    private transient DocumentReference userRef;
    private transient CollectionReference qrcodesRef;
    //QRGenerator qrGenerator;

    String username;
    String password;
    String name;
    Long qrSum;
    Long qrCount;
    Long qrHighest;
    Long qrLowest;
    ArrayList<ScoreQrcode> qrCodes;

    public Player(){}

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

    public Player(String username, String name, long score) {
        this.username = username;
        this.name = name;
        this.qrSum = score;
        this.qrCount = Long.valueOf(0);
        this.qrHighest = Long.valueOf(0);
        this.qrLowest = Long.valueOf(0);
        this.qrCodes = new ArrayList<ScoreQrcode>();
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
        if(this.qrCodes.size() == 0){
            return (long)0;
        }

        return (long)Collections.max(qrCodes).getScore();
    }

    public void setQrHighest(Long qrHighest) {
        this.qrHighest = qrHighest;
    }

    public Long getQrLowest() {
        if(this.qrCodes.size() == 0){
            return (long)0;
        }

        return (long)Collections.min(qrCodes).getScore();
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
        if(qrHighest < qrCode.getScore()){
            qrHighest = (long)qrCode.getScore();
        }
        if(qrLowest > qrCode.getScore()){
            qrLowest = (long)qrCode.getScore();
        }
        this.qrSum += qrCode.getScore();
    }

    public void removeQrCode(ScoreQrcode qrCode) {
        this.qrCodes.remove(qrCode);
        qrHighest = getQrHighest();
        qrLowest = getQrLowest();
        this.qrSum -= qrCode.getScore();
    }

    public boolean hasQrCode(ScoreQrcode qrcode) {
        return qrCodes.contains(qrcode);
    }
}
