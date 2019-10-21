package com.formato.isp.DesarrolloEncuesta;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.formato.isp.Clases.Area;
import com.formato.isp.GestionEmpresa.infoDetallada;
import com.formato.isp.R;
import com.formato.isp.resource;
import com.formato.isp.utils.AdapterListFolderFile;
import com.formato.isp.utils.FolderFile;
import com.formato.isp.utils.ItemAnimation;
import com.formato.isp.utils.Tools;
import com.formato.isp.utils.ViewAnimation;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.formato.isp.R.color.colorLetraBlanco;

public class menuEncuesta extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {

    private View parent_view;
    private RecyclerView recyclerView;
    private AdapterListFolderFile mAdapter;
    ArrayList<String> listComponentes;
    private RequestQueue queue;
    ProgressDialog p;
    private int numeroRevision;
    List<FolderFile> items;
    public static List<Area> areasEncuestadas = new ArrayList<>();
    String URI = resource.URLAPI + "/dato";
    RequestQueue requestQueue;
    JsonObjectRequest request;
    private boolean avisoInsertar = false;
    private RelativeLayout layout_nointernetMenu;
    public ProgressBar progress_barMenu;
    private LinearLayout lyt_no_connectionMenu;
    private LinearLayout lyt_internetMenu;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_encuesta);

        progress_barMenu = (ProgressBar) findViewById(R.id.progress_barMenu);
        progress_barMenu.setVisibility(View.VISIBLE);

        layout_nointernetMenu = (RelativeLayout) findViewById(R.id.relative_nointernetMenu);
        if (comprobarConexion()) {
            layout_nointernetMenu.setVisibility(View.GONE);
        } else {
            layout_nointernetMenu.setVisibility(View.VISIBLE);
        }
        lyt_no_connectionMenu = (LinearLayout) findViewById(R.id.lyt_no_connectionMenu);
        lyt_internetMenu = (LinearLayout) findViewById(R.id.lyt_internetMenu);
        lyt_internetMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                progress_barMenu.setVisibility(View.VISIBLE);
                lyt_no_connectionMenu.setVisibility(View.GONE);
                if (comprobarConexion()) {
                    Intent abrirDeNuevo = new Intent(getApplicationContext(), menuEncuesta.class);
                    startActivity(abrirDeNuevo);
                } else {
                    progress_barMenu.setVisibility(View.GONE);
                    lyt_no_connectionMenu.setVisibility(View.VISIBLE);
                }
            }
        });


        parent_view = findViewById(android.R.id.content);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        queue = Volley.newRequestQueue(this);
        requestQueue = Volley.newRequestQueue(this);
        listComponentes = new ArrayList();
        items = new ArrayList<>();

        p = new ProgressDialog(this);
        p.setMessage("Insertando datos de la encuesta");
        p.setCancelable(false);

        initToolbar();
        loadingAndDisplayContent();
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

    public void insertarDato() {
        p.show();
        Map params = new HashMap();
        for (int i = 0; i < infoDetallada.acumuladorPreguntas.size(); i++) {
            params.put("revision_revi_id", infoDetallada.idRevision);
            params.put("indicador_indi_id", infoDetallada.acumuladorPreguntas.get(i).getIndicadorId());
            switch (infoDetallada.acumuladorPreguntas.get(i).getCriterio()) {
                case 1:
                    String valores[] = infoDetallada.acumuladorPreguntas.get(i).getRespuesta().split("-");
                    if (valores[0].equals("") && valores[0].equals("")) {
                        params.put("dato_hombres", 0);
                        params.put("dato_mujeres", 0);
                        params.put("dato_otro", 0);
                    } else {
                        params.put("dato_hombres", valores[0]);
                        params.put("dato_mujeres", valores[1]);
                        params.put("dato_otro", 0);
                    }
                    break;
                case 2:
                case 3:
                case 4:
                case 5:
                    params.put("dato_hombres", 0);
                    params.put("dato_mujeres", 0);
                    params.put("dato_otro", infoDetallada.acumuladorPreguntas.get(i).getRespuesta());
            }
            params.put("dato_puntaje", 30);
            params.put("dato_observ", "NADA");

            if (infoDetallada.acumuladorPreguntas.get(i).getValor() < 26) {
                params.put("tipo_escala_ties_id", 1);
            } else if (infoDetallada.acumuladorPreguntas.get(i).getValor() < 51) {
                params.put("tipo_escala_ties_id", 2);
            } else if (infoDetallada.acumuladorPreguntas.get(i).getValor() < 76) {
                params.put("tipo_escala_ties_id", 3);
            } else {
                params.put("tipo_escala_ties_id", 4);
            }
            params.put("esca_valor", infoDetallada.acumuladorPreguntas.get(i).getValor());
            params.put("esca_observ", "ninguno");

            request = new JsonObjectRequest(Request.Method.POST, URI, new JSONObject(params), this, this);
            requestQueue.add(request);
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.colorPrimary);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(parent_view.getContext(), reporteGrafico.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void loadingAndDisplayContent() {
        recyclerView.setVisibility(View.GONE);

        obtenerComponente();
    }

    private void obtenerComponente() {
        String url = "https://formatoisp-api.herokuapp.com/api/area/?opt=1";
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                try {
                    int numeroComponente = 0;
                    int contador = 0;
                    JSONArray jsonArr = res.getJSONArray("data");
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = jsonArr.getJSONObject(i).getJSONObject("componente");
                        if (jsonObj.getInt("comp_id") != numeroComponente) {
                            numeroComponente = jsonObj.getInt("comp_id");
                            listComponentes.add(numeroComponente + "-" + jsonObj.getString("comp_nombre"));
                        }
                    }
                    obtenerArea(listComponentes, jsonArr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(req);
    }

    private void obtenerArea(ArrayList listComponente, JSONArray jsonComponente) throws JSONException {

        for (int i = 0; i < listComponente.size(); i++) {
            String[] numeroComponente = listComponente.get(i).toString().split("-");
            items.add(new FolderFile(numeroComponente[1], true));  // add section
            for (int j = 0; j < jsonComponente.length(); j++) {
                JSONObject jsonComp = jsonComponente.getJSONObject(j);
                JSONObject jsonObj = jsonComponente.getJSONObject(j).getJSONObject("componente");
                if (Integer.parseInt(numeroComponente[0]) == jsonObj.getInt("comp_id")) {
                    String valor = "Sin iniciar";
                    if (buscarAreaBoolean(jsonComp.getInt("area_id"))) {
                        valor = "Realizada";
                    }
                    items.add(new FolderFile(jsonComp.getInt("area_id"), jsonComp.getString("area_nombre"), valor, jsonComp.getInt("area_logo"), buscar(jsonComp.getInt("area_id")), true));  // add section
                }
            }
        }
        initComponent();
    }

    public int buscar(int areaId) {
        int retorno = 0;
        float auxiliar = 0;
        int valor = 0;
        float promedio = 0;
        for (int i = 0; i < menuEncuesta.areasEncuestadas.size(); i++) {
            if (menuEncuesta.areasEncuestadas.get(i).getAreaId() == areaId) {
                if (menuEncuesta.areasEncuestadas.get(i).getTotalIndicadores() > 0) {
                    valor = 100 / menuEncuesta.areasEncuestadas.get(i).getTotalIndicadores();
                    auxiliar = valor * menuEncuesta.areasEncuestadas.get(i).getAreaAvance();
                    promedio = menuEncuesta.areasEncuestadas.get(i).getPromedioEscala() / menuEncuesta.areasEncuestadas.get(i).getTotalIndicadores();
                    menuEncuesta.areasEncuestadas.get(i).setPromedioEscala(promedio);
                }
            }
        }
        retorno = Math.round(auxiliar);
        return retorno;
    }

    private void initComponent() {

        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        mAdapter = new AdapterListFolderFile(this, items, ItemAnimation.FADE_IN);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new AdapterListFolderFile.OnItemClickListener() {
            @Override
            public void onItemClick(View view, FolderFile obj, int position) {
                if (!buscarAreaBoolean(obj.id)) {
                    Intent abrirEncuesta = new Intent(view.getContext(), preguntasEncuesta.class);
                    abrirEncuesta.putExtra("areaId", obj.id);
                    startActivity(abrirEncuesta);
                } else {
                    Toast.makeText(view.getContext(), "Ésta área ya fue evaluada", Toast.LENGTH_SHORT).show();
                }
            }
        });
        crearBoton();
    }

    public void crearBoton() {
        LinearLayout layout = findViewById(R.id.botonFinalizar_lyt);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        Button but = new Button(this);
        but.setLayoutParams(lp);
        but.setText("FINALIZAR");
        but.setBackground(getDrawable(R.drawable.boton));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            but.setTextColor(getColor(colorLetraBlanco));
        }
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarDato();
            }
        });
        layout.addView(but);
    }


    public static boolean buscarAreaBoolean(int areaId) {
        boolean saber = false;

        for (int i = 0; i < areasEncuestadas.size(); i++) {
            if (areasEncuestadas.get(i).getAreaId() == areaId) {
                saber = true;
            }
        }
        return saber;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        p.hide();
        if (error instanceof NetworkError) {
            Toast.makeText(getApplicationContext(), "Por favor verifica tu conexión a internet", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        p.hide();
        Intent intent = new Intent(getApplicationContext(), cargando.class);
        startActivity(intent);
    }
}
