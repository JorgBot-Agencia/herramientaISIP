package com.formato.isp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class infoDetallada extends AppCompatActivity {

    Button btnIniciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_detallada);

        btnIniciar = findViewById(R.id.btn_start);
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abrirEncuesta = new Intent(view.getContext(), preguntasEncuesta.class);
                startActivity(abrirEncuesta);
            }
        });

    }
}
