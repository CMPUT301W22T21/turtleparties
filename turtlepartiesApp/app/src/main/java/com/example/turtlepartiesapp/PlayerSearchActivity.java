package com.example.turtlepartiesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.protobuf.StringValue;

import java.util.ArrayList;

/**
 * This is a class that provides a UI for searching other players
 */
public class PlayerSearchActivity extends AppCompatActivity {

    public static final String EXTRA_OTHER_PLAYER = "com.example.turtlepartiesapp.MESSAGE";

    ListView playerList;
    ArrayAdapter<Player> playerAdapter;
    ArrayList<Player> players;

    Player mainUser;
    PlayerSearcherController searcherController;

    ResultHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_search);

        Intent mainIntent = getIntent();
        Bundle playerBundle = mainIntent.getBundleExtra(MainActivity.EXTRA_USER);
        mainUser = (Player) playerBundle.getSerializable("mainUser");
        Log.d("SearchActivity", mainUser.getUsername());
        
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


    /**
     * Searches db for other players with usernames that contain name
     * @param view
     * view is an EditText containing the search word
     */
    public void onClickSearch(View view){
        EditText editText = (EditText) findViewById(R.id.search_text);
        String name = editText.getText().toString();

        searcherController.searchByName(name, players,20, handler);
    }


    public void initiateOtherPlayerActivity(Player thisPlayer){
        Bundle args = new Bundle();
        args.putSerializable("thisPlayer", thisPlayer);
        args.putSerializable("mainPlayer", mainUser);
        Intent showOtherProfileIntent = new Intent (this, OtherPlayerProfileActivity.class);
        showOtherProfileIntent.putExtra(EXTRA_OTHER_PLAYER, args);
        startActivity(showOtherProfileIntent);
    }

}