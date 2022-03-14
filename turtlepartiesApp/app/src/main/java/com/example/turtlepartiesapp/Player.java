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
//Player class whcihc stores player data and releavant methods
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
    String phoneNumber;
    String email;
    Long qrSum;
    Long qrCount;
    Long qrHighest;
    Long qrLowest;
    ArrayList<ScoreQrcode> qrCodes;

    /**
     * Constructor for player only requires username
     * @param username
     */
    public Player(String username) {
        this.username = username;
        this.qrSum = Long.valueOf(0);
        this.qrCount = Long.valueOf(0);
        this.qrHighest = Long.valueOf(0);
        this.qrLowest = Long.valueOf(0);
        this.name = "";
        this.phoneNumber = "";
        this.email = "";
        this.qrCodes = new ArrayList<ScoreQrcode>();
        this.db = FirebaseFirestore.getInstance();


        userRef  = db.collection("Users").document(username);
        qrcodesRef  = db.collection("Users").document(username).collection("qrcodes");

        //add listener for changes to profile on db
        addProfileListener(userRef);
        //add listener for qrcode changes on db
        addQrCodeListener(qrcodesRef);
    }

    /**
     * Alternative constructor which takes ina username, name and score
     * @param username
     * @param name
     * @param score
     */
    public Player(String username, String name, long score) {
        this.username = username;
        this.name = name;
        this.qrSum = score;
        this.qrCount = Long.valueOf(0);
        this.qrHighest = Long.valueOf(0);
        this.qrLowest = Long.valueOf(0);
        this.qrCodes = new ArrayList<ScoreQrcode>();
    }

    /**
     * Listener whcih checks for when new QR codes are added
     * @param qrcodesRef
     * Pass in Database collectionreference to monitor
     */
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

    /**
     * Profile listener
     * @param userRef
     * Pass in document reference of user
     */
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

    /**
     * Getter method for username
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter method for name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for password
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter method for password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter method for qr sum
     * @return
     */
    public Long getQrSum() {
        return qrSum;
    }

    /**
     * Setter method for QR sum
     * @param qrSum
     */
    public void setQrSum(Long qrSum) {
        this.qrSum = qrSum;
    }

    /**
     * Getter for QR Count
     * @return
     */
    public Long getQrCount() {
        return qrCount;
    }

    /**
     * Setter method for QR count
     * @param qrCount
     */
    public void setQrCount(Long qrCount) {
        this.qrCount = qrCount;
    }

    /**
     * Getter method for QR Highest
     * @return
     */
    public Long getQrHighest() {
        if(this.qrCodes.size() == 0){
            return (long)0;
        }

        return (long)Collections.max(qrCodes).getScore();
    }

    /**
     * Setter for QR Highest
     * @param qrHighest
     */
    public void setQrHighest(Long qrHighest) {
        this.qrHighest = qrHighest;
    }

    /**
     * GEtter for QR lowest
     * @return
     */
    public Long getQrLowest() {
        if(this.qrCodes.size() == 0){
            return (long)0;
        }

        return (long)Collections.min(qrCodes).getScore();
    }

    /**
     * Setter for QR lowest
     * @param qrLowest
     */
    public void setQrLowest(Long qrLowest) {
        this.qrLowest = qrLowest;
    }

    /**
     * Getter for arraylist of QR codes
     * @return
     */
    public ArrayList<ScoreQrcode> getQrCodes() {
        return qrCodes;
    }

    /**
     * Setter for arraylist of qr codes
     * @param qrCodes
     */
    public void setQrCodes(ArrayList<ScoreQrcode> qrCodes) {
        this.qrCodes = qrCodes;
    }

    /**
     * Adds qr codes to arraylist of player
     * @param qrCode
     * Pass in a object of type QR Codes
     */
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

    /**
     * REmoves QR codes from player class
     * @param qrCode
     */
    public void removeQrCode(ScoreQrcode qrCode) {
        this.qrCodes.remove(qrCode);
        qrHighest = getQrHighest();
        qrLowest = getQrLowest();
        this.qrSum -= qrCode.getScore();
    }

    /**
     * Checks if player has a certain qr code
     * @param qrcode
     * @return
     */
    public boolean hasQrCode(ScoreQrcode qrcode) {
        return qrCodes.contains(qrcode);
    }

    /**
     * Gette method for player phone number
     * @return
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Setter method for phone numebr
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Getter method for email
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter method for email
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
