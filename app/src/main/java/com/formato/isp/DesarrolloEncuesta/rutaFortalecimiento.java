package com.formato.isp.DesarrolloEncuesta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.formato.isp.GestionEmpresa.ThreeLevelListAdapter;
import com.formato.isp.R;
import com.formato.isp.utils.Tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class rutaFortalecimiento extends AppCompatActivity  {

    private ExpandableListView expandableListView;

    String[] parent = new String[]{"CAPACITACIÓN", "GESTIÓN DE MERCADOS", "CONSTRUCCIÓN DE MARCA", "ACCESO A CAPITAL"};
    String[] q1 = new String[]{"Técnica y Productiva", "Financiera y Administrativa", "Cultura e Innovación"};
    String[] q2 = new String[]{"Plan de Productividad", "Identificación de Mercados", "Acceso a Nuevas Tecnologías", "Política de Identificación de Precios"};
    String[] q3 = new String[]{"Imagen e Identidad", "Presentación de Producto", "Sello Frontera de Oportunidades"};
    String[] q4 = new String[]{"Créditos", "Capitales Reembolsables", "Capitales No Reembolsables"};
    String[] des1 = new String[]{"Servicio Nacional de Aprendizaje - SENA", "Universidad Francisco de Paula Santander - UFPS", "Deutsche Gesellschaft für Internationale Zusammenarbeit - GIZ"};
    String[] des2 = new String[]{"Servicio Nacional de Aprendizaje - SENA", "Universidad Francisco de Paula Santander - UFPS", "Universidad Simón Bolivar - UNISIM", "Federación Nacional de Comerciantes - FENALCO"};
    String[] des3 = new String[]{"Universidad Simón Bolivar - UNISIM","Universidad Libre - UNILIBR","Ministerio de Tecnologías de la Información y Comunicaciones - MINTIC" , "Deutsche Gesellschaft für Internationale Zusammenarbeit - GIZ", "Federación Nacional de Comerciantes - FENALCO"};
    String[] des4 = new String[]{"Servicio Nacional de Aprendizaje - SENA", "Universidad Francisco de Paula Santander - UFPS", "Universidad Simón Bolivar - UNISIM","Universidad Libre - UNILIBR", "Federación Nacional de Comerciantes - FENALCO"};
    String[] des5 = new String[]{"Universidad Simón Bolivar - UNISIM","Federación Nacional de Comerciantes - FENALCO", "Asociación Nacional de Empresarios de Colombia - ANDI"};
    String[] des6 = new String[]{"Ministerio de Tecnologías de la Información y Comunicaciones - MINTIC" ,"Asociación Nacional de Empresarios de Colombia - ANDI"};
    String[] des7 = new String[]{"Deutsche Gesellschaft für Internationale Zusammenarbeit - GIZ", "Programa de las Naciones Unidas para el Desarrollo - PNUD"};
    String[] des8 = new String[]{"Procolombia - PROCOL", "Universidad Simón Bolivar - UNISIM"};
    String[] des9 = new String[]{"Procolombia - PROCOL"};
    String[] des10 = new String[]{"Programa de las Naciones Unidas para el Desarrollo - PNUD"};
    String[] des11 = new String[]{"INPUL", "BANCA PR"};
    String[] des12 = new String[]{"Universidad Libre - UNILIBR", "Ministerio de Tecnologías de la Información y Comunicaciones - MINTIC", "INPUL", "BANCA PR"};
    String[] des13 = new String[]{"Deutsche Gesellschaft für Internationale Zusammenarbeit - GIZ","World Vision - World V", "Programa de las Naciones Unidas para el Desarrollo - PNUD", "Organización Internacional Para las Migraciones - OIM", "Universidad Libre - UNILIBR"};

    LinkedHashMap<String, String[]> thirdLevelq1 = new LinkedHashMap<>();
    LinkedHashMap<String, String[]> thirdLevelq2 = new LinkedHashMap<>();
    LinkedHashMap<String, String[]> thirdLevelq3 = new LinkedHashMap<>();
    LinkedHashMap<String, String[]> thirdLevelq4 = new LinkedHashMap<>();
    /**
     * Second level array list
     */
    List<String[]> secondLevel = new ArrayList<>();
    /**
     * Inner level data
     */
    List<LinkedHashMap<String, String[]>> data = new ArrayList<>();



    ///////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruta_fortalecimiento);
        initToolbar();
        setUpAdapter();

    }
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_persona);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Ruta de Fortalecimiento");
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

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"Utilice las opciones proporcionadas por la aplicación", Toast.LENGTH_SHORT).show();
    }

    private void setUpAdapter() {
        secondLevel.add(q1);
        secondLevel.add(q2);
        secondLevel.add(q3);
        secondLevel.add(q4);
        thirdLevelq1.put(q1[0], des1);
        thirdLevelq1.put(q1[1], des2);
        thirdLevelq1.put(q1[2], des3);
        thirdLevelq2.put(q2[0], des4);
        thirdLevelq2.put(q2[1], des5);
        thirdLevelq2.put(q2[2], des6);
        thirdLevelq2.put(q2[3], des7);
        thirdLevelq3.put(q3[0], des8);
        thirdLevelq3.put(q3[1], des9);
        thirdLevelq3.put(q3[2], des10);
        thirdLevelq4.put(q4[0], des11);
        thirdLevelq4.put(q4[1], des12);
        thirdLevelq4.put(q4[2], des13);

        data.add(thirdLevelq1);
        data.add(thirdLevelq2);
        data.add(thirdLevelq3);
        data.add(thirdLevelq4);
        expandableListView = (ExpandableListView) findViewById(R.id.expandible_listview);
        //passing three level of information to constructor
        ThreeLevelListAdapter threeLevelListAdapterAdapter = new ThreeLevelListAdapter(this, parent, secondLevel, data);
        expandableListView.setAdapter(threeLevelListAdapterAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
                    expandableListView.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });


    }

}














