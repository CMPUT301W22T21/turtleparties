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
    private transient BitMatrix result = null;
    private transient Bitmap myBitmap;
    private Integer score;
    private Double lat;
    private Double lon;
    private String comment;


    public Qrcode(String str_text) throws WriterException {
        this.text = str_text;
        this.score = null;
        this.lat = null;
        this.lon = null;
        this.comment = null;
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

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
