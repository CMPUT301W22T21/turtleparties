package com.example.turtlepartiesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * Adapter used to display players in main acitivty.
 * No outstnading issues
 */
public class PlayerListAdapter extends ArrayAdapter<Player> {

    private ArrayList<Player> players;
    private Context context;

    public PlayerListAdapter(@NonNull Context context, ArrayList<Player> players) {
        super(context, 0, players);
        this.players = players;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.leaderboard_content, parent,false);
        }

        Player player = players.get(position);

        String name = player.getUsername();
        String score = player.getQrSum().toString();

        TextView playerTextview = view.findViewById(R.id.playerNametextView);
        TextView leaderboardStatTextView = view.findViewById(R.id.leaderboardstatTextView);

        playerTextview.setText(name);
        leaderboardStatTextView.setText("Score: " + score);

        return view;
    }
}
