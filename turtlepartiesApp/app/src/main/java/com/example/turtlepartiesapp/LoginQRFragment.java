package com.example.turtlepartiesapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class LoginQRFragment extends DialogFragment {
    private com.example.turtlepartiesapp.LoginQRFragment.OnFragmentInteractionListener listener;



    public interface OnFragmentInteractionListener{
        void onclosePressed();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;

        } else {
            throw new RuntimeException(context.toString()+"must implement OnFragmentInteractionListener");

        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // this part creates the code //
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_login_qr,null);
        ImageView loginQRImage = view.findViewById(R.id.loginQRImageView);
        Player fragPlayer = (Player)getArguments().getSerializable("player");
        LoginQrcode logincode = new LoginQrcode(fragPlayer.toString());
        logincode.generateQRimage();
        loginQRImage.setImageBitmap(logincode.getMyBitmap());



        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("LOGIN QR CODE")
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create();
    }






    // old method for showing fragment of profile qrcode

    /*
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

    }*/
}