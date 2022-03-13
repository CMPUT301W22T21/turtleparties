package com.example.turtlepartiesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class PlayerSearchActivity extends AppCompatActivity {

    ListView playerList;
    ArrayAdapter<Player> playerAdapter;
    ArrayList<Player> players;

    PlayerSearcherController searcherController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_search);


        playerList = findViewById(R.id.playerListView);
        players = new ArrayList<Player>();
        playerAdapter= new PlayerListAdapter(this, players);
        playerList.setAdapter(playerAdapter);

        searcherController = new PlayerSearcherController();
    }


    public void onClickSearch(View view){
        EditText editText = (EditText) findViewById(R.id.search_text);
        String name = editText.getText().toString();
        ResultHandler handler = () -> playerAdapter.notifyDataSetChanged();
        searcherController.searchByName(name, players,20, handler);
    }
}