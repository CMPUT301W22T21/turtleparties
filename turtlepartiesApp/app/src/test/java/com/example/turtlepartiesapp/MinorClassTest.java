package com.example.turtlepartiesapp;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class MinorClassTest {


    @Test
    public void commentTest(){
        Comment testcm = new Comment("I love this place", "billy");
        assertEquals(testcm.getCommentBody(),"I love this place");
        assertEquals(testcm.getAuthor(),"billy");
    }

    @Test
    public void geoLocationTest(){
        Geolocation gl = new Geolocation(0,0);
        gl.setLat(10.0);
        assertEquals((double)gl.getLat(),10.0, 0);
        gl.setLon(10.0);
        assertEquals((double)gl.getLon(),10.0,0 );

    }


}
