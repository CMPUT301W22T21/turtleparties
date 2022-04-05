package com.example.turtlepartiesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.turtlepartiesapp.Adapters.QRList;
import com.example.turtlepartiesapp.Models.QRInfo;
import com.example.turtlepartiesapp.Models.ScoreQrcode;

import java.util.ArrayList;

// This activity shows another players Profile QR codes

public class OtherPlayerProfileActivity extends AppCompatActivity {

    String EXTRA_QR = "com.example.turtlepartiesapp.MESSAGE";
    String TAG = "OtherProfile";
    Player thisUser;
    Player mainUser;
    Context context;

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

    ScoreQrcode currentQr;

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

        otherQRList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                currentQr = (ScoreQrcode) otherQRList.getItemAtPosition(position);
                qrInfoActivity(currentQr);
            }
        });
    }

    public void qrInfoActivity(ScoreQrcode qrToPass){
        Bundle args = new Bundle();
        args.putSerializable("qrcode", qrToPass);
        args.putSerializable("showDeleteButton", false);

        try {
            args.putSerializable("lat", qrToPass.getGeolocation().getLatitude());
            args.putSerializable("lon", qrToPass.getGeolocation().getLongitude());
        }catch (Exception e){
            args.putSerializable("lat", 0.0);
            args.putSerializable("lon", 0.0);
        }

        Intent qrinfoIntent = new Intent (this, QRInfo.class);
        qrinfoIntent.putExtra(EXTRA_QR, args);
        startActivity(qrinfoIntent);
    }

    public void setView(){
        formattedHeader = getString(R.string.other_player_user_qr_list_header_textview, "USER");

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