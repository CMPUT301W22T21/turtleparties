package com.example.turtlepartiesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class QRInfoOwner extends QRInfo{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_qrinfo_owner);
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


        //put in handler
        ResultHandler handler = new ResultHandler() {

            @Override
            public void handleResult(Object data) {
                finish();
            }
        };
        playerController.deleteQrCode(thisQr, handler);

    }

    @Override
    public void onLocationPictureButtonClicked(View view){ }
}