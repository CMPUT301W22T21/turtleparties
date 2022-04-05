package com.example.turtlepartiesapp;


import static com.example.turtlepartiesapp.PlayerController.sortByValue;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.turtlepartiesapp.Adapters.LeaderboardAdapter;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

//Activity which displays the leaderboard activity.
// No active errors. Need more cohesion with rest of project
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
        PlayerController pc = new PlayerController();
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
                highestSumMap = pc.getLeaderboardStatsFromFireBase("sum");
                highestScoreMap = pc.getLeaderboardStatsFromFireBase("score");
                highestQRScanMap = pc.getLeaderboardStatsFromFireBase("qr");
                //pc.getLeaderboardStatsFromFireBase(highestQRScanMap,highestScoreMap,highestSumMap);
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
        System.out.println(currentUser + " is current user");

    }

    /**
     * Changes view to show players who have highest score
     */
    public void onHighestScoreClicked(){

        choosePrompt.setVisibility(View.GONE);

        peopleNames.clear();
        scoresList.clear();

        highestScoreMap = sortByValue(highestScoreMap);
        peopleNames.addAll(highestScoreMap.keySet());
        scoresList.addAll(highestScoreMap.values());
        highestScoreMap.remove("Owner");
        personAdapter.notifyDataSetChanged();
    }

    /**
     * Changes view to show players who have the most scans
     */
    public void onMostScansClicked(){

        choosePrompt.setVisibility(View.GONE);

        peopleNames.clear();
        scoresList.clear();

        highestQRScanMap = sortByValue(highestQRScanMap);

        peopleNames.addAll(highestQRScanMap.keySet());
        scoresList.addAll(highestQRScanMap.values());
        highestQRScanMap.remove("Owner");

        personAdapter.notifyDataSetChanged();

    }


    /**
     * Changes view to show players who have the greatest sum
     */
    public void onGreatestSumClicked(){

        choosePrompt.setVisibility(View.GONE);

        peopleNames.clear();
        scoresList.clear();

        highestSumMap = sortByValue(highestSumMap);
        peopleNames.addAll(highestSumMap.keySet());
        scoresList.addAll(highestSumMap.values());
        highestSumMap.remove("Owner");

        personAdapter.notifyDataSetChanged();
    }








}