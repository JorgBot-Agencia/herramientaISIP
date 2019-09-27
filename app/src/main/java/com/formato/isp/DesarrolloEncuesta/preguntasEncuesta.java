package com.formato.isp.DesarrolloEncuesta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.formato.isp.Adaptadores.adaptadorPregunta;
import com.formato.isp.Clases.Pregunta;
import com.formato.isp.R;
import com.formato.isp.utils.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class preguntasEncuesta extends AppCompatActivity {

    private static final int MAX_STEP = 5;
    private int current_step = 1;
    private TextView numero;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RequestQueue queue;
    private RecyclerView.Adapter adapter;
    private List<Pregunta> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas_encuesta);

        numero = findViewById(R.id.numero);

        listItems = new ArrayList<>();
        listItems.add(new Pregunta(1, "Pregunta 1", "Incompleto"));
        listItems.add(new Pregunta(2, "Pregunta 2", "Incompleto"));
        listItems.add(new Pregunta(3, "Pregunta 3", "Incompleto"));
        listItems.add(new Pregunta(4, "Pregunta 4", "Incompleto"));
        listItems.add(new Pregunta(5, "Pregunta 5", "Incompleto"));
        //recyclerView = findViewById(R.id.recyclerPregunta);
        //recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        initComponent();

    }

    private void obtenerPreguntas() {
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

    private void initComponent() {
        progressBar = findViewById(R.id.progress);
        progressBar.setMax(MAX_STEP);
        progressBar.setProgress(current_step);
        progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        (findViewById(R.id.lyt_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backStep(current_step);
            }
        });

        (findViewById(R.id.lyt_next)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextStep(current_step);
            }
        });


        //for (int i = 1; i <= 5; i++) {
            //Pregunta listItem = new Pregunta(i + 1, "Pregunta", "Descripcion " + i);
            //listItems.add(listItem);
        //}
        //adapter = new adaptadorPregunta(listItems, this);
        //recyclerView.setAdapter(adapter);

    }

    private void nextStep(int progress) {
        if (progress <= MAX_STEP) {
            progress++;
            current_step = progress;
        }
        if (progress == listItems.size()) {
            Intent abrirProgress = new Intent(this, cargando.class);
            startActivity(abrirProgress);
        }
        numero.setText(String.format(listItems.get(progress).getContenido()));
        progressBar.setProgress(current_step);
    }

    private void backStep(int progress) {
        if (progress > 1) {
            progress--;
            current_step = progress;
        }
        progressBar.setProgress(current_step);
    }
}
