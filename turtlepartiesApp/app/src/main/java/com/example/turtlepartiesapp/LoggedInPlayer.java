package com.example.turtlepartiesapp;
// Extends player and stores currently logged in player
// To be deprecated in future iterations
public class LoggedInPlayer extends Player{
    //String Password;
    String phoneNumber;
    String email;

    public LoggedInPlayer(String username) {
        super(username);
    }

    public void addQrCode(ScoreQrcode qrCodes) {
        this.qrCodes.add(qrCodes);
        //add in db
    }

    public void removeQrCode(Qrcode qrCodes) {
        this.qrCodes.remove(qrCodes);
        //add in db
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setUserName(String newUserName) {
        this.name = newUserName;
    }

    public void setEmail(String newEmail){
        this.email = newEmail;
    }

    public void setPhoneNumber(String newPhoneNumber){
        this.phoneNumber = newPhoneNumber;
    }
}
