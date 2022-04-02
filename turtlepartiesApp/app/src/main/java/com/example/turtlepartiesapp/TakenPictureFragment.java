package com.example.turtlepartiesapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class TakenPictureFragment extends DialogFragment {
    private com.example.turtlepartiesapp.TakenPictureFragment.OnFragmentInteractionListener listener;



    public interface OnFragmentInteractionListener{
        void onclosePressed();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof TakenPictureFragment.OnFragmentInteractionListener) {
            listener = (TakenPictureFragment.OnFragmentInteractionListener) context;

        } else {
            throw new RuntimeException(context.toString()+"must implement OnFragmentInteractionListener");

        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // this part creates the code //
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_taken_picture,null);
        ImageView Picture = view.findViewById(R.id.TakenPictureImageView);

        /* IMPLEMENTATION OF SHOWING IMAGE TAKEN SHOULD GO HERE
        Bundle byteArray = getArguments("image");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        */





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


}