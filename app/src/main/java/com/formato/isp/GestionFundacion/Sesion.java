package com.formato.isp.GestionFundacion;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Sesion{
    private SharedPreferences sesion;

    public Sesion(Context cntx) {
        // TODO Auto-generated constructor stub
        sesion = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setUsername(String username) {
        sesion.edit().putString("username", username).commit();
    }

    public String getUsername() {
        String username = sesion.getString("username","");
        return username;
    }

    public void setContrasena(String contrasena) {
        sesion.edit().putString("contrasena", contrasena).commit();
    }

    public String getContrasena() {
        String contrasena = sesion.getString("contrasena","");
        return contrasena;
    }

    public void setNitFun(String nitfun) {
        sesion.edit().putString("nitfun", nitfun).commit();
    }

    public String getNitFun() {
        String nitfun = sesion.getString("nitfun","");
        return nitfun;
    }

    public void setNombreFun(String nomfun) {
        sesion.edit().putString("nomfun", nomfun).commit();
    }

    public String getNombreFun() {
        String nomfun = sesion.getString("nomfun","");
        return nomfun;
    }

    public void setDireccion(String dirfun) {
        sesion.edit().putString("dirfun", dirfun).commit();
    }

    public String getDireccion() {
        String dirfun = sesion.getString("dirfun","");
        return dirfun;
    }

    public void setTelefono(String telfun) {
        sesion.edit().putString("telfun", telfun).commit();
    }

    public String getTelefono() {
        String telfun = sesion.getString("telfun","");
        return telfun;
    }

    public void setLogo(String logofun) {
        sesion.edit().putString("logofun", logofun).commit();
    }

    public String getLogo() {
        String logofun = sesion.getString("logofun","");
        return logofun;
    }
}












