package com.example.turtlepartiesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class QRInfo extends AppCompatActivity {

    final String TAG = "info";
    private ImageView qrImage;
    private TextView scoreView;
    private TextView locationView;
    private Button deleteButton;
    private View view;
    protected ScoreQrcode thisQr;
    private ListView commentList;
    private ArrayAdapter<Comment> commentAdapter;
    private ArrayList<Comment> commentDataList;
    private String username;
    private Player user;
    protected boolean showDeleteButton;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrinfo);

        view = this.findViewById(android.R.id.content);

        Intent intent = getIntent();
        Bundle qrBundle = intent.getBundleExtra(MainActivity.EXTRA_QR);
        handleBundle(qrBundle);

        Double lat = (Double) qrBundle.getSerializable("lat");
        Double lon = (Double) qrBundle.getSerializable("lon");
        thisQr.setGeolocation(new GeoPoint(lat, lon));
        String qrname = thisQr.getCode();
        thisQr.generateQRimage();

        db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("QR codes");

        qrImage = view.findViewById(R.id.qr_imageView);
        scoreView = view.findViewById(R.id.score_info_view);
        locationView = view.findViewById(R.id.location_view);
        deleteButton = view.findViewById(R.id.deleteQrButton);

        if (thisQr.isToShow()) {
            qrImage.setImageBitmap(thisQr.getMyBitmap());
        }else{
            qrImage.setImageResource(R.drawable.ic_baseline_qr_code_24);
        }
        scoreView.setText(String.valueOf(thisQr.getScore()));
        if (lat != 0.0) {
            locationView.setText(String.valueOf(lat + "° N " + lon + "° W"));
        }else{
            locationView.setText("n/a");
        }
        if (!showDeleteButton){
            deleteButton.setVisibility(view.INVISIBLE);
        }
        commentList = findViewById(R.id.comment_list);
        commentDataList=new ArrayList<>();

        commentAdapter = new QRCommentList(this, commentDataList);
        commentList.setAdapter(commentAdapter);

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
                    FirebaseFirestoreException error) {
                db.collection("QR codes").document(qrname).collection("comments").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                String username = doc.getId();
                                Log.d(TAG, username);
                                db.collection("QR codes").document(qrname).collection("comments").document(username).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot doc = task.getResult();
                                            String userComment = (String) doc.getData().get("comment");
                                            commentDataList.add((new Comment(userComment, username)));
                                            Log.d(TAG, userComment + "  " + username );
                                            commentAdapter.notifyDataSetChanged();
                                        }
                                    }
                                });
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
                commentAdapter.notifyDataSetChanged();
            }

        });
    }

    public void handleBundle(Bundle qrBundle ){
        thisQr = (ScoreQrcode) qrBundle.getSerializable("qrcode");
        user = (Player) qrBundle.getSerializable("user");
        showDeleteButton = (boolean) qrBundle.getSerializable("showDeleteButton");
    }


    public void deleteButtonClicked(View view){
        PlayerController playerController = new PlayerController();
        playerController.removeQrFromPlayer(user, thisQr);

        finish();
    }

}