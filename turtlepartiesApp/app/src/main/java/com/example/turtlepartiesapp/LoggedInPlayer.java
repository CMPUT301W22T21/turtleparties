package com.example.turtlepartiesapp;

public class LoggedInPlayer extends Player{
    //String Password;

    public LoggedInPlayer(String username) {
        super(username);
    }

    public void addQrCode(Qrcode qrCodes) {
        this.qrCodes.add(qrCodes);
        //add in db
    }

    public void removeQrCode(Qrcode qrCodes) {
        this.qrCodes.remove(qrCodes);
        //add in db
    }
}
