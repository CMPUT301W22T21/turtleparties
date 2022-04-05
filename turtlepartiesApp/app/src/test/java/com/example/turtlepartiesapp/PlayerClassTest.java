package com.example.turtlepartiesapp;

import static org.junit.Assert.assertEquals;


import com.example.turtlepartiesapp.Models.ScoreQrcode;

import org.junit.Before;
import org.junit.Test;


/**
 * Unit Testing for Player class
 */
public class PlayerClassTest {
    Player newp;
    @Before
    public void setup(){
        newp = new Player("Billy");
    }

    /**
     * Test initial values of player
     */
    @Test
    public void testInit(){
        assertEquals(newp.qrCodes.size(),0);
        long val = newp.getQrHighest();
        assertEquals(val, 0);
        val = newp.getQrLowest();
        assertEquals(val, 0);
        val = newp.getQrSum();
        assertEquals(val, 0);
        assertEquals("Billy", newp.getUsername());
    }

    @Test
    public void testAddQrCode(){
        int size = newp.qrCodes.size();
        ScoreQrcode qr = new ScoreQrcode("123445");
        newp.addQrCode(qr);
        assertEquals(newp.qrCodes.size(),size+1);
    }

    @Test
    public void testRemoveQrCode(){
        int size = newp.qrCodes.size();
        ScoreQrcode qr1 = new ScoreQrcode("123445");
        newp.addQrCode(qr1);
        ScoreQrcode qr2 = new ScoreQrcode("123124");
        newp.removeQrCode(qr2);
        assertEquals(newp.qrCodes.size(),size+1);
        newp.removeQrCode(qr1);
        assertEquals(newp.qrCodes.size(),size);
    }


    @Test
    public void hasQrCode(){
        ScoreQrcode qr = new ScoreQrcode("123445");
        assertEquals(newp.hasQrCode(qr),false);
        newp.addQrCode(qr);
        assertEquals(newp.hasQrCode(qr),true);
    }
}
