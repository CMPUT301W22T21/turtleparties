package com.example.turtlepartiesapp;

import static org.junit.Assert.assertEquals;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

import java.util.ArrayList;

public class MainActivityTest {

    ArrayList<String> databaseQRCodes;

    @Before
    public void setUpLinkToDatabase(){
        databaseQRCodes = new ArrayList<>();
        databaseQRCodes.clear();
        //databaseQRCodes = MainActivity.getQRCodeStrings();

    }

    

    @Test
    public void deleteQRTest(){
        System.out.println(databaseQRCodes + "  is qr codes");
        assertEquals(4, 2 + 2);
    }

}
