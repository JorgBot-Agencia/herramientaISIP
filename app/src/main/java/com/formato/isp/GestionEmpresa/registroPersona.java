package com.formato.isp.GestionEmpresa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.formato.isp.R;
import com.formato.isp.utils.Tools;

import java.util.ArrayList;

public class registroPersona extends AppCompatActivity {

    private Spinner sprol;
    ArrayList<String> rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_persona);


        sprol = (Spinner)findViewById(R.id.sp_rol);

        initToolbar();
        llenarSpinerRol();
    }

    private void llenarSpinerRol() {
        rol = new ArrayList<>();
        rol.add("Seleccionar:");
        rol.add("Personal");
        rol.add("Representante Legal");
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, rol);
            sprol.setAdapter(adapter);

    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbarRegPersona);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Formulario de Inscripción");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.colorSecondary);
    }
}
