package com.formato.isp.GestionEmpresa;

public class datosEmpresa {

    private String id;
    private String nombre;
    private String nit;
    private String ubicacion;
    private String departamento;
    private String telefono;
    private String sitioweb;
    private String descripcion;
    private String fecha_creacion;
    private String fecha_inicio;
    private  String logo;


    public datosEmpresa(){
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public datosEmpresa(String id, String nombre, String nit, String ubicacion, String departamento, String telefono, String sitioweb, String fecha_creacion, String fecha_inicio, String logo, String des) {
        this.id = id;
        this.nombre = nombre;
        this.nit = nit;
        this.ubicacion = ubicacion;
        this.departamento = departamento;
        this.telefono = telefono;
        this.sitioweb = sitioweb;
        this.fecha_creacion = fecha_creacion;
        this.fecha_inicio = fecha_inicio;
        this.logo = logo;
        this.descripcion=des;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
