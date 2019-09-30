package com.formato.isp.model;

import java.util.Date;

public class Empresa {

    public String  empr_nombre;
    public String empr_NIT;
    public Date empr_fechacreacion;
    public Date empr_fechainicio;
    public String empr_direccion;
    public String empr_barrio;
    public String empr_ciudad;
    public String empr_depart;
    public String empr_telefono;
    public String empr_paginaweb;
    public int empr_image;

    public Empresa(String empr_nombre, String empr_NIT, Date empr_fechacreacion, Date empr_fechainicio, String empr_direccion, String empr_barrio, String empr_ciudad, String empr_depart, String empr_telefono, String empr_paginaweb, int empr_image) {
        this.empr_nombre = empr_nombre;
        this.empr_NIT = empr_NIT;
        this.empr_fechacreacion = empr_fechacreacion;
        this.empr_fechainicio = empr_fechainicio;
        this.empr_direccion = empr_direccion;
        this.empr_barrio = empr_barrio;
        this.empr_ciudad = empr_ciudad;
        this.empr_depart = empr_depart;
        this.empr_telefono = empr_telefono;
        this.empr_paginaweb = empr_paginaweb;
        this.empr_image= empr_image;
    }



    public Empresa() {

    }




}
