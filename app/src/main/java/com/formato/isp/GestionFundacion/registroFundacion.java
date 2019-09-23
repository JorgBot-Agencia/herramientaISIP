package com.formato.isp.GestionFundacion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.net.*;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.formato.isp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class registroFundacion extends AppCompatActivity {

    private static final int ACTIVITY_SELECT_IMAGE = 1020;
    private static final int ACTIVITY_SELECT_FROM_CAMERA = 1040, ACTIVITY_SHARE = 1030;

    private static final int SELECT_PICTURE = 200;

    private String APP_DIRECTORY = "FormatoISP/";
    private String MEDIA_DIRECTORY = APP_DIRECTORY + "media";
    private String TEMPORAL_PICTURE_NAME = "temporal.jpg";

    private AlertDialog _photoDialog;

    Uri imageUri;
    ImageView foto_gallery;
    FloatingActionButton btnSeleccionar;
    RelativeLayout rlView;
    ContactsContract.Contacts.Photo photo;

    private String mpath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_fundacion);

        foto_gallery = findViewById(R.id.foto_gallery);
        btnSeleccionar = findViewById(R.id.btnSeleccionar);
        rlView = findViewById(R.id.rlView);

        getPhotoDialog();
        getPhotoButton();

    }

    private void getPhotoButton() {
        btnSeleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!getPhotoDialog().isShowing() && !isFinishing()){
                    getPhotoDialog().show();
                }
            }
        });
    }

    private AlertDialog getPhotoDialog() {
        if(_photoDialog == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(registroFundacion.this);
            builder.setTitle("Elige una opción");
            builder.setPositiveButton("Galeria", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, ACTIVITY_SELECT_IMAGE);
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            _photoDialog = builder.create();
        }
        return _photoDialog;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == ACTIVITY_SELECT_IMAGE){
            imageUri = data.getData();
            foto_gallery.setImageURI(imageUri);
        }
    }

}
