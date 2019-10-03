package com.formato.isp;

public class Pregunta {

    public Pregunta(int preguntaId, String preguntaContenido, String preguntaDescripcion, int indicadorId, String contenidoIndicador, int criterio) {
        this.preguntaId = preguntaId;
        this.preguntaContenido = preguntaContenido;
        this.preguntaDescripcion = preguntaDescripcion;
        this.indicadorId = indicadorId;
        this.contenidoIndicador = contenidoIndicador;
        this.criterio = criterio;
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

    private String preguntaDescripcion;
    private int indicadorId;
    private String contenidoIndicador;
    private int criterio;


}
