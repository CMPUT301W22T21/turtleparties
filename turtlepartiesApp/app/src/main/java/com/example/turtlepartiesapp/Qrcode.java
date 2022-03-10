package com.example.turtlepartiesapp;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.Serializable;

public class Qrcode implements Serializable {

    private String text;
    private String QRstring;
    private BitMatrix result = null;
    private Bitmap myBitmap;
    private Integer score;
    private Double lat;
    private Double lon;
    private String comment;


    public Qrcode(String str_text, Integer score, Double lat, Double lon, String comment) throws WriterException {
        this.text = str_text;
        this.score = score;
        this.lat = lat;
        this.lon = lon;
        this.comment = comment;
    }


    public void generateQRimage(){
        try{
            result = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, 300,300,null);
            QRstring = result.toString();
        }catch (WriterException e){
            e.printStackTrace();
        }


        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int i = 0; i< height;i++){
            int offset = i * width;
            for (int x = 0; x<width; x++){
                pixels[offset+x] = result.get(x,i) ? BLACK : WHITE;
            }
        }
        myBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        myBitmap.setPixels(pixels,0,width,0,0,width,height);
    }


    public Bitmap getMyBitmap() {
        return myBitmap;
    }

    public String getText() {
        return text;
    }

    public Integer getScore() {
        return score;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
