package com.formato.isp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargando);

        parent_view = findViewById(android.R.id.content);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        loadingAndDisplayContent();
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
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initComponent() {
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        List<FolderFile> items = new ArrayList<>();

        items.add(new FolderFile("Áreas de fortalecimiento productivo", true));  // add section
        items.add(new FolderFile("Técnica y productiva", "Jan 9, 2014", R.drawable.ic_folder, 50,true));
        items.add(new FolderFile("Financiera y administrativa", "Jan 17, 2014", R.drawable.ic_folder, 80,true));


        //set data and list adapter
        mAdapter = new AdapterListFolderFile(this, items, ItemAnimation.FADE_IN);
        recyclerView.setAdapter(mAdapter);

        LinearLayout layout = findViewById(R.id.botones);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        Button but = new Button(this);
        but.setLayoutParams(lp);
        but.setBackground(getDrawable(R.drawable.boton));
        but.setText("Generar reporte");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            but.setTextColor(getColor(colorLetraBlanco));
        }
        layout.addView(but);

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterListFolderFile.OnItemClickListener() {
            @Override
            public void onItemClick(View view, FolderFile obj, int position) {
                Snackbar.make(parent_view, "Item " + obj.name + " clicked", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
