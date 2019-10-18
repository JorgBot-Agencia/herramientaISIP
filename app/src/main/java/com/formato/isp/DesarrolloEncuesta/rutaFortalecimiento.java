package com.formato.isp.DesarrolloEncuesta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.formato.isp.GestionEmpresa.CustomExpandableListAdapter;
import com.formato.isp.GestionEmpresa.ExpandableListDataPump;
import com.formato.isp.R;
import com.google.common.collect.ArrayListMultimap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class rutaFortalecimiento extends AppCompatActivity {

    ListView lrutas;
    ArrayList<String> rutas;

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruta_fortalecimiento);
        rutas = new ArrayList<>();
        //llenarRutas();
        //ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, rutas);
        //lrutas = (ListView) findViewById(R.id.listRuta);
        //lrutas.setAdapter(adapter);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });
    }

    public void llenarRutas(){
        rutas.add("EDUCACIÓN");
        rutas.add(" - Servicio Nacional de Aprendizaje - SENA");
        rutas.add(" - Universidad Francisco de Paula Santander");
        rutas.add(" - Universidad Simón Bolívar");
        rutas.add(" - Universidad Libre");
        rutas.add(" - Universidad de Pamplona");
        rutas.add(" - Fundación de Estudios Superiores Confanorte");
        rutas.add(" ");
        rutas.add("PRIVADO");
        rutas.add(" - Asociación Nacional de Aprendizaje de Empresarios de Colombia - ANDI");
        rutas.add(" - Comisión Regional de Competitividad de Norte de Santander - CRCNS");
        rutas.add(" - Cámara de Comercio");
        rutas.add(" ");
        rutas.add("PÚBLICO");
        rutas.add(" - Colombia Nos Une");
        rutas.add(" - PROCOLOMBIA");
        rutas.add(" - Secretaría de Frontera");
        rutas.add(" - Secretaría de Desarrollo Social");
        rutas.add(" - Alcaldía de Villa del Rosario");
        rutas.add(" - Centro Integrado de Servicios- MICITIo");
        rutas.add(" - Migración Colombia ");
        rutas.add("");
        rutas.add("CORPORACIÓN INTERNACIONAL");
        rutas.add(" - Organización Internacional Para las Migraciones - OIM");
        rutas.add(" - Agencia de los Estados Unidos para el Desarrollo Internacional - USAID");
        rutas.add(" - Agricultural Cooperative Development International Volunteers in Overseas Cooperative Assistance - ACDI VOCA");
        rutas.add(" - World Vision");
        rutas.add(" - Deutsche Gesellschaft für Internationale Zusammenarbeit - GIZ ");
        rutas.add(" - Consejo Noruego para Refugiados - NRC");
        rutas.add(" - Programa de las Naciones Unidas para el Desarrollo -PNUD");
    }
}














