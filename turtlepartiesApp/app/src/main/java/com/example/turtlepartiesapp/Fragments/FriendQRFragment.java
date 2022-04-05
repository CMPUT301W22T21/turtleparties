package com.example.turtlepartiesapp.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.turtlepartiesapp.Models.FriendQrcode;
import com.example.turtlepartiesapp.Player;
import com.example.turtlepartiesapp.R;


public class FriendQRFragment extends DialogFragment {
    private FriendQRFragment.OnFragmentInteractionListener  listener;


    // code for when pressing close
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
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_friend_qr,null);
        ImageView loginQRImage = view.findViewById(R.id.FriendQRImage);
        Player fragPlayer = (Player)getArguments().getSerializable("player");

        // changed to create QRcode by username
        FriendQrcode friendcode = new FriendQrcode(fragPlayer.getUsername());
        friendcode.generateQRimage();
        loginQRImage.setImageBitmap(friendcode.getMyBitmap());



        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("FRIEND QR CODE")
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create();
    }
}
