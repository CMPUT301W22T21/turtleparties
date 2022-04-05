package com.example.turtlepartiesapp.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.turtlepartiesapp.R;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class GeolocationFragment extends DialogFragment {

    private OnFragmentInteractionListener listener;
    private LocationManager locationManager;

    public interface OnFragmentInteractionListener{
        double[] getGeoLocation();
        void setLocation();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement onfragmentinteractionlsitener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_geolocation,null);

        double[] geoLoc = listener.getGeoLocation();
        Log.d("GEO", geoLoc[0] + "   " + geoLoc[1]);

        TextView latView = view.findViewById(R.id.lat_textview);
        TextView longView = view.findViewById(R.id.long_textview);

        latView.setText(String.valueOf(geoLoc[0]));
        longView.setText(String.valueOf(geoLoc[1]));

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add Geolocation")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.setLocation();
                    }
                }).create();
    }

}