package com.example.turtlepartiesapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class LeaderboardAdapter extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String> people;
    private ArrayList<Integer> scores;
    private String ownerUserID;


    public LeaderboardAdapter(Context context,ArrayList<String> people, ArrayList<Integer> scores, String userID){
        super(context,0,people);
        this.context = context;
        this.people = people;
        this.scores = scores;
        this.ownerUserID = userID;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

        //Set up the view
        View view = convertView;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.leaderboard_content,parent,false);
        }
        String name = people.get(position);
        String score = scores.get(position).toString();
        TextView playerTextview = view.findViewById(R.id.playerNametextView);
        TextView leaderboardStatTextView = view.findViewById(R.id.leaderboardstatTextView);
        if(name.equalsIgnoreCase(ownerUserID)){
            playerTextview.setText(String.valueOf(position+1) + ". " + name + " (ME)");
            playerTextview.setTextColor(Color.parseColor("#E1306C"));
        }else{
            playerTextview.setText(String.valueOf(position+1) + ". " + name);
        }

        leaderboardStatTextView.setText(score + "   ");

        return view;
    }

}
