package com.formato.isp.DesarrolloEncuesta;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.formato.isp.Clases.Area;
import com.formato.isp.R;
import com.formato.isp.utils.ViewAnimation;
import com.formato.isp.MenuLateral.*;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import com.formato.isp.utils.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.formato.isp.R.color.colorLetraBlanco;


public class cargando extends AppCompatActivity {

    private View parent_view;
    private final static int LOADING_DURATION = 2000;
    private RecyclerView recyclerView;
    private AdapterListFolderFile mAdapter;
    private AdapterListFolderFile mAdapterBoton;
    Area areaRecibida;
    ArrayList<String> listComponentes;
    private RequestQueue queue;
    List<FolderFile> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargando);

        parent_view = findViewById(android.R.id.content);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        queue = Volley.newRequestQueue(this);
        listComponentes = new ArrayList();
        items = new ArrayList<>();

        initToolbar();
        loadingAndDisplayContent();
    }

    public int buscarArea(int areaId){
        int retorno = 0;
        int valor = 0;
        float promedio = 0;
        for (int i = 0; i < menuEncuesta.areasEncuestadas.size(); i++){
            if(menuEncuesta.areasEncuestadas.get(i).getAreaId() == areaId){
                valor = 100 / menuEncuesta.areasEncuestadas.get(i).getTotalIndicadores();
                retorno = valor * menuEncuesta.areasEncuestadas.get(i).getAreaAvance();
                promedio = menuEncuesta.areasEncuestadas.get(i).getPromedioEscala() / menuEncuesta.areasEncuestadas.get(i).getTotalIndicadores();
                menuEncuesta.areasEncuestadas.get(i).setPromedioEscala(promedio);
            }
        }
        return retorno;
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Visualización de porcentajes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.colorPrimary);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        Tools.changeMenuIconColor(menu, getResources().getColor(R.color.colorPrimary));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(), menuprincipal.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(parent_view.getContext(), reporteGrafico.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadingAndDisplayContent() {
        final LinearLayout lyt_progress = (LinearLayout) findViewById(R.id.lyt_progress);
        lyt_progress.setVisibility(View.VISIBLE);
        lyt_progress.setAlpha(1.0f);
        recyclerView.setVisibility(View.GONE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewAnimation.fadeOut(lyt_progress);
            }
        }, LOADING_DURATION);

        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                obtenerComponente();
            }
        }, LOADING_DURATION + 400);

        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                crearBoton();
            }
        }, LOADING_DURATION + 900);
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
                    String valor = "No fue realizada";
                    if(menuEncuesta.buscarAreaBoolean(jsonComp.getInt("area_id"))){
                        valor = "Realizada";
                    }
                    items.add(new FolderFile(jsonComp.getInt("area_id"),jsonComp.getString("area_nombre"), valor, jsonComp.getInt("area_logo"), buscarArea(jsonComp.getInt("area_id")), true));  // add section

                }
            }
        }
        initComponent();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initComponent() {
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //set data and list adapter
        mAdapter = new AdapterListFolderFile(this, items, ItemAnimation.FADE_IN);
        recyclerView.setAdapter(mAdapter);

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterListFolderFile.OnItemClickListener() {
            @Override
            public void onItemClick(View view, FolderFile obj, int position) {
                Snackbar.make(parent_view, "Item " + obj.name + " clicked", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void crearBoton() {
        LinearLayout layout = findViewById(R.id.lyt_boton);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        Button but = new Button(this);
        but.setLayoutParams(lp);
        but.setText("RUTA DE FORTALECIMIENTO");
        but.setBackground(getDrawable(R.drawable.boton));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            but.setTextColor(getColor(colorLetraBlanco));
        }
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abrirMapa = new Intent(view.getContext(), rutaFortalecimiento.class);
                startActivity(abrirMapa);
            }
        });
        layout.addView(but);
    }
}
