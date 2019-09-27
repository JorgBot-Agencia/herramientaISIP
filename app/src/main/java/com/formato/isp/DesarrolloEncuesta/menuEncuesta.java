package com.formato.isp.DesarrolloEncuesta;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.formato.isp.R;
import com.formato.isp.utils.AdapterListFolderFile;
import com.formato.isp.utils.FolderFile;
import com.formato.isp.utils.ItemAnimation;
import com.formato.isp.utils.Tools;
import com.formato.isp.utils.ViewAnimation;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class menuEncuesta extends AppCompatActivity {

    private View parent_view;
    private RecyclerView recyclerView;
    private AdapterListFolderFile mAdapter;
    private RequestQueue queue;
    List<FolderFile> items = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_encuesta);
        parent_view = findViewById(android.R.id.content);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        initToolbar();
        loadingAndDisplayContent();
    }
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.colorPrimary);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(parent_view.getContext(),reporteGrafico.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void loadingAndDisplayContent() {
        recyclerView.setVisibility(View.GONE);

        initComponent();
    }

    //private void obtenerAreas() {
        //String url = "https://formatoisp-api.herokuapp.com/api/area";
        //JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            //@Override
            //public void onResponse(JSONObject res) {
                //try {
                    //JSONArray jsonArr = res.getJSONArray("data");
                    //items.add(new FolderFile("Áreas de fortalecimiento productivo", true));  // add section
                    //for (int i = 0; i < jsonArr.length(); i++) {
                        //JSONObject jsonObj = jsonArr.getJSONObject(i);
                        //items.add(new FolderFile(jsonObj.getString("area_nombre"), "Sin iniciar", jsonObj.getInt("area_logo"), 0,true));
                    //}
                //} catch (JSONException e) {
                    //e.printStackTrace();
                //}
            //}
        //}, new Response.ErrorListener() {
            //@Override
            //public void onErrorResponse(VolleyError error) {

            //}
        //});
        //queue.add(req);
    //}


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initComponent() {
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        items.add(new FolderFile("Áreas de fortalecimiento productivo", true));  // add section
        items.add(new FolderFile("Técnica y productiva", "Incompleto", R.drawable.ic_settings_black_24dp, 50,true));

        //obtenerAreas();
        //set data and list adapter
        mAdapter = new AdapterListFolderFile(this, items, ItemAnimation.FADE_IN);
        recyclerView.setAdapter(mAdapter);

        //LinearLayout layout = findViewById(R.id.botones);
        //LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //Button but = new Button(this);
        //but.setLayoutParams(lp);
        //but.setBackground(getDrawable(R.drawable.boton));
        //but.setText("Generar reporte");
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        //but.setTextColor(getColor(colorLetraBlanco));
        //}
        //layout.addView(but);

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterListFolderFile.OnItemClickListener() {
            @Override
            public void onItemClick(View view, FolderFile obj, int position) {
                Intent abrirEncuesta = new Intent(view.getContext(), preguntasEncuesta.class);
                startActivity(abrirEncuesta);
            }
        });
    }
}
