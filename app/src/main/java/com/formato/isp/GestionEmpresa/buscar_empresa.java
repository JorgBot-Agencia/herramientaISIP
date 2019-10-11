package com.formato.isp.GestionEmpresa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.formato.isp.GestionFundacion.Sesion;
import com.formato.isp.R;
import com.formato.isp.resource;
import com.formato.isp.utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class buscar_empresa extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject>{

    ArrayList<datosEmpresa> dato;
    ImageView img;
    private RequestQueue queue;
    private JsonRequest req;
    private ListView lvItems;
    private Adaptador adaptador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_empresa);

        queue = Volley.newRequestQueue(this);
        lvItems = (ListView)findViewById(R.id.lv_items);
        initToolbar();
        img = findViewById(R.id.image_1);
        queue = Volley.newRequestQueue(this);



        lvItems = (ListView)findViewById(R.id.lv_items);

        obtenerEmpresas();

        initToolbar();


    }

    private void obtenerEmpresas() {
        Sesion session;
        session = new Sesion(getApplicationContext());
        String id = session.getIdFun();
        String url = resource.URLAPI + "/empresa/?fund_id=" + id;
        req = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        queue.add(req);
    }
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbarListEmp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.colorPrimary);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (error instanceof NetworkError) {
            Toast.makeText(this, "Por favor verifica tu conexión a internet", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(JSONObject response) {

        try {
            datosEmpresa d = new datosEmpresa();
            dato = new ArrayList<datosEmpresa>();
            JSONArray jsonArr = response.getJSONArray("data");
            JSONObject jsonObj = null;
            String id_empr = "";
            String barrio = "" ;
            String ciudad = "" ;
            String nombre = "" ;
            String nit = "" ;
            String dep = "";
            String tel = "";
            String sitioweb = "";
            String fecha_ini = "";
            String fecha_crea = "";
            String logo = "";
            for (int i = 0; i < jsonArr.length(); i++) {
                jsonObj = jsonArr.getJSONObject(i);
                id_empr = jsonObj.getString("empr_id");
                barrio = jsonObj.getString("empr_barrio");
                nombre = jsonObj.getString("empr_nombre");
                nit = jsonObj.getString("empr_nit");
                ciudad = jsonObj.getString("empr_ciudad");
                dep = jsonObj.getString("empr_depart");
                tel = jsonObj.getString("empr_telefono");
                sitioweb = jsonObj.getString("empr_paginaweb");
                fecha_crea = jsonObj.getString("empr_fechacreacion");
                fecha_ini = jsonObj.getString("empr_fechainicio");
                logo = jsonObj.getString("empr_logo");
                dato.add(new datosEmpresa(id_empr, nombre,nit,barrio + ", " + ciudad, dep, tel, sitioweb, fecha_crea, fecha_ini, logo));
            }
            adaptador = new Adaptador(this, dato);
            lvItems.setAdapter(adaptador);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
