package com.example.turtlepartiesapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class GoalsFragment extends DialogFragment {

//    private com.example.turtlepartiesapp.GoalsFragment.OnFragmentInteractionListener listener;
    private OnFragmentInteractionListener listener;

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

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_goals,null);
        ImageView goalImage = view.findViewById(R.id.goalImage);
        TextView text = view.findViewById(R.id.goaltext);
//        Player fragPlayer = (Player)getArguments().getSerializable("player");


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("QRcodes Scanned")
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create();
    }
}
