package com.formato.isp.GestionEmpresa;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.formato.isp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import android.content.SharedPreferences;

public class Adaptador extends BaseAdapter {

    private Context contexto;
    private ArrayList<datosEmpresa> lista;


    public Adaptador(Context contexto, ArrayList<datosEmpresa> lista) {
        this.contexto = contexto;
        this.lista = lista;
    }


    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {



        convertView = LayoutInflater.from(contexto).inflate(R.layout.include_store_simple, null);
        TextView Nombre = (TextView) convertView.findViewById(R.id.nombre);
        TextView Nit = (TextView) convertView.findViewById(R.id.nit);
        TextView Ubicacion = (TextView) convertView.findViewById(R.id.ubicacion);
        ImageView logo = (ImageView) convertView.findViewById(R.id.image_1);

        Nombre.setText(lista.get(position).getNombre());
        Nit.setText(lista.get(position).getNit());
        Ubicacion.setText(lista.get(position).getUbicacion());
        String ruta = "https://cdn.pixabay.com/photo/2016/09/02/18/38/architecture-1639990_960_720.jpg";
        Picasso.with(convertView.getContext()).load(ruta).into(logo);

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarNit(lista.get(position).getId(), lista.get(position).getNit().toString(),lista.get(position).getNombre().toString(), lista.get(position).getUbicacion().toString(),
                        lista.get(position).getDepartamento(), lista.get(position).getTelefono(), lista.get(position).getSitioweb(),
                        lista.get(position).getFecha_creacion(), lista.get(position).getFecha_inicio());
                Intent abrirInfo = new Intent(v.getContext(), infoDetallada.class);
                contexto.startActivity(abrirInfo);
            }
        });


        return convertView;
    }

    public void guardarNit(String id, String nit, String nom, String ubi, String dep, String tel, String sitioweb, String f_crea, String f_ini){
        SharedPreferences pref = contexto.getSharedPreferences("nitEmpresa", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("ID");
        editor.remove("NIT");
        editor.remove("NOMBRE");
        editor.remove("UBICACION");
        editor.remove("TELEFONO");
        editor.remove("DEPARTAMENTO");
        editor.remove("SITIOWEB");
        editor.remove("F_CREA");
        editor.remove("F_INI");
        editor.putString("ID", id);
        editor.putString("NIT",nit);
        editor.putString("NOMBRE",nom);
        editor.putString("UBICACION",ubi);
        editor.putString("TELEFONO", tel);
        editor.putString("DEPARTAMENTO",dep);
        editor.putString("SITIOWEB",sitioweb);
        editor.putString("F_CREA",f_crea);
        editor.putString("F_INI",f_ini);
        editor.apply();
    }
}