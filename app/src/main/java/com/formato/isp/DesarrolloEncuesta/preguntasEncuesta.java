package com.formato.isp.DesarrolloEncuesta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
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

import static com.formato.isp.R.color.colorLetraBlanco;

public class preguntasEncuesta extends AppCompatActivity {

    private int MAX_STEP;
    private int current_step = 0;
    private ProgressBar progressBar;
    private TextView status;
    private TextView contenidoPregunta;
    private TextView descripcionPregunta;
    private TextView indicadorPregunta;
    private TextView indicadorId;
    private RequestQueue queue;
    private int numeroArea;
    List<Pregunta> listaPreguntas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas_encuesta);

        status = findViewById(R.id.numero);
        contenidoPregunta = findViewById(R.id.formulacion);
        indicadorId = findViewById(R.id.idIndicador);
        descripcionPregunta = findViewById(R.id.descripcion);
        indicadorPregunta = findViewById(R.id.indicador);

        progressBar = findViewById(R.id.progress);
        queue = Volley.newRequestQueue(this);
        listaPreguntas = new ArrayList<>();
        numeroArea = getIntent().getExtras().getInt("areaId");

        obtenerPreguntas();
    }

    private void obtenerPreguntas() {
        String url = "https://formatoisp-api.herokuapp.com/api/indicador/?area="+numeroArea;
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                try {
                    JSONArray jsonArr = res.getJSONArray("data");
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonCriterio = jsonArr.getJSONObject(i);
                        JSONObject jsonObj = jsonArr.getJSONObject(i).getJSONObject("indicadors");
                        JSONObject jsonPreguntum = jsonObj.getJSONObject("preguntum");
                        listaPreguntas.add(new Pregunta(jsonPreguntum.getInt("preg_id"), jsonPreguntum.getString("preg_contenido"), jsonPreguntum.getString("preg_descrip"), jsonObj.getInt("indi_id"), jsonObj.getString("indi_contenido"), jsonCriterio.getInt("crit_id")));
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
    private int obtenerIdIndicador(int parametro) {
        int preguntaId = 0;
        for (int i = 0; i < listaPreguntas.size(); i++) {
            if (i == parametro) {
                preguntaId = listaPreguntas.get(i).getIndicadorId();
            }
        }
        return preguntaId;
    }
    private int obtenerIdCriterio(int parametro) {
        int preguntaId = 0;
        for (int i = 0; i < listaPreguntas.size(); i++) {
            if (i == parametro) {
                preguntaId = listaPreguntas.get(i).getCriterio();
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
    private String obtenerContenidoIndicador(int parametro) {
        String preguntaContenido = "";
        for (int i = 0; i < listaPreguntas.size(); i++) {
            if (i == parametro) {
                preguntaContenido = listaPreguntas.get(i).getContenidoIndicador();
            }
        }
        if(preguntaContenido.isEmpty()){
            preguntaContenido = "SIN INDICADOR";
        }
        return preguntaContenido;
    }
    private String obtenerDescripcion(int parametro) {
        String preguntaContenido = "";
        for (int i = 0; i < listaPreguntas.size(); i++) {
            if (i == parametro) {
                preguntaContenido = listaPreguntas.get(i).getPreguntaDescripcion();
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
        if (progress == listaPreguntas.size()+1) {
            Intent abrirProgress = new Intent(this, menuEncuesta.class);
            startActivity(abrirProgress);
        } else if (progress <= MAX_STEP) {
            current_step = progress;
            ViewAnimation.fadeOutIn(status);
        }
        status.setText("PREGUNTA " + obtenerId(progress-1));
        contenidoPregunta.setText(obtenerContenido(progress-1));
        descripcionPregunta.setText(obtenerDescripcion(progress-1));
        indicadorId.setText("INDICADOR "+obtenerIdIndicador(progress-1));
        indicadorPregunta.setText(obtenerContenidoIndicador(progress-1));
        crearComponente(obtenerIdCriterio(progress-1));
        progressBar.setProgress(current_step);
    }

    public void crearComponente(int criterio){
        LinearLayout layout = findViewById(R.id.criterio);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        switch (criterio){
            case 1:
                TextView hombresView = new TextView(getApplicationContext());
                hombresView.setTextAppearance(getApplicationContext(), R.style.boldText);
                hombresView.setText("Seleccione la cantidad de hombres");
                layout.addView(hombresView);

                final TextView cantidadView = new TextView(getApplicationContext());
                cantidadView.setGravity(Gravity.CENTER);
                layout.addView(cantidadView);

                AppCompatSeekBar hombres = new AppCompatSeekBar(this);
                hombres.setThumb(getDrawable(R.drawable.seek_thumb_accent_outline));
                hombres.setMax(100);
                hombres.setProgress(1);
                hombres.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        cantidadView.setText(""+progress);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                layout.addView(hombres);

                TextView mujeresView = new TextView(getApplicationContext());
                mujeresView.setTextAppearance(getApplicationContext(), R.style.boldText);
                mujeresView.setText("Seleccione la cantidad de mujeres");
                layout.addView(mujeresView);

                final TextView cantidadMujeresView = new TextView(getApplicationContext());
                cantidadMujeresView.setGravity(Gravity.CENTER);
                layout.addView(cantidadMujeresView);

                AppCompatSeekBar mujeres = new AppCompatSeekBar(this);
                mujeres.setThumb(getDrawable(R.drawable.seek_thumb_primary_outline));
                mujeres.setMax(100);
                mujeres.setProgress(1);
                mujeres.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBarMujeres, int progressMujeres, boolean fromUser) {
                        cantidadMujeresView.setText(""+progressMujeres);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBarMujeres) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBarMujeres) {

                    }
                });
                layout.addView(mujeres);
                break;
            case 2:
                TextView porcentajeView = new TextView(getApplicationContext());
                porcentajeView.setText("Porcentaje");
                porcentajeView.setTextAppearance(getApplicationContext(), R.style.boldText);
                layout.addView(porcentajeView);

                final TextView cantidadPorcentajeView = new TextView(getApplicationContext());
                cantidadPorcentajeView.setGravity(Gravity.CENTER);
                layout.addView(cantidadPorcentajeView);

                AppCompatSeekBar porcentajeSeekBar = new AppCompatSeekBar(this);
                porcentajeSeekBar.setThumb(getDrawable(R.drawable.seek_thumb_accent_outline));
                porcentajeSeekBar.setMax(100);
                porcentajeSeekBar.setProgress(1);
                porcentajeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        cantidadPorcentajeView.setText(""+progress);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                layout.addView(porcentajeSeekBar);
                break;
            case 3:
                TextView dineroView = new TextView(getApplicationContext());
                dineroView.setText("Dinero");
                dineroView.setTextAppearance(getApplicationContext(), R.style.boldText);
                layout.addView(dineroView);

                final TextView cantidadDineroView = new TextView(getApplicationContext());
                cantidadDineroView.setGravity(Gravity.CENTER);
                layout.addView(cantidadDineroView);

                AppCompatSeekBar dineroSeekBar = new AppCompatSeekBar(this);
                dineroSeekBar.setThumb(getDrawable(R.drawable.seek_thumb_accent_outline));
                dineroSeekBar.setMax(2000000);
                dineroSeekBar.setProgress(10000);
                dineroSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        cantidadDineroView.setText(""+progress);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                layout.addView(dineroSeekBar);
                break;
        }



    }
}
