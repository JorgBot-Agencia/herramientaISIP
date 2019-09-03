package com.formato.isp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class principal extends AppCompatActivity {

    Button abrirInformacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        abrirInformacion = (Button) findViewById(R.id.button02);
        abrirInformacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirInformacion();
            }
        });
    }
    public void abrirInformacion(){
        Intent intent = new Intent(this, informacionEmpresarial.class);
        startActivity(intent);
    }
}
