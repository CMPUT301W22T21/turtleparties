package com.example.turtlepartiesapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class LoginQRFragment extends Fragment {

    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login_qr, container, false);
        Player fragPlayer = (Player)getArguments().getSerializable("player");
        LoginQrcode player = new LoginQrcode(fragPlayer.toString());


        ImageView loginQRImage = view.findViewById(R.id.loginQRImageView);
        //fragPlayer;
        //loginQRImage.setImageResource()
        player.generateQRimage();
        loginQRImage.setImageBitmap(player.getMyBitmap());


        return view;

    }
}