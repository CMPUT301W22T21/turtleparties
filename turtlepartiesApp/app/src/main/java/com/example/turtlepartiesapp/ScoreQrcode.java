package com.example.turtlepartiesapp;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

import android.graphics.Bitmap;

import com.google.common.hash.Hashing;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.nio.charset.StandardCharsets;

public class ScoreQrcode extends Qrcode {


    private int score;
    private Geolocation geolocation;
    private String comment;


    /**
     * This class is an extension of the QRcode class, it has a score, a comment, and a geolocation.
     * @param code
     */
   public ScoreQrcode(String code){
       super(code);
       this.calculatescore();
       this.geolocation = null;
       this.comment = null;
   }


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
     * This function will run a hash function on the qrcode's code
     */
    private String sha_hash(){
        return Hashing.sha256().hashString(this.getCode(), StandardCharsets.UTF_8).toString();
    }

    public int getScore(){
        return score;
    }

    public Geolocation getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(Geolocation geolocation) {
        this.geolocation = geolocation;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
