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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.formato.isp.R;
import com.formato.isp.utils.ViewAnimation;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import com.formato.isp.utils.*;

import static com.formato.isp.R.color.colorLetraBlanco;


public class cargando extends AppCompatActivity {

    private View parent_view;
    private final static int LOADING_DURATION = 2000;

    private RecyclerView recyclerView;
    private AdapterListFolderFile mAdapter;
    private AdapterListFolderFile mAdapterBoton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargando);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        Tools.changeMenuIconColor(menu, getResources().getColor(R.color.colorPrimary));
        return true;
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

    private void loadingAndDisplayContent() {
        final LinearLayout lyt_progress = (LinearLayout) findViewById(R.id.lyt_progress);
        lyt_progress.setVisibility(View.VISIBLE);
        lyt_progress.setAlpha(1.0f);
        recyclerView.setVisibility(View.GONE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewAnimation.fadeOut(lyt_progress);
            }
        }, LOADING_DURATION);

        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                initComponent();
            }
        }, LOADING_DURATION + 400);

        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                crearBoton();
            }
        }, LOADING_DURATION + 900);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initComponent() {
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        List<FolderFile> items = new ArrayList<>();

        items.add(new FolderFile("Áreas de fortalecimiento productivo", true));  // add section
        items.add(new FolderFile(1,"Técnica y productiva", "Incompleto", R.drawable.ic_settings_black_24dp, 50,true));
        items.add(new FolderFile(2,"Financiera y administrativa", "Incompleto", R.drawable.ic_attach_money_black_24dp, 80,true));
        items.add(new FolderFile(3,"Cultura empresarial e innovación", "Incompleto", R.drawable.ic_track_changes_black_24dp, 80,true));
        items.add(new FolderFile(4,"Recursos de inversión", "Incompleto", R.drawable.ic_equalizer_black_24dp, 20,true));
        items.add(new FolderFile(5,"Imagen e identidad corporativa", "Incompleto", R.drawable.ic_group_black_24dp, 40,true));
        items.add(new FolderFile(6,"Presentación de producto", "Incompleto", R.drawable.ic_spa_black_24dp, 10,true));
        items.add(new FolderFile(7,"Sellos de calidad", "Completo", R.drawable.ic_assignment_turned_in_black_24dp, 100,true));
        items.add(new FolderFile(8,"Política de identificación de precios", "Incompleto", R.drawable.ic_style_black_24dp, 80,true));
        items.add(new FolderFile(9,"Acceso a nuevas tecnologías", "Incompleto", R.drawable.ic_laptop_mac_black_24dp, 20,true));

        //set data and list adapter
        mAdapter = new AdapterListFolderFile(this, items, ItemAnimation.FADE_IN);
        recyclerView.setAdapter(mAdapter);



        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterListFolderFile.OnItemClickListener() {
            @Override
            public void onItemClick(View view, FolderFile obj, int position) {
                Snackbar.make(parent_view, "Item " + obj.name + " clicked", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void crearBoton(){
        LinearLayout layout = findViewById(R.id.lyt_boton);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        Button but = new Button(this);
        but.setLayoutParams(lp);
        but.setText("Generar reporte");
        but.setBackground(getDrawable(R.drawable.boton));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            but.setTextColor(getColor(colorLetraBlanco));
        }
        layout.addView(but);
    }
}
