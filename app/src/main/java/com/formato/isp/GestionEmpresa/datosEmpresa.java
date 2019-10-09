package com.formato.isp.GestionEmpresa;

public class datosEmpresa {


    private String nombre;
    private String nit;
    private String ubicacion;
    private String departamento;
    private String telefono;
    private String sitioweb;
    private String fecha_creacion;
    private String fecha_inicio;


    public datosEmpresa(){
    }

    public datosEmpresa(String nombre, String nit, String ubicacion, String departamento, String telefono, String sitioweb, String fecha_creacion, String fecha_inicio) {
        this.nombre = nombre;
        this.nit = nit;
        this.ubicacion = ubicacion;
        this.departamento = departamento;
        this.telefono = telefono;
        this.sitioweb = sitioweb;
        this.fecha_creacion = fecha_creacion;
        this.fecha_inicio = fecha_inicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getSitioweb() {
        return sitioweb;
    }

    public void setSitioweb(String sitioweb) {
        this.sitioweb = sitioweb;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }
}
