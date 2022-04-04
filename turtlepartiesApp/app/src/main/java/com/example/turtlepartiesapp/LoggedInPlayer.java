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

    /**
     * Adds the qr code to show
     */
    public void addQrCode(ScoreQrcode qrCodes) {
        this.qrCodes.add(qrCodes);
        //add in db
    }

    /**
     * method for removing qrcodes from player
     * @param qrCodes
     */
    public void removeQrCode(Qrcode qrCodes) {
        this.qrCodes.remove(qrCodes);
        //add in db
    }

    /**
     * returns the players email
     * @return
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * returns the players phonenumber
     * @return
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * setter for players name
     * @param newName
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * setter for players username
     * @param newUserName
     */
    public void setUserName(String newUserName) {
        this.name = newUserName;
    }

    /**
     * setter for players email
     * @param newEmail
     */
    public void setEmail(String newEmail){
        this.email = newEmail;
    }

    /**
     * setter for players phonenumber
     * @param newPhoneNumber
     */
    public void setPhoneNumber(String newPhoneNumber){
        this.phoneNumber = newPhoneNumber;
    }
}
