package com.formato.isp.GestionEmpresa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.formato.isp.MenuLateral.menuprincipal;
import com.formato.isp.R;
import com.formato.isp.noItemInternetIcon;
import com.formato.isp.resource;
import com.formato.isp.utils.Tools;
import com.formato.isp.utils.ViewAnimation;
import com.github.mikephil.charting.charts.RadarChart;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.itextpdf.text.pdf.parser.Line;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


public class buscar_empresa extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {

    ArrayList<datosEmpresa> dato;
    ImageView img;
    private Context ctx;
    private Context ctx2;
    private RequestQueue queue;
    private JsonRequest req;
    private ListView lvItems;
    private Adaptador adaptador;
    private EditText buscarEmp;
    private boolean rotate = false;
    private TextView irInicio;
    private View lyt_mic;
    private View back_drop;
    private ImageButton btnBuscarEmpr;
    private ImageButton btnRefrescar;
    ArrayList<datosEmpresa> listafiltro;
    ArrayList<datosEmpresa> listafiltro2;
    private RelativeLayout layout_nointernet;
    public ProgressBar progress_bar;
    private LinearLayout lyt_no_connection;
    private LinearLayout lyt_internet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_empresa);

        initToolbar();
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        progress_bar.setVisibility(View.VISIBLE);
        layout_nointernet = (RelativeLayout) findViewById(R.id.relative_nointernet);
        if (comprobarConexion()) {
            layout_nointernet.setVisibility(View.GONE);
        }else{
            layout_nointernet.setVisibility(View.VISIBLE);
        }
        lyt_no_connection = (LinearLayout) findViewById(R.id.lyt_no_connection);
        lyt_internet = (LinearLayout) findViewById(R.id.lyt_internet);
        lyt_internet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                progress_bar.setVisibility(View.VISIBLE);
                lyt_no_connection.setVisibility(View.GONE);
                if (comprobarConexion()) {
                    Intent abrirDeNuevo = new Intent(getApplicationContext(), buscar_empresa.class);
                    startActivity(abrirDeNuevo);
                } else {
                    progress_bar.setVisibility(View.GONE);
                    lyt_no_connection.setVisibility(View.VISIBLE);
                }
            }
        });
        back_drop = findViewById(R.id.back_drop);
        final FloatingActionButton fab_mic = (FloatingActionButton) findViewById(R.id.fab_mic);
        final FloatingActionButton fab_add = (FloatingActionButton) findViewById(R.id.fab_add);
        lyt_mic = findViewById(R.id.lyt_mic);
        ViewAnimation.initShowOut(lyt_mic);
        back_drop.setVisibility(View.GONE);

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFabMode(v);
            }
        });
        fab_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), menuprincipal.class);
                startActivity(intent);
            }
        });
        irInicio = findViewById(R.id.volver);
        irInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), menuprincipal.class);
                startActivity(intent);
            }
        });

        queue = Volley.newRequestQueue(this);
        lvItems = (ListView) findViewById(R.id.lv_items);
        initToolbar();
        img = findViewById(R.id.image_1);
        queue = Volley.newRequestQueue(this);
        buscarEmp = findViewById(R.id.et_search);
        buscarEmp.clearFocus();
        btnBuscarEmpr = findViewById(R.id.btnbuscarEmpr);
        btnRefrescar = findViewById(R.id.btnrefres);
        lvItems = (ListView) findViewById(R.id.lv_items);
        listafiltro = new ArrayList<>();
        listafiltro2 = new ArrayList<>();
        btnRefrescar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarEmp.setText("");
                finish();
                startActivity(getIntent());
            }
        });
        btnBuscarEmpr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String datobusc = (String) buscarEmp.getText().toString();
                if (datobusc != null) {
                    for (Iterator<datosEmpresa> it = listafiltro.iterator(); it.hasNext(); ) {
                        if (!it.next().getNombre().contains(datobusc))
                            it.remove(); // NOTE: Iterator's remove method, not ArrayList's, is used.
                    }
                    adaptador = new Adaptador(ctx, listafiltro);
                    lvItems.setAdapter(adaptador);
                } else {

                }
            }
        });

        obtenerEmpresas();
    }

    private void toggleFabMode(View v) {
        rotate = ViewAnimation.rotateFab(v, !rotate);
        if (rotate) {
            ViewAnimation.showIn(lyt_mic);
            back_drop.setVisibility(View.VISIBLE);
        } else {
            ViewAnimation.showOut(lyt_mic);
            back_drop.setVisibility(View.GONE);
        }
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
        Toolbar toolbar = findViewById(R.id.toolbar_buscar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.colorPrimary);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (error instanceof NetworkError) {
            progress_bar.setVisibility(View.GONE);
            layout_nointernet.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean comprobarConexion() {
        boolean connected = false;

        ConnectivityManager connec = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] redes = connec.getAllNetworkInfo();
        for (int i = 0; i < redes.length; i++) {
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                connected = true;
            }
        }
        return connected;
    }
    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"Utilice las opciones proporcionadas por la aplicación", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onResponse(JSONObject response) {

        try {
            datosEmpresa d = new datosEmpresa();
            dato = new ArrayList<datosEmpresa>();
            JSONArray jsonArr = response.getJSONArray("data");
            JSONObject jsonObj = null;
            String id_empr = "";
            String barrio = "";
            String ciudad = "";
            String nombre = "";
            String nit = "";
            String dep = "";
            String tel = "";
            String sitioweb = "";
            String fecha_ini = "";
            String fecha_crea = "";
            String logo = "";
            String desc = "";
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
                desc = jsonObj.getString("empr_descripcion");
                dato.add(new datosEmpresa(id_empr, nombre, nit, barrio + ", " + ciudad, dep, tel, sitioweb, fecha_crea, fecha_ini, logo,desc));
            }
            listafiltro = dato;
            adaptador = new Adaptador(this, dato);
            ctx = this;
            lvItems.setAdapter(adaptador);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
