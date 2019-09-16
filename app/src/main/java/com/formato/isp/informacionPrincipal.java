package com.formato.isp;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class informacionPrincipal extends Fragment {

    Button btnEncuesta;
    Dialog epicDialog;
    Button iniciarEncuesta, btnAccept, abrirInformacion;
    TextView titleTv, messageTv;
    ImageView closeImg;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_informacion_principal, container, false);

        epicDialog = new Dialog(getContext());

        iniciarEncuesta= root.findViewById(R.id.iniciarEncuesta);
        iniciarEncuesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), preguntasEncuesta.class);
                startActivity(intent);
            }
        });

        return root;
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
