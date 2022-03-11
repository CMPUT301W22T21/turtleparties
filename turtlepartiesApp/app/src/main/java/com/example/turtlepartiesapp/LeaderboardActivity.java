package com.example.turtlepartiesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class LeaderboardActivity extends AppCompatActivity {

    private Button highestScoreBut;
    private Button mostScansBut;
    private Button greatesScansBut;
    FirebaseFirestore db;
    ListView leaderboardList;
    ArrayAdapter<String> personAdapter;
    ArrayList<Integer> scoresList;
    ArrayList<String> peopleNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboards_screen);
        db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("Users");
        identifyAllButtons();

        Integer [] scores = {3,7,6};

        scoresList = new ArrayList<>();
        peopleNames = new ArrayList<>();
        scoresList.addAll(Arrays.asList(scores));

        personAdapter= new LeaderboardAdapter(this,peopleNames,scoresList);
        leaderboardList.setAdapter(personAdapter);

        Log.d("popie", String.valueOf(peopleNames) + " is popo");



        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
                    FirebaseFirestoreException error) {
                getDataFromFireBaseForLeaderboard();
                personAdapter.notifyDataSetChanged();
            }

        });

        getGreatestSumFromFirebase();

    }

    public void identifyAllButtons(){
        highestScoreBut = findViewById(R.id.highestScoreButton);
        mostScansBut = findViewById(R.id.mostScansButton);
        greatesScansBut = findViewById(R.id.greatestSumButton);
        leaderboardList = findViewById(R.id.leadboardListView);

        highestScoreBut.setOnClickListener(view -> onHighestScoreClicked());
        mostScansBut.setOnClickListener(view -> onMostScansClicked());
        greatesScansBut.setOnClickListener(view -> onGreatestSumClicked());



    }

    public void onHighestScoreClicked(){
        String []people = {"bob","joe","daill", "rhs", "asd"};
        Integer [] scores = {300,300,5434,336,745};

        scoresList = new ArrayList<>();

        scoresList.addAll(Arrays.asList(scores));

        personAdapter= new LeaderboardAdapter(this,peopleNames,scoresList);
        leaderboardList.setAdapter(personAdapter);
        return;
    }

    public void onMostScansClicked(){
        return;
    }

    public void onGreatestSumClicked(){
        return;
    }


    public void getDataFromFireBaseForLeaderboard(){

        db.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        String name = doc.getId();
                        Log.d("popie", name);
                        peopleNames.add(name);
                        personAdapter.notifyDataSetChanged();
                    }

                }
            }
        });

    }


    public void getGreatestSumFromFirebase(){
        ApiFuture<QuerySnapshot> future = db.collection("cities").get();
        Log.d("popie", qrCodes.);
    }

}