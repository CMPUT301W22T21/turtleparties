package com.example.turtlepartiesapp.Controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.turtlepartiesapp.Player;
import com.example.turtlepartiesapp.ResultHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Controller class to find players
 */
public class PlayerSearcherController {
    private static final String TAG = "player_searcher";
    FirebaseFirestore db;

    public PlayerSearcherController() {
        db = FirebaseFirestore.getInstance();
    }

    /**
     * Queries db for players with usernames that contain search String. Adds matching players to players list.
     * @param search
     * String to search for in usernames
     * @param players
     * List of players to update
     * @param max
     * Integer for max amount of results
     * @param handler
     * Handler for callback after search
     */
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

                                if(username.contains(search)){

                                    try{
                                        Object player = document.toObject(Player.class);
                                        players.add((Player) player);
                                    }
                                    catch (Exception e){
                                        e.printStackTrace();
                                    }
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


    public void searchByCode(String code, ResultHandler handler){
        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //players.clear();
                            Object player = null;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String username = document.getId();
                                if(username.equals(code)){
                                    try{
                                        player = document.toObject(Player.class);
                                        //players.add((Player) player);
                                        break;
                                    }
                                    catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            }
                            handler.handleResult(player);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        return;
    }


    public void SearchByQR(String DID){
    // implementation for searching the player By there DeviceId goes here



    }

}
