package com.example.turtlepartiesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.zxing.WriterException;

public class QRInfo extends AppCompatActivity {

    final String TAG = "info";
    private TextView scoreView;
    private TextView locationView;
    private EditText commentText;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrinfo);

        view = this.findViewById(android.R.id.content);

        Intent intent = getIntent();
        Bundle qrBundle = intent.getBundleExtra(MainActivity.EXTRA_QR);
        Qrcode thisQr = (Qrcode) qrBundle.getSerializable("qrcode");

        scoreView = view.findViewById(R.id.score_info_view);
        locationView = view.findViewById(R.id.location_view);
        commentText = view.findViewById(R.id.comment_edittext);

        try {
            scoreView.setText(String.valueOf(thisQr.getScore()));
            locationView.setText(String.valueOf(thisQr.getLat() + "° N " + thisQr.getLon()+ "° W"));
            commentText.setText(thisQr.getComment());
        }catch (Exception e) {
            Log.d(TAG, String.valueOf(e));
            e.printStackTrace();
        }

        //scoreView.setText(thisQr.getScore());
        //locationView.setText(String.valueOf(thisQr.getLat() + "° N" + thisQr.getLon()+ "° W"));
        //commentText.setText(thisQr.getComment());
    }

}