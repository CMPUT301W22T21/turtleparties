package com.example.turtlepartiesapp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Controller to find players
 * CUrrenly implemented in playersearch acitivty
 * To Do: Impelenment in leaderbaord
 */
public class PlayerSearcherController {
    private static final String TAG = "player_searcher";
    FirebaseFirestore db;

    public PlayerSearcherController() {
        db = FirebaseFirestore.getInstance();
    }


    public void searchByName(String search, ArrayList<Player> players, int max, ResultHandler handler){
        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int i = 0;
                            players.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(players.size() == max){
                                    break;
                                }
                                String username = document.getId();
                                long score = 0;
                                Object playerScore = document.get("qrSum");
                                if(playerScore != null){
                                    score = (long)playerScore;
                                }
                                if(username.contains(search)){
                                    players.add(new Player(username,username,score));
                                }
                            }
                            handler.handleResult(players);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        return;
    }
}
