package com.formato.isp.GestionEmpresa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.formato.isp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;

public class buscar_empresa extends AppCompatActivity {

    ImageView img;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_empresa);

        queue = Volley.newRequestQueue(this);

        img = findViewById(R.id.image_1);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abrirInfo = new Intent(view.getContext(), infoDetallada.class);
                startActivity(abrirInfo);
            }
        });

        obtenerEmpresas();
    }

    private void obtenerEmpresas() {
        String url = "https://formatoisp-api.herokuapp.com/api/empresa";
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                try {
                    JSONArray jsonArr = res.getJSONArray("data");
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);
                        String empr_nit = jsonObj.getString("empr_nit");
                        String empr_nombre = jsonObj.getString("empr_nombre");
                        Toast.makeText(getApplicationContext(),"Nombre", Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(),"Nombre de la empresa: " + empr_nombre, Toast.LENGTH_LONG).show();
                    }
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
}
