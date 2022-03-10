package com.example.turtlepartiesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class LeaderboardActivity extends AppCompatActivity {

    private Button highestScoreBut;
    private Button mostScansBut;
    private Button greatesScansBut;

    ListView leaderboardList;
    ArrayAdapter<String> personAdapter;
    ArrayList<Integer> scoresList;
    ArrayList<String> peoplesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboards_screen);

        identifyAllButtons();
        String []people = {"bob","joe","daill", "rhs", "asd"};
        Integer [] scores = {3,3,5,6,7};
        peoplesList = new ArrayList<>();
        scoresList = new ArrayList<>();
        peoplesList.addAll(Arrays.asList(people));
        scoresList.addAll(Arrays.asList(scores));

        personAdapter= new LeaderboardAdapter(this,peoplesList,scoresList);
        leaderboardList.setAdapter(personAdapter);
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
        peoplesList = new ArrayList<>();
        scoresList = new ArrayList<>();
        peoplesList.addAll(Arrays.asList(people));
        scoresList.addAll(Arrays.asList(scores));

        personAdapter= new LeaderboardAdapter(this,peoplesList,scoresList);
        leaderboardList.setAdapter(personAdapter);
        return;
    }

    public void onMostScansClicked(){
        return;
    }

    public void onGreatestSumClicked(){
        return;
    }
}