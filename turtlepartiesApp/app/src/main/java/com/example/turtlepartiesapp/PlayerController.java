package com.example.turtlepartiesapp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

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
            }
        });

        return;
    }

    public void savePlayer(Player player){
        db.collection("Users").document(player.username).set(player);
        return;
    }

    public void addQrToPlayer(Player player, ScoreQrcode qrcode){
        player.addQrCode(qrcode);
        savePlayer(player);
    }

    public void removeQrFromPlayer(Player player, ScoreQrcode qrcode){
        player.removeQrCode(qrcode);
        savePlayer(player);
    }
}
