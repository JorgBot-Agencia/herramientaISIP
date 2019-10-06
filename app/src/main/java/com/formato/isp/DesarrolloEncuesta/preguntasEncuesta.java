package com.formato.isp.DesarrolloEncuesta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.formato.isp.Clases.Area;
import com.formato.isp.Pregunta;
import com.formato.isp.R;
import com.formato.isp.utils.*;

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
    private TextView indicadorContenido;
    private TextView descripcionPregunta;
    private TextView txtAdicional;
    private TextView indicadorId;
    private RequestQueue queue;
    public static Area areaProceso;
    private int numeroArea;
    List<Pregunta> listaPreguntas;
    public static List<Area> areasEncuestadas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas_encuesta);

        status = findViewById(R.id.numero);
        contenidoPregunta = findViewById(R.id.formulacion);
        indicadorContenido = findViewById(R.id.indicador);
        indicadorId = findViewById(R.id.idIndicador);
        descripcionPregunta = findViewById(R.id.descripcion);
        progressBar = findViewById(R.id.progress);
        txtAdicional = findViewById(R.id.txtAdicional);

        queue = Volley.newRequestQueue(this);
        listaPreguntas = new ArrayList<>();
        numeroArea = getIntent().getExtras().getInt("areaId");

        areaProceso = new Area(numeroArea, 0, 0);
        obtenerPreguntas();
    }

    private void obtenerPreguntas() {
        String url = "https://formatoisp-api.herokuapp.com/api/criterio/?area=" + numeroArea;
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                try {
                    JSONArray jsonArr = res.getJSONArray("data");
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONArray jsonCriterio = jsonArr.getJSONObject(i).getJSONArray("indicadors");
                        for (int j = 0; j < jsonCriterio.length(); j++) {
                            JSONObject jsonIndicador = jsonCriterio.getJSONObject(j);
                            JSONObject jsonPreguntum = jsonIndicador.getJSONObject("preguntum");
                            listaPreguntas.add(new Pregunta(jsonPreguntum.getInt("preg_id"), jsonPreguntum.getString("preg_contenido"), jsonPreguntum.getString("preg_descrip"), jsonIndicador.getInt("indi_id"), jsonIndicador.getString("indi_contenido"), jsonIndicador.getInt("criterio_crit_id")));
                        }
                    }
                    MAX_STEP = listaPreguntas.size();
                    areaProceso.setTotalIndicadores(MAX_STEP);
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

    private String obtenerDescripcion(int parametro) {
        String preguntaContenido = "";
        for (int i = 0; i < listaPreguntas.size(); i++) {
            if (i == parametro) {
                preguntaContenido = listaPreguntas.get(i).getPreguntaDescripcion();
            }
        }
        return preguntaContenido;
    }

    private String obtenerContenidoIndicador(int parametro) {
        String preguntaContenido = "";
        for (int i = 0; i < listaPreguntas.size(); i++) {
            if (i == parametro) {
                preguntaContenido = listaPreguntas.get(i).getIndicadorContenido();
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
                backStep(current_step);
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
        txtAdicional.setText("");
        indicadorContenido.setText("Indicador de la pregunta");
        indicadorId.setText("Número de indicador");
        descripcionPregunta.setText("Descripción de la pregunta");
        contenidoPregunta.setText("Formulación de pregunta");
        status.setText("Número de pregunta");
        if (progress < MAX_STEP) {
            areaProceso.setAreaAvance(progress);
            status.setText("PREGUNTA " + obtenerId(progress));
            contenidoPregunta.setText(obtenerContenido(progress));
            indicadorContenido.setText(obtenerContenidoIndicador(progress));
            descripcionPregunta.setText(obtenerDescripcion(progress));
            indicadorId.setText("INDICADOR " + obtenerIdIndicador(progress));
            crearComponente(obtenerIdCriterio(progress));
            progressBar.setProgress(current_step);

            progress = progress + 1;
            current_step = progress;
            ViewAnimation.fadeOutIn(status);
        } else {
            areasEncuestadas.add(new Area(areaProceso.getAreaId(), areaProceso.getTotalIndicadores(), areaProceso.getAreaAvance()));
            Intent abrirProgress = new Intent(this, menuEncuesta.class);
            startActivity(abrirProgress);
        }
    }

    private void backStep(int progress) {
        txtAdicional.setText("");
        indicadorContenido.setText("Indicador de la pregunta");
        indicadorId.setText("Número de indicador");
        descripcionPregunta.setText("Descripción de la pregunta");
        contenidoPregunta.setText("Formulación de pregunta");
        status.setText("Número de pregunta");
        if (progress < MAX_STEP) {
            status.setText("PREGUNTA " + obtenerId(progress));
            contenidoPregunta.setText(obtenerContenido(progress));
            indicadorContenido.setText(obtenerContenidoIndicador(progress));
            descripcionPregunta.setText(obtenerDescripcion(progress));
            indicadorId.setText("INDICADOR " + obtenerIdIndicador(progress));
            crearComponente(obtenerIdCriterio(progress));
            progressBar.setProgress(current_step);

            progress = progress + 1;
            current_step = progress;
            ViewAnimation.fadeOutIn(status);
        } else {
            areasEncuestadas.add(new Area(areaProceso.getAreaId(), areaProceso.getTotalIndicadores(), areaProceso.getAreaAvance()));
            Intent abrirProgress = new Intent(this, menuEncuesta.class);
            startActivity(abrirProgress);
        }
    }

    public void crearComponente(int criterio) {
        LinearLayout layout = findViewById(R.id.criterio);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        if (layout.getChildCount() > 0) {
            layout.removeAllViews();
        }

        LinearLayout layoutEscala = findViewById(R.id.escala);
        LinearLayout.LayoutParams lpEscala = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        if (layoutEscala.getChildCount() > 0) {
            layoutEscala.removeAllViews();
        }

        ViewGroup buttonLayoutEscala = findViewById(R.id.grupoRadioEscala);
        if (buttonLayoutEscala.getChildCount() > 0) {
            buttonLayoutEscala.removeAllViews();
        }
        AppCompatRadioButton[] rbEscala = new AppCompatRadioButton[4];
        RadioGroup rgEscala = new RadioGroup(getApplicationContext());

        rbEscala[0] = new AppCompatRadioButton(this);
        rbEscala[0].setText("Nivel critico");
        rgEscala.addView(rbEscala[0]);

        rbEscala[1] = new AppCompatRadioButton(this);
        rbEscala[1].setText("Nivel basico");
        rgEscala.addView(rbEscala[1]);

        rbEscala[2] = new AppCompatRadioButton(this);
        rbEscala[2].setText("Nivel intermedio");
        rgEscala.addView(rbEscala[2]);

        rbEscala[3] = new AppCompatRadioButton(this);
        rbEscala[3].setText("Nivel avanzado");
        rgEscala.addView(rbEscala[3]);

        buttonLayoutEscala.addView(rgEscala);

        switch (criterio) {
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
                        cantidadView.setText("" + progress);
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
                        cantidadMujeresView.setText("" + progressMujeres);
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
                porcentajeView.setText("Seleccione el porcentaje");
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
                        cantidadPorcentajeView.setText("" + progress);
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
                dineroView.setText("Seleccione la cantidad de dinero");
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
                        cantidadDineroView.setText("" + progress);
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
            case 4:
                TextView radioView = new TextView(getApplicationContext());
                radioView.setText("Seleccione una opción");
                radioView.setTextAppearance(getApplicationContext(), R.style.boldText);
                layout.addView(radioView);

                ViewGroup buttonLayout = findViewById(R.id.grupoRadio);
                if (buttonLayout.getChildCount() > 0) {
                    buttonLayout.removeAllViews();
                }
                AppCompatRadioButton[] rb = new AppCompatRadioButton[2];
                RadioGroup rg = new RadioGroup(getApplicationContext());

                for (int i = 0; i < 2; i++){
                    rb[i] = new AppCompatRadioButton(this);
                    if(i == 0){
                        rb[i].setText("Si");
                    }else{
                        rb[i].setText("No");
                    }
                    rg.addView(rb[i]);
                }
                buttonLayout.addView(rg);
                break;
            case 5:
                TextView numeroView = new TextView(getApplicationContext());
                numeroView.setText("Seleccione la cantidad");
                numeroView.setTextAppearance(getApplicationContext(), R.style.boldText);
                layout.addView(numeroView);

                final TextView cantidadNumeroView = new TextView(getApplicationContext());
                cantidadNumeroView.setGravity(Gravity.CENTER);
                layout.addView(cantidadNumeroView);

                AppCompatSeekBar numeroSeekBar = new AppCompatSeekBar(this);
                numeroSeekBar.setThumb(getDrawable(R.drawable.seek_thumb_accent_outline));
                numeroSeekBar.setMax(200);
                numeroSeekBar.setProgress(1);
                numeroSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        cantidadNumeroView.setText("" + progress);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                layout.addView(numeroSeekBar);
                break;
        }


    }
}
