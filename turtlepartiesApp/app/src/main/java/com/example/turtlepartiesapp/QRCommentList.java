package com.example.turtlepartiesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class QRCommentList extends ArrayAdapter<Comment> {

    private Context context;
    private ArrayList<Comment> comments;

    protected QRCommentList(Context context, ArrayList<Comment> commentDataList) {
        super(context, 0, commentDataList);
        this.context = context;
        this.comments = commentDataList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.commentlist_content, parent,false);
        }

        Comment thisComment = comments.get(position);

        ImageView userImg = view.findViewById(R.id.profile_imageview);
        TextView commentText = view.findViewById(R.id.commentlist_text);
        TextView authorText = view.findViewById(R.id.username_text);

        userImg.setImageResource(R.drawable.ic_baseline_person_24);
        commentText.setText(thisComment.getCommentBody());
        authorText.setText(thisComment.getAuthor());

        return view;

    }
}