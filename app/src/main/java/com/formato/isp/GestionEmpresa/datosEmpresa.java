package com.formato.isp.GestionEmpresa;

public class datosEmpresa {


    private String nombre;
    private String nit;
    private String ubicacion;


    public datosEmpresa(){
    }

    public datosEmpresa( String nombre, String nit, String ubicacion) {
        this.nombre = nombre;
        this.nit = nit;
        this.ubicacion = ubicacion;
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
}
