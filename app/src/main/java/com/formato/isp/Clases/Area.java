package com.formato.isp.Clases;

import android.os.Parcelable;

import java.io.Serializable;

public class Area implements Serializable {

    public Area(int areaId, int totalIndicadores, int areaAvance) {
        this.areaId = areaId;
        this.totalIndicadores = totalIndicadores;
        this.areaAvance = areaAvance;
    }

    private int areaId;

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

    private int totalIndicadores;
    private int areaAvance;

}
