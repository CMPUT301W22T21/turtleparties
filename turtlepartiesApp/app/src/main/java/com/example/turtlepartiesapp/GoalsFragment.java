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
        ImageView goalImage1 = view.findViewById(R.id.goalImage1);
        ImageView goalImage2 = view.findViewById(R.id.goalImage2);
        ImageView goalImage3 = view.findViewById(R.id.goalImage3);
        ImageView goalImage4 = view.findViewById(R.id.goalImage4);
        ImageView goalImage5 = view.findViewById(R.id.goalImage5);
        ImageView goalImage6 = view.findViewById(R.id.goalImage6);


        Player fragPlayer = (Player)getArguments().getSerializable("player");
        Integer qrCount = Integer.valueOf(fragPlayer.getQrCount().toString());


        if (qrCount < 1) {
            goalImage1.setImageResource(R.drawable.turtle1g);
            goalImage2.setImageResource(R.drawable.turtle3g);
            goalImage3.setImageResource(R.drawable.turtle10g);
            goalImage4.setImageResource(R.drawable.turtle15g);
            goalImage5.setImageResource(R.drawable.turtle20g);
            goalImage6.setImageResource(R.drawable.turtle30g);
        }

        else if (qrCount < 3) {
            goalImage1.setImageResource(R.drawable.turtle1);
            goalImage2.setImageResource(R.drawable.turtle3g);
            goalImage3.setImageResource(R.drawable.turtle10g);
            goalImage4.setImageResource(R.drawable.turtle15g);
            goalImage5.setImageResource(R.drawable.turtle20g);
            goalImage6.setImageResource(R.drawable.turtle30g);

        }
        else if (qrCount < 10) {
            goalImage1.setImageResource(R.drawable.turtle1);
            goalImage2.setImageResource(R.drawable.turtle3);
            goalImage3.setImageResource(R.drawable.turtle10g);
            goalImage4.setImageResource(R.drawable.turtle15g);
            goalImage5.setImageResource(R.drawable.turtle20g);
            goalImage6.setImageResource(R.drawable.turtle30g);

        }
        else if (qrCount < 15) {
            goalImage1.setImageResource(R.drawable.turtle1);
            goalImage2.setImageResource(R.drawable.turtle3);
            goalImage3.setImageResource(R.drawable.turtle10);
            goalImage4.setImageResource(R.drawable.turtle15g);
            goalImage5.setImageResource(R.drawable.turtle20g);
            goalImage6.setImageResource(R.drawable.turtle30g);

        }
        else if (qrCount < 20) {
            goalImage1.setImageResource(R.drawable.turtle1);
            goalImage2.setImageResource(R.drawable.turtle3);
            goalImage3.setImageResource(R.drawable.turtle10);
            goalImage4.setImageResource(R.drawable.turtle15);
            goalImage5.setImageResource(R.drawable.turtle20g);
            goalImage6.setImageResource(R.drawable.turtle30g);

        }
        else if (qrCount < 30) {
            goalImage1.setImageResource(R.drawable.turtle1);
            goalImage2.setImageResource(R.drawable.turtle3);
            goalImage3.setImageResource(R.drawable.turtle10);
            goalImage4.setImageResource(R.drawable.turtle15);
            goalImage5.setImageResource(R.drawable.turtle20);
            goalImage6.setImageResource(R.drawable.turtle30g);

        }
        else {
            goalImage1.setImageResource(R.drawable.turtle1);
            goalImage2.setImageResource(R.drawable.turtle3);
            goalImage3.setImageResource(R.drawable.turtle10);
            goalImage4.setImageResource(R.drawable.turtle15);
            goalImage5.setImageResource(R.drawable.turtle20);
            goalImage6.setImageResource(R.drawable.turtle30);
        }





        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("The Number of QRcodes")
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create();
    }
}
