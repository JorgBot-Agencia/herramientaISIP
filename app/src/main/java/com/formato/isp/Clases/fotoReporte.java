package com.formato.isp.Clases;

import android.graphics.Bitmap;

public class fotoReporte {

    private Bitmap bitmabEmpr;

    public Bitmap getBitmabEmpr() {
        return bitmabEmpr;
    }

    public void setBitmabEmpr(Bitmap bitmabEmpr) {
        this.bitmabEmpr = bitmabEmpr;
    }

    public String getNomb_empr() {
        return nomb_empr;
    }

    public void setNomb_empr(String nomb_empr) {
        this.nomb_empr = nomb_empr;
    }

    private String nomb_empr;

    public fotoReporte(Bitmap bitmabEmpr, String nomb_empr) {
        this.bitmabEmpr = bitmabEmpr;
        this.nomb_empr = nomb_empr;
    }


}
