package com.formato.isp.GestionDocumental;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.formato.isp.Adapter.AdapterListBasic;
import com.formato.isp.R;
import com.formato.isp.data.DataGenerator;
import com.formato.isp.model.Empresa;
import com.formato.isp.model.People;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Gestion_documental extends Fragment {

    private View parent_view;
    private View root;
    private RequestQueue queue;
    private ArrayList<Empresa> lista ;


    private RecyclerView recyclerView;
    private AdapterListBasic mAdapter;
    private GestionDocumentalViewModel mViewModel;
    public static Gestion_documental newInstance() {
        return new Gestion_documental();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.gestion_documental_fragment, container, false);
        queue = Volley.newRequestQueue(root.getContext());
        parent_view = root.findViewById(android.R.id.content);
        lista =  new ArrayList<>();
        consultar_emp();
        return root;
    }


    public void CargarListView(JSONArray ja) throws JSONException {
        for (int i = 0; i < ja.length(); i++) {
            JSONObject jsonObj = ja.getJSONObject(i);
            Empresa e = new Empresa();
            e.empr_barrio = jsonObj.getString("empr_barrio");
            e.empr_nombre = jsonObj.getString("empr_nombre");
            lista.add(e);

        }
        initComponent(root);
    }

    private void initComponent(final View root) {
        recyclerView = (RecyclerView) root.findViewById(R.id.Idrecycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setHasFixedSize(true);
        
        //set data and list adapter
        mAdapter = new AdapterListBasic(root.getContext(), lista);
        recyclerView.setAdapter(mAdapter);

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterListBasic.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Empresa obj, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("nombre", obj.empr_nombre);

                bundle.putString("email", obj.empr_barrio);
                //bundle.putInt("foto",obj.image);
                //bundle.putString("foto", obj.image);

                Navigation.findNavController(root).navigate(R.id.detalle_gestion,bundle);
            }
        });

    }



    public void consultar_emp(){
        String url = "https://formatoisp-api.herokuapp.com/api/empresa";

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

            }
        });
        queue.add(rs);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(GestionDocumentalViewModel.class);
        // TODO: Use the ViewModel
    }

}
