package com.formato.isp.DesarrolloEncuesta;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.util.DisplayMetrics;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.formato.isp.Clases.*;
import com.formato.isp.GestionDocumental.DetalleGestionDocumental;
import com.formato.isp.GestionEmpresa.*;
import com.formato.isp.GestionFundacion.*;
import com.formato.isp.MenuLateral.*;
import com.formato.isp.PDF.*;
import com.formato.isp.model.*;
import com.formato.isp.resource;
import com.formato.isp.utils.*;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.formato.isp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONObject;

public class reporteGrafico extends AppCompatActivity {

    public static final float MAX = 12, MIN = 1f;
    public static final int NB_QUALITIES = 4;
    public static ArrayList<fotoReporte> arrfoto;
    private Context context;
    private File pdfFile;
    private Sesion session;
    private View back_drop;
    private TemplatePDF templatePDF;
    private boolean rotate = false;
    private TextView irInicio;
    private RadarChart chart;
    private View lyt_mic;
    private ArrayList<RadarDataSet> areasyCriterios;
    private String[]header={"Capacitación","Construccion de marca","Gestión de mercados","Acceso a capital"};
    private String[]infor={"Fecha de diligenciamiento", "Diligenciado por:", "Contacto de la unidad"};
    private Bitmap bimatcapture;
    private ProgressDialog p;
    RequestQueue requestQueue;
    JsonObjectRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_grafico);
        initToolbar();
        context = getApplicationContext();
        back_drop = findViewById(R.id.back_drop);
        final FloatingActionButton fab_mic = (FloatingActionButton) findViewById(R.id.fab_mic);
        final FloatingActionButton fab_add = (FloatingActionButton) findViewById(R.id.fab_add);
        lyt_mic = findViewById(R.id.lyt_mic);
        ViewAnimation.initShowOut(lyt_mic);
        back_drop.setVisibility(View.GONE);

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFabMode(v);
            }
        });
        fab_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), menuprincipal.class);
                startActivity(intent);
            }
        });
        areasyCriterios = new ArrayList<>();
        arrfoto= new ArrayList<>();

        irInicio = findViewById(R.id.volver);
        irInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), menuprincipal.class);
                startActivity(intent);
            }
        });

        chart = findViewById(R.id.chart1);
        //chart.setBackgroundColor(Color.rgb(60, 65, 82));
        chart.getDescription().setEnabled(false);
        chart.setWebLineWidth(1f);
        chart.setWebColor(Color.BLACK);
        chart.setWebLineWidth(1f);
        chart.setWebColor(Color.BLACK);
        chart.setWebAlpha(100);
        setTitle("RadarChartActivity");

        setData();
        chart.animateXY(0, 0, Easing.EasingOption.EaseInOutQuad, Easing.EasingOption.EaseInOutQuad);
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
        xAxis.setTextColor(Color.BLACK);
        YAxis yAxis = chart.getYAxis();
        yAxis.setLabelCount(NB_QUALITIES, false);
        yAxis.setAxisMinimum(MIN);
        yAxis.setAxisMaximum(MAX);
        yAxis.setDrawLabels(false);

        Legend l = chart.getLegend();
        l.setEnabled(true);
        l.setTextColor(Color.BLACK);
        l.setTextSize(12f);
        l.setForm(Legend.LegendForm.LINE);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        TemplatePDF tf = new TemplatePDF(this);
        Bitmap bm=loadBitmapFromView(this, this.getWindow().getDecorView().findViewById(android.R.id.content));
        bimatcapture=bm;
        String nom = infoDetallada.dato;
        tf.SaveImage(bm, nom);
        arrfoto.add(new fotoReporte(bm,nom));
        pruebaRegistro(String.valueOf(infoDetallada.idRevision));
    }

    public void viewmPDF(){
        Intent intent= new Intent(context, viewPDF.class);
        intent.putExtra("path",pdfFile.getAbsolutePath());
        intent.addFlags((Intent.FLAG_ACTIVITY_CLEAR_TASK));
        context.startActivity(intent);

    }

    private void toggleFabMode(View v) {
        rotate = ViewAnimation.rotateFab(v, !rotate);
        if (rotate) {
            ViewAnimation.showIn(lyt_mic);
            back_drop.setVisibility(View.VISIBLE);
        } else {
            ViewAnimation.showOut(lyt_mic);
            back_drop.setVisibility(View.GONE);
        }
    }

    private void guardarExcelGraficoRadar() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            //New Workbook
            Workbook wb = new HSSFWorkbook();
            CellStyle style = wb.createCellStyle();
            style.setFillBackgroundColor(IndexedColors.VIOLET.getIndex());
            style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            style.setAlignment(CellStyle.ALIGN_CENTER);
            Cell c = null;
            //New Sheet
            Sheet sheet1 = null;
            sheet1 = wb.createSheet("reporte");
            // Generate column headings
            Row row = sheet1.createRow(0);
            c = row.createCell(0);
            c.setCellValue("Area");
            c.setCellStyle(style);
            c = row.createCell(1);
            c.setCellValue("25%");
            c.setCellStyle(style);
            c = row.createCell(2);
            c.setCellValue("50%");
            c.setCellStyle(style);
            c = row.createCell(3);
            c.setCellValue("75%");
            c.setCellStyle(style);
            c = row.createCell(4);
            c.setCellValue("100%");
            c.setCellStyle(style);

            for (int i = 0; i < areasyCriterios.size(); i++) {
                RadarDataSet rd = areasyCriterios.get(i);
                row = sheet1.createRow(i+1);
                c = row.createCell(0);
                c.setCellValue(rd.getLabel());
                c = row.createCell(1);
                c.setCellValue(rd.getEntryForIndex(0).getValue());
                c = row.createCell(2);
                c.setCellValue(rd.getEntryForIndex(1).getValue());
                c = row.createCell(3);
                c.setCellValue(rd.getEntryForIndex(2).getValue());
                c = row.createCell(4);
                c.setCellValue(rd.getEntryForIndex(3).getValue());
            }

            // Create a path where we will place our List of objects on external storage
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "reporteGrafico" + new Date().toString().replace(" ", "") + ".xls");
            FileOutputStream os = null;

            try {
                os = new FileOutputStream(file);
                wb.write(os);
                Toast.makeText(this,"Creado en: " + file, Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(this,"Error al crear " + file, Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(this,"Falló al guardar " + file, Toast.LENGTH_LONG).show();
            } finally {
                try {
                    if (null != os)
                        os.close();
                } catch (Exception ex) {
                }
            }
        }
    }

    public ArrayList<String[]>getResltEsp(){
        ArrayList<String[]>row= new ArrayList<>();
        row.add(new String[]{"1-Técnica y productiva", "2-Financiera y administrativa","3-Cultura e innovacion",
                "4-Imagen e identidad", "5-Presentacion producto","6-Sello de frontera de oportunidad"
                ,"7-Politica de identificación de precios","8-Acceso a nuevas tecnologias","9-Identificacion de mercados","10-Plan de productividad"
                ,"11-Recursos de inversión"});
        return row;
    }
    public ArrayList<String[]>getinfor(){
        ArrayList<String[]>row= new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaComoCadena = sdf.format(new Date());
        row.add(new String[]{"Fecha: "+fechaComoCadena,"Nombre de la Organización: "+session.getNombreFun(),session.getTelefono(),"Politica de identificación de precios"});
        return row;
    }

    private ArrayList<RadarEntry> dataValues1(int area){
        ArrayList<RadarEntry> dataVals = new ArrayList<RadarEntry>();
        float valor25=0;
        float valor50=0;
        float valor75=0;
        float valor100=0;

        for (int i = 0; i < infoDetallada.acumuladorPreguntas.size(); i++){
            if(infoDetallada.acumuladorPreguntas.get(i).getAreaId() == area){
                float auxiliar = 0;
                if(infoDetallada.acumuladorPreguntas.get(i).getValor() < 26){
                    auxiliar = infoDetallada.acumuladorPreguntas.get(i).getValor() / 5;
                    valor25 = valor25 + auxiliar;
                    valor25 = valor25 / 2;
                }else if(infoDetallada.acumuladorPreguntas.get(i).getValor() < 51){
                    auxiliar = infoDetallada.acumuladorPreguntas.get(i).getValor() / 5;
                    valor50 = valor50 + auxiliar;
                    valor50 = valor50 / 2;
                }else if(infoDetallada.acumuladorPreguntas.get(i).getValor() < 75){
                    auxiliar = infoDetallada.acumuladorPreguntas.get(i).getValor() / 5;
                    valor75 = valor75 + auxiliar;
                    valor75 = valor75 / 2;
                }else if(infoDetallada.acumuladorPreguntas.get(i).getValor() < 101){
                    auxiliar = infoDetallada.acumuladorPreguntas.get(i).getValor() / 5;
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
        areasyCriterios.add(set1);
        set1.setColor(Color.RED);
        set1.setFillColor(Color.RED);
        set1.setDrawFilled(true);
        set1.setFillAlpha(100);
        set1.setLineWidth(2f);
        set1.setDrawHighlightIndicators(false);
        set1.setDrawHighlightCircleEnabled(true);

        RadarDataSet set2 = new RadarDataSet(dataValues1(2), "Financiera y administrativa");
        areasyCriterios.add(set2);
        set2.setColor(Color.GREEN);
        set2.setFillColor(Color.GREEN);
        set2.setDrawFilled(true);
        set2.setFillAlpha(100);
        set2.setLineWidth(2f);
        set2.setDrawHighlightIndicators(false);
        set2.setDrawHighlightCircleEnabled(true);

        RadarDataSet set3 = new RadarDataSet(dataValues1(3), "Cultura empresarial e innovación");
        areasyCriterios.add(set3);
        set3.setColor(Color.MAGENTA);
        set3.setFillColor(Color.MAGENTA);
        set3.setDrawFilled(true);
        set3.setFillAlpha(100);
        set3.setLineWidth(2f);
        set3.setDrawHighlightIndicators(false);
        set3.setDrawHighlightCircleEnabled(true);

        RadarDataSet set4 = new RadarDataSet(dataValues1(4), "Recursos de inversión");
        areasyCriterios.add(set4);
        set4.setColor(Color.YELLOW);
        set4.setFillColor(Color.YELLOW);
        set4.setDrawFilled(true);
        set4.setFillAlpha(100);
        set4.setLineWidth(2f);
        set4.setDrawHighlightIndicators(false);
        set4.setDrawHighlightCircleEnabled(true);

        RadarDataSet set5 = new RadarDataSet(dataValues1(5), "Imagen e identidad corporativa");
        areasyCriterios.add(set5);
        set5.setColor(Color.RED);
        set5.setFillColor(Color.RED);
        set5.setDrawFilled(true);
        set5.setFillAlpha(100);
        set5.setLineWidth(2f);
        set5.setDrawHighlightIndicators(false);
        set5.setDrawHighlightCircleEnabled(true);

        RadarDataSet set6 = new RadarDataSet(dataValues1(6), "Presentación de producto");
        areasyCriterios.add(set6);
        set6.setColor(Color.BLACK);
        set6.setFillColor(Color.BLACK);
        set6.setDrawFilled(true);
        set6.setFillAlpha(100);
        set6.setLineWidth(2f);
        set6.setDrawHighlightIndicators(false);
        set6.setDrawHighlightCircleEnabled(true);

        RadarDataSet set7 = new RadarDataSet(dataValues1(7), "Sellos de calidad");
        areasyCriterios.add(set7);
        set7.setColor(Color.DKGRAY);
        set7.setFillColor(Color.DKGRAY);
        set7.setDrawFilled(true);
        set7.setFillAlpha(100);
        set7.setLineWidth(2f);
        set7.setDrawHighlightIndicators(false);
        set7.setDrawHighlightCircleEnabled(true);

        RadarDataSet set8 = new RadarDataSet(dataValues1(8), "Politica de identificación de precios");
        areasyCriterios.add(set8);
        set8.setColor(Color.GRAY);
        set8.setFillColor(Color.GRAY);
        set8.setDrawFilled(true);
        set8.setFillAlpha(100);
        set8.setLineWidth(2f);
        set8.setDrawHighlightIndicators(false);
        set8.setDrawHighlightCircleEnabled(true);

        RadarDataSet set9 = new RadarDataSet(dataValues1(9), "Acceso a nuevas tecnologías");
        areasyCriterios.add(set9);
        set9.setColor(Color.YELLOW);
        set9.setFillColor(Color.YELLOW);
        set9.setDrawFilled(true);
        set9.setFillAlpha(100);
        set9.setLineWidth(2f);
        set9.setDrawHighlightIndicators(false);
        set9.setDrawHighlightCircleEnabled(true);

        RadarDataSet set10 = new RadarDataSet(dataValues1(10), "Identificación de posibles mercados");
        areasyCriterios.add(set10);
        set10.setColor(Color.BLUE);
        set10.setFillColor(Color.BLUE);
        set10.setDrawFilled(true);
        set10.setFillAlpha(100);
        set10.setLineWidth(2f);
        set10.setDrawHighlightIndicators(false);
        set10.setDrawHighlightCircleEnabled(true);

        RadarDataSet set11 = new RadarDataSet(dataValues1(11), "Plan de productividad y empleo");
        areasyCriterios.add(set11);
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
        data.setValueTextColor(Color.BLACK);

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
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"Utilice las opciones proporcionadas por la aplicación", Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Toast.makeText(context,"No puede volver para atras, oprima el botón si desea ir a inicio",Toast.LENGTH_LONG).show();
        }else if(item.getTitle().equals("Generar Excel")){
            guardarExcelGraficoRadar();
        }else if(item.getTitle().equals("Generar PDF")){
            //Evento para generar PDF
            Empresa empr_select= Adaptador.empr_select;
            templatePDF= new TemplatePDF(context);
            templatePDF.OpenDocument();
            templatePDF.addMetadata("Informe de resultados", "Informe de encuesta ISIP","ISIP");
            session = new Sesion(context);
            Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.banner);
            templatePDF.creartablaimagencentra(icon, "logofrontera", "encabezado");
            Bitmap icon2 = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.isip);
            templatePDF.creartablaimagencentra(icon2, "isip","normal");
            templatePDF.addTitulos("","","");
            templatePDF.addTitulosizq("Nombre de la unidad: "+ empr_select.getEmpr_nombre(),  empr_select.getEmpr_direccion(),empr_select.getEmpr_telefono()," ");
            templatePDF.addletraroja("__________________________________________________________");
            templatePDF.addtitulo("ISIP");

            templatePDF.creartabla(infor, getinfor());
            templatePDF.addtitulo("Descripcion de unidad Productiva");
            templatePDF.addparrafo(empr_select.getEmpr_descripcion());
            templatePDF.addtitulo("RESULTADOS");
            templatePDF.addtitulocentrado("Resultado total");
            ArrayList<fotoReporte> foto = reporteGrafico.arrfoto;
            if(foto!=null) {
                int cc = 0;
                for (int i = 0; i < foto.size(); i++) {
                    if (foto.get(i).getNomb_empr().equals(empr_select.getEmpr_nombre())) {
                        templatePDF.creartablaimagenGRAFICA(foto.get(i).getBitmabEmpr(), "MIINFORME");
                        cc++;
                    }
                }
            }else{
                templatePDF.addparrafo("La empresa aun no cuenta con un reporte estadistico, debe diligenciar la encuesta...");
            }
            templatePDF.addtitulo("Resultados especificos");
            //templatePDF.creartablapers(header,getResltEsp(),1);
            templatePDF.creartablapers3(header,getResltEsp(), (ArrayList<Area>) menuEncuesta.areasEncuestadas);
            templatePDF.closeDocument();
            Toast.makeText(context,"PDF generado y guardado en "+Environment.getExternalStorageDirectory().toString()+"/recursosisp/",Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public static Bitmap loadBitmapFromView(Context context, View v) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        v.measure(View.MeasureSpec.makeMeasureSpec(dm.widthPixels, View.MeasureSpec.AT_MOST),
                View.MeasureSpec.makeMeasureSpec(dm.heightPixels, View.MeasureSpec.EXACTLY));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        Bitmap returnedBitmap = Bitmap.createBitmap(v.getMeasuredWidth(),
                v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(returnedBitmap);
        v.draw(c);
        return returnedBitmap;
    }

    private void pruebaRegistro(String id) {
        // loading or check internet connection or something...
        // ... then
        String url = resource.URLAPI + "/revision/"+id;
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.PUT, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                Toast.makeText(context, "Revisión Registrada", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error al registrar reporte de revision: "+error.getMessage(), Toast.LENGTH_LONG).show();

                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    if (networkResponse.statusCode == 404) {
                        errorMessage = "Resource not found";
                    } else if (networkResponse.statusCode == 401) {
                        errorMessage = " Please login again";
                    } else if (networkResponse.statusCode == 400) {
                        errorMessage = " Check your inputs";
                    } else if (networkResponse.statusCode == 500) {
                        errorMessage = " Something is getting wrong";
                    }
                }
                Log.i("Error", errorMessage);
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bimatcapture.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
                params.put("revi_imagen", new DataPart("file_avatar.png", byteArrayOutputStream.toByteArray(), "image/jpeg"));
                return params;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(multipartRequest);
    }
}
