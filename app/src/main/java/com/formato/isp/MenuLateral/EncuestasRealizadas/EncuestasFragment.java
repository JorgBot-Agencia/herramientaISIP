package com.formato.isp.MenuLateral.EncuestasRealizadas;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.formato.isp.Adapter.AdapterListBasic;
import com.formato.isp.GestionFundacion.Sesion;
import com.formato.isp.R;
import com.formato.isp.model.Empresa;
import com.formato.isp.resource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EncuestasFragment extends Fragment {

    private View root;
    private RecyclerView recyclerView;
    private RequestQueue queue;
    private ArrayList<Empresa> lista ;
    Sesion session;
    private AdapterListBasic mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_encuestasrealizadas, container, false);
        queue = Volley.newRequestQueue(root.getContext());
        lista =  new ArrayList<>();
        consultar_emp();
        return root;
    }

    public void consultar_emp(){
        session = new Sesion(getActivity().getApplicationContext());
        String id = session.getIdFun();
        String url = resource.URLAPI + "/empresa/?fund_id=" + id;


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
    public void CargarListView(JSONArray ja) throws JSONException {
        for (int i = 0; i < ja.length(); i++) {
            JSONObject jsonObj = ja.getJSONObject(i);
            TypedArray drw_arr = root.getContext().getResources().obtainTypedArray(R.array.empr_images);
            Empresa e = new Empresa();
            e.empr_NIT= jsonObj.getString("empr_nit");
            e.empr_barrio =  "Ubicación: "+jsonObj.getString("empr_ciudad")+", Barrio: "+jsonObj.getString("empr_barrio");
            e.empr_nombre = jsonObj.getString("empr_nombre");
            e.empr_telefono ="Teléfono: " +jsonObj.getString("empr_telefono");
            e.empr_image=drw_arr.getResourceId(0, -1);;
            lista.add(e);

        }
        initComponent(root);
    }

    private void initComponent(final View root) {
        recyclerView = (RecyclerView) root.findViewById(R.id.IdrecycleviewEncReal);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setHasFixedSize(true);

        //set data and list adapter
        if(lista.size()!=0){
            mAdapter = new AdapterListBasic(root.getContext(), lista);
            recyclerView.setAdapter(mAdapter);
        }else{
            Toast.makeText(root.getContext(),"La fundación no tiene empresas registradas...",Toast.LENGTH_LONG).show();
        }
    }
}