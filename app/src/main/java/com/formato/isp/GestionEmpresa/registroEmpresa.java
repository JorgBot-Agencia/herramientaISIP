package com.formato.isp.GestionEmpresa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.Button;

import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.formato.isp.DesarrolloEncuesta.menuEncuesta;
import com.formato.isp.GestionFundacion.AppHelper;
import com.formato.isp.GestionFundacion.Sesion;
import com.formato.isp.GestionFundacion.VolleyMultipartRequest;
import com.formato.isp.GestionFundacion.VolleySingleton;
import com.formato.isp.GestionFundacion.registroFundacion;
import com.formato.isp.MenuLateral.menuprincipal;
import com.formato.isp.R;
import com.formato.isp.resource;
import com.formato.isp.utils.Tools;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class registroEmpresa extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    private final String URI = resource.URLAPI + "/empresa";
    private Button btnRegistrar_Empresa;
    private AlertDialog _photoDialog;
    private static final int ACTIVITY_SELECT_IMAGE = 1020;
    Uri imageUri;
    ImageView foto_gallery;
    RequestQueue queue;
    JsonObjectRequest req;
    FloatingActionButton btnSelectImage;
    private EditText nit;
    private EditText nombre;
    private EditText fecha_crea;
    private EditText fecha_ini;
    private EditText departamento;
    private EditText ciudad;
    private EditText barrio;
    private EditText telefono;
    private EditText sitioweb;
    private EditText resenahistorica;
    private EditText dedicacion;
    private EditText descripcion;
    ProgressDialog p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_empresa);

        initToolbar();
        p = new ProgressDialog(this);
        p.setMessage("Cargando...");
        p.setCancelable(false);

        nit = (EditText)findViewById(R.id.nit_empresa);
        nombre = (EditText)findViewById(R.id.nombre_empresa);
        fecha_crea = (EditText)findViewById(R.id.fecha_creacion);
        fecha_ini = (EditText)findViewById(R.id.fecha_inicio);
        departamento = (EditText)findViewById(R.id.departamento);
        ciudad = (EditText)findViewById(R.id.ciudad);
        barrio = (EditText)findViewById(R.id.barrio);
        telefono = (EditText)findViewById(R.id.telefono);
        sitioweb = (EditText)findViewById(R.id.sitioweb);
        foto_gallery = findViewById(R.id.foto_gallery2);
        btnSelectImage = findViewById(R.id.btnSeleccionar2);
        resenahistorica = findViewById(R.id.resenahistorica);
        descripcion = findViewById(R.id.descripcionempresa);
        dedicacion = findViewById(R.id.dedicacion);

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
        getPhotoDialog();
        getPhotoButton();
        btnRegistrar_Empresa = (Button)findViewById(R.id.btnRegistrarEmpresa);
        btnRegistrar_Empresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearEmpresa();

            }
        });
    }

    private void getPhotoButton() {
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!getPhotoDialog().isShowing() && !isFinishing()){
                    getPhotoDialog().show();
                }
            }
        });
    }

    private AlertDialog getPhotoDialog() {
        if(_photoDialog == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(registroEmpresa.this);
            builder.setTitle("Elige una opción");
            builder.setPositiveButton("Galería", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, ACTIVITY_SELECT_IMAGE);
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            _photoDialog = builder.create();
        }
        return _photoDialog;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == ACTIVITY_SELECT_IMAGE) {
            imageUri = data.getData();
            foto_gallery.setImageURI(imageUri);
        }
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

        if (validarCamposEmpresa()) {
            pruebaRegistro();
        }else{
            Toast.makeText(this, "Revisa los campos, por favor", Toast.LENGTH_SHORT).show();

        }
    }

    private boolean validarCamposEmpresa() {
        if(!nit.getText().toString().isEmpty() && !nombre.getText().toString().isEmpty() && !fecha_crea.getText().toString().isEmpty()
                && !fecha_ini.getText().toString().isEmpty() && !departamento.getText().toString().isEmpty() && !ciudad.getText().toString().isEmpty()
                && !barrio.getText().toString().isEmpty() && !telefono.getText().toString().isEmpty() && !sitioweb.getText().toString().isEmpty()
                && !resenahistorica.getText().toString().isEmpty() && !descripcion.getText().toString().isEmpty() && !dedicacion.getText().toString().isEmpty()){
                    if(Date.valueOf(fecha_crea.getText().toString()).before(Date.valueOf(fecha_ini.getText().toString())) ||
                            Date.valueOf(fecha_crea.getText().toString()).equals(Date.valueOf(fecha_ini.getText().toString()))){
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
        p.hide();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getApplicationContext(), "Empresa creada", Toast.LENGTH_LONG).show();
        limpiarCampos();
        p.hide();
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
        resenahistorica.setText("");
        dedicacion.setText("");
        descripcion.setText("");
    }

    private void pruebaRegistro() {
        // loading or check internet connection or something...
        // ... then
        p.show();
        String url = resource.URLAPI + "/empresa";
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                Toast.makeText(getApplicationContext(), "Empresa Registrada", Toast.LENGTH_SHORT).show();
                limpiarCampos();
                p.hide();
                Intent intent = new Intent(getApplicationContext(), menuEncuesta.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        p.hide();
                        errorMessage = "Request timeout";
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        p.hide();
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    p.hide();
                    if (networkResponse.statusCode == 404) {
                        errorMessage = "Resource not found";
                    } else if (networkResponse.statusCode == 401) {
                        errorMessage = " Please login again";
                    } else if (networkResponse.statusCode == 400) {
                        errorMessage = " Check your inputs";
                    } else if (networkResponse.statusCode == 500) {
                        errorMessage = " Something is getting wrong";
                    }
                }
                Log.i("Error", errorMessage);
                error.printStackTrace();
                p.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Sesion session;
                session = new Sesion(getApplicationContext());
                Map<String, String> params = new HashMap<>();
                params.put("fundacion_fund_id", session.getIdFun());
                params.put("empr_nit", nit.getText().toString());
                params.put("empr_nombre", nombre.getText().toString());
                params.put("empr_fechacreacion", fecha_crea.getText().toString());
                params.put("empr_fechainicio", fecha_ini.getText().toString());
                params.put("empr_barrio", barrio.getText().toString());
                params.put("empr_ciudad", ciudad.getText().toString());
                params.put("empr_depart", departamento.getText().toString());
                params.put("empr_telefono", telefono.getText().toString());
                params.put("empr_paginaweb", sitioweb.getText().toString());
                params.put("empr_resehistorica", resenahistorica.getText().toString());
                params.put("empr_dedica", dedicacion.getText().toString());
                params.put("empr_descripcion", descripcion.getText().toString());

                return params;
            }
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                params.put("empr_logo", new DataPart("file_avatar.jpg", AppHelper.getFileDataFromDrawable(getBaseContext(), foto_gallery.getDrawable()), "image/jpeg"));
                return params;
            }
        };
        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(multipartRequest);
    }

}
