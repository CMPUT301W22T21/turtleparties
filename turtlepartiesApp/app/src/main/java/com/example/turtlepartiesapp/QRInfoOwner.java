package com.example.turtlepartiesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class QRInfoOwner extends QRInfo {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void handleBundle(Bundle qrBundle ){
        thisQr = (ScoreQrcode) qrBundle.getSerializable("qrcode");
        showDeleteButton = (boolean) qrBundle.getSerializable("showDeleteButton");
    }

    @Override
    public void deleteButtonClicked(View view){
        PlayerController playerController = new PlayerController();
        //playerController.removeQrFromPlayer(user, thisQr);

        finish();
    }
}