package com.example.turtlepartiesapp;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class PlayerClassTest {
    Player newp;
    @Before
    public void playerTest(){
        newp = new Player("Billy");

    }

    @Test
    public void testName(){
        String cn =newp.getName();
        assertEquals(cn,"Billy");
    }

    @Test
    public void testPassword(){
        String pass = "LovesMilk";
        assertEquals(pass,newp.getPassword());
    }

    @Test
    public void testStats(){
        newp.setQrHighest((long) 5000);
        assertEquals(newp.getQrHighest(),(long)5000,0);
        newp.setQrLowest((long) 5);
        assertEquals(newp.getQrLowest(),(long)5,0);
        newp.setQrSum((long) 50000);
        assertEquals(newp.getQrSum(),(long)50000,0);
    }
}
