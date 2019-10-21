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
import android.widget.ImageButton;
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
import com.anychart.core.axes.Linear;
import com.formato.isp.Clases.Area;
import com.formato.isp.GestionEmpresa.infoDetallada;
import com.formato.isp.Pregunta;
import com.formato.isp.R;
import com.formato.isp.utils.*;
import com.google.android.material.textfield.TextInputEditText;
import com.itextpdf.text.pdf.parser.Line;

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
    public static String checked = "";

    //Respuestas

    private TextView cantidadPorcentajeRespuesta;
    private SeekBar porcentajeSeekBar;
    private EditText cantidadDineroRespuesta;
    private ImageButton imagenDinero;
    private EditText cantidadNumeroRespuesta;
    private ImageButton imagenCantidad;
    private EditText cantidadHombresRespuesta;
    private LinearLayout linearHombres;
    private LinearLayout linearMujeres;
    private LinearLayout linearCantidad;
    private LinearLayout linearDinero;
    private LinearLayout linearPorcentaje;
    private ImageButton imagenHombre;
    private ImageButton imagenMujer;
    private EditText cantidadMujeresRespuesta;

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

        //Respuestas
        linearHombres = (LinearLayout) findViewById(R.id.layout_hombre);
        imagenHombre = (ImageButton) findViewById(R.id.imagen_hombre);
        cantidadHombresRespuesta = (EditText) findViewById(R.id.cantidadHombresRespuesta);
        imagenMujer = (ImageButton) findViewById(R.id.imagen_mujer);
        linearMujeres = (LinearLayout) findViewById(R.id.layout_mujer);
        cantidadMujeresRespuesta = (EditText) findViewById(R.id.cantidadMujeresRespuesta);
        ocultarCamposHM();

        cantidadPorcentajeRespuesta = (TextView) findViewById(R.id.cantidadPorcentajeRespuesta);
        linearPorcentaje = (LinearLayout) findViewById(R.id.layout_porcentaje);
        porcentajeSeekBar = (SeekBar) findViewById(R.id.seekbar);
        ocultarCamposPorcentaje();

        cantidadDineroRespuesta = (EditText) findViewById(R.id.cantidadDineroRespuesta);
        linearDinero = (LinearLayout) findViewById(R.id.layout_dinero);
        imagenDinero = (ImageButton) findViewById(R.id.imagen_dinero);
        ocultarCamposDinero();

        cantidadNumeroRespuesta = (EditText) findViewById(R.id.cantidadNumeroRespuesta);
        linearCantidad = (LinearLayout) findViewById(R.id.layout_cantidad);
        imagenCantidad = (ImageButton) findViewById(R.id.imagen_cantidad);
        ocultarCamposCantidad();
        //

        queue = Volley.newRequestQueue(this);
        numeroArea = getIntent().getExtras().getInt("areaId");
        listaPreguntas = new ArrayList<>();
        areaProceso = new Area(numeroArea, 0, 0, 0);
        obtenerPreguntas();
    }

    private void ocultarCamposHM() {
        linearHombres.setVisibility(View.GONE);
        imagenHombre.setVisibility(View.GONE);
        cantidadHombresRespuesta.setVisibility(View.GONE);
        linearMujeres.setVisibility(View.GONE);
        imagenMujer.setVisibility(View.GONE);
        cantidadMujeresRespuesta.setVisibility(View.GONE);
    }

    private void habilitarCamposHM() {
        linearHombres.setVisibility(View.VISIBLE);
        imagenHombre.setVisibility(View.VISIBLE);
        cantidadHombresRespuesta.setVisibility(View.VISIBLE);
        linearMujeres.setVisibility(View.VISIBLE);
        imagenMujer.setVisibility(View.VISIBLE);
        cantidadMujeresRespuesta.setVisibility(View.VISIBLE);
    }

    private void ocultarCamposPorcentaje() {
        linearPorcentaje.setVisibility(View.GONE);
        cantidadPorcentajeRespuesta.setVisibility(View.GONE);
        porcentajeSeekBar.setVisibility(View.GONE);
    }

    private void habilitarCamposPorcentaje() {
        linearPorcentaje.setVisibility(View.VISIBLE);
        cantidadPorcentajeRespuesta.setVisibility(View.VISIBLE);
        porcentajeSeekBar.setVisibility(View.VISIBLE);
    }

    private void ocultarCamposDinero() {
        linearDinero.setVisibility(View.GONE);
        cantidadDineroRespuesta.setVisibility(View.GONE);
        imagenDinero.setVisibility(View.GONE);
    }

    private void habilitarCamposDinero() {
        linearDinero.setVisibility(View.VISIBLE);
        cantidadDineroRespuesta.setVisibility(View.VISIBLE);
        imagenDinero.setVisibility(View.VISIBLE);
    }

    private void ocultarCamposCantidad() {
        linearCantidad.setVisibility(View.GONE);
        cantidadNumeroRespuesta.setVisibility(View.GONE);
        imagenCantidad.setVisibility(View.GONE);
    }

    private void habilitarCamposCantidad() {
        linearCantidad.setVisibility(View.VISIBLE);
        cantidadNumeroRespuesta.setVisibility(View.VISIBLE);
        imagenCantidad.setVisibility(View.VISIBLE);
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
                            listaPreguntas.add(new Pregunta(jsonPreguntum.getInt("preg_id"), jsonPreguntum.getString("preg_contenido"), jsonPreguntum.getString("preg_descrip"), jsonIndicador.getInt("indi_id"), jsonIndicador.getString("indi_contenido"), jsonObject.getInt("crit_id"), numeroArea, 0, ""));
                        }
                    }
                    MAX_STEP = listaPreguntas.size();
                    progressBar.setMax(MAX_STEP);
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

    private void enviarRespuesta(int parametro) {
        for (int i = 0; i < listaPreguntas.size(); i++) {
            if (i == parametro) {
                switch (listaPreguntas.get(i).getCriterio()) {
                    case 1:
                        listaPreguntas.get(i).setRespuesta(cantidadHombresRespuesta.getText().toString() + "-" + cantidadMujeresRespuesta.getText().toString());
                        cantidadMujeresRespuesta.setText("");
                        cantidadHombresRespuesta.setText("");
                        break;
                    case 2:
                        listaPreguntas.get(i).setRespuesta(cantidadPorcentajeRespuesta.getText().toString());
                        cantidadPorcentajeRespuesta.setText("0");
                        porcentajeSeekBar.setProgress(0);
                        break;
                    case 3:
                        listaPreguntas.get(i).setRespuesta(cantidadDineroRespuesta.getText().toString());
                        cantidadDineroRespuesta.setText("");
                        break;
                    case 4:
                        listaPreguntas.get(i).setRespuesta(checked);
                        break;
                    case 5:
                        listaPreguntas.get(i).setRespuesta(cantidadNumeroRespuesta.getText().toString());
                        cantidadNumeroRespuesta.setText("");
                        break;
                }
            }
        }
    }

    private void nextStep(int progress) {
        txtAdicional.setText("");
        indicadorContenido.setText("Indicador de la pregunta");
        //indicadorId.setText("Número de indicador");
        descripcionPregunta.setText("Descripción de la pregunta");
        contenidoPregunta.setText("Formulación de pregunta");
        status.setText("Número de pregunta");
        progressBar.setProgress(progress);

        if (progress < MAX_STEP) {
            //status.setText("PREGUNTA " + obtenerId(progress));
            status.setText("PREGUNTA " + cc++);
            contenidoPregunta.setText(obtenerContenido(progress));
            indicadorContenido.setText(obtenerContenidoIndicador(progress));
            descripcionPregunta.setText(obtenerDescripcion(progress));
            //indicadorId.setText("INDICADOR " + obtenerIdIndicador(progress));
            crearComponente(obtenerIdCriterio(progress));

            if (progress > 0) {
                areaProceso.setAreaAvance(progress);
                asignarValor(valor, progress - 1);
                enviarRespuesta(progress - 1);
                if (!marcarGrupo) {
                    Toast.makeText(getApplicationContext(), "Debe asignar una escala a este indicador.", Toast.LENGTH_SHORT).show();
                }
            }
            progress = progress + 1;
            current_step = progress;
            ViewAnimation.fadeOutIn(status);

        } else {
            areaProceso.setAreaAvance(areaProceso.getAreaAvance()+1);
            infoDetallada.acumuladorPreguntas.addAll(listaPreguntas);
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
        progressBar.setProgress(progress);

        if (progress < MAX_STEP) {
            status.setText("PREGUNTA " + cc++);
            contenidoPregunta.setText(obtenerContenido(progress));
            //indicadorContenido.setText(obtenerContenidoIndicador(progress));
            descripcionPregunta.setText(obtenerDescripcion(progress));
            //indicadorId.setText("INDICADOR " + obtenerIdIndicador(progress));
            crearComponente(obtenerIdCriterio(progress));

            progress = progress + 1;
            current_step = progress;
            ViewAnimation.fadeOutIn(status);
        } else {
            infoDetallada.acumuladorPreguntas.addAll(listaPreguntas);
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
                ocultarCamposPorcentaje();
                ocultarCamposCantidad();
                ocultarCamposDinero();
                habilitarCamposHM();
                break;
            case 2:
                habilitarCamposPorcentaje();
                ocultarCamposCantidad();
                ocultarCamposDinero();
                ocultarCamposHM();
                porcentajeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        cantidadPorcentajeRespuesta.setText("" + progress);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                break;
            case 3:
                ocultarCamposPorcentaje();
                ocultarCamposHM();
                ocultarCamposCantidad();
                habilitarCamposDinero();
                break;
            case 4:
                ocultarCamposCantidad();
                ocultarCamposDinero();
                ocultarCamposPorcentaje();
                ocultarCamposHM();

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
                        rb[i].setId(R.id.radioSi);
                    } else {
                        rb[i].setText("No");
                        rb[i].setId(R.id.radioNo);
                    }
                    rg.addView(rb[i]);
                }
                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        switch (i) {
                            case R.id.radioSi:
                                checked = "Si";
                                break;
                            case R.id.radioNo:
                                checked = "No";
                                break;
                        }
                    }
                });
                buttonLayout.addView(rg);
                break;
            case 5:
                habilitarCamposCantidad();
                ocultarCamposDinero();
                ocultarCamposHM();
                ocultarCamposPorcentaje();
                break;
        }


    }


}
