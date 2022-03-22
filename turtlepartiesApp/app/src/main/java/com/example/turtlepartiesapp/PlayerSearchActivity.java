package com.example.turtlepartiesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Player search acitivyti
 * To DO: Improve effectiveness and info displayed
 */
public class PlayerSearchActivity extends AppCompatActivity {

    public static final String EXTRA_OTHER_PLAYER = "com.example.turtlepartiesapp.MESSAGE";

    ListView playerList;
    ArrayAdapter<Player> playerAdapter;
    ArrayList<Player> players;

    PlayerSearcherController searcherController;

    ResultHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_search);
        
        handler = new ResultHandler() {
            @Override
            public void handleResult(Object data) {
                playerAdapter.notifyDataSetChanged();
            }
        };

        playerList = findViewById(R.id.playerListView);
        players = new ArrayList<Player>();
        playerAdapter= new PlayerListAdapter(this, players);
        playerList.setAdapter(playerAdapter);

        searcherController = new PlayerSearcherController();

        playerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Player thisPlayer = (Player) playerList.getItemAtPosition(position);
                initiateOtherPlayerActivity(thisPlayer);
            }
        });
    }

    public void onClickSearch(View view){
        EditText editText = (EditText) findViewById(R.id.search_text);
        String name = editText.getText().toString();

        searcherController.searchByName(name, players,20, handler);
    }

    public void initiateOtherPlayerActivity(Player thisPlayer){
        Bundle args = new Bundle();
        args.putSerializable("thisPlayer", thisPlayer);
        Intent showOtherProfileIntent = new Intent (this, OtherPlayerProfileActivity.class);
        showOtherProfileIntent.putExtra(EXTRA_OTHER_PLAYER, args);
        startActivity(showOtherProfileIntent);
    }
}