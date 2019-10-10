package com.formato.isp.GestionFundacion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.net.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.anychart.core.Base;
import com.formato.isp.R;
import com.formato.isp.resource;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class registroFundacion extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    private static final int ACTIVITY_SELECT_IMAGE = 1020;
    private static final int ACTIVITY_SELECT_FROM_CAMERA = 1040, ACTIVITY_SHARE = 1030;

    private static final int SELECT_PICTURE = 200;

    private String APP_DIRECTORY = "FormatoISP/";
    private String MEDIA_DIRECTORY = APP_DIRECTORY + "media";
    private String TEMPORAL_PICTURE_NAME = "temporal.jpg";

    private AlertDialog _photoDialog;

    private final String URI = resource.URLAPI + "/fundacion";

    RequestQueue queue;
    JsonObjectRequest req;

    Uri imageUri;
    ImageView foto_gallery;
    Bitmap bitmap;
    String ruta;
    FloatingActionButton btnSeleccionar;
    RelativeLayout rlView;
    ContactsContract.Contacts.Photo photo;
    private EditText nit_fundacion;
    private EditText nom_fundacion;
    private EditText tel_fundacion;
    private EditText dir_fundacion;
    private EditText user_fundacion;
    private EditText pass_fundacion;
    private EditText pass_confirm;
    private Button regis_fundacion;

    private String mpath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_fundacion);

        foto_gallery = findViewById(R.id.logo_fundacion);
        btnSeleccionar = findViewById(R.id.btnSeleccionar);
        rlView = findViewById(R.id.rlView);
        nit_fundacion = (EditText) findViewById(R.id.nit_fundacion);
        nom_fundacion = (EditText) findViewById(R.id.nombre_fundacion);
        tel_fundacion = (EditText) findViewById(R.id.tel_fundacion);
        dir_fundacion = (EditText) findViewById(R.id.dir_fundacion);
        user_fundacion = (EditText) findViewById(R.id.usuario);
        pass_fundacion = (EditText) findViewById(R.id.contrasena);
        pass_confirm = (EditText) findViewById(R.id.confir_contrasena);
        regis_fundacion = (Button) findViewById(R.id.btnRegistrarFundacion);


        queue = Volley.newRequestQueue(this);
        getPhotoDialog();
        getPhotoButton();
        regis_fundacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarFundacion();

                //Toast.makeText(getApplicationContext(), "ImageURI: "+ imageUri.getPath(), Toast.LENGTH_LONG).show();
                //System.out.println("HOLAAAAAAAAAA");
                //foto_gallery.setImageDrawable(getDrawable(R.drawable.ic_location_city_black_24dp));
            }
        });

    }

    private void getPhotoButton() {
        btnSeleccionar.setOnClickListener(new View.OnClickListener() {
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
            AlertDialog.Builder builder = new AlertDialog.Builder(registroFundacion.this);
            builder.setTitle("Elige una opción");
            builder.setPositiveButton("Galeria", new DialogInterface.OnClickListener() {
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
        public void registrarFundacion(){

        if (validarCampos()) {
            Toast.makeText(getApplicationContext(), "En proceso...", Toast.LENGTH_SHORT).show();
            pruebaRegistro();

        }else{
            Toast.makeText(getApplicationContext(), "Por favor, completa todos los campos",Toast.LENGTH_SHORT).show();
        }

    }

    public boolean validarCampos(){
        if(!nit_fundacion.getText().toString().isEmpty() && !nom_fundacion.getText().toString().isEmpty() && !tel_fundacion.getText().toString().isEmpty() && !dir_fundacion.getText().toString().isEmpty()
                && !user_fundacion.getText().toString().isEmpty() && !pass_fundacion.getText().toString().isEmpty() && !pass_confirm.getText().toString().isEmpty() ){
                if(pass_fundacion.getText().toString().equals(pass_confirm.getText().toString()) ){
                    return true;
                }
                Toast.makeText(getApplicationContext(),"Las contraseñas no coinciden",Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getApplicationContext(), "Fundación creada", Toast.LENGTH_LONG).show();
        limpiarCampos();
    }

    public void limpiarCampos(){
        nit_fundacion.setText("");
        nom_fundacion.setText("");
        dir_fundacion.setText("");
        tel_fundacion.setText("");
        user_fundacion.setText("");
        pass_fundacion.setText("");
        pass_confirm.setText("");
        foto_gallery.setImageDrawable(getDrawable(R.drawable.ic_location_city_black_24dp));
    }

    public String convertirImgaString(Bitmap bitmap1){
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, array);
        byte[] imagenByte = array.toByteArray();
        String imagenString = Base64.encodeToString(imagenByte, Base64.DEFAULT);
        return imagenString;
    }

    private void pruebaRegistro() {
        // loading or check internet connection or something...
        // ... then
        String url = resource.URLAPI + "/fundacion";
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                Toast.makeText(getApplicationContext(), "REGISTRADOOOOOOOO", Toast.LENGTH_SHORT).show();
                limpiarCampos();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    String result = new String(networkResponse.data);


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
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("fund_nit", nit_fundacion.getText().toString());
                params.put("fund_nombre", nom_fundacion.getText().toString());
                params.put("fund_direccion", dir_fundacion.getText().toString());
                params.put("fund_telefono", tel_fundacion.getText().toString());
                //params.put("fund_logo", imagen);
                params.put("cuen_username", user_fundacion.getText().toString());
                params.put("cuen_password", pass_confirm.getText().toString());
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                params.put("fund_logo", new DataPart("file_avatar.jpg", AppHelper.getFileDataFromDrawable(getBaseContext(), foto_gallery.getDrawable()), "image/jpeg"));
                return params;
            }
        };
        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(multipartRequest);
    }




}
