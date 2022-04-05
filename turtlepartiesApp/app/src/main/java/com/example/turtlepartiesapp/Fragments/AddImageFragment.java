package com.example.turtlepartiesapp.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.turtlepartiesapp.R;

public class AddImageFragment extends DialogFragment {
    private AddImageFragment.OnFragmentInteractionListener listener;


    public interface OnFragmentInteractionListener {
        void OnOpenCamera();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;

        } else {
            throw new RuntimeException(context.toString() + "must implement OnFragmentInteractionListener");

        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // this part creates the code //
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_image, null);


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add Image")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.OnOpenCamera();
                    }
                }).create();

    }
}