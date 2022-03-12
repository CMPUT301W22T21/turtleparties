package com.example.turtlepartiesapp;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;


public abstract class Qrcode {


    private String code;
    private BitMatrix result = null;
    private Bitmap myBitmap;

    // instantiates the QR class

    /**
     * Abstract class for QR code it is extended by ScoreQRcode and LoginQRcode. This class generates a bitmap and the qrcode.
     * @param code
     */
    public Qrcode(String code){
        this.code = code;
    }



    /**
     * This method will generate the bitmap for a qr code. Bitmap must be set to imageview for qr code to be visible
     * @return Bitmap
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

    public Bitmap getMyBitmap() {
        return myBitmap;
    }

    public String getCode(){
        return code;
    }

}
