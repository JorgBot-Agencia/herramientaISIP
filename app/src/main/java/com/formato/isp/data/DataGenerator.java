package com.formato.isp.data;

import android.content.Context;
import android.content.res.TypedArray;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.formato.isp.R;
import com.formato.isp.model.Empresa;
import com.formato.isp.model.People;
import com.formato.isp.utils.Tools;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import java.util.Random;

@SuppressWarnings("ResourceType")
public class DataGenerator {
    private static List<Empresa> itemsfinal;
    private static List<Empresa> lista;
    public static String nombreprueba = "";
    public static RequestQueue requestQueue;

    private static Random r = new Random();

    public static int randInt(int max) {
        int min = 0;
        return r.nextInt((max - min) + 1) + min;
    }

    public static List<String> getStringsShort(Context ctx) {
        List<String> items = new ArrayList<>();
        String name_arr[] = ctx.getResources().getStringArray(R.array.strings_short);
        for (String s : name_arr) items.add(s);
        Collections.shuffle(items);
        return items;
    }


    public static List<People> getPeopleData(Context ctx) {
        List<People> items = new ArrayList<>();
        TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.people_images);
        String name_arr[] = ctx.getResources().getStringArray(R.array.people_names);

        for (int i = 0; i < drw_arr.length(); i++) {
            People obj = new People();
            obj.image = drw_arr.getResourceId(i, -1);
            obj.name = name_arr[i];
            obj.email = Tools.getEmailFromName(obj.name);
            obj.imageDrw = ctx.getResources().getDrawable(obj.image);
            items.add(obj);
        }
        Collections.shuffle(items);
        return items;
    }

    public static List<Empresa> getEmpresa(Context ctx) {
        String s = "";//obtenerEmpresas();
        Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();
        List<Empresa> items = lista;;
        for (int i = 0; i < lista.size(); i++) {
            Empresa obj = new Empresa();
            obj.empr_nombre = lista.get(i).empr_nombre;
            obj.empr_direccion = String.valueOf(lista.get(i).empr_NIT);
            items.add(obj);
        }


        Collections.shuffle(items);
        return items;
    }


}



