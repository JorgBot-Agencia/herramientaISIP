package com.formato.isp.GestionEmpresa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.formato.isp.R;
import com.formato.isp.resource;
import com.formato.isp.utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class registroPersona extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    RequestQueue queue;
    JsonObjectRequest req;
    String id;

    private Spinner sprol;
    ArrayList<String> rol;
    ArrayList<String> idrol;
    private EditText doc_persona;
    private EditText nom_persona;
    private EditText ape_persona;
    private EditText dir_persona;
    private EditText tel_persona;
    private Button registrar_Persona;
    String URI = resource.URLAPI + "/persona";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_persona);
        cargarPref();
        rol = new ArrayList<>();
        idrol = new ArrayList<>();
        sprol = (Spinner)findViewById(R.id.sp_rol);
        doc_persona = (EditText)findViewById(R.id.doc_persona);
        nom_persona = (EditText)findViewById(R.id.nom_persona);
        ape_persona = (EditText)findViewById(R.id.ape_persona);
        dir_persona = (EditText)findViewById(R.id.dir_persona);
        tel_persona = (EditText)findViewById(R.id.tel_persona);
        registrar_Persona = (Button)findViewById(R.id.regis_persona);
        queue = Volley.newRequestQueue(this);
        registrar_Persona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Position: "+idrol.get(sprol.getSelectedItemPosition()), Toast.LENGTH_SHORT).show();
                crearPersona();
            }
        });

        initToolbar();
        llenarSpinerRol();
    }

    private void llenarSpinerRol() {
        idrol.add("0");
        rol.add("Seleccionar:");
        String url = resource.URLAPI + "/rol";
        req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArr = response.getJSONArray("data");
                    JSONObject jsonObj = null;
                    for (int i = 0; i < jsonArr.length(); i++) {
                        jsonObj = jsonArr.getJSONObject(i);
                        idrol.add(jsonObj.getString("rol_id"));
                        rol.add(jsonObj.getString("rol_nombre"));
                    }
                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, rol);
                    sprol.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(),"Por favor verifica tu conexión a internet", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        queue.add(req);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbarRegPersona);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Formulario de Inscripción");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.colorSecondary);
    }

    public void crearPersona() {
        Toast.makeText(this, "En proceso...", Toast.LENGTH_SHORT).show();
        if (validarCamposPersona()) {
            Map params = new HashMap();
            params.put("empresa_empr_id", id);
            params.put("pers_documento", doc_persona.getText().toString());
            params.put("pers_nombre", nom_persona.getText().toString());
            params.put("pers_apellido", ape_persona.getText().toString());
            params.put("pers_direccion", dir_persona.getText().toString());
            params.put("pers_telefono", tel_persona.getText().toString());
            params.put("rol_rol_id", idrol.get(sprol.getSelectedItemPosition()));
            req = new JsonObjectRequest(Request.Method.POST, URI, new JSONObject(params), this, this);
            queue.add(req);
        }else{
            Toast.makeText(this, "Revisa los campos, por favor", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validarCamposPersona() {
        if(!nom_persona.getText().toString().isEmpty() && !ape_persona.getText().toString().isEmpty() && !dir_persona.getText().toString().isEmpty()
                && !tel_persona.getText().toString().isEmpty() && !doc_persona.getText().toString().isEmpty() && (sprol.getSelectedItem().toString() != "Seleccionar:" )){
                return true;
        }
        return false;
    }

    public void limpiarCampos(){
        nom_persona.setText("");
        doc_persona.setText("");
        ape_persona.setText("");
        dir_persona.setText("");
        tel_persona.setText("");
        sprol.setSelection(0);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (error instanceof NetworkError) {
            Toast.makeText(getApplicationContext(),"Por favor verifica tu conexión a internet", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getApplicationContext(), "Persona registrada", Toast.LENGTH_LONG).show();
        limpiarCampos();
    }

    public void cargarPref(){
        SharedPreferences pref = getSharedPreferences("nitEmpresa", Context.MODE_PRIVATE);
        id = pref.getString("ID","No existe");
    }
}
