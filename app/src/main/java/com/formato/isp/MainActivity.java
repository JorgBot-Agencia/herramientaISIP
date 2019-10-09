package com.formato.isp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.formato.isp.GestionFundacion.registroFundacion;
import com.formato.isp.MenuLateral.menuprincipal;

public class MainActivity extends AppCompatActivity {

    Button btnIniciarSesion;
    TextView txtLink;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyStoragePermissions(this);

        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarSesion();
            }
        });

        txtLink = findViewById(R.id.link);
        txtLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abrir = new Intent(view.getContext(), registroFundacion.class);
                startActivity(abrir);
            }
        });
    }
    public void iniciarSesion(){
        Intent intent = new Intent(this, menuprincipal.class);
        intent.putExtra("direccion","1");
        startActivity(intent);
    }

    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
// We don’t have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
