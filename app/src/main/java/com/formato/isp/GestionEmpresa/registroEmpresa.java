package com.formato.isp.GestionEmpresa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.formato.isp.DesarrolloEncuesta.reporteGrafico;
import com.formato.isp.R;
import com.formato.isp.source;
import com.formato.isp.utils.Tools;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class registroEmpresa extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    private final String URI = source.URLAPI + "empresa";
    private Button btnRegistrarEmpresa;

    private RequestQueue queue;
    private JsonObjectRequest req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_empresa);

        initToolbar();

        queue = Volley.newRequestQueue(this);

        btnRegistrarEmpresa = (Button) findViewById(R.id.btnRegistrarEmpresa);
        btnRegistrarEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearEmpresa();
            }
        });
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
        }
        return super.onOptionsItemSelected(item);
    }

    private void crearEmpresa() {
        if (validarCamposEmpresa()) {
            Map params = new HashMap();
            params.put("empr_nit", "8");
            params.put("empr_nombre", "Fundacion H");
            params.put("empr_fechacreacion", "2019-09-29");
            params.put("empr_fechainicio", "2019-09-29");
            params.put("empr_barrio", "San Rafael");
            params.put("empr_ciudad", "Cúcuta");
            params.put("empr_depart", "Norte de Santander");
            params.put("empr_telefono", "5774452");
            params.put("empr_paginaweb", "http://EmpresaH.com");
            req = new JsonObjectRequest(Request.Method.POST, URI, new JSONObject(params), this, this);
            queue.add(req);
        }
    }

    private boolean validarCamposEmpresa() {
        return true;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getApplicationContext(), "Empresa creada", Toast.LENGTH_LONG).show();
    }
}
