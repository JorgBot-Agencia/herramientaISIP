package com.formato.isp.DesarrolloEncuesta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.formato.isp.R;

public class rutaFortalecimiento extends AppCompatActivity {

    private ImageView ruta1;
    private ImageView ruta2;
    private ImageView ruta3;
    private ImageView ruta4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruta_fortalecimiento);
        ruta1 = findViewById(R.id.ruta1);
        ruta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Educación", Toast.LENGTH_SHORT).show();
            }
        });
        ruta2 = findViewById(R.id.ruta2);
        ruta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Privado", Toast.LENGTH_SHORT).show();
            }
        });
        ruta3 = findViewById(R.id.ruta3);
        ruta3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Publico", Toast.LENGTH_SHORT).show();
            }
        });
        ruta4 = findViewById(R.id.ruta4);
        ruta4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Cooperación internacional", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
