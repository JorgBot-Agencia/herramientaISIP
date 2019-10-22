package com.formato.isp.Clases;

import java.util.ArrayList;
import java.util.List;

public class Pregunta {

    public int id;
    public String contenido;
    public String descripcion;
    public boolean section = false;
    public static List<Indicador> listaIndicadores = new ArrayList<>();

    public Pregunta() {
    }

    public Pregunta(int id, String contenido, String descripcion) {
        this.id = id;
        this.contenido = contenido;
        this.descripcion = descripcion;
    }

    public int getId(){
        return id;
    }
    public String getContenido(){
        return contenido;
    }
    public String getDescripcion(){
        return descripcion;
    }

}
