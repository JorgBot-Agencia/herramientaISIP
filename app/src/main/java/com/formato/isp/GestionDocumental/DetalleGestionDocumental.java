package com.formato.isp.GestionDocumental;


import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.formato.isp.Clases.Area;
import com.formato.isp.Clases.fotoReporte;
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
import com.formato.isp.DesarrolloEncuesta.reporteGrafico;
import com.formato.isp.GestionFundacion.Sesion;
import com.formato.isp.MenuLateral.EncuestasRealizadas.EncuestasViewModel;
import com.formato.isp.PDF.TemplatePDF;
import com.formato.isp.R;
import com.formato.isp.model.Revision;
import com.formato.isp.resource;
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
    private Sesion session;
    private String n;
    private String d;
    private String t;
    public View root;
    public  TextView nombre, info;
    public Button generarpdf;
    private RequestQueue queue;
    private ArrayList<Revision> lista ;
    private ArrayList<Area> areaspdf;
    private RecyclerView recyclerView;
    private AdapterListBasicDetalleGestion mAdapter;
    private String[]header={"Capacitación","Construccion de marca","Gestión de mercados","Acceso a capital"};
    private String[]infor={"Fecha de diligenciamiento", "Diligenciado por:", "Contacto de la unidad"};
    private String piso="_____________________________________________";
    private TemplatePDF templatePDF;
    CircularImageView foto;
    private int c1=0,c2=0,c3=0,c4=0,c5=0,c6=0,c7=0,c8=0,c9=0,c10=0,c11=0,c12=0,c13=0;
    private float prom1=0,prom2=0,prom3=0,prom4=0,prom5=0,prom6=0,prom7=0,prom8=0,prom9=0,prom10=0,prom11=0,prom12=0,prom13=0;


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
        areaspdf=new ArrayList<>();
        if (getArguments() != null) {
            String id=getArguments().getString("nit");
            n = getArguments().getString("nombre");
            d = getArguments().getString("direccion");
            t = getArguments().getString("telefono");
            int f = getArguments().getInt("foto");

            nombre.setText(n);
            info.setText(d);
            Tools.displayImageRound(root.getContext(), foto ,f);
            consultar_revEmp(id);

        }


        return root;
    }

    public void calcularPromediosxrevision(ArrayList<Area> arr) {
        if(c1!=0) {
           arr.add(new Area(1,0,0,prom1 / c1));
        }
        if(c2!=0) {
            arr.add(new Area(2,0,0,prom2 / c2));
        }
        if(c3!=0) {
            arr.add(new Area(3,0,0,prom3 / c3));
        }
        if(c4!=0) {
            Area e = new Area(4,0,0,prom4 / c4);
            arr.add(e);
            //arr.add(new Area(4,0,0,prom4 / c4));
        }
        if(c5!=0) {
            arr.add(new Area(5,0,0,prom5 / c5));
        }
        if(c6!=0) {
            arr.add(new Area(6,0,0,prom6 / c6));
        }
        if(c7!=0) {
            arr.add(new Area(7,0,0,prom7 / c7));
        }
        if(c8!=0) {
            arr.add(new Area(7,0,0,prom8 / c8));
        }
        if(c9!=0) {
            arr.add(new Area(9,0,0,prom9 / c9));
        }
        if(c10!=0) {
            arr.add(new Area(10,0,0,prom10 / c10));
        }
        if(c11!=0) {
            arr.add(new Area(11,0,0,prom11 / c11));
        }
        if(c12!=0) {
            arr.add(new Area(12,0,0,prom12 / c12));
        }if(c13!=0) {
            arr.add(new Area(13,0,0,prom13 / c13));
        }
    }


    public ArrayList<String[]>getResltEsp(){
        ArrayList<String[]>row= new ArrayList<>();
        row.add(new String[]{"1-Técnica y productiva", "2-Financiera y administrativa","3-Cultura e innovacion",
                "4-Imagen e identidad", "5-Presentacion producto","6-Sello de frontera de oportunidad"
                ,"7-Politica de identificación de precios","8-Acceso a nuevas tecnologias","9-Identificacion de mercados","10-Plan de productividad"
                ,"11-Recursos de inversión","12-Capitales reembolsables","13-Capitales no reembolsables",});
        return row;
    }

    public ArrayList<String[]>getinfor(){
        ArrayList<String[]>row= new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaComoCadena = sdf.format(new Date());
        row.add(new String[]{"Fecha: "+fechaComoCadena,"Nombre de la funcacion: "+session.getNombreFun(),session.getTelefono(),"Politica de identificación de precios"});
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
    public void Cargararray(JSONArray ja) throws JSONException {
        String area = "";
        int idarea = 0;
        int escala=0;


        for (int i = 0; i < ja.length(); i++) {
            JSONObject jsonObj = ja.getJSONObject(i);
            area= ja.getJSONObject(i).getJSONObject("indicador").getJSONObject("preguntum").getJSONObject("area").getString("area_nombre");
            idarea= Integer.parseInt(ja.getJSONObject(i).getJSONObject("indicador").getJSONObject("preguntum").getJSONObject("area").getString("area_id"));
            JSONArray escalas= ja.getJSONObject(i).getJSONArray("escalas");
            escala=Integer.parseInt(escalas.getJSONObject(0).getString("esca_valor"));
            if(idarea==1){
                c1++;
                prom1+=escala;
            }
            if(idarea==2){
                c2++;
                prom2+=escala;

            }
            if(idarea==3){
                c3++;
                prom3+=escala;
            }
            if(idarea==4){
                c4++;
                prom4+=escala;
            }
            if(idarea==5){
                c5++;
                prom5+=escala;
            }
            if(idarea==6){
                c6++;
                prom6+=escala;
            }
            if(idarea==7){
                c7++;
                prom7+=escala;
            }
            if(idarea==8){
                c8++;
                prom8+=escala;

            }if(idarea==9){
                c9++;
                prom9+=escala;
            }
            if(idarea==10){
                c10++;
                prom10+=escala;
            }
            if(idarea==11){
                c11++;
                prom11+=escala;

            }if(idarea==12){
                c12++;
                prom12+=escala;

            }
            if(idarea==13){
                c13++;
                prom13+=escala;
            }
        }
        areaspdf.clear();
        calcularPromediosxrevision(areaspdf);
         c1=0;c2=0;c3=0;c4=0;c5=0;c6=0;c7=0;c8=0;c9=0;c10=0;c11=0;c12=0;c13=0;
         prom1=0;prom2=0;prom3=0;prom4=0;prom5=0;prom6=0;prom7=0;prom8=0;prom9=0;prom10=0;prom11=0;prom12=0;prom13=0;



        templatePDF= new TemplatePDF(root.getContext());
        templatePDF.OpenDocument();
        templatePDF.addMetadata("Informe de resultados", "Informe de encuesta ISIP","ISIP");
        session = new Sesion(root.getContext());
        Bitmap icon = BitmapFactory.decodeResource(root.getContext().getResources(),
                R.drawable.banner);
        templatePDF.creartablaimagencentra(icon, "logofrontera", "encabezado");
        Bitmap icon2 = BitmapFactory.decodeResource(root.getContext().getResources(),
                R.drawable.isip);
        templatePDF.creartablaimagencentra(icon2, "isip","normal");
        templatePDF.addTitulos("","","");
        templatePDF.addTitulosizq("Nombre de la unidad: "+ n,  d,t," ");
        templatePDF.addletraroja(piso);
        templatePDF.addtitulo("ISIP");
        templatePDF.creartabla(infor, getinfor());
        templatePDF.addtitulo("Descripcion de unidad Productiva");
        templatePDF.addparrafo("CUADRO DE DESCRIPCION BREVE DE LA UNIDAD PRODUCTIVA, RESEÑA HISTORICA, INFORMACION DE QUE PRODUCE, DE QUE CLASE SI ES MIXTA O DE EMPRENDIMIENTO DE POBLACION MIGRANTE, DESDE CUANDO ESTA EN COLOMBIA.");
        templatePDF.addtitulo("RESULTADOS");
        templatePDF.addtitulocentrado("Resultado total");
        ArrayList<fotoReporte> foto = reporteGrafico.arrfoto;
        if(foto!=null) {
            int cc = 0;
            for (int i = 0; i < foto.size(); i++) {
                if (foto.get(i).getNomb_empr().equals(n)) {
                    templatePDF.creartablaimagenGRAFICA(foto.get(i).getBitmabEmpr(), "MIINFORME");
                    cc++;
                }
            }
        }else{
            templatePDF.addparrafo("La empresa aun no cuenta con un reporte estadistico, debe diligenciar la encuesta...");
        }
        templatePDF.addtitulo("Resultados especificos");
        templatePDF.creartablapers2(header,getResltEsp(),areaspdf);
        templatePDF.closeDocument();
        templatePDF.viewmPDF();
        areaspdf.clear();
    }

    public void consultar_revEmp(String id){
        String url = resource.URLAPI + "/revision/?empr="+id;
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

    public void consultar_escalasXrevision(String id){
        String url = resource.URLAPI + "/dato/?revi="+id;
        JsonObjectRequest rs= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {

                JSONArray ja = null;
                try {
                    ja = response.getJSONArray("data");
                    Cargararray(ja);
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

            mAdapter.setOnItemClickListener(new AdapterListBasicDetalleGestion.OnItemClickListener() {
                @Override
                public void onItemClick(View view, Revision obj, int position) {
                    consultar_escalasXrevision(obj.getRevi_id());
                }
            });
        }
    }

}
