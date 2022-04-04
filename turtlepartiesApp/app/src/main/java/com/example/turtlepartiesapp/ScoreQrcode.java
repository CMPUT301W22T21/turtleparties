package com.example.turtlepartiesapp;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.google.common.hash.Hashing;
import com.google.firebase.database.Exclude;
import com.google.firebase.firestore.GeoPoint;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

public class ScoreQrcode extends Qrcode implements Serializable, Comparable {

    private String qrName;
    private int score;
    private transient GeoPoint geolocation;
    private String comment;
    private boolean toShow;
    @Exclude
    protected Bitmap picture;
    private String pictureString;

    /**
     * This class is an extension of the QRcode class, it has a score, a comment, and a geolocation.
     * @param code
     */
   public ScoreQrcode(String code){
       super(code);
       this.qrName = null;
       this.calculatescore();
       this.geolocation = null;
       this.comment = null;
       this.toShow = true;
       this.picture = null;
       this.pictureString = null;
   }
    public ScoreQrcode(){}

    /**
     * This function will calculate the score of a qr code upon instantiating
     * @return score
     */
    private void calculatescore(){

        int current = 0; // stores the current decimal number
        int previous = -1; // stores previous decimal number
        int decimal; // decimal representation
        int streak = 0; // stores the number of repeats in the hashed value
        String mychar = "";


        // gets the sha256 hash for our Qr code
        String sha256hex = this.sha_hash();

        // for loop will iterate through characters that are encoded by SHA256
        for(int i=0; i<sha256hex.length(); i++){
            decimal = Integer.parseInt(mychar+sha256hex.charAt(i),16); // gets decimal value of hexadecimal number
            current = decimal;

            if(i!=0) { //checks to see if not on first iteration

                if (current == previous) { // increments streak if two numbers appear in a row
                    streak++;
                } else { // Once streak ends calculate power and add to score
                    score = (int) (score + Math.pow(previous, streak));
                    streak = 0;
                }
            }
            previous = current;

        }

        // tally score up with last element
        this.score = (int) (score + Math.pow(current, streak));

    }

    public String getQrName() {
        return qrName;
    }

    public void setQrName(String qrName) {
        this.qrName = qrName;
    }

    /**
     * This function will run a hash function on the qrcode's code
     */
    private String sha_hash(){
        return Hashing.sha256().hashString(this.getCode(), StandardCharsets.UTF_8).toString();
    }

    public boolean isToShow() {
        return toShow;
    }

    public void setToShow(boolean toShow) {
        this.toShow = toShow;
    }

    public int getScore(){
        return score;
    }

    public GeoPoint getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(GeoPoint geolocation) {
        this.geolocation = geolocation;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @com.google.firebase.firestore.Exclude
    public Bitmap getPicture() { return picture; }

    public void setPicture(Bitmap picture) { this.picture = picture; }

    @Override
    public int compareTo(Object o) {
        if (o == null || o.getClass() != ScoreQrcode.class) {
            return -1;
        }
        ScoreQrcode other = (ScoreQrcode) o;
        if (this.getScore() > other.getScore()) {
            return 1;
        }
        if (this.getScore() < other.getScore()) {
            return -1;
        }
        return 0;
    }

    public Boolean hasGeoLocation(){
        if(geolocation == null){
            return false;
        }else{
            return true;
        }
    }

    public String sha256(){
        return this.sha_hash();
    }


    public void PictureToString(){
        if(picture == null){
            return;
        }
        ByteArrayOutputStream baos =new  ByteArrayOutputStream();
        picture.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        pictureString = Base64.encodeToString(b, Base64.DEFAULT);
    }

    public void StringToBitMap(){
        if(pictureString == null){
            return;
        }
        try {
            byte [] encodeByte=Base64.decode(pictureString,Base64.DEFAULT);
            this.picture = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch(Exception e) {
            e.getMessage();
        }
    }

    public String getPictureString() {
        return pictureString;
    }
}
