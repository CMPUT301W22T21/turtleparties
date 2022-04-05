package com.example.turtlepartiesapp;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.turtlepartiesapp.Models.ScoreQrcode;
import com.google.firebase.firestore.GeoPoint;

import org.junit.Before;
import org.junit.Test;


public class QRCodesTest {

    ScoreQrcode testQR;
    @Before
    public void makeAQRCode(){
        testQR = new ScoreQrcode("BFG5DGW54");
    }


    @Test
    public void checkSha256Calculation(){
        String expectedSha256 = "8227ad036b504e39fe29393ce170908be2b1ea636554488fa86de5d9d6cd2c32";
        String classCalc = testQR.sha256();
        assertEquals(classCalc,expectedSha256);
    }

    @Test
    public void scoreCacl(){
        Integer expectedScore = 75;
        Integer actualScore = testQR.getScore();
        assertEquals(expectedScore,actualScore);
    }

    @Test
    public void checkStringCodeRetur(){
        String expected = "BFG5DGW54";
        String classCalc = testQR.getCode();
        assertEquals(expected,classCalc);
    }

    @Test
    public void checkHasGeoPoint(){
        GeoPoint gp = new GeoPoint(12,12);
        testQR.setGeolocation(gp);
        assertTrue(testQR.hasGeoLocation());
    }

    @Test
    public void checkCorrectGeoPointReturned(){
        GeoPoint gp = new GeoPoint(12,12);
        testQR.setGeolocation(gp);
        GeoPoint ap = testQR.getGeolocation();
        assertEquals(ap,gp);
    }

    @Test
    public void checkQrCodeNaming(){
        testQR.setQrName("Billy");
        String expectedName = "Billy";
        assertEquals(expectedName,testQR.getQrName());
    }

    @Test
    public void checkCommentSetting(){
        String comment = "Found near a small village";
        testQR.setComment(comment);
        assertEquals(comment,testQR.getComment());
    }


}
