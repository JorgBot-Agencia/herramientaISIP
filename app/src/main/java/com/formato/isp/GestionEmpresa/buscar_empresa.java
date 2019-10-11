package com.formato.isp.GestionEmpresa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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
import java.util.Iterator;


public class buscar_empresa extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject>{

    ArrayList<datosEmpresa> dato;
    ImageView img;
    private Context ctx;
    private Context ctx2;
    private RequestQueue queue;
    private JsonRequest req;
    private ListView lvItems;
    private Adaptador adaptador;
    private EditText buscarEmp;
    private ImageButton btnBuscarEmpr;
    private ImageButton btnRefrescar;
    ArrayList<datosEmpresa> listafiltro;
    ArrayList<datosEmpresa> listafiltro2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_empresa);

        queue = Volley.newRequestQueue(this);
        lvItems = (ListView)findViewById(R.id.lv_items);
        initToolbar();
        img = findViewById(R.id.image_1);
        queue = Volley.newRequestQueue(this);
        buscarEmp= findViewById(R.id.et_search);
        buscarEmp.clearFocus();
        btnBuscarEmpr= findViewById(R.id.btnbuscarEmpr);
        btnRefrescar= findViewById(R.id.btnrefres);
        lvItems = (ListView)findViewById(R.id.lv_items);
        listafiltro= new ArrayList<>();
        listafiltro2= new ArrayList<>();
        btnRefrescar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adaptador = new Adaptador(ctx2, listafiltro2);
                lvItems.setAdapter(adaptador);
            }
        });
        btnBuscarEmpr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String datobusc = (String) buscarEmp.getText().toString();
                if(datobusc!=null) {
                    for (Iterator<datosEmpresa> it = listafiltro.iterator(); it.hasNext(); ) {
                        if (!it.next().getNombre().contains(datobusc))
                            it.remove(); // NOTE: Iterator's remove method, not ArrayList's, is used.
                    }
                    adaptador = new Adaptador(ctx, listafiltro);
                    lvItems.setAdapter(adaptador);
                }else{

                }
            }
        });

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
                dato.add(new datosEmpresa(id_empr, nombre,nit,barrio + ", " + ciudad, dep, tel, sitioweb, fecha_crea, fecha_ini));
            }
            listafiltro=dato;
            listafiltro2=dato;
            adaptador = new Adaptador(this, dato);
            ctx=this;
            ctx2=this;
            lvItems.setAdapter(adaptador);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
