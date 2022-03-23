package com.example.turtlepartiesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

public class OtherPlayerProfileActivity extends AppCompatActivity {

    Player thisUser;
    PlayerController playerControl;
    Context context;
    String TAG = "OtherProfile";

    FirebaseFirestore db;
    DocumentReference userRef;

    TextView usernameTextView;
    TextView nameTextView;
    TextView headerTextView;
    Button changeListViewButton;

    String formattedHeader;

    ListView otherQRList;
    ArrayAdapter<ScoreQrcode> otherQRAdapter;
    ArrayList<ScoreQrcode> otherQRDataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_player_profile);

        db = FirebaseFirestore.getInstance();
        context = this;

        Intent searchIntent = getIntent();
        Bundle playerBundle = searchIntent.getBundleExtra(PlayerSearchActivity.EXTRA_OTHER_PLAYER);
        thisUser = (Player) playerBundle.getSerializable("thisPlayer");

        setView();

        otherQRList = findViewById(R.id.other_player_qr_list);
        otherQRDataList=new ArrayList<>();
        otherQRDataList.addAll(thisUser.getQrCodes());
        otherQRAdapter = new QRList(this, otherQRDataList);
        otherQRList.setAdapter(otherQRAdapter);

//        userRef = db.collection("Users").document(thisUser.getUsername());
//        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if(task.isSuccessful()){
//                    DocumentSnapshot document = task.getResult();
//                    ArrayList <ScoreQrcode> listQR = (ArrayList) document.get("qrCodes");
//                    otherQRDataList.addAll(listQR);
//                    otherQRAdapter.notifyDataSetChanged();
//                }else{
//                    Log.d(TAG, "FAILED");
//                }
//            }
//        });
    }

    public void setView(){
        formattedHeader = getString(R.string.other_player_user_qr_list_header_textview, thisUser.getUsername());

        changeListViewButton = findViewById(R.id.other_player_change_qr_list_type_button);
        usernameTextView = findViewById(R.id.other_player_username_textview);
        nameTextView = findViewById(R.id.other_player_name_textview);
        headerTextView = findViewById(R.id.other_player_qr_list_header_textview);
        usernameTextView.setText(thisUser.getUsername());
        nameTextView.setText(thisUser.getName());
        headerTextView.setText(formattedHeader);
    }

    public void changeQRListView(View view){
        if (headerTextView.getText() == formattedHeader) {
            headerTextView.setText(R.string.other_player_common_qr_list_header_textview);
            changeListViewButton.setText(getString(R.string.other_player_show_user_qr_button_text));
        }else{
            headerTextView.setText(formattedHeader);
            changeListViewButton.setText(getString(R.string.other_player_show_common_qr_button_text));
        }
    }

}