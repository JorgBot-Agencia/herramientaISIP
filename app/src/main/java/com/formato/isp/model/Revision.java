package com.formato.isp.model;

import java.sql.Timestamp;

public class Revision {


    public Revision(String empresa_empr_nit, String revi_id, String revi_descripcion, String revi_fechainicio, String revi_fechafinal, int revi_prioridad, boolean revi_estado) {
        this.empresa_empr_nit = empresa_empr_nit;
        this.revi_id = revi_id;
        this.revi_descripcion = revi_descripcion;
        this.revi_fechainicio = revi_fechainicio;
        this.revi_fechafinal = revi_fechafinal;
        this.revi_prioridad = revi_prioridad;
        this.revi_estado = revi_estado;
    }

    private String empresa_empr_nit;
    private String revi_id;
    private String revi_descripcion;
    private String revi_fechainicio;
    private String revi_fechafinal;
    private int revi_prioridad;
    private boolean revi_estado;

    public String getEmpresa_empr_nit() {
        return empresa_empr_nit;
    }

    public void setEmpresa_empr_nit(String empresa_empr_nit) {
        this.empresa_empr_nit = empresa_empr_nit;
    }

    public String getRevi_id() {
        return revi_id;
    }

    public void setRevi_id(String revi_id) {
        this.revi_id = revi_id;
    }

    public String getRevi_descripcion() {
        return revi_descripcion;
    }

    public void setRevi_descripcion(String revi_descripcion) {
        this.revi_descripcion = revi_descripcion;
    }

    public String getRevi_fechainicio() {
        return revi_fechainicio;
    }

    public void setRevi_fechainicio(String revi_fechainicio) {
        this.revi_fechainicio = revi_fechainicio;
    }

    public String getRevi_fechafinal() {
        return revi_fechafinal;
    }

    public void setRevi_fechafinal(String revi_fechafinal) {
        this.revi_fechafinal = revi_fechafinal;
    }

    public int getRevi_prioridad() {
        return revi_prioridad;
    }

    public void setRevi_prioridad(int revi_prioridad) {
        this.revi_prioridad = revi_prioridad;
    }

    public boolean isRevi_estado() {
        return revi_estado;
    }

    public void setRevi_estado(boolean revi_estado) {
        this.revi_estado = revi_estado;
    }







    public Revision() {

    }


}
