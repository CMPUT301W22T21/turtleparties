package com.example.turtlepartiesapp;

import android.os.Bundle;
import android.util.Log;
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

        Player fragPlayer = (Player) getArguments().getSerializable("player");
        Log.d("WTF", String.valueOf(fragPlayer));
        LoginQrcode player = new LoginQrcode(fragPlayer.toString());


        ImageView loginQRImage = view.findViewById(R.id.loginQRImageView);

        //fragPlayer;
        //loginQRImage.setImageResource()
        player.generateQRimage();
        loginQRImage.setImageBitmap(player.getMyBitmap());
        return view;

    }
}