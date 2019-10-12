package com.formato.isp;

public class Pregunta {

    public Pregunta(int preguntaId, String preguntaContenido, String preguntaDescripcion, int indicadorId, String indicadorContenido, int criterio, int areaId, float valor) {
        this.preguntaId = preguntaId;
        this.preguntaContenido = preguntaContenido;
        this.preguntaDescripcion = preguntaDescripcion;
        this.indicadorId = indicadorId;
        this.indicadorContenido = indicadorContenido;
        this.criterio = criterio;
        this.areaId = areaId;
        this.valor = valor;
    }

    private int preguntaId;
    private String preguntaContenido;

    public int getPreguntaId() {
        return preguntaId;
    }

    public void setPreguntaId(int preguntaId) {
        this.preguntaId = preguntaId;
    }

    public String getPreguntaContenido() {
        return preguntaContenido;
    }

    public void setPreguntaContenido(String preguntaContenido) {
        this.preguntaContenido = preguntaContenido;
    }

    public String getPreguntaDescripcion() {
        return preguntaDescripcion;
    }

    public void setPreguntaDescripcion(String preguntaDescripcion) {
        this.preguntaDescripcion = preguntaDescripcion;
    }

    public int getIndicadorId() {
        return indicadorId;
    }

    public void setIndicadorId(int indicadorId) {
        this.indicadorId = indicadorId;
    }

    public String getIndicadorContenido() {
        return indicadorContenido;
    }

    public void setIndicadorContenido(String indicadorContenido) {
        this.indicadorContenido = indicadorContenido;
    }

    public int getCriterio() {
        return criterio;
    }

    public void setCriterio(int criterio) {
        this.criterio = criterio;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    private String preguntaDescripcion;
    private int indicadorId;
    private String indicadorContenido;
    private int criterio;
    private int areaId;
    private float valor;
}
