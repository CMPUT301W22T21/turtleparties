package com.example.turtlepartiesapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class LoginQRFragment extends Fragment {

    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login_qr, container, false);

        LoggedInPlayer fragPlayer = getArguments().getParcelable("player");
        ImageView loginQRImage = view.findViewById(R.id.loginQRImageView);

        //fragPlayer;
        //loginQRImage.setImageResource();

        return view;

    }
}