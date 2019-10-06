package com.formato.isp;

public class Pregunta {
    public Pregunta(int preguntaId, String preguntaContenido, String preguntaDescripcion, int indicadorId, String indicadorContenido, int criterio) {
        this.preguntaId = preguntaId;
        this.preguntaContenido = preguntaContenido;
        this.preguntaDescripcion = preguntaDescripcion;
        this.indicadorId = indicadorId;
        this.indicadorContenido = indicadorContenido;
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

    private String preguntaDescripcion;
    private int indicadorId;
    private String indicadorContenido;
    private int criterio;
}
