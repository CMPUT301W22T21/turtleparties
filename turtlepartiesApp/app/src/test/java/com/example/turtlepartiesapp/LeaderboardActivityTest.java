package com.example.turtlepartiesapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class LeaderboardActivityTest {


    //Sorting Testing
    @Test
    public void testHashMapSorting(){
        ArrayList<Integer> ourSortedResults = new ArrayList<>();
        HashMap<String,Integer> testHashMap = new HashMap<>();
        testHashMap.put("bob", 12);
        testHashMap.put("john", 3112);
        testHashMap.put("dillon", 1);
        ourSortedResults.addAll(testHashMap.values());
        ArrayList<Integer> expectedResult = new ArrayList<>();
        expectedResult.add(3112);
        expectedResult.add(12);
        expectedResult.add(1);
        assertFalse(ourSortedResults.equals(expectedResult));
        assertEquals(0,0);
        testHashMap = PlayerController.sortByValue(testHashMap);
        ourSortedResults.clear();
        ourSortedResults.addAll(testHashMap.values());
        assertTrue(ourSortedResults.equals(expectedResult));
    }




}
