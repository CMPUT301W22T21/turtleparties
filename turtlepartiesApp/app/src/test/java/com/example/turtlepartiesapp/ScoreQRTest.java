package com.example.turtlepartiesapp;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

public class ScoreQRTest {

    private ScoreQrcode createQR(){
        ScoreQrcode QR1 = new ScoreQrcode("helloworld");
        return QR1;

    }

    @Test
    public void testScoring(){
        ScoreQrcode QR = createQR();
        // the score for the QR code with helloworld should be 196 from the hash of 936a185caaa266bb9cbe981e9e05cb78cd732b0b3280eb944412bb6f8f8f07af
        assertEquals(QR.getScore(),196);

    }






}
