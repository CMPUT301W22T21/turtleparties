package com.example.turtlepartiesapp;

import com.google.common.hash.Hashing;
import java.lang.Math;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Scorer {

    private int score;

    // pass in qr code to be scored //
    public Scorer() {;}


    public int getScore(Qrcode myqr){

        int current = 0; // stores the current decimal number
        int previous = -1; // stores previous decimal number
        int decimal; // decimal representation
        int streak = 0; // stores the number of repeats in the hashed value
        String mychar = "";


        // gets the sha256 hash for our Qr code
        String sha256hex = Hashing.sha256()
                .hashString(myqr.getText(), StandardCharsets.UTF_8)
                .toString();


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
        score = (int) (score + Math.pow(current, streak));

        return score;

    }


}
