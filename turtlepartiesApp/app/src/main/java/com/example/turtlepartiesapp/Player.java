package com.example.turtlepartiesapp;

import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.WriterException;

import java.util.ArrayList;

/**
 * Todo: implement Qrcodes and db connection
 */
public class Player {
    private static final String TAG = "player";
    FirebaseFirestore db;
    //QRGenerator qrGenerator;

    String username;
    String password;
    String name;
    Long score;
    ArrayList<Qrcode> qrCodes;

    public Player(String username) {
        this.username = username;
        this.score = Long.valueOf(0);
        this.name = "";
        this.qrCodes = new ArrayList<Qrcode>();
        this.db = FirebaseFirestore.getInstance();

        final DocumentReference userRef  = db.collection("Users").document(username);
        final CollectionReference qrcodesRef  = db.collection("Users").document(username).collection("qrcodes");
        //add listener for changes to profile on db
        addProfileListener(userRef);
        //add listener for qrcode changes on db
        addQrCodeListener(qrcodesRef);

    }

    public String getUsername(){
        return username;
    }

    public long getScore() {
        return score;
    }

    public ArrayList<Qrcode> getQrCodes() {
        return qrCodes;
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

                score = Long.valueOf(0);
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
                        score += (Long) qrscore;
                    }

                    try {
                        qrCodes.add(new Qrcode(qrcodestring));
                    } catch (WriterException e) {
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
}
