package com.formato.isp.model;

import java.util.Date;

public class Empresa {

    public String  empr_nombre;
    public String empr_NIT;
    public Date empr_fechacreacion;
    public Date empr_fechainicio;
    public String empr_direccion;

    public String getEmpr_descripcion() {
        return empr_descripcion;
    }

    public void setEmpr_descripcion(String empr_descripcion) {
        this.empr_descripcion = empr_descripcion;
    }

    public String empr_descripcion;
    public String empr_barrio;
    public String empr_ciudad;
    public String empr_depart;
    public String empr_logo;

    public String getEmpr_nombre() {
        return empr_nombre;
    }

    public void setEmpr_nombre(String empr_nombre) {
        this.empr_nombre = empr_nombre;
    }

    public String getEmpr_NIT() {
        return empr_NIT;
    }

    public void setEmpr_NIT(String empr_NIT) {
        this.empr_NIT = empr_NIT;
    }

    public Date getEmpr_fechacreacion() {
        return empr_fechacreacion;
    }

    public void setEmpr_fechacreacion(Date empr_fechacreacion) {
        this.empr_fechacreacion = empr_fechacreacion;
    }

    public Date getEmpr_fechainicio() {
        return empr_fechainicio;
    }

    public void setEmpr_fechainicio(Date empr_fechainicio) {
        this.empr_fechainicio = empr_fechainicio;
    }

    public String getEmpr_direccion() {
        return empr_direccion;
    }

    public void setEmpr_direccion(String empr_direccion) {
        this.empr_direccion = empr_direccion;
    }

    public String getEmpr_barrio() {
        return empr_barrio;
    }

    public void setEmpr_barrio(String empr_barrio) {
        this.empr_barrio = empr_barrio;
    }

    public String getEmpr_ciudad() {
        return empr_ciudad;
    }

    public void setEmpr_ciudad(String empr_ciudad) {
        this.empr_ciudad = empr_ciudad;
    }

    public String getEmpr_depart() {
        return empr_depart;
    }

    public void setEmpr_depart(String empr_depart) {
        this.empr_depart = empr_depart;
    }

    public String getEmpr_telefono() {
        return empr_telefono;
    }

    public void setEmpr_telefono(String empr_telefono) {
        this.empr_telefono = empr_telefono;
    }

    public String getEmpr_paginaweb() {
        return empr_paginaweb;
    }

    public void setEmpr_paginaweb(String empr_paginaweb) {
        this.empr_paginaweb = empr_paginaweb;
    }

    public int getEmpr_image() {
        return empr_image;
    }

    public void setEmpr_image(int empr_image) {
        this.empr_image = empr_image;
    }

    public String getEmpr_logo() {
        return empr_logo;
    }

    public void setEmpr_logo(String empr_logo) {
        this.empr_logo = empr_logo;
    }

    public String empr_telefono;
    public String empr_paginaweb;
    public int empr_image;

    public Empresa(String empr_nombre, String empr_NIT, Date empr_fechacreacion, Date empr_fechainicio, String empr_direccion, String empr_barrio, String empr_ciudad, String empr_depart, String empr_telefono, String empr_paginaweb, int empr_image, String empr_logo, String desc) {
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
        this.empr_logo = empr_logo;
        this.empr_descripcion=desc;
    }



    public Empresa() {

    }




}
