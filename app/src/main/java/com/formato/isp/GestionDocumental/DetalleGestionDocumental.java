package com.formato.isp.GestionDocumental;


import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.formato.isp.Adapter.AdapterListBasicDetalleGestion;
import com.formato.isp.MenuLateral.EncuestasRealizadas.EncuestasViewModel;
import com.formato.isp.R;
import com.formato.isp.model.Revision;
import com.formato.isp.utils.Tools;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleGestionDocumental extends Fragment {

    private View root;
    public  TextView nombre, correo;
    private RequestQueue queue;
    private ArrayList<Revision> lista ;
    private RecyclerView recyclerView;
    private AdapterListBasicDetalleGestion mAdapter;
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
        correo = root.findViewById(R.id.IdInformacion);
        foto = root.findViewById(R.id.IdImagen);
        lista =  new ArrayList<>();
        Toast.makeText(root.getContext(),"crea la lista",Toast.LENGTH_LONG);
        consultar_revEmp();

        //final TextView textView =  info.setText("TEXTO"); info.setText("TEXTO");root.findViewById(R.id.nombre);

        if (getArguments() != null) {
            String n = getArguments().getString("nombre");
            String c = getArguments().getString("email");
            int f = getArguments().getInt("foto");

            nombre.setText(n);
            correo.setText(c);
            Tools.displayImageRound(root.getContext(), foto ,f);
        }


        return root;
    }

    public void CargarListView(JSONArray ja) throws JSONException {
        for (int i = 0; i < ja.length(); i++) {
            JSONObject jsonObj = ja.getJSONObject(i);
            TypedArray drw_arr = root.getContext().getResources().obtainTypedArray(R.array.empr_images);
            Revision e = new Revision();
            e.setRevi_id(jsonObj.getString("revi_descripcion"));
            e.setRevi_fechainicio(jsonObj.getString("revi_fechainicio"));
            e.setRevi_fechafinal(jsonObj.getString("revi_fechafinal"));
            lista.add(e);

        }
        if(lista.size()==0){
            Toast.makeText(root.getContext(),"nada",Toast.LENGTH_LONG);
        }else{
            Toast.makeText(root.getContext(),"tiene algo",Toast.LENGTH_LONG);
        }

        initComponent(root);
    }

    public void consultar_revEmp(){
        String url = "https://formatoisp-api.herokuapp.com/api/revision/?empr=1";

        JsonObjectRequest rs= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {

                JSONArray ja = null;
                try {
                    ja = response.getJSONArray("data");
                    Toast.makeText(root.getContext(),"nn",Toast.LENGTH_LONG);
                    CargarListView(ja);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener(){


            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(rs);
        Toast.makeText(root.getContext(),"ready",Toast.LENGTH_LONG);
    }

    private void initComponent(final View root) {
        recyclerView = (RecyclerView) root.findViewById(R.id.Idrecycleviewdetalle);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setHasFixedSize(true);

        //set data and list adapter
        mAdapter = new AdapterListBasicDetalleGestion(root.getContext(), lista);
        recyclerView.setAdapter(mAdapter);

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterListBasicDetalleGestion.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Revision obj, int position) {

            }
        });
    }

}
