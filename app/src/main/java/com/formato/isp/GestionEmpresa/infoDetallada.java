package com.formato.isp.GestionEmpresa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.formato.isp.DesarrolloEncuesta.menuEncuesta;
import com.formato.isp.R;
import com.formato.isp.resource;
import com.formato.isp.utils.Tools;
import com.github.mikephil.charting.data.RadarDataSet;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class infoDetallada extends AppCompatActivity {
    public static String dato;
    private Button btnIniciar;
    private Button btnRegistroPersona;
    private ViewPager view_pager;
    private TabLayout tab_layout;
    private String id;
    private String nit;
    private String nom;
    private String ubicacion;
    private String dep;
    private String tel;
    private String sitioweb;
    private String fecha_ini;
    private String fecha_crea;
    private TextView nombre_empr;
    private TextView ubicacion_empr;
    private AppBarLayout abl;
    private ListView lvInfo;
    private int n;
    ArrayList<String> infoEmpresa;
    ArrayList<String> infoPersonal;
    RequestQueue queue;
    String url;
    JsonRequest req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_detallada);
        Bundle bundle = getIntent().getExtras();
        dato=bundle.getString("name");
        initToolbar();
        initComponent();
        cargarPref();
        datosempresa();
        queue = Volley.newRequestQueue(this);
        btnIniciar = findViewById(R.id.btn_start);
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abrirEncuesta = new Intent(view.getContext(), menuEncuesta.class);
                startActivity(abrirEncuesta);
            }
        });
        btnRegistroPersona = (Button)findViewById(R.id.registro_persona);
        btnRegistroPersona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirEncuesta = new Intent(v.getContext(), registroPersona.class);
                startActivity(abrirEncuesta);
            }
        });
        nombre_empr = (TextView)findViewById(R.id.nombre_empresa);
        ubicacion_empr = (TextView)findViewById(R.id.ubicacion_empresa);
        nombre_empr.setText(nom);
        ubicacion_empr.setText(ubicacion);

        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //Toast.makeText(getApplicationContext(),tab.getText() + "1",Toast.LENGTH_SHORT).show();
                switch (tab.getText().toString()){
                    case ("Información General"):
                        datosempresa();
                        break;
                    case ("Personal"):
                        preguntaExcell();
                        datoPersonal();
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //Toast.makeText(getApplicationContext(),tab.getText() + "2",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //Toast.makeText(getApplicationContext(),tab.getText() + "3",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_info);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.colorPrimary);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private void initComponent() {
        view_pager = (ViewPager) findViewById(R.id.view_pager);
        setupViewPager(view_pager);
        tab_layout = (TabLayout) findViewById(R.id.tab_layout);
        tab_layout.setupWithViewPager(view_pager);


    }
    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(PlaceholderFragment.newInstance(1), "Información General");
        adapter.addFragment(PlaceholderFragment.newInstance(2), "Personal");
        viewPager.setAdapter(adapter);
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tabs_basic, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public void cargarPref(){
        SharedPreferences pref = getSharedPreferences("nitEmpresa", Context.MODE_PRIVATE);
        id = pref.getString("ID", "No existe");
        nit = pref.getString("NIT","No existe");
        nom = pref.getString("NOMBRE","No existe");
        ubicacion = pref.getString("UBICACION","No existe");
        dep = pref.getString("DEPARTAMENTO","No existe");
        tel = pref.getString("TELEFONO","No existe");
        sitioweb = pref.getString("SITIOWEB","No existe");
        fecha_crea = pref.getString("F_CREA","No existe");
        fecha_ini = pref.getString("F_INI","No existe");
    }

    public void datosempresa(){
        infoEmpresa = new ArrayList<>();
        infoEmpresa.add("Nombre: " + nom);
        infoEmpresa.add("Ubicación: " + ubicacion + " - " + dep);
        infoEmpresa.add("Teléfono: " + tel);
        infoEmpresa.add("Sitio Web: " + sitioweb);
        infoEmpresa.add("Fecha de Creación: " + fecha_crea);
        infoEmpresa.add("Fecha de Inicio: " + fecha_ini);
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, infoEmpresa);
        lvInfo = (ListView) findViewById(R.id.lv_info);
        lvInfo.setAdapter(adapter);
    }

    public void datoPersonal(){
        url = resource.URLAPI + "/persona/?empr="+ id;
        req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (android.os.Build.VERSION.SDK_INT > 9) {
                        infoPersonal = new ArrayList<>();
                        JSONArray jsonArr = response.getJSONArray("data");
                        JSONObject jsonObj = null;
                        String nombre = "" ;
                        String ape = "" ;
                        String tel = "" ;
                        //EXCELL
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
                        c.setCellValue("Documento");
                        c.setCellStyle(style);
                        c = row.createCell(1);
                        c.setCellValue("Nombres");
                        c.setCellStyle(style);
                        c = row.createCell(2);
                        c.setCellValue("Apellidos");
                        c.setCellStyle(style);
                        c = row.createCell(3);
                        c.setCellValue("Teléfono");
                        c.setCellStyle(style);
                        c = row.createCell(4);
                        c.setCellValue("Dirección");
                        c.setCellStyle(style);
                        for (int i = 0; i < jsonArr.length(); i++) {
                            jsonObj = jsonArr.getJSONObject(i);
                            nombre = jsonObj.getString("pers_nombre");
                            ape = jsonObj.getString("pers_apellido");
                            tel = jsonObj.getString("pers_telefono");
                            infoPersonal.add(nombre + " " + ape + " - " + tel);
                            row = sheet1.createRow(i+1);
                            c = row.createCell(0);
                            c.setCellValue(jsonObj.getString("pers_documento"));
                            c = row.createCell(1);
                            c.setCellValue(nombre);
                            c = row.createCell(2);
                            c.setCellValue(ape);
                            c = row.createCell(3);
                            c.setCellValue(tel);
                            c = row.createCell(4);
                            c.setCellValue(jsonObj.getString("pers_direccion"));
                        }
                        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, infoPersonal);
                        lvInfo = (ListView) findViewById(R.id.lv_info);
                        lvInfo.setAdapter(adapter);
                        if(n == 1){
                            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "PersonalDeLaEmpresa" + nom + ".xls");
                            FileOutputStream os = null;

                            try {
                                os = new FileOutputStream(file);
                                wb.write(os);
                                Toast.makeText(getApplicationContext(),"Creado en: " + file, Toast.LENGTH_LONG).show();
                            } catch (IOException e) {
                                Toast.makeText(getApplicationContext(),"Error al crear " + file, Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(),"Falló al guardar " + file, Toast.LENGTH_LONG).show();
                            } finally {
                                try {
                                    if (null != os)
                                        os.close();
                                } catch (Exception ex) {
                                }
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(), "Por favor verifica tu conexión a internet", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No se han registrado personas", Toast.LENGTH_SHORT).show();
                }
            }
        });
        queue.add(req);

    }

    public void preguntaExcell(){
        n = 0;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Desea exportar un Excell con todos los datos del Personal?").setCancelable(false)
                .setPositiveButton(Html.fromHtml("<font color='#4784ba'>Cancelar</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        n = 0;
                        dialog.cancel();
                    }
                })
                .setNegativeButton(Html.fromHtml("<font color='#4784ba'>Aceptar</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        n = 1;
                        datoPersonal();
                    }
                });
        AlertDialog des = builder.create();
        des.setTitle("Exportar datos");
        des.show();
    }

}
