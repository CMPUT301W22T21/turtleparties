package com.example.turtlepartiesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    final String TAG = "Sample";
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("Users");

        Button button = findViewById(R.id.button);

        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieving the city name and the province name from the EditText fields
                final String cityName = "test2";
                final String provinceName = "test2";

                HashMap<String, String> data = new HashMap<>();

                if (cityName.length()>0 && provinceName.length()>0) {
                    // If there’s some data in the EditText field, then we create a new key-value pair.
                    data.put("Province Name", provinceName);
                    // We are doing firebase to store this value on cloud
                }
                // The set method sets a unique id for the document
                collectionReference.document(cityName)
                        .set(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // These are a method which gets executed when the task is succeeded
                                Log.d(TAG, "Data has been added successfully!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // These are a method which gets executed if there’s any problem
                                Log.d(TAG, "Data could not be added!" + e.toString());
                            }
                        });
            }
        });*/
    }

    public void on_button_click(View view){
        Log.d(TAG, "on_button_click: CLICK");
        map_activity();
    }

    public void map_activity(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}