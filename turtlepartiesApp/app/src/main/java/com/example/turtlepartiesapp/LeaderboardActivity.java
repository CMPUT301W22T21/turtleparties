package com.example.turtlepartiesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LeaderboardActivity extends AppCompatActivity {

    private Button highestScoreBut;
    private Button mostScansBut;
    private Button greatesScansBut;
    private TextView choosePrompt;
    FirebaseFirestore db;
    ListView leaderboardList;
    ArrayAdapter<String> personAdapter;
    ArrayList<Integer> scoresList;
    ArrayList<String> peopleNames;
    String currentUser;

    HashMap<String, Integer> highestSumMap;
    HashMap<String, Integer> highestQRScanMap;
    HashMap<String, Integer> highestScoreMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboards_screen);
        db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("Users");

        identifyAllButtons();
        choosePrompt.setVisibility(View.VISIBLE);



        highestSumMap = new HashMap<>();
        highestQRScanMap = new HashMap<>();
        highestScoreMap = new HashMap<>();

        scoresList = new ArrayList<>();
        peopleNames = new ArrayList<>();



        personAdapter= new LeaderboardAdapter(this,peopleNames,scoresList,currentUser);
        leaderboardList.setAdapter(personAdapter);





        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
                    FirebaseFirestoreException error) {
                getLeaderboardStatsFromFireBase();
                personAdapter.notifyDataSetChanged();
            }

        });



    }

    public void identifyAllButtons(){
        highestScoreBut = findViewById(R.id.highestScoreButton);
        mostScansBut = findViewById(R.id.mostScansButton);
        greatesScansBut = findViewById(R.id.greatestSumButton);
        leaderboardList = findViewById(R.id.leadboardListView);
        choosePrompt = findViewById(R.id.choosePromptTextView);

        highestScoreBut.setOnClickListener(view -> onHighestScoreClicked());
        mostScansBut.setOnClickListener(view -> onMostScansClicked());
        greatesScansBut.setOnClickListener(view -> onGreatestSumClicked());

        //Get User from intent info
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            currentUser = extras.getString("USER_IDENTIFIER");
        }

    }

    public void onHighestScoreClicked(){

        choosePrompt.setVisibility(View.GONE);

        peopleNames.clear();
        scoresList.clear();

        highestScoreMap = sortByValue(highestScoreMap);
        peopleNames.addAll(highestScoreMap.keySet());
        scoresList.addAll(highestScoreMap.values());
        personAdapter.notifyDataSetChanged();
    }

    public void onMostScansClicked(){

        choosePrompt.setVisibility(View.GONE);

        peopleNames.clear();
        scoresList.clear();

        highestQRScanMap = sortByValue(highestQRScanMap);

        peopleNames.addAll(highestQRScanMap.keySet());
        scoresList.addAll(highestQRScanMap.values());

        personAdapter.notifyDataSetChanged();

    }

    public void onGreatestSumClicked(){

        choosePrompt.setVisibility(View.GONE);

        peopleNames.clear();
        scoresList.clear();

        highestSumMap = sortByValue(highestSumMap);
        peopleNames.addAll(highestSumMap.keySet());
        scoresList.addAll(highestSumMap.values());
        personAdapter.notifyDataSetChanged();
    }



    public void getLeaderboardStatsFromFireBase(){
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
                                        qrsum = ((Number) doc.getData().get("qrsum")).intValue();
                                        highestSumMap.put(name,qrsum);
                                    }catch (Exception e){
                                        highestSumMap.put(name,0);
                                    }
                                    //Highest score
                                    try{
                                        highestqr = ((Number) doc.getData().get("highestqr")).intValue();
                                        highestScoreMap.put(name,highestqr);
                                    }catch (Exception e){
                                        highestScoreMap.put(name,0);
                                    }
                                    //Highest scan amount
                                    try{
                                        qrcount = ((Number) doc.getData().get("qrcount")).intValue();
                                        highestQRScanMap.put(name,qrcount);
                                    }catch (Exception e){
                                        highestQRScanMap.put(name,0);
                                    }


                                    Log.d("LEADERBOARD_DEBUG", highestqr + "  " + qrcount + "  " + qrsum);
                                    personAdapter.notifyDataSetChanged();


                                }
                            }
                        });
                    }

                }
            }
        });


    }

    //Sort in descending order
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