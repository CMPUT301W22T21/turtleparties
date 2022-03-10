package com.example.turtlepartiesapp;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Player {
    FirebaseFirestore db;
    int id;
    int score;
    ArrayList<Qrcode> qrCodes;

}
