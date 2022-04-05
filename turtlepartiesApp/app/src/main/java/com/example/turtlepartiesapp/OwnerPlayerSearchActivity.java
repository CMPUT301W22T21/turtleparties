package com.example.turtlepartiesapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.turtlepartiesapp.Fragments.PlayerDeleteFragment;


// this activity allows owner to search for other players and delete them
public class OwnerPlayerSearchActivity extends PlayerSearchActivity implements PlayerDeleteFragment.OnFragmentInteractionListener {

    private Player selectedPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    void handleBundle(Intent mainIntent){ return; }

    @Override
    public void initiateOtherPlayerActivity(Player thisPlayer){
        new PlayerDeleteFragment().show(getSupportFragmentManager(), "DELETE_QR");
        selectedPlayer = thisPlayer;
    }

    @Override
    public void onDeleteClicked() {
        PlayerController controller = new PlayerController();
        controller.deletePlayer(selectedPlayer);

        players.remove(selectedPlayer);
        playerAdapter.notifyDataSetChanged();

        return;
    }
}