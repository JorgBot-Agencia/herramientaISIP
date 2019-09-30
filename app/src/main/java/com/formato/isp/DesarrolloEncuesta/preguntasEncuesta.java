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
import com.android.volley.toolbox.Volley;
import com.formato.isp.Pregunta;
import com.formato.isp.R;
import com.formato.isp.utils.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class preguntasEncuesta extends AppCompatActivity {

    private int MAX_STEP;
    private int current_step = 0;
    private ProgressBar progressBar;
    private TextView status;
    private TextView contenidoPregunta;
    private RequestQueue queue;
    private int numeroArea;
    List<Pregunta> listaPreguntas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas_encuesta);

        status = findViewById(R.id.numero);
        contenidoPregunta = findViewById(R.id.messageTv);
        progressBar = findViewById(R.id.progress);
        queue = Volley.newRequestQueue(this);
        listaPreguntas = new ArrayList<>();
        numeroArea = getIntent().getExtras().getInt("areaId");

        obtenerPreguntas();
    }

    private void obtenerPreguntas() {
        String url = "https://formatoisp-api.herokuapp.com/api/pregunta/?area="+numeroArea;
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                try {
                    JSONArray jsonArr = res.getJSONArray("data");
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);
                        listaPreguntas.add(new Pregunta(jsonObj.getInt("preg_id"), jsonObj.getString("preg_contenido"), jsonObj.getString("preg_descrip")));
                    }
                    MAX_STEP = listaPreguntas.size();
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
        initComponent();
    }

    private int obtenerId(int parametro) {
        int preguntaId = 0;
        for (int i = 0; i < listaPreguntas.size(); i++) {
            if (i == parametro) {
                preguntaId = listaPreguntas.get(i).getPreguntaId();
            }
        }
        return preguntaId;
    }

    private String obtenerContenido(int parametro) {
        String preguntaContenido = "";
        for (int i = 0; i < listaPreguntas.size(); i++) {
            if (i == parametro) {
                preguntaContenido = listaPreguntas.get(i).getPreguntaContenido();
            }
        }
        return preguntaContenido;
    }

    private void initComponent() {
        progressBar = findViewById(R.id.progress);
        progressBar.setMax(MAX_STEP);
        progressBar.setProgress(current_step);
        progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);


        (findViewById(R.id.lyt_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextStep(current_step);
            }
        });

        (findViewById(R.id.lyt_next)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextStep(current_step);
            }
        });
    }

    private void nextStep(int progress) {
        if (progress <= MAX_STEP) {
            progress++;
            current_step = progress;
        }
        if (progress == listaPreguntas.size()) {
            Intent abrirProgress = new Intent(this, menuEncuesta.class);
            startActivity(abrirProgress);
        } else if (progress <= MAX_STEP) {
            current_step = progress;
            ViewAnimation.fadeOutIn(status);
        }
        status.setText("PREGUNTA " + obtenerId(progress-1));
        contenidoPregunta.setText(obtenerContenido(progress-1));
        progressBar.setProgress(current_step);
    }
}
