package com.example.turtlepartiesapp.Controllers;

import android.graphics.Bitmap;

import com.example.turtlepartiesapp.Models.ScoreQrcode;
import com.google.firebase.firestore.GeoPoint;

public class QrScanController {

    private ScoreQrcode QRcode;

    /**
     * Initializing the controller
     */
    public QrScanController(){;}

    /**
     * For creating a new QRcode
     * @param code
     */
    public void NewQR(String code){
        QRcode = new ScoreQrcode(code);
    }


    /**
     * Allows controller to return the qrcode's bitmap
     * @return
     */
    public Bitmap getBitmap(){
        Bitmap BMP;
        BMP = QRcode.getMyBitmap();
        return BMP;
    }

    /**
     *Allows controller to create the qrcode's bitmap
     */
    public void createBitmap(){
        QRcode.generateQRimage();

    }

    /**
     * Allows controller to return the qrcode's score
     * @return
     */
    public int getScore(){
        int Score;
        Score = QRcode.getScore();
        return Score;
    }

    /**
     *Lets controller add comment to qrcode
     * @param comment
     */
    public void addComment(String comment){
        QRcode.setComment(comment);
    }


    /**
     * Lets controller to get the qrcode's score
     * @return
     */
    public ScoreQrcode getQRcode() {
        return QRcode;
    }

    /**
     * lets controller to set a location picture to the qrcode
     * @param image
     */
    public void setLocationPicture(Bitmap image){
        QRcode.setPicture(image);
    }

    /**
     * lets controller to retrieve the bitmap of the loaction picture for the qrcode
     * @return
     */
    public Bitmap getLocationPicture(){
        Bitmap image;
        image = QRcode.getPicture();
        return image;
    }

    /**
     * Lets controller to set the geopoint of the qrcode
     * @param geolocation
     */
    public void setGeo(GeoPoint geolocation) {
        QRcode.setGeolocation(geolocation);
    }
}
