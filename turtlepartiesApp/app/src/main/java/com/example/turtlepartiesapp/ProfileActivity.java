package com.example.turtlepartiesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    EditText editText_name, editText_userName, editText_email, editText_phoneNumber;
    Button saveButton;
    Player player;

    //

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_screen);

        editText_name = findViewById(R.id.editName);
        editText_userName = findViewById(R.id.editUserName);
        editText_email = findViewById(R.id.editEmail);
        editText_phoneNumber = findViewById(R.id.editPhoneNumber);
        saveButton = findViewById(R.id.saveChangesButton);

//        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
//
//        textView_bigName = findViewById(R.id.bigName);
//        textView_bigUserName = findViewById(R.id.bigUserName);
//        textView_name = findViewById(R.id.text_name);
//        textView_userName = findViewById(R.id.text_userName);
//        textView_email = findViewById(R.id.text_email);
//        textView_phoneNumber = findViewById(R.id.text_phoneNumber);

        editText_name.setText("This sets the text.", TextView.BufferType.EDITABLE);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // when click a button, put data on Shared preferences
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString(KEY_NAME, editText_name.getText().toString());
//                editor.putString(KEY_USERNAME, editText_userName.getText().toString());
//                editor.putString(KEY_EMAIL, editText_email.getText().toString());
//                editor.putString(KEY_PHONENUMBER, editText_phoneNumber.getText().toString());
//
//                editor.apply();
//            }
//        });
//
//        // set the text
//        String name = sharedPreferences.getString(KEY_NAME, null);
//        String userName = sharedPreferences.getString(KEY_USERNAME, null);
//        String email = sharedPreferences.getString(KEY_EMAIL, null);
//        String phoneNumber = sharedPreferences.getString(KEY_PHONENUMBER, null);
//
//        if (name != null || userName != null || email != null || phoneNumber != null) {
//            textView_bigName.setText(name);
//            textView_bigUserName.setText(userName);
//            textView_name.setText(name);
//            textView_userName.setText(userName);
//            textView_email.setText(email);
//            textView_phoneNumber.setText(phoneNumber);
//        }


    }

    // saves user information changes

//    private EditText editText_name, editText_userName, editText_email, editText_phoneNumber;
//    private Button saveButton;
//    SharedPreferences sharedPreferences;
//
//    private TextView textView_name, textView_userName, textView_email, textView_phoneNumber, textView_bigName, textView_bigUserName;
//
//    // Create shared preferences name and key name
//    private static final String SHARED_PREF_NAME = "mypref";
//    private static final String KEY_NAME = "name";
//    private static final String KEY_USERNAME = "userName";
//    private static final String KEY_EMAIL = "email";
//    private static final String KEY_PHONENUMBER = "phoneNumber";
//
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.profile_screen);
//
//        editText_name = findViewById(R.id.editName);
//        editText_userName = findViewById(R.id.editUserName);
//        editText_email = findViewById(R.id.editEmail);
//        editText_phoneNumber = findViewById(R.id.editPhoneNumber);
//        saveButton = findViewById(R.id.saveChangesButton);
//
//        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
//
//        textView_bigName = findViewById(R.id.bigName);
//        textView_bigUserName = findViewById(R.id.bigUserName);
//        textView_name = findViewById(R.id.text_name);
//        textView_userName = findViewById(R.id.text_userName);
//        textView_email = findViewById(R.id.text_email);
//        textView_phoneNumber = findViewById(R.id.text_phoneNumber);
//
//
//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // when click a button, put data on Shared preferences
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString(KEY_NAME, editText_name.getText().toString());
//                editor.putString(KEY_USERNAME, editText_userName.getText().toString());
//                editor.putString(KEY_EMAIL, editText_email.getText().toString());
//                editor.putString(KEY_PHONENUMBER, editText_phoneNumber.getText().toString());
//
//                editor.apply();
//            }
//        });
//
//        // set the text
//        String name = sharedPreferences.getString(KEY_NAME, null);
//        String userName = sharedPreferences.getString(KEY_USERNAME, null);
//        String email = sharedPreferences.getString(KEY_EMAIL, null);
//        String phoneNumber = sharedPreferences.getString(KEY_PHONENUMBER, null);
//
//        if (name != null || userName != null || email != null || phoneNumber != null) {
//            textView_bigName.setText(name);
//            textView_bigUserName.setText(userName);
//            textView_name.setText(name);
//            textView_userName.setText(userName);
//            textView_email.setText(email);
//            textView_phoneNumber.setText(phoneNumber);
//        }
//
//
//    }
}
