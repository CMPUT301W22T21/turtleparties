package com.example.turtlepartiesapp.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.turtlepartiesapp.Models.Comment;
import com.example.turtlepartiesapp.R;

import java.util.ArrayList;

// adapter for showing list of comments for a qr code
public class QRCommentList extends ArrayAdapter<Comment> {

    private Context context;
    private ArrayList<Comment> comments;

    public QRCommentList(Context context, ArrayList<Comment> commentDataList) {
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