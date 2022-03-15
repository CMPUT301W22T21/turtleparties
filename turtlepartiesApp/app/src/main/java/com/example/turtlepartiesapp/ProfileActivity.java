package com.example.turtlepartiesapp;

import android.content.Intent;
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

/**
 * Profile activity
 * Displays current players information
 */
public class ProfileActivity extends AppCompatActivity {

    TextView text_name, text_userName;
    EditText editText_name, editText_userName, editText_email, editText_phoneNumber;
    Button saveButton, showLoginQRButton, showFriendQRButton;
    Player player;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_screen);

        text_name = findViewById(R.id.bigName);
        text_userName = findViewById(R.id.bigUserName);
        editText_name = findViewById(R.id.editName);
        editText_email = findViewById(R.id.editEmail);
        editText_phoneNumber = findViewById(R.id.editPhoneNumber);
        saveButton = findViewById(R.id.saveChangesButton);
        showLoginQRButton = findViewById(R.id.showLoginQRButton);
        showFriendQRButton = findViewById(R.id.showFriendQRButton);

        Intent intent = getIntent();
        Bundle userBundle = intent.getBundleExtra(MainActivity.EXTRA_USER);
        player = (Player) userBundle.getSerializable("usr");

        text_name.setText(player.getName());
        text_userName.setText(player.getUsername());
        editText_name.setText(player.getName());
        editText_email.setText(player.getEmail());
        editText_phoneNumber.setText(player.getPhoneNumber());

    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, fragment);
        ft.commit();
    }

    public void onSaveButtonClicked(View view){
        player.setName(String.valueOf(editText_name.getText()));
        player.setEmail(String.valueOf(editText_email.getText()));
        player.setPhoneNumber(String.valueOf(editText_name.getText()));
    }

    public void onShowLoginQRButtonClicked(View view){
        Bundle userBundle = new Bundle();
        userBundle.putSerializable("player", player);
        Fragment loginFrag = new Fragment();
        loginFrag.setArguments(userBundle);
        replaceFragment(loginFrag);
    }

    public void onShowFriendQRButtonClicked(View view){
        replaceFragment(new FriendQRFragment());
    }


}
