package com.example.turtlepartiesapp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
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
        HashMap<String, Object> commentMap = new HashMap<>();
        commentMap.put("comment", qrcode.getComment());
        db.collection("QR codes").document(qrcode.getCode())
                .collection("comments").document(player.username).set(commentMap);
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
        deleteQRComment(player, qrcode);
        return true;
    }

    public void deleteQRComment(Player player, ScoreQrcode qrcode){
        db.collection("QR codes").document(qrcode.getCode())
                .collection("comments").document(player.username).delete();
    }

    public void savePlayerProfile(Player player) {
        db.collection("Users").document(player.username).update(
                "qrCount",player.qrCodes.size(),
                "qrHighest", player.qrHighest,
                "qrLowest", player.qrLowest,
                "qrSum", player.getQrSum()
        );
    }

    public HashMap<String,Integer> getLeaderboardStatsFromFireBase(String find){
        HashMap<String,Integer> qrscore = new HashMap<>();
        HashMap<String,Integer> score = new HashMap<>();
        HashMap<String,Integer> sum = new HashMap<>();

        db.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        String name = doc.getId();
                        db.collection("Users").document(name).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot doc = task.getResult();
                                    Integer highestqr = null;
                                    Integer qrcount = null;
                                    Integer qrsum = null;
                                    //Highest sum
                                    try{
                                        qrsum = ((Number) doc.getData().get("qrSum")).intValue();
                                        sum.put(name,qrsum);
                                    }catch (Exception e){
                                        sum.put(name,0);
                                    }
                                    //Highest score
                                    try{
                                        highestqr = ((Number) doc.getData().get("qrHighest")).intValue();
                                        score.put(name,highestqr);
                                    }catch (Exception e){
                                        score.put(name,0);
                                    }
                                    //Highest scan amount
                                    try{
                                        qrcount = ((Number) doc.getData().get("qrCount")).intValue();
                                        qrscore.put(name,qrcount);
                                    }catch (Exception e){
                                        qrscore.put(name,0);
                                    }


                                    Log.d("LEADERBOARD_DEBUG", highestqr + "  " + qrcount + "  " + qrsum);
                                   // personAdapter.notifyDataSetChanged();


                                }
                            }
                        });
                    }

                }
            }
        });
        if(find == "score"){
            return score;
        }else if(find == "sum"){
            return sum;
        }else if(find == "qr"){
            return qrscore;
        }else{
            return null;
        }


    }







    public static HashMap<String, Integer>
    sortByValue(HashMap<String, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list
                = new LinkedList<Map.Entry<String, Integer> >(
                hm.entrySet());

        // Sort the list using lambda expression
        Collections.sort(
                list,
                (i2,
                 i1) -> i1.getValue().compareTo(i2.getValue()));

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp
                = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }


}
