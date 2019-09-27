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

import com.formato.isp.R;
import com.formato.isp.utils.AdapterListFolderFile;
import com.formato.isp.utils.FolderFile;
import com.formato.isp.utils.ItemAnimation;
import com.formato.isp.utils.Tools;
import com.formato.isp.utils.ViewAnimation;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class menuEncuesta extends AppCompatActivity {

    private View parent_view;
    private RecyclerView recyclerView;
    private AdapterListFolderFile mAdapter;
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





    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initComponent() {
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        List<FolderFile> items = new ArrayList<>();




        items.add(new FolderFile("Áreas de fortalecimiento productivo", true));  // add section
        items.add(new FolderFile("Técnica y productiva", "Sin iniciar", R.drawable.ic_settings_black_24dp, 0,true));
        items.add(new FolderFile("Financiera y administrativa", "Sin iniciar", R.drawable.ic_attach_money_black_24dp, 0,true));
        items.add(new FolderFile("Cultura empresarial e innovación", "Sin iniciar", R.drawable.ic_track_changes_black_24dp, 0,true));
        items.add(new FolderFile("Recursos de inversión", "Sin iniciar", R.drawable.ic_equalizer_black_24dp, 0,true));
        items.add(new FolderFile("Imagen e identidad corporativa", "Sin iniciar", R.drawable.ic_group_black_24dp, 0,true));
        items.add(new FolderFile("Presentación de producto", "Sin iniciar", R.drawable.ic_spa_black_24dp, 0,true));
        items.add(new FolderFile("Sellos de calidad", "Sin iniciar", R.drawable.ic_assignment_turned_in_black_24dp, 0,true));
        items.add(new FolderFile("Política de identificación de precios", "Sin iniciar", R.drawable.ic_style_black_24dp, 0,true));
        items.add(new FolderFile("Acceso a nuevas tecnologías", "Sin iniciar", R.drawable.ic_laptop_mac_black_24dp, 0,true));

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
