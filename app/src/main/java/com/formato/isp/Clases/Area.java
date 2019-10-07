package com.formato.isp.Clases;

import android.os.Parcelable;

import java.io.Serializable;

public class Area implements Serializable {

    public Area(int areaId, int totalIndicadores, int areaAvance, float promedioEscala) {
        this.areaId = areaId;
        this.totalIndicadores = totalIndicadores;
        this.areaAvance = areaAvance;
        this.promedioEscala = promedioEscala;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public int getTotalIndicadores() {
        return totalIndicadores;
    }

    public void setTotalIndicadores(int totalIndicadores) {
        this.totalIndicadores = totalIndicadores;
    }

    public int getAreaAvance() {
        return areaAvance;
    }

    public void setAreaAvance(int areaAvance) {
        this.areaAvance = areaAvance;
    }

    public float getPromedioEscala() {
        return promedioEscala;
    }

    public void setPromedioEscala(float promedioEscala) {
        this.promedioEscala = promedioEscala;
    }

    private int areaId;
    private int totalIndicadores;
    private int areaAvance;
    private float promedioEscala;

}
