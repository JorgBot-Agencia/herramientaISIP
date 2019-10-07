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

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarNit(lista.get(position).getNit().toString(),lista.get(position).getNombre().toString(), lista.get(position).getUbicacion().toString());
                Intent abrirInfo = new Intent(v.getContext(), infoDetallada.class);
                contexto.startActivity(abrirInfo);
            }
        });


        return convertView;
    }

    public void guardarNit(String nit, String nom, String ubi){
        SharedPreferences pref = contexto.getSharedPreferences("nitEmpresa", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("NIT");
        editor.remove("NOMBRE");
        editor.remove("UBICACION");
        editor.putString("NIT",nit);
        editor.putString("NOMBRE",nom);
        editor.putString("UBICACION",ubi);
        editor.apply();
    }
}