package com.example.turtlepartiesapp;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.HashMap;
import java.util.Map;

/**
 * PLayer Controller class
 * Manages player and other players
 */
public class PlayerController {

    private static final String READTAG = "PLAYER_CONTROLLER_READ";
    FirebaseFirestore db;


    public PlayerController() {
        db = FirebaseFirestore.getInstance();
    }
    public PlayerController(FirebaseFirestore db) {
        this.db = db;
    }

    public void getPlayer(String id, ResultHandler handler){
        final DocumentReference userRef  = db.collection("Users").document(id);

        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();
                Object player = doc.toObject(Player.class);
                handler.handleResult(player);
                return;
            }
        });

        return;
    }

    /**
     * Adds player to DB
     * @param player
     * Pass in player
     */
    public void savePlayer(Player player){
        db.collection("Users").document(player.username).set(player);
        return;
    }


    /**
     * Adds a QR to player in DB
     * @param player
     * @param qrcode
     * Pass in player and QR Code
     * @return
     * Returns success of fucntion
     */
    public boolean addQrToPlayer(Player player, ScoreQrcode qrcode){
        if(player.hasQrCode(qrcode)){
            return false;
        }
        player.addQrCode(qrcode);
        db.collection("Users").document(player.username).update("qrCodes", FieldValue.arrayUnion(qrcode));
        db.collection("Users").document(player.username).update(
                "qrCount",player.qrCodes.size(),
                "qrHighest", player.qrHighest,
                "qrLowest", player.qrLowest,
                "qrSum", player.getQrSum()
        );

        HashMap<String, Object> qrMap = new HashMap<>();
        qrMap.put("qrText", qrcode.getCode());
        qrMap.put("geolocation", qrcode.getGeolocation());
        qrMap.put("score", qrcode.getScore());
        qrMap.put("toShow", qrcode.isToShow());

        db.collection("QR codes").document(qrcode.getCode()).set(qrMap);

        return true;
    }

    /**
     * Removes QR From PLayer
     * @param player
     * @param qrcode
     * @return
     * Returns wheteher it was scucesfull
     */
    public boolean removeQrFromPlayer(Player player, ScoreQrcode qrcode){
        if(!player.hasQrCode(qrcode)){
            return false;
        }
        player.removeQrCode(qrcode);
        db.collection("Users").document(player.username).update("qrCodes", FieldValue.arrayRemove(qrcode));

        db.collection("Users").document(player.username).update(
                "qrCount",player.qrCodes.size(),
                "qrHighest", player.qrHighest,
                "qrLowest", player.qrLowest,
                "qrSum", player.getQrSum()
        );
        return true;
    }
}
