package com.demo.myapplication;

import android.graphics.Bitmap;

public class Celebrity {
    private String name;
    private Bitmap bitmap;
    private String bitmapURL;
    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public Celebrity() {
    }

    public Celebrity(String name, Bitmap bitmap) {
        this.name = name;
        this.bitmap = bitmap;
    }

    public void setBitmapURL(String bitmapURL) {
        this.bitmapURL = bitmapURL;
    }

    public Celebrity(String name, String bitmapURL) {
        this.name = name;
        this.bitmapURL = bitmapURL;
    }

    public String getBitmapURL() {
        return bitmapURL;
    }


    public String getName() {
        return name;
    }

}
