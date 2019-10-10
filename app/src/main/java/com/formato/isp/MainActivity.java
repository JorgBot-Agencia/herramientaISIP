package com.formato.isp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.formato.isp.GestionFundacion.registroFundacion;
import com.formato.isp.MenuLateral.menuprincipal;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button btnIniciarSesion;
    TextView txtLink;
    EditText usuario;
    EditText contrasena;

    private final String URI = resource.URLAPI + "/fundacion/iniciarSesion";

    RequestQueue queue;
    JsonObjectRequest req;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyStoragePermissions(this);


        usuario = (EditText)findViewById(R.id.txtUsuario);
        contrasena = (EditText)findViewById(R.id.txtContrasena);

        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!usuario.getText().toString().isEmpty() && !contrasena.getText().toString().isEmpty() ){
                    iniciarSesion1();
                }else{
                    Toast.makeText(getApplicationContext(), "Completa todos los campos", Toast.LENGTH_LONG).show();
                }

            }
        });

        txtLink = findViewById(R.id.link);
        txtLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abrir = new Intent(view.getContext(), registroFundacion.class);
                startActivity(abrir);
            }
        });
    }
    public void iniciarSesion(){
        Intent intent = new Intent(this, menuprincipal.class);
        intent.putExtra("direccion","1");
        startActivity(intent);
    }

    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
// We don’t have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private void iniciarSesion1() {
        Toast.makeText(this, "En proceso...", Toast.LENGTH_SHORT).show();

            Map params = new HashMap();
            params.put("cuen_username", usuario.getText().toString());
            params.put("cuen_password", contrasena.getText().toString());
            req = new JsonObjectRequest(Request.Method.POST, URI, new JSONObject(params), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Intent intent = new Intent(getApplicationContext(), menuprincipal.class);
                    intent.putExtra("direccion","1");
                    startActivity(intent);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Credenciales Incorrectas", Toast.LENGTH_LONG).show();
                }
            });
            queue.add(req);

    }
}
