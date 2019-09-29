package com.formato.isp.GestionEmpresa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.formato.isp.DesarrolloEncuesta.reporteGrafico;
import com.formato.isp.R;
import com.formato.isp.utils.Tools;

public class registroEmpresa extends AppCompatActivity {

    private EditText nit;
    private EditText nombre;
    private EditText fecha_crea;
    private EditText fecha_ini;
    private EditText departamento;
    private EditText ciudad;
    private EditText barrio;
    private EditText telefono;
    private EditText sitioweb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_empresa);

        initToolbar();
        nit = (EditText)findViewById(R.id.nit_empresa);
        nombre = (EditText)findViewById(R.id.nombre_empresa);
        fecha_crea = (EditText)findViewById(R.id.fecha_creacion);
        fecha_ini = (EditText)findViewById(R.id.fecha_inicio);
        departamento = (EditText)findViewById(R.id.departamento);
        ciudad = (EditText)findViewById(R.id.ciudad);
        barrio = (EditText)findViewById(R.id.barrio);
        telefono = (EditText)findViewById(R.id.telefono);
        sitioweb = (EditText)findViewById(R.id.sitioweb);

        fecha_crea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.fecha_creacion:
                        showDatePickerDialog();
                        break;
                }
            }
        });

        fecha_ini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.fecha_inicio:
                        showDatePickerDialog1();
                        break;
                }
            }
        });

    }
    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = year + " / " + (month+1) + " / " + day;
                fecha_crea.setText(selectedDate);
            }
        });

        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }
    private void showDatePickerDialog1() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = year + " / " + (month+1) + " / " + day;
                fecha_ini.setText(selectedDate);
            }
        });

        newFragment.show(this.getSupportFragmentManager(), "datePicker");
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
        }
        return super.onOptionsItemSelected(item);
    }

    pu


}
