package com.formato.isp.DesarrolloEncuesta;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.formato.isp.utils.Tools;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;

import java.lang.reflect.Array;
import java.util.ArrayList;

import com.formato.isp.R;

public class reporteGrafico extends AppCompatActivity {

    public static final float MAX = 12, MIN = 1f;
    public static final int NB_QUALITIES = 4;

    private RadarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_grafico);
        initToolbar();

        chart = findViewById(R.id.chart1);
        chart.setBackgroundColor(Color.rgb(60, 65, 82));
        chart.getDescription().setEnabled(false);
        chart.setWebLineWidth(1f);
        chart.setWebColor(Color.WHITE);
        chart.setWebLineWidth(1f);
        chart.setWebColor(Color.WHITE);
        chart.setWebAlpha(100);
        setTitle("RadarChartActivity");

        setData();
        chart.animateXY(1400, 1400, Easing.EasingOption.EaseInOutQuad, Easing.EasingOption.EaseInOutQuad);
        XAxis xAxis = chart.getXAxis();
        xAxis.setTextSize(9f);
        xAxis.setYOffset(0);
        xAxis.setXOffset(0);
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            private String[] qualities = new String[]{"Nivel critico", "Nivel básico", "Nivel intermedio", "Nivel avanzado"};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return qualities[(int) value % qualities.length];
            }
        });
        xAxis.setTextColor(Color.WHITE);
        YAxis yAxis = chart.getYAxis();
        yAxis.setLabelCount(NB_QUALITIES, false);
        yAxis.setAxisMinimum(MIN);
        yAxis.setAxisMaximum(MAX);
        yAxis.setDrawLabels(false);

        Legend l = chart.getLegend();
        l.setTextSize(15f);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        l.setTextColor(Color.WHITE);
    }

    private ArrayList<RadarEntry> dataValues1(int area){
        ArrayList<RadarEntry> dataVals = new ArrayList<RadarEntry>();
        float valor25=0;
        float valor50=0;
        float valor75=0;
        float valor100=0;

        for (int i = 0; i < preguntasEncuesta.listaPreguntas.size(); i++){
            if(preguntasEncuesta.listaPreguntas.get(i).getAreaId() == area){
                float auxiliar = 0;
                if(preguntasEncuesta.listaPreguntas.get(i).getValor() < 26){
                    auxiliar = preguntasEncuesta.listaPreguntas.get(i).getValor() / 5;
                    valor25 = valor25 + auxiliar;
                    valor25 = valor25 / 2;
                }else if(preguntasEncuesta.listaPreguntas.get(i).getValor() < 51){
                    auxiliar = preguntasEncuesta.listaPreguntas.get(i).getValor() / 5;
                    valor50 = valor50 + auxiliar;
                    valor50 = valor50 / 2;
                }else if(preguntasEncuesta.listaPreguntas.get(i).getValor() < 75){
                    auxiliar = preguntasEncuesta.listaPreguntas.get(i).getValor() / 5;
                    valor75 = valor75 + auxiliar;
                    valor75 = valor75 / 2;
                }else if(preguntasEncuesta.listaPreguntas.get(i).getValor() < 101){
                    auxiliar = preguntasEncuesta.listaPreguntas.get(i).getValor() / 5;
                    valor100 = valor100 + auxiliar;
                    valor100 = valor100 / 2;
                }
            }
        }
        dataVals.add(new RadarEntry(valor25));
        dataVals.add(new RadarEntry(valor50));
        dataVals.add(new RadarEntry(valor75));
        dataVals.add(new RadarEntry(valor100));
        return dataVals;
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbarGrafico);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.colorPrimary);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setData() {

        RadarDataSet set1 = new RadarDataSet(dataValues1(1), "Técnico y productiva");
        set1.setColor(Color.RED);
        set1.setFillColor(Color.RED);
        set1.setDrawFilled(true);
        set1.setFillAlpha(100);
        set1.setLineWidth(2f);
        set1.setDrawHighlightIndicators(false);
        set1.setDrawHighlightCircleEnabled(true);

        RadarDataSet set2 = new RadarDataSet(dataValues1(2), "Financiera y administrativa");
        set2.setColor(Color.GREEN);
        set2.setFillColor(Color.GREEN);
        set2.setDrawFilled(true);
        set2.setFillAlpha(100);
        set2.setLineWidth(2f);
        set2.setDrawHighlightIndicators(false);
        set2.setDrawHighlightCircleEnabled(true);

        RadarDataSet set3 = new RadarDataSet(dataValues1(3), "Cultura empresarial e innovación");
        set3.setColor(Color.MAGENTA);
        set3.setFillColor(Color.MAGENTA);
        set3.setDrawFilled(true);
        set3.setFillAlpha(100);
        set3.setLineWidth(2f);
        set3.setDrawHighlightIndicators(false);
        set3.setDrawHighlightCircleEnabled(true);

        RadarDataSet set4 = new RadarDataSet(dataValues1(4), "Recursos de inversión");
        set4.setColor(Color.YELLOW);
        set4.setFillColor(Color.YELLOW);
        set4.setDrawFilled(true);
        set4.setFillAlpha(100);
        set4.setLineWidth(2f);
        set4.setDrawHighlightIndicators(false);
        set4.setDrawHighlightCircleEnabled(true);

        RadarDataSet set5 = new RadarDataSet(dataValues1(5), "Imagen e identidad corporativa");
        set5.setColor(Color.RED);
        set5.setFillColor(Color.RED);
        set5.setDrawFilled(true);
        set5.setFillAlpha(100);
        set5.setLineWidth(2f);
        set5.setDrawHighlightIndicators(false);
        set5.setDrawHighlightCircleEnabled(true);

        RadarDataSet set6 = new RadarDataSet(dataValues1(6), "Presentación de producto");
        set6.setColor(Color.BLACK);
        set6.setFillColor(Color.BLACK);
        set6.setDrawFilled(true);
        set6.setFillAlpha(100);
        set6.setLineWidth(2f);
        set6.setDrawHighlightIndicators(false);
        set6.setDrawHighlightCircleEnabled(true);

        RadarDataSet set7 = new RadarDataSet(dataValues1(7), "Sellos de calidad");
        set7.setColor(Color.DKGRAY);
        set7.setFillColor(Color.DKGRAY);
        set7.setDrawFilled(true);
        set7.setFillAlpha(100);
        set7.setLineWidth(2f);
        set7.setDrawHighlightIndicators(false);
        set7.setDrawHighlightCircleEnabled(true);

        RadarDataSet set8 = new RadarDataSet(dataValues1(8), "Politica de identificación de precios");
        set8.setColor(Color.GRAY);
        set8.setFillColor(Color.GRAY);
        set8.setDrawFilled(true);
        set8.setFillAlpha(100);
        set8.setLineWidth(2f);
        set8.setDrawHighlightIndicators(false);
        set8.setDrawHighlightCircleEnabled(true);

        RadarDataSet set9 = new RadarDataSet(dataValues1(9), "Acceso a nuevas tecnologías");
        set9.setColor(Color.YELLOW);
        set9.setFillColor(Color.YELLOW);
        set9.setDrawFilled(true);
        set9.setFillAlpha(100);
        set9.setLineWidth(2f);
        set9.setDrawHighlightIndicators(false);
        set9.setDrawHighlightCircleEnabled(true);

        RadarDataSet set10 = new RadarDataSet(dataValues1(10), "Identificación de posibles mercados");
        set10.setColor(Color.BLUE);
        set10.setFillColor(Color.BLUE);
        set10.setDrawFilled(true);
        set10.setFillAlpha(100);
        set10.setLineWidth(2f);
        set10.setDrawHighlightIndicators(false);
        set10.setDrawHighlightCircleEnabled(true);

        RadarDataSet set11 = new RadarDataSet(dataValues1(11), "Plan de productividad y empleo");
        set11.setColor(Color.CYAN);
        set11.setFillColor(Color.CYAN);
        set11.setDrawFilled(true);
        set11.setFillAlpha(100);
        set11.setLineWidth(2f);
        set11.setDrawHighlightIndicators(false);
        set11.setDrawHighlightCircleEnabled(true);

        RadarData data = new RadarData();
        data.addDataSet(set1);
        data.addDataSet(set2);
        data.addDataSet(set3);
        data.addDataSet(set4);
        data.addDataSet(set5);
        data.addDataSet(set6);
        data.addDataSet(set7);
        data.addDataSet(set8);
        data.addDataSet(set9);
        data.addDataSet(set10);
        data.addDataSet(set11);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.WHITE);

        chart.setData(data);
        chart.invalidate();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.radar, menu);
        Tools.changeMenuIconColor(menu, getResources().getColor(R.color.colorPrimary));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
