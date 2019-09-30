package com.formato.isp;

public class Pregunta {

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

    private int preguntaId;

    public Pregunta(int preguntaId, String preguntaContenido, String preguntaDescripcion) {
        this.preguntaId = preguntaId;
        this.preguntaContenido = preguntaContenido;
        this.preguntaDescripcion = preguntaDescripcion;
    }

    private String preguntaContenido;
    private String preguntaDescripcion;


}
