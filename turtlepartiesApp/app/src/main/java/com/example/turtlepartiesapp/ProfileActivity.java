package com.example.turtlepartiesapp;

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
            }
        });

        // Open friend QR Fragment
        showFriendQRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                replaceFragment(new FriendQRFragment());
            }
        });




    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, fragment);
        ft.commit();
    }


}
