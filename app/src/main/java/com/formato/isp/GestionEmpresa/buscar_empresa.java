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
import com.formato.isp.R;
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
        //Toast.makeText(this,"Metodo obtener empresas", Toast.LENGTH_SHORT).show();
        String url = "https://formatoisp-api.herokuapp.com/api/empresa";
        req = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        queue.add(req);
    }
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbarListEmp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.colorSecondary);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        //Toast.makeText(this,"ERROOOOOOOOOOOOOOOOOR", Toast.LENGTH_SHORT).show();
        if (error instanceof NetworkError) {
            Toast.makeText(this, "Por favor verifica tu conexión a internet", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(JSONObject response) {

        //Toast.makeText(this,"DATOS", Toast.LENGTH_SHORT).show();
        try {
            datosEmpresa d = new datosEmpresa();
            dato = new ArrayList<datosEmpresa>();
            JSONArray jsonArr = response.getJSONArray("data");
            JSONObject jsonObj = null;
            String barrio = "" ;
            String ciudad = "" ;
            String nombre = "" ;
            String nit = "" ;
            for (int i = 0; i < jsonArr.length(); i++) {
                jsonObj = jsonArr.getJSONObject(i);
                barrio = jsonObj.getString("empr_barrio");
                nombre = jsonObj.getString("empr_nombre");
                nit = jsonObj.getString("empr_nit");
                ciudad = jsonObj.getString("empr_ciudad");
                dato.add(new datosEmpresa(nombre,nit,barrio + ", " + ciudad));
            }
            adaptador = new Adaptador(this, dato);
            lvItems.setAdapter(adaptador);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
