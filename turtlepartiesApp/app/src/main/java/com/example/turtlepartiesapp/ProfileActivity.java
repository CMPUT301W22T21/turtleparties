package com.example.turtlepartiesapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    EditText editText_name, editText_email, editText_phoneNumber;
    Button saveButton, showLoginQRButton, showFriendQRButton;
    Player player;

    PlayerController playerControl;
    final String TAG = "ProfileActivity";


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_screen);

        text_name = findViewById(R.id.other_player_name_textview);
        text_userName = findViewById(R.id.other_player_username_textview);
        editText_name = findViewById(R.id.editName);
        editText_email = findViewById(R.id.editEmail);
        editText_phoneNumber = findViewById(R.id.editPhoneNumber);
        saveButton = findViewById(R.id.saveChangesButton);
        showLoginQRButton = findViewById(R.id.showLoginQRButton);
        showFriendQRButton = findViewById(R.id.showFriendQRButton);

        Intent intent = getIntent();
        Bundle userBundle = intent.getBundleExtra(MainActivity.EXTRA_USER);
        player = (Player) userBundle.getSerializable("usr");

        playerControl = new PlayerController();


        setInfo();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, fragment);
        ft.commit();
    }

    public void setInfo(){
        text_name.setText(player.getName());
        text_userName.setText(player.getUsername());
        editText_name.setText(player.getName());
        editText_email.setText(player.getEmail());
        editText_phoneNumber.setText(player.getPhoneNumber());
        Log.d(TAG, player.getEmail() + "  " + player.getEmail());
    }

    public void onSaveButtonClicked(View view){
        player.setName(String.valueOf(editText_name.getText()));
        player.setEmail(String.valueOf(editText_email.getText()));
        player.setPhoneNumber(String.valueOf(editText_phoneNumber.getText()));
        playerControl.savePlayer(player);
        setInfo();
    }

    public void onShowLoginQRButtonClicked(View view){
        Bundle userBundle = new Bundle();
        userBundle.putSerializable("player", player);
        LoginQRFragment loginFrag = new LoginQRFragment();
        loginFrag.setArguments(userBundle);
        replaceFragment(loginFrag);
    }

    public void onShowFriendQRButtonClicked(View view){
        replaceFragment(new FriendQRFragment());
    }


}
