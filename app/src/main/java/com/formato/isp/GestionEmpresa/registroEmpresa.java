package com.formato.isp.GestionEmpresa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.Button;

import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.formato.isp.R;
import com.formato.isp.source;
import com.formato.isp.utils.Tools;

import org.json.JSONObject;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class registroEmpresa extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    private final String URI = source.URLAPI + "empresa";
    private Button btnRegistrar_Empresa;

    RequestQueue queue;
    JsonObjectRequest req;

    private EditText nit;
    private EditText nombre;
    private EditText fecha_crea;
    private EditText fecha_ini;
    private EditText departamento;
    private EditText ciudad;
    private EditText barrio;
    private EditText telefono;
    private EditText sitioweb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_empresa);

        initToolbar();

        nit = (EditText)findViewById(R.id.nit_empresa);
        nombre = (EditText)findViewById(R.id.nombre_empresa);
        fecha_crea = (EditText)findViewById(R.id.fecha_creacion);
        fecha_ini = (EditText)findViewById(R.id.fecha_inicio);
        departamento = (EditText)findViewById(R.id.departamento);
        ciudad = (EditText)findViewById(R.id.ciudad);
        barrio = (EditText)findViewById(R.id.barrio);
        telefono = (EditText)findViewById(R.id.telefono);
        sitioweb = (EditText)findViewById(R.id.sitioweb);

        fecha_crea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.fecha_creacion:
                        showDatePickerDialog();
                        break;
                }
            }
        });

        fecha_ini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.fecha_inicio:
                        showDatePickerDialog1();
                        break;
                }
            }
        });

        queue = Volley.newRequestQueue(this);

        btnRegistrar_Empresa = (Button)findViewById(R.id.btnRegistrarEmpresa);
        btnRegistrar_Empresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearEmpresa();
            }
        });
    }


    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = year + "-" + (month+1) + "-" + day;
                fecha_crea.setText(selectedDate);
            }
        });

        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }
    private void showDatePickerDialog1() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = year + "-" + (month+1) + "-" + day;
                fecha_ini.setText(selectedDate);
            }
        });

        newFragment.show(this.getSupportFragmentManager(), "datePicker");
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
        Toast.makeText(this, "En proceso...", Toast.LENGTH_SHORT).show();
        if (validarCamposEmpresa()) {
            Map params = new HashMap();
            params.put("empr_nit", nit.getText().toString());
            params.put("empr_nombre", nombre.getText().toString());
            params.put("empr_fechacreacion", fecha_crea.getText().toString());
            params.put("empr_fechainicio", fecha_ini.getText().toString());
            params.put("empr_barrio", barrio.getText().toString());
            params.put("empr_ciudad", ciudad.getText().toString());
            params.put("empr_depart", departamento.getText().toString());
            params.put("empr_telefono", telefono.getText().toString());
            params.put("empr_paginaweb", sitioweb.getText().toString());
            req = new JsonObjectRequest(Request.Method.POST, URI, new JSONObject(params), this, this);
            queue.add(req);
        }else{
            Toast.makeText(this, "Revisa los campos, por favor", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validarCamposEmpresa() {
        if(!nit.getText().toString().isEmpty() && !nombre.getText().toString().isEmpty() && !fecha_crea.getText().toString().isEmpty()
                && !fecha_ini.getText().toString().isEmpty() && !departamento.getText().toString().isEmpty() && !ciudad.getText().toString().isEmpty()
                && !barrio.getText().toString().isEmpty() && !telefono.getText().toString().isEmpty() && !sitioweb.getText().toString().isEmpty() ){
                    if(Date.valueOf(fecha_crea.getText().toString()).before(Date.valueOf(fecha_ini.getText().toString())) ){
                        return true;
                    }else{
                        return false;
                    }
        }
            return false;

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getApplicationContext(), "Empresa creada", Toast.LENGTH_LONG).show();
        limpiarCampos();
    }

    public void limpiarCampos(){
        nit.setText("");
        nombre.setText("");
        fecha_crea.setText("");
        fecha_ini.setText("");
        departamento.setText("");
        ciudad.setText("");
        barrio.setText("");
        telefono.setText("");
        sitioweb.setText("");
    }

}
