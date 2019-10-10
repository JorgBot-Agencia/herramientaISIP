package com.formato.isp.GestionEmpresa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class infoDetallada extends AppCompatActivity {

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
    ArrayList<String> infoEmpresa;
    ArrayList<String> infoPersonal;
    RequestQueue queue;
    String url;
    JsonRequest req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_detallada);
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
                        url = resource.URLAPI + "/persona/?empr="+ id;
                        req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    infoPersonal = new ArrayList<>();
                                    JSONArray jsonArr = response.getJSONArray("data");
                                    JSONObject jsonObj = null;
                                    String nom = "" ;
                                    String ape = "" ;
                                    String tel = "" ;
                                    for (int i = 0; i < jsonArr.length(); i++) {
                                        jsonObj = jsonArr.getJSONObject(i);
                                        nom = jsonObj.getString("pers_nombre");
                                        ape = jsonObj.getString("pers_apellido");
                                        tel = jsonObj.getString("pers_telefono");
                                        infoPersonal.add(nom + " " + ape + " - " + tel);
                                    }
                                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, infoPersonal);
                                    lvInfo = (ListView) findViewById(R.id.lv_info);
                                    lvInfo.setAdapter(adapter);

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
                                    Toast.makeText(getApplicationContext(), "No se han registrado pERSONAS", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        queue.add(req);

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
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.colorPrimary);
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
}
