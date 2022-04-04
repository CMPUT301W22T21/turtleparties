package com.example.turtlepartiesapp;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

import android.graphics.Bitmap;

import com.google.firebase.database.Exclude;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.Serializable;


public abstract class Qrcode implements Serializable {


    private String code;
    @Exclude
    private transient BitMatrix result = null;
    @Exclude
    private transient Bitmap myBitmap;

    // instantiates the QR class

    /**
     * Abstract class for QR code it is extended by ScoreQRcode and LoginQRcode. This class generates a bitmap and the qrcode.
     * @param code
     */
    public Qrcode(String code){
        this.code = code;
    }
    public Qrcode(){}


    /**
     * This method will generate the bitmap for a qr code. Bitmap must be set to imageview for qr code to be visible
     */
    public void generateQRimage(){

        try{
            result = new MultiFormatWriter().encode(this.getCode(), BarcodeFormat.QR_CODE, 300,300,null);
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


    /**
     * This method will return the bitmap
     * @return
     */
    @com.google.firebase.firestore.Exclude
    public Bitmap getMyBitmap() {
        return myBitmap;
    }

    /**
     * This method will return the code used to generate QRcode
     * @return
     */
    public String getCode(){
        return code;
    }


    /**
     * This method lets you check if QRcode is of type QRcode
     * @param qrCode
     * @return
     */
    @Override
    public boolean equals(Object qrCode){
        if(qrCode == null || qrCode.getClass() != this.getClass()){
            return false;
        }
        Qrcode otherQr = (Qrcode) qrCode;
        if(otherQr.getCode().equals(this.getCode())){
            return true;
        }
        return false;
    }

    /**
     * This method returs the hash of the code
     * @return
     */
    @Override
    public int hashCode(){
        return getCode().hashCode();
    }
}
