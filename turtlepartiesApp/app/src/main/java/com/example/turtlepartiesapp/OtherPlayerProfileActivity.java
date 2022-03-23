package com.example.turtlepartiesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class OtherPlayerProfileActivity extends AppCompatActivity {

    Player thisUser;
    Player mainUser;
    Context context;
    String TAG = "OtherProfile";

    TextView usernameTextView;
    TextView nameTextView;
    TextView headerTextView;
    Button changeListViewButton;

    String formattedHeader;

    ListView otherQRList;
    ArrayAdapter<ScoreQrcode> otherUserQRAdapter;
    ArrayAdapter<ScoreQrcode> commonQRAdapter;
    ArrayList<ScoreQrcode> otherUserQRDataList;
    ArrayList<ScoreQrcode> commonQRDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_player_profile);
        context = this;

        Intent searchIntent = getIntent();
        Bundle playerBundle = searchIntent.getBundleExtra(PlayerSearchActivity.EXTRA_OTHER_PLAYER);
        thisUser = (Player) playerBundle.getSerializable("thisPlayer");
        mainUser = (Player) playerBundle.getSerializable("mainPlayer");

        otherUserQRDataList =new ArrayList<>();
        otherUserQRDataList.addAll(thisUser.getQrCodes());
        commonQRDataList =new ArrayList<>();
        commonQRDataList.addAll(mainUser.getQrCodes());
        commonQRDataList.retainAll(otherUserQRDataList);

        Log.d(TAG, otherUserQRDataList.toString() + "  " + commonQRDataList.toString());
        setView();

        otherQRList = findViewById(R.id.other_player_qr_list);
        otherUserQRAdapter = new QRList(this, otherUserQRDataList);
        commonQRAdapter = new QRList(this, commonQRDataList);
        otherQRList.setAdapter(otherUserQRAdapter);
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
            otherQRList.setAdapter(commonQRAdapter);
            commonQRAdapter.notifyDataSetChanged();
        }else{
            headerTextView.setText(formattedHeader);
            changeListViewButton.setText(getString(R.string.other_player_show_common_qr_button_text));
            otherQRList.setAdapter(otherUserQRAdapter);
            otherUserQRAdapter.notifyDataSetChanged();
        }
    }

}