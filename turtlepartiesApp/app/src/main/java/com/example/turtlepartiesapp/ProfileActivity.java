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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ProfileActivity extends AppCompatActivity {

    TextView text_name, text_userName;
    EditText editText_name, editText_userName, editText_email, editText_phoneNumber;
    Button saveButton, showLoginQRButton, showFriendQRButton;
    LoggedInPlayer player;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_screen);

        text_name = findViewById(R.id.bigName);
        text_userName = findViewById(R.id.bigUserName);
        editText_name = findViewById(R.id.editName);
        editText_userName = findViewById(R.id.editUserName);
        editText_email = findViewById(R.id.editEmail);
        editText_phoneNumber = findViewById(R.id.editPhoneNumber);
        saveButton = findViewById(R.id.saveChangesButton);
        showLoginQRButton = findViewById(R.id.showLoginQRButton);
        showFriendQRButton = findViewById(R.id.showFriendQRButton);


        // Open login QR Fragment
        showLoginQRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                replaceFragment(new LoginQRFragment());
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.loginQRContainer, new LoginQRFragment());
//                ft.commit();
            }
        });

        // Open friend QR Fragment
        showFriendQRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                replaceFragment(new FriendQRFragment());
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.friendQRContainer, new LoginQRFragment());
//                ft.commit();
            }
        });



//        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
//
//        textView_bigName = findViewById(R.id.bigName);
//        textView_bigUserName = findViewById(R.id.bigUserName);
//        textView_name = findViewById(R.id.text_name);
//        textView_userName = findViewById(R.id.text_userName);
//        textView_email = findViewById(R.id.text_email);
//        textView_phoneNumber = findViewById(R.id.text_phoneNumber);

//        String name, userName, email, phoneNumber;

//        name = player.getName();

//        editText_name.setText(name, TextView.BufferType.EDITABLE);
//
//
//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

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

    private void replaceFragment(Fragment fragment) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, fragment);
        ft.commit();
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
