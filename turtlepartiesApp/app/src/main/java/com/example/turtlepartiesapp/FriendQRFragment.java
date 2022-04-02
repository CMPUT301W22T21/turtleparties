package com.example.turtlepartiesapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FriendQRFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FriendQRFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friend_qr, container, false);
        Player fragPlayer = (Player)getArguments().getSerializable("player");
        FriendQrcode player = new FriendQrcode(fragPlayer.getName());


        ImageView loginQRImage = v.findViewById(R.id.FriendQRImage);

        //fragPlayer;
        //loginQRImage.setImageResource()
        player.generateQRimage();
        loginQRImage.setImageBitmap(player.getMyBitmap());

        return  v;
    }
}