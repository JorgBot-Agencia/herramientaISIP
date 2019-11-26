package com.formato.isp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class informacionEmpresarial extends AppCompatActivity {

    Dialog epicDialog;
    Button iniciarEncuesta, btnAccept, abrirInformacion;
    TextView titleTv, messageTv;
    ImageView closeImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_empresarial);

        epicDialog = new Dialog(this);

        iniciarEncuesta = (Button) findViewById(R.id.iniciarEncuesta);
        iniciarEncuesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup();
            }
        });
    }
    public void showPopup(){
        epicDialog.setContentView(R.layout.custom_popu_p);
        closeImg = (ImageView) epicDialog.findViewById(R.id.closeImg);
        btnAccept = (Button) epicDialog.findViewById(R.id.buttonAccept);
        titleTv= (TextView) epicDialog.findViewById(R.id.titleTv);
        messageTv = (TextView) epicDialog.findViewById(R.id.messageTv);

        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                epicDialog.dismiss();
            }
        });
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }

}
