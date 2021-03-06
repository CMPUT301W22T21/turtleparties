package com.example.turtlepartiesapp.Models;

import android.os.Bundle;
import android.view.View;

import com.example.turtlepartiesapp.Models.QRInfo;
import com.example.turtlepartiesapp.Models.ScoreQrcode;
import com.example.turtlepartiesapp.PlayerController;
import com.example.turtlepartiesapp.ResultHandler;

// activity for showing qrcodes when you are the owner
public class QRInfoOwner extends QRInfo {

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