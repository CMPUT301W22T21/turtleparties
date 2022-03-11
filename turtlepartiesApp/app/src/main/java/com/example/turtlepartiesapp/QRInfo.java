package com.example.turtlepartiesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.WriterException;

import java.util.ArrayList;
import java.util.Arrays;

public class QRInfo extends AppCompatActivity {

    final String TAG = "info";
    private TextView scoreView;
    private TextView locationView;
    private EditText commentText;
    private View view;

    private ListView commentList;
    private ArrayAdapter<Comment> commentAdapter;
    private ArrayList<Comment> commentDataList;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrinfo);

        view = this.findViewById(android.R.id.content);

        Intent intent = getIntent();
        Bundle qrBundle = intent.getBundleExtra(MainActivity.EXTRA_QR);
        Qrcode thisQr = (Qrcode) qrBundle.getSerializable("qrcode");
        String qrname = thisQr.getId();

        db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("QR codes");

        scoreView = view.findViewById(R.id.score_info_view);
        locationView = view.findViewById(R.id.location_view);

        scoreView.setText(String.valueOf(thisQr.getScore()));
        locationView.setText(String.valueOf(thisQr.getLat() + "° N " + thisQr.getLon()+ "° W"));

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

}