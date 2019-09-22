package com.formato.isp.utils;

import android.graphics.drawable.Drawable;

public class FolderFile {

    public int image;
    public Drawable imageDrw;
    public String name;
    public String date;
    public boolean section = false;
    public boolean folder = true;
    public int cantidad;

    public FolderFile() {
    }

    public FolderFile(String name, String date, int image, int cantidad,boolean folder) {
        this.image = image;
        this.name = name;
        this.date = date;
        this.cantidad = cantidad;
        this.folder = folder;
    }

    public FolderFile(String name, boolean section) {
        this.name = name;
        this.section = section;
    }

}
