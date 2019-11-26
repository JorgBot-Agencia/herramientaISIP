package com.formato.isp.utils;

import android.graphics.drawable.Drawable;

public class FolderFile {

    public int image;
    public Drawable imageDrw;
    public String name;
    public String estado;
    public boolean section = false;
    public boolean folder = true;
    public double cantidad;
    public int id;

    public FolderFile() {
    }

    public FolderFile(int id, String name, String date, int image, double cantidad, boolean folder) {
        this.image = image;
        this.name = name;
        this.estado = date;
        this.cantidad = cantidad;
        this.folder = folder;
        this.id = id;
    }

    public FolderFile(String name, boolean section) {
        this.name = name;
        this.section = section;
    }

}
