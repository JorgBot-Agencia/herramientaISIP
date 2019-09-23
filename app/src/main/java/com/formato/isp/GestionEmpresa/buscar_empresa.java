package com.formato.isp.GestionEmpresa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.formato.isp.R;

public class buscar_empresa extends AppCompatActivity {

    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_empresa);

        img = findViewById(R.id.image_1);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abrirInfo = new Intent(view.getContext(), infoDetallada.class);
                startActivity(abrirInfo);
            }
        });
    }
}
