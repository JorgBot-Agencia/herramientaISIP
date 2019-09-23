package com.formato.isp.DesarrolloEncuesta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.formato.isp.R;
import com.formato.isp.utils.ViewAnimation;

public class preguntasEncuesta extends AppCompatActivity {

    private static final int MAX_STEP = 20;
    private int current_step = 1;
    private ProgressBar progressBar;
    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas_encuesta);
        initComponent();
    }

    private void initComponent() {

        status = (TextView) findViewById(R.id.status);
        progressBar = findViewById(R.id.progress);
        progressBar.setMax(MAX_STEP);
        progressBar.setProgress(current_step);
        progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        (findViewById(R.id.lyt_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backStep(current_step);
            }
        });

        (findViewById(R.id.lyt_next)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextStep(current_step);
            }
        });
        String str_progress = String.format("Pregunta %1$d", current_step, MAX_STEP);
        status.setText(str_progress);
    }
    private void nextStep(int progress) {
        if (progress <= MAX_STEP) {
            progress++;
            current_step = progress;
            ViewAnimation.fadeOutIn(status);
        }
        if(progress == 21){
            Intent abrirProgress = new Intent(this, cargando.class);
            startActivity(abrirProgress);
        }
        String str_progress = String.format("Pregunta %1$d", current_step, MAX_STEP);
        status.setText(str_progress);
        progressBar.setProgress(current_step);
    }

    private void backStep(int progress) {
        if (progress > 1) {
            progress--;
            current_step = progress;
        }
        String str_progress = String.format("Pregunta %1$d", current_step, MAX_STEP);
        status.setText(str_progress);
        progressBar.setProgress(current_step);
    }
}
