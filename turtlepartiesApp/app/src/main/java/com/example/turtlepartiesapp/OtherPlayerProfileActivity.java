package com.example.turtlepartiesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class OtherPlayerProfileActivity extends AppCompatActivity {
    private Player loggedInPlayer;
    private Player otherPlayer;

    private PlayerController playerController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_player_profile);

        Intent intent = getIntent();
        Bundle userBundle = intent.getBundleExtra(MainActivity.EXTRA_USER);
        loggedInPlayer = (Player) userBundle.getSerializable("usr");
        otherPlayer = (Player) userBundle.getSerializable("usr");

        playerController = new PlayerController();
    }
}