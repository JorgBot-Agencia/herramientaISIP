package com.formato.isp.GestionDocumental;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.formato.isp.Adapter.AdapterListBasic;
import com.formato.isp.R;

public class GestionDocumental extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterListBasic mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_documental);
    }


}
