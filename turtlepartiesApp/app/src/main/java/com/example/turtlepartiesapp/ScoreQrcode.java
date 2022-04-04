package com.example.turtlepartiesapp;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

import android.graphics.Bitmap;

import com.google.common.hash.Hashing;
import com.google.firebase.database.Exclude;
import com.google.firebase.firestore.GeoPoint;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

public class ScoreQrcode extends Qrcode implements Serializable, Comparable {

    private String qrName;
    private int score;
    private transient GeoPoint geolocation;
    private String comment;
    private boolean toShow;
    private Bitmap picture;

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


    /**
     * returns the name assigned to the qrcode
     * @return
     */
    public String getQrName() {
        return qrName;
    }


    /**
     * allows setting of qrcode name
     * @param qrName
     */
    public void setQrName(String qrName) {
        this.qrName = qrName;
    }

    /**
     * This function will run a hash function on the qrcode's code
     */
    private String sha_hash(){
        return Hashing.sha256().hashString(this.getCode(), StandardCharsets.UTF_8).toString();
    }


    /**
     * checks qrCode visibility
     * @return
     */
    public boolean isToShow() {
        return toShow;
    }

    /**
     * set qrcode visibility
     * @param toShow
     */
    public void setToShow(boolean toShow) {
        this.toShow = toShow;
    }

    /**
     * get qrcode score
     * @return
     */
    public int getScore(){
        return score;
    }

    /**
     * gets qrcode geoloaction
     * @return
     */
    public GeoPoint getGeolocation() {
        return geolocation;
    }

    /**
     * for setting qrcode geolocation
     * @param geolocation
     */
    public void setGeolocation(GeoPoint geolocation) {
        this.geolocation = geolocation;
    }

    /**
     * gets qrcode comment
     * @return
     */
    public String getComment() {
        return comment;
    }

    /**
     * for setting qrcode comment
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * gets qrcode location image
     * @return
     */
    public Bitmap getPicture() { return picture; }

    /**
     * for setting qrcode location image
     * @param picture
     */
    public void setPicture(Bitmap picture) { this.picture = picture; }

    /**
     * for comparing scores of qrcodes
     * @param o
     * @return
     */
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

    /**
     * for checking if qrcode has a geolocation
     * @return
     */
    public Boolean hasGeoLocation(){
        if(geolocation == null){
            return false;
        }else{
            return true;
        }
    }

    /**
     * for hashing the qrcode
     * @return
     */
    public String sha256(){
        return this.sha_hash();
    }
}
