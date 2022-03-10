package com.example.turtlepartiesapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class QRList extends ArrayAdapter<Qrcode> {

    private ArrayList<Qrcode> codes;
    private Context context;

    public QRList(Context context, ArrayList<Qrcode> qr){
        super(context,0, qr);
        this.codes = qr;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.qrlist_content, parent,false);
        }

        Qrcode thisCode = codes.get(position);

        ImageView qrImage = view.findViewById(R.id.qr_imageView);
        TextView comment = view.findViewById(R.id.comment_text);
        TextView score = view.findViewById(R.id.score_view2);

        //qrImage.setImageBitmap(thisCode.getMyBitmap());
        qrImage.setImageResource(R.drawable.ic_baseline_qr_code_24);
        comment.setText(thisCode.getComment());
        score.setText(String.valueOf(thisCode.getScore()));

        return view;

    }
}