package com.formato.isp.DesarrolloEncuesta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class preguntasEncuesta extends AppCompatActivity {

    private int MAX_STEP;
    private int cc;
    private int current_step = 0;
    public boolean marcarGrupo = false;
    private ProgressBar progressBar;
    private TextView status;
    private TextView contenidoPregunta;
    private TextView indicadorContenido;
    private TextView descripcionPregunta;
    private TextView txtAdicional;
    private TextView indicadorId;
    private RequestQueue queue;
    public static Area areaProceso;
    public int numeroArea;
    public static List<Pregunta> listaPreguntas;
    public static float valor = 0;

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
        numeroArea = getIntent().getExtras().getInt("areaId");
        listaPreguntas = new ArrayList<>();
        areaProceso = new Area(numeroArea, 0, 0, 0);
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
                        JSONObject jsonObject = jsonArr.getJSONObject(i);
                        JSONArray jsonCriterio = jsonArr.getJSONObject(i).getJSONArray("indicadors");
                        for (int j = 0; j < jsonCriterio.length(); j++) {
                            JSONObject jsonIndicador = jsonCriterio.getJSONObject(j);
                            JSONObject jsonPreguntum = jsonIndicador.getJSONObject("preguntum");
                            listaPreguntas.add(new Pregunta(jsonPreguntum.getInt("preg_id"), jsonPreguntum.getString("preg_contenido"), jsonPreguntum.getString("preg_descrip"), jsonIndicador.getInt("indi_id"), jsonIndicador.getString("indi_contenido"), jsonObject.getInt("crit_id"), numeroArea, 0));
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

    private void asignarValor(float valor, int parametro) {
        for (int i = 0; i < listaPreguntas.size(); i++) {
            if (i == parametro) {
                listaPreguntas.get(i).setValor(valor);
            }
        }
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
        cc = 1;
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
        //indicadorId.setText("Número de indicador");
        descripcionPregunta.setText("Descripción de la pregunta");
        contenidoPregunta.setText("Formulación de pregunta");
        status.setText("Número de pregunta");

        if (progress < MAX_STEP) {
            areaProceso.setAreaAvance(progress);
            //status.setText("PREGUNTA " + obtenerId(progress));
            status.setText("PREGUNTA " + cc++);
            contenidoPregunta.setText(obtenerContenido(progress));
            indicadorContenido.setText(obtenerContenidoIndicador(progress));
            descripcionPregunta.setText(obtenerDescripcion(progress));
            //indicadorId.setText("INDICADOR " + obtenerIdIndicador(progress));
            crearComponente(obtenerIdCriterio(progress));
            progressBar.setProgress(current_step);

            if (progress > 0) {
                asignarValor(valor, progress - 1);
                if (!marcarGrupo) {
                    Toast.makeText(getApplicationContext(), "Debe asignar una escala a este indicador.", Toast.LENGTH_SHORT).show();
                }
            }
            progress = progress + 1;
            current_step = progress;
            ViewAnimation.fadeOutIn(status);

        } else {
            menuEncuesta.areasEncuestadas.add(new Area(areaProceso.getAreaId(), areaProceso.getTotalIndicadores(), areaProceso.getAreaAvance(), areaProceso.getPromedioEscala()));
            Intent abrirProgress = new Intent(this, menuEncuesta.class);
            startActivity(abrirProgress);
        }
    }

    private void backStep(int progress) {
        txtAdicional.setText("");
        //indicadorContenido.setText("Indicador de la pregunta");
        //indicadorId.setText("Número de indicador");
        descripcionPregunta.setText("Descripción de la pregunta");
        contenidoPregunta.setText("Formulación de pregunta");
        status.setText("Número de pregunta");
        if (progress < MAX_STEP) {
            status.setText("PREGUNTA " + cc++);
            contenidoPregunta.setText(obtenerContenido(progress));
            //indicadorContenido.setText(obtenerContenidoIndicador(progress));
            descripcionPregunta.setText(obtenerDescripcion(progress));
            //indicadorId.setText("INDICADOR " + obtenerIdIndicador(progress));
            crearComponente(obtenerIdCriterio(progress));
            progressBar.setProgress(current_step);

            progress = progress + 1;
            current_step = progress;
            ViewAnimation.fadeOutIn(status);
        } else {
            menuEncuesta.areasEncuestadas.add(new Area(areaProceso.getAreaId(), areaProceso.getTotalIndicadores(), areaProceso.getAreaAvance(), areaProceso.getPromedioEscala()));
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
        rbEscala[0].setId(R.id.radio1);
        rgEscala.addView(rbEscala[0]);

        rbEscala[1] = new AppCompatRadioButton(this);
        rbEscala[1].setText("Nivel basico");
        rbEscala[1].setId(R.id.radio2);
        rgEscala.addView(rbEscala[1]);

        rbEscala[2] = new AppCompatRadioButton(this);
        rbEscala[2].setId(R.id.radio3);
        rbEscala[2].setText("Nivel intermedio");
        rgEscala.addView(rbEscala[2]);

        rbEscala[3] = new AppCompatRadioButton(this);
        rbEscala[3].setText("Nivel avanzado");
        rbEscala[3].setId(R.id.radio4);
        rgEscala.addView(rbEscala[3]);

        rgEscala.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radio1:
                        valor = 25;
                        areaProceso.setPromedioEscala(areaProceso.getPromedioEscala() + 25);
                        marcarGrupo = true;
                        break;
                    case R.id.radio2:
                        valor = 50;
                        areaProceso.setPromedioEscala(areaProceso.getPromedioEscala() + 50);
                        marcarGrupo = true;
                        break;
                    case R.id.radio3:
                        valor = 75;
                        areaProceso.setPromedioEscala(areaProceso.getPromedioEscala() + 75);
                        marcarGrupo = true;
                        break;
                    case R.id.radio4:
                        valor = 100;
                        areaProceso.setPromedioEscala(areaProceso.getPromedioEscala() + 100);
                        marcarGrupo = true;
                        break;
                }
            }
        });

        buttonLayoutEscala.addView(rgEscala);

        switch (criterio) {
            case 1:
                TextView hombresView = new TextView(getApplicationContext());
                hombresView.setTextAppearance(getApplicationContext(), R.style.boldText);
                hombresView.setText("Digite la cantidad de hombres");
                layout.addView(hombresView);

                EditText cantidadHombres = new EditText(getApplicationContext());
                cantidadHombres.setGravity(Gravity.CENTER);
                cantidadHombres.setTextColor(Color.WHITE);
                cantidadHombres.setWidth(20);
                cantidadHombres.setHeight(120);
                cantidadHombres.setInputType(InputType.TYPE_CLASS_NUMBER);
                layout.addView(cantidadHombres);

                TextView mujeresView = new TextView(getApplicationContext());
                mujeresView.setTextAppearance(getApplicationContext(), R.style.boldText);
                mujeresView.setText("Digite la cantidad de mujeres");
                layout.addView(mujeresView);

                EditText cantidadMujeres = new EditText(getApplicationContext());
                cantidadMujeres.setGravity(Gravity.CENTER);
                cantidadMujeres.setTextColor(Color.WHITE);
                cantidadMujeres.setWidth(20);
                cantidadMujeres.setHeight(120);
                cantidadMujeres.setInputType(InputType.TYPE_CLASS_NUMBER);
                layout.addView(cantidadMujeres);
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
                dineroView.setText("Ingrese la cantidad de dinero");
                dineroView.setTextAppearance(getApplicationContext(), R.style.boldText);
                layout.addView(dineroView);

                EditText cantidadDinero = new EditText(getApplicationContext());
                cantidadDinero.setGravity(Gravity.CENTER);
                cantidadDinero.setTextColor(Color.WHITE);
                cantidadDinero.setWidth(20);
                cantidadDinero.setHeight(120);
                cantidadDinero.setInputType(InputType.TYPE_CLASS_NUMBER);

                layout.addView(cantidadDinero);
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

                for (int i = 0; i < 2; i++) {
                    rb[i] = new AppCompatRadioButton(this);
                    if (i == 0) {
                        rb[i].setText("Si");
                    } else {
                        rb[i].setText("No");
                    }
                    rg.addView(rb[i]);
                }
                buttonLayout.addView(rg);
                break;
            case 5:
                TextView numeroView = new TextView(getApplicationContext());
                numeroView.setText("Ingrese la cantidad");
                numeroView.setTextAppearance(getApplicationContext(), R.style.boldText);
                layout.addView(numeroView);

                EditText cantidad = new EditText(getApplicationContext());
                cantidad.setGravity(Gravity.CENTER);
                cantidad.setTextColor(Color.WHITE);
                cantidad.setWidth(20);
                cantidad.setHeight(120);
                cantidad.setInputType(InputType.TYPE_CLASS_NUMBER);
                layout.addView(cantidad);
                break;
        }


    }


}
