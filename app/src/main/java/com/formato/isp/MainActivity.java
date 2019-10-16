package com.formato.isp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.PixelCopy;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.formato.isp.GestionFundacion.Sesion;
import com.formato.isp.GestionFundacion.registroFundacion;
import com.formato.isp.MenuLateral.menuprincipal;
import com.formato.isp.PDF.TemplatePDF;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button btnIniciarSesion;
    TextView txtLink;
    EditText usuario;
    EditText contrasena;
    ProgressDialog p;

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

        p = new ProgressDialog(this);
        p.setMessage("Cargando...");
        p.setCancelable(false);
        usuario = (EditText)findViewById(R.id.txtUsuario);
        usuario.clearFocus();
        contrasena = (EditText)findViewById(R.id.txtContrasena);
        contrasena.clearFocus();

        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!usuario.getText().toString().isEmpty() && !contrasena.getText().toString().isEmpty() ){
                    queue = Volley.newRequestQueue(getApplicationContext());
                    iniciarSesion1();
                    p.show();
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

            Map params = new HashMap();
            params.put("cuen_username", usuario.getText().toString());
            params.put("cuen_password", contrasena.getText().toString());
            req = new JsonObjectRequest(Request.Method.POST, URI, new JSONObject(params), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject res) {
                    try {
                        if (res.getString("message").equals("Bienvenido")) {
                            Toast.makeText(getApplicationContext(), res.getString("message"), Toast.LENGTH_LONG).show();
                            Sesion session;
                            session = new Sesion(getApplicationContext());
                            JSONObject fundacion = res.getJSONObject("data");
                            session.setUsername(usuario.getText().toString());
                            session.setContrasena(contrasena.getText().toString());
                            session.setIdFun(fundacion.getString("fund_id"));
                            session.setNitFun(fundacion.getString("fund_nit"));
                            session.setNombreFun(fundacion.getString("fund_nombre"));
                            session.setDireccion(fundacion.getString("fund_direccion"));
                            session.setTelefono(fundacion.getString("fund_telefono"));
                            session.setLogo(fundacion.getString("fund_logo"));
                            p.hide();
                            Intent intent = new Intent(getApplicationContext(), menuprincipal.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), res.getString("message"), Toast.LENGTH_LONG).show();
                            p.hide();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Credenciales Incorrectas", Toast.LENGTH_LONG).show();
                    p.hide();
                }
            });
            queue.add(req);

    }
}
