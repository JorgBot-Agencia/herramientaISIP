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
import java.util.List;

import static com.formato.isp.R.color.colorLetraBlanco;

public class menuEncuesta extends AppCompatActivity {

    private View parent_view;
    private RecyclerView recyclerView;
    private AdapterListFolderFile mAdapter;
    ArrayList<String> listComponentes;
    private RequestQueue queue;
    List<FolderFile> items;
    public static List<Area> areasEncuestadas = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_encuesta);

        parent_view = findViewById(android.R.id.content);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        queue = Volley.newRequestQueue(this);
        listComponentes = new ArrayList();
        items = new ArrayList<>();

        initToolbar();
        loadingAndDisplayContent();
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
                            listComponentes.add(numeroComponente+"-"+jsonObj.getString("comp_nombre"));
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
                    if(cargando.buscarArea(jsonComp.getInt("area_id")) > 0){
                        valor = "Realizada";
                    }
                    items.add(new FolderFile(jsonComp.getInt("area_id"),jsonComp.getString("area_nombre"), valor, jsonComp.getInt("area_logo"), cargando.buscarArea(jsonComp.getInt("area_id")), true));  // add section
                }
            }
        }
        initComponent();
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
                if(!buscarArea(obj.id)){
                    Intent abrirEncuesta = new Intent(view.getContext(), preguntasEncuesta.class);
                    abrirEncuesta.putExtra("areaId", obj.id);
                    startActivity(abrirEncuesta);
                }else{
                    Toast.makeText(view.getContext(), "Ésta área ya fue evaluada", Toast.LENGTH_SHORT).show();
                }
            }
        });
        crearBoton();
    }
    public void crearBoton(){
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
                Intent abrirCargando = new Intent(v.getContext(), cargando.class);
                startActivity(abrirCargando);
            }
        });
        layout.addView(but);
    }


    public static boolean buscarArea(int areaId){
        boolean saber = false;

        for (int i = 0; i < areasEncuestadas.size(); i++){
            if(areasEncuestadas.get(i).getAreaId() == areaId){
                saber = true;
            }
        }
        return saber;
    }
}
