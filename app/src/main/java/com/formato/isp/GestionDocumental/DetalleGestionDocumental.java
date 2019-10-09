package com.formato.isp.GestionDocumental;


import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.formato.isp.Adapter.AdapterListBasicDetalleGestion;
import com.formato.isp.MenuLateral.EncuestasRealizadas.EncuestasViewModel;
import com.formato.isp.PDF.TemplatePDF;
import com.formato.isp.R;
import com.formato.isp.model.Revision;
import com.formato.isp.utils.Tools;
import com.formato.isp.utils.ViewAnimation;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.security.auth.Destroyable;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleGestionDocumental extends Fragment {

    public View root;
    public  TextView nombre, info;
    public Button generarpdf;
    private RequestQueue queue;
    private ArrayList<Revision> lista ;
    private RecyclerView recyclerView;
    private AdapterListBasicDetalleGestion mAdapter;
    private String[]header={"Gestión de mercados", "Capacitación", "Construccion de marca"};
    private String[]infor={"Fecha de diligenciamiento", "Diligenciado por:", "Contacto de la unidad"};
    private String piso="_____________________________________________";
    private TemplatePDF templatePDF;
    CircularImageView foto;

    public DetalleGestionDocumental() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_detalle_gestion_documental, container, false);
        queue = Volley.newRequestQueue(root.getContext());
        nombre = root.findViewById(R.id.IdNombre);
        info = root.findViewById(R.id.IdInformacion);
        foto = root.findViewById(R.id.IdImagen);
        lista =  new ArrayList<>();

        //Generacion de documento PDf
        templatePDF= new TemplatePDF(root.getContext());
        templatePDF.OpenDocument();
        templatePDF.addMetadata("Informe de resultados", "Informe de encuesta ISIP","ISIP");
        //templatePDF.creartablaimagen();
        templatePDF.addTitulos("Logo","","");
        templatePDF.addTitulosizq("NOMBRE DE LA UNIDAD", "Ubicación de la unidad","Teléfono de la unidad","Correo de la unidad");
        templatePDF.addletraroja(piso);
        templatePDF.addtitulo("ISIP");
        templatePDF.creartabla(infor, getinfor());
        templatePDF.addtitulo("Descripcion de unidad Productiva");
        templatePDF.addparrafo("CUADRO DE DESCRIPCION BREVE DE LA UNIDAD PRODUCTIVA, RESEÑA HISTORICA, INFORMACION DE QUE PRODUCE, DE QUE CLASE SI ES MIXTA O DE EMPRENDIMIENTO DE POBLACION MIGRANTE, DESDE CUANDO ESTA EN COLOMBIA.");
        templatePDF.addtitulo("RESULTADOS");
        templatePDF.addTitulos("","Resultado total","");
        templatePDF.addtitulo("Resultados especificos");
        templatePDF.creartabla(header,getResltEsp());
        templatePDF.closeDocument();

        generarpdf= root.findViewById(R.id.btngenerarpdf);

        generarpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                templatePDF.viewmPDF();

            }
        });


        if (getArguments() != null) {
            String id=getArguments().getString("nit");
            String n = getArguments().getString("nombre");
            String c = getArguments().getString("email");
            int f = getArguments().getInt("foto");

            nombre.setText(n);
            info.setText(c);
            Tools.displayImageRound(root.getContext(), foto ,f);
            consultar_revEmp(id);
        }


        return root;
    }






    private ArrayList<String[]>getResltEsp(){
        ArrayList<String[]>row= new ArrayList<>();
        row.add(new String[]{"Plan de productividad","Identificacion de mercados","Acceso a nuevas tecnologias"});
        return row;
    }

    private ArrayList<String[]>getinfor(){
        ArrayList<String[]>row= new ArrayList<>();
        row.add(new String[]{"Fecha","Nombre de la funcacion","Nombre de quien o quienes atienderon la encuesta","Politica de identificación de precios"});
        return row;
    }

    public void CargarListView(JSONArray ja) throws JSONException {
        String[] fechaInicio= new String[2];
        String fech="";
        for (int i = 0; i < ja.length(); i++) {
            JSONObject jsonObj = ja.getJSONObject(i);
            TypedArray drw_arr = root.getContext().getResources().obtainTypedArray(R.array.empr_images);
            Revision e = new Revision();
            e.setRevi_id( (jsonObj.getString("revi_id")));
            e.setRevi_descripcion((jsonObj.getString("revi_descripcion")).toUpperCase());
            fech=jsonObj.getString("revi_fechainicio");
            fechaInicio = fech.split("T");
            e.setRevi_fechainicio("Fecha de inicio: "+fechaInicio[0]);
            fech=jsonObj.getString("revi_fechafinal");
            fechaInicio = fech.split("T");
            e.setRevi_fechafinal("Fecha Final: "+fechaInicio[0]);
            lista.add(e);

        }

        initComponent(root);
    }

    public void consultar_revEmp(String id){
        String url = "https://formatoisp-api.herokuapp.com/api/revision/?empr="+id;

        JsonObjectRequest rs= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {

                JSONArray ja = null;
                try {
                    ja = response.getJSONArray("data");
                    CargarListView(ja);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener(){


            @Override
            public void onErrorResponse(VolleyError error) {
                if(error instanceof NetworkError){
                    Toast.makeText(root.getContext(), "Verifique su conexion a internet", Toast.LENGTH_LONG).show();
                }
            }
        });
        queue.add(rs);
    }

    private void initComponent(final View root) {
        recyclerView = (RecyclerView) root.findViewById(R.id.Idrecycleviewdetalle);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setHasFixedSize(true);

        //set data and list adapter

        if(lista.size()==0){
            Toast.makeText(root.getContext(), "La empresa no tiene revisiones...",Toast.LENGTH_LONG).show();
        }else{
            mAdapter = new AdapterListBasicDetalleGestion(root.getContext(), lista);
            recyclerView.setAdapter(mAdapter);
        }
    }

}
