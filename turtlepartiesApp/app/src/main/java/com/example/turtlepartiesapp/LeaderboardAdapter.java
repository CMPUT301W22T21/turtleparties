package com.example.turtlepartiesapp;

import android.app.Activity;
import android.content.Context;
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
    int n = 0;

    public LeaderboardAdapter(Context context,ArrayList<String> people, ArrayList<Integer> scores){
        super(context,0,people);
        this.context = context;
        this.people = people;
        this.scores = scores;
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
        n++;
        playerTextview.setText(String.valueOf(n) + ". " + name);
        leaderboardStatTextView.setText(score + "   ");

        return view;
    }

}
