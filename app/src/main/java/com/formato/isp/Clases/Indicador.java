package com.formato.isp.Clases;

public class Indicador {
    private int idIndicador;

    public int getIdIndicador() {
        return idIndicador;
    }

    public void setIdIndicador(int idIndicador) {
        this.idIndicador = idIndicador;
    }

    public String getContenidoIndicador() {
        return contenidoIndicador;
    }

    public void setContenidoIndicador(String contenidoIndicador) {
        this.contenidoIndicador = contenidoIndicador;
    }

    public int getCriterio() {
        return criterio;
    }

    public void setCriterio(int criterio) {
        this.criterio = criterio;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    private String contenidoIndicador;
    private int criterio;
    private float valor;

    public Indicador(int idIndicador, String contenidoIndicador, int criterio, float valor) {
        this.idIndicador = idIndicador;
        this.contenidoIndicador = contenidoIndicador;
        this.criterio = criterio;
        this.valor = valor;
    }


}
