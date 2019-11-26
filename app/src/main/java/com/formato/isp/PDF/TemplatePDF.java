package com.formato.isp.PDF;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.util.RangeValueIterator;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.DrawableRes;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.formato.isp.Clases.Area;
import com.formato.isp.DesarrolloEncuesta.menuEncuesta;
import com.formato.isp.GestionDocumental.DetalleGestionDocumental;
import com.formato.isp.GestionFundacion.AppHelper;
import com.formato.isp.GestionFundacion.VolleyMultipartRequest;
import com.formato.isp.GestionFundacion.VolleySingleton;
import com.formato.isp.MainActivity;
import com.formato.isp.R;
import com.formato.isp.resource;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TemplatePDF {

    private Context context;
    private File pdfFile;
    private Document document;
    private PdfWriter pdfwriter;
    private Paragraph paragraph;// este es el parrafoo
    private Font letratitulo = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
    private Font letraroja = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD, BaseColor.RED);
    private Font letrasubtitulo = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
    private Font letrapequeña = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);
    private Font letratexto = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
    private Font letratexto2 = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.NORMAL);
    private Font letraresaltado = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD, BaseColor.RED);

    public TemplatePDF(Context context) {
        this.context = context;
    }

    private void crearFile() {
        File folder = new File(Environment.getExternalStorageDirectory().toString(), "recursosisp");
        if (!folder.exists())
            folder.mkdirs();
        pdfFile = new File(folder, "ReporteTemporal.pdf");


    }

    public void OpenDocument() {
        crearFile();
        try {
            document = new Document(PageSize.A4);
            pdfwriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();
        } catch (Exception e) {
            Log.e("openDocument", e.toString());
        }
    }

    public void closeDocument() {
        document.close();
    }

    public void addMetadata(String titulo, String tema, String autor) {
        document.addTitle(titulo);
        document.addSubject(tema);
        document.addAuthor(autor);
    }

    public void addTitulos(String titulo, String subtitulo, String fecha) {
        try {


            paragraph = new Paragraph();
            addparafoHijocentrado(new Paragraph(titulo, letratitulo));
            addparafoHijocentrado(new Paragraph(subtitulo, letrasubtitulo));
            addparafoHijocentrado(new Paragraph(fecha, letraresaltado));
            paragraph.setSpacingAfter(10);//espacio entre parrafo padre y parrafos hijos
            document.add(paragraph);
        } catch (Exception e) {
            Log.e("addTutulos", e.toString());
        }
    }

    public void addTitulosizq(String nombre, String ubicacion, String telefono, String correo) {
        try {


            paragraph = new Paragraph();
            addparafoHijoizquierda(new Paragraph(nombre, letratitulo));
            addparafoHijoizquierda(new Paragraph(ubicacion, letrasubtitulo));
            addparafoHijoizquierda(new Paragraph(telefono, letrasubtitulo));
            addparafoHijoizquierda(new Paragraph(correo, letrasubtitulo));
            paragraph.setSpacingAfter(0);//espacio entre parrafo padre y parrafos hijos
            document.add(paragraph);
        } catch (Exception e) {
            Log.e("addTutulos", e.toString());
        }
    }

    public void addletraroja(String nombre) {
        try {


            paragraph = new Paragraph();
            addparafoHijoizquierda(new Paragraph(nombre, letraroja));
            paragraph.setSpacingAfter(5);//espacio entre parrafo padre y parrafos hijos
            document.add(paragraph);
        } catch (Exception e) {
            Log.e("addTutulos", e.toString());
        }
    }

    private void addparafoHijocentrado(Paragraph parafoHijo) {
        parafoHijo.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(parafoHijo);
    }

    private void addparafoHijoizquierda(Paragraph parafoHijo) {
        parafoHijo.setAlignment(Element.ALIGN_LEFT);
        paragraph.add(parafoHijo);
    }

    public void addparrafo(String text) {
        try {

            paragraph = new Paragraph(text, letratexto);
            paragraph.setSpacingAfter(5);
            paragraph.setSpacingBefore(5);
            document.add(paragraph);
        } catch (Exception e) {
            Log.e("addparrafo", e.toString());
        }
    }


    public void addtitulo(String text) {
        try {


            paragraph = new Paragraph(text, letratitulo);
            paragraph.setSpacingAfter(5);
            paragraph.setSpacingBefore(5);
            document.add(paragraph);
        } catch (Exception e) {
            Log.e("addparrafo", e.toString());
        }
    }

    public void addtitulocentrado(String text) {
        try {


            paragraph = new Paragraph(text, letratitulo);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingAfter(5);
            paragraph.setSpacingBefore(5);
            document.add(paragraph);
        } catch (Exception e) {
            Log.e("addparrafo", e.toString());
        }
    }


    public void creartabla(String[] encabezado, ArrayList<String[]> clients) {
        try {

            paragraph = new Paragraph();
            paragraph.setSpacingBefore(10);
            paragraph.setFont(letratexto);
            PdfPTable pdfPTable = new PdfPTable(encabezado.length);
            pdfPTable.setWidthPercentage(100);
            PdfPCell pdfPCell;
            int indexC = 0;//columnas
            while (indexC < encabezado.length) {
                pdfPCell = new PdfPCell(new Phrase(encabezado[indexC++], letrasubtitulo));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setBackgroundColor(BaseColor.GRAY);
                pdfPTable.addCell(pdfPCell);
            }
            for (int indexrow = 0; indexrow < clients.size(); indexrow++) {
                String[] row = clients.get(indexrow);
                for (indexC = 0; indexC < encabezado.length; indexC++) {
                    pdfPCell = new PdfPCell(new Phrase(row[indexC]));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfPCell.setFixedHeight(48);
                    pdfPTable.addCell(pdfPCell);
                }
            }
            paragraph.add(pdfPTable);
            document.add(paragraph);
        } catch (Exception e) {
            Log.e("creartabla", e.toString());
        }


    }

    public void creartablapers(String[] encabezado, ArrayList<String[]> clients, int opc) {
        try {

            paragraph = new Paragraph();
            paragraph.setSpacingBefore(10);
            paragraph.setFont(letratexto);
            PdfPTable pdfPTable = new PdfPTable(13);
            pdfPTable.setWidthPercentage(100);
            PdfPCell pdfPCell;
            int indexC = 0;//columnas
            while (indexC < encabezado.length) {
                pdfPCell = new PdfPCell(new Phrase(encabezado[indexC], letrasubtitulo));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setBackgroundColor(BaseColor.GRAY);
                if (indexC == 0) {
                    pdfPCell.setColspan(3);
                }
                if (indexC == 1) {
                    pdfPCell.setColspan(3);
                }
                if (indexC == 2) {
                    pdfPCell.setColspan(4);
                }
                if (indexC == 3) {
                    pdfPCell.setColspan(3);
                }
                pdfPTable.addCell(pdfPCell);
                indexC++;
            }
            for (int indexrow = 0; indexrow < clients.size(); indexrow++) {
                String[] row = clients.get(indexrow);
                for (indexC = 0; indexC < 13; indexC++) {
                    pdfPCell = new PdfPCell(new Phrase(row[indexC], letrapequeña));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfPCell.setFixedHeight(48);
                    pdfPTable.addCell(pdfPCell);
                }
            }
            ArrayList<Area> areas;
            if(opc==1){
            areas = (ArrayList<Area>) menuEncuesta.areasEncuestadas;
            }else{
                 areas = DetalleGestionDocumental.areaspdf2;
            }
            int aux = 0;
            int c = 0;
            int c2 = 0;
            String[] row = clients.get(0);

            for (int cont = 0; cont < row.length; cont++) {
                String[] dato = row[cont].split("-", 2);
                int datoentero = Integer.parseInt(dato[0]);
                aux = 0;
                c2 = 0;
                if (datoentero >= 4 && datoentero<11) {
                    datoentero = datoentero + 1;
                }
               // Toast.makeText(context,"tamaño del array: "+areas.size(),Toast.LENGTH_SHORT).show();
                while (aux < areas.size()) {
                    if (areas.get(aux).getAreaId() != 4) {
                        if (datoentero == areas.get(aux).getAreaId()) {
                            pdfPCell = new PdfPCell(new Phrase(String.valueOf(areas.get(aux).getPromedioEscala()), letrapequeña));
                            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            pdfPCell.setFixedHeight(48);
                            pdfPTable.addCell(pdfPCell);
                            c++;
                            aux = areas.size() + 2;
                        } else {
                            c2++;
                            aux++;
                        }
                    } else {
                        c2++;
                        aux++;
                    }
                    if (c2 >= areas.size()) {
                        pdfPCell = new PdfPCell(new Phrase("0%", letrapequeña));
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setFixedHeight(48);
                        pdfPTable.addCell(pdfPCell);
                    }

                }

            }


            paragraph.add(pdfPTable);
            document.add(paragraph);
        } catch (Exception e) {
            Log.e("creartablapers", e.toString());
        }


    }
    public void creartablapers2(String[] encabezado, ArrayList<String[]> clients, ArrayList<Area> areas) {
        try {
            paragraph = new Paragraph();
            paragraph.setSpacingBefore(10);
            paragraph.setFont(letratexto);
            PdfPTable pdfPTable = new PdfPTable(13);
            pdfPTable.setWidthPercentage(100);
            PdfPCell pdfPCell;
            int indexC = 0;//columnas
            while (indexC < encabezado.length) {
                pdfPCell = new PdfPCell(new Phrase(encabezado[indexC], letrasubtitulo));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setBackgroundColor(BaseColor.GRAY);
                if (indexC == 0) {
                    pdfPCell.setColspan(3);
                }
                if (indexC == 1) {
                    pdfPCell.setColspan(3);
                }
                if (indexC == 2) {
                    pdfPCell.setColspan(4);
                }
                if (indexC == 3) {
                    pdfPCell.setColspan(3);
                }
                pdfPTable.addCell(pdfPCell);
                indexC++;
            }
            for (int indexrow = 0; indexrow < clients.size(); indexrow++) {
                String[] row = clients.get(indexrow);
                for (indexC = 0; indexC < 13; indexC++) {
                    pdfPCell = new PdfPCell(new Phrase(row[indexC], letrapequeña));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfPCell.setFixedHeight(48);
                    pdfPTable.addCell(pdfPCell);
                }
            }

            int aux = 0;
            int c = 0;
            int c2 = 0;
            int ar=0;
            String[] row = clients.get(0);

            for (int cont = 0; cont < row.length; cont++) {
                String[] dato = row[cont].split("-", 2);
                int datoentero = Integer.parseInt(dato[0]);
                aux = 0;
                c2 = 0;

                // Toast.makeText(context,"tamaño del array: "+areas.size(),Toast.LENGTH_SHORT).show();
                while (aux < areas.size()) {
                    ar=areas.get(aux).getAreaId();
                    if (areas.get(aux).getAreaId() == 4) {
                        areas.get(aux).setAreaId(11);
                    }
                    if (datoentero >= 4 && datoentero<=10) {
                        ar = areas.get(aux).getAreaId() - 1;
                    }
                        if (datoentero == ar) {

                            Toast.makeText(context,"entero de la tabla: "+datoentero,Toast.LENGTH_SHORT).show();
                            Toast.makeText(context,"id de la base: "+ar,Toast.LENGTH_SHORT).show();
                            pdfPCell = new PdfPCell(new Phrase(String.valueOf(areas.get(aux).getPromedioEscala()), letrapequeña));
                            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            pdfPCell.setFixedHeight(48);
                            pdfPTable.addCell(pdfPCell);
                            c2++;
                            aux = areas.size() + 2;
                        } else {
                            aux++;
                        }
                    }
                    if (c2 == 0) {
                        pdfPCell = new PdfPCell(new Phrase("0%", letrapequeña));
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setFixedHeight(48);
                        pdfPTable.addCell(pdfPCell);
                    }

                }

            paragraph.add(pdfPTable);
            document.add(paragraph);
        } catch (Exception e) {
            Log.e("creartablapers", e.toString());
        }


    }

    public void creartablapers3(String[] encabezado, ArrayList<String[]> clients, ArrayList<Area> areas) {
        try {
            paragraph = new Paragraph();
            paragraph.setSpacingBefore(10);
            paragraph.setFont(letratexto);
            PdfPTable pdfPTable = new PdfPTable(12);
            pdfPTable.setWidthPercentage(100);
            PdfPCell pdfPCell;
            int indexC = 0;//columnas
            while (indexC < encabezado.length) {
                pdfPCell = new PdfPCell(new Phrase(encabezado[indexC], letrasubtitulo));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setBackgroundColor(BaseColor.GRAY);
                if (indexC == 0) {
                    pdfPCell.setColspan(3);
                }
                if (indexC == 1) {
                    pdfPCell.setColspan(3);
                }
                if (indexC == 2) {
                    pdfPCell.setColspan(4);
                }
                if (indexC == 3) {
                    pdfPCell.setColspan(2);
                }
                pdfPTable.addCell(pdfPCell);
                indexC++;
            }
            for (int indexrow = 0; indexrow < clients.size(); indexrow++) {
                String[] row = clients.get(indexrow);
                for (indexC = 0; indexC < 11; indexC++) {
                    if(indexC==10){
                        pdfPCell = new PdfPCell(new Phrase(row[indexC], letrapequeña));
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(2);
                        pdfPTable.addCell(pdfPCell);
                    }else{
                        pdfPCell = new PdfPCell(new Phrase(row[indexC], letrapequeña));
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setFixedHeight(48);
                        pdfPTable.addCell(pdfPCell);
                    }

                }
            }

            int aux = 0;
            int c = 0;
            int c2 = 0;
            int ar=0;
            String[] row = clients.get(0);

            for (int cont = 0; cont < 11; cont++) {
                String[] dato = row[cont].split("-", 2);
                int datoentero = Integer.parseInt(dato[0]);

                double valor_add=Math.rint(Buscar_en_array(areas,datoentero)*100)/100;
                if(datoentero==11){
                    if (valor_add == 0) {
                        pdfPCell = new PdfPCell(new Phrase("0%", letrapequeña));
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(2);
                        pdfPTable.addCell(pdfPCell);
                    } else {

                        pdfPCell = new PdfPCell(new Phrase(String.valueOf(valor_add), letrapequeña));
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setColspan(2);
                        pdfPTable.addCell(pdfPCell);


                    }

                }else {
                    if (valor_add == 0) {
                        pdfPCell = new PdfPCell(new Phrase("0%", letrapequeña));
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setFixedHeight(48);
                        pdfPTable.addCell(pdfPCell);
                    } else {

                        pdfPCell = new PdfPCell(new Phrase(String.valueOf(valor_add), letrapequeña));
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pdfPCell.setFixedHeight(48);
                        pdfPTable.addCell(pdfPCell);


                    }
                }
            }

            paragraph.add(pdfPTable);
            document.add(paragraph);
        } catch (Exception e) {
            Log.e("creartablapers3", e.toString());
        }


    }

    public Double Buscar_en_array(ArrayList<Area> ar, int id){
        double porc=0;
        int dato=0;
        for (int i=0;i<ar.size();i++){
            if(id <4){
                if(ar.get(i).getAreaId()==id){
                    porc=ar.get(i).getPromedioEscala();
                }
            }

            if(id==4){
                if(ar.get(i).getAreaId()==5){
                    porc=ar.get(i).getPromedioEscala();
                }

            }
            if(id==5){
                if(ar.get(i).getAreaId()==6){
                    porc=ar.get(i).getPromedioEscala();
                }
            }
            if(id==6){
                if(ar.get(i).getAreaId()==7){
                    porc=ar.get(i).getPromedioEscala();
                }

            }
            if(id==7){
                if(ar.get(i).getAreaId()==8){
                    porc=ar.get(i).getPromedioEscala();
                }
            }
            if(id==8){
                if(ar.get(i).getAreaId()==9){
                    porc=ar.get(i).getPromedioEscala();
                }

            }
            if(id==9){
                if(ar.get(i).getAreaId()==10){
                    porc=ar.get(i).getPromedioEscala();
                }

            }
            if(id==10){
                if(ar.get(i).getAreaId()==11){
                    porc=ar.get(i).getPromedioEscala();
                }

            }
            if(id==11){
                if(ar.get(i).getAreaId()==4){
                    porc=ar.get(i).getPromedioEscala();
                }

            }
            if(id==12){

            }
            if(id==13){

            }
        }
        return porc;
    }

    public void creartablaimagencentra(Bitmap bitmap, String name, String opc) {
        try {
            paragraph = new Paragraph();
            paragraph.setSpacingBefore(0);
            paragraph.setSpacingAfter(20);
            paragraph.setFont(letratexto);
            Image image = null;
            SaveImage(bitmap, name);
            image = Image.getInstance(Environment.getExternalStorageDirectory().toString() + "/recursosisp/" + name + ".png");
            if (opc.equals("encabezado")) {
                image.scaleAbsolute(320f, 80f);
            } else {
                image.scaleAbsolute(80f, 80f);
            }
            PdfPTable pdfPTable = new PdfPTable(1);
            pdfPTable.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell(image);
            // Propiedades de la celda
            cell.setColspan(5);
            cell.setBorderColor(BaseColor.WHITE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);

            // Agregar la celda a la tabla
            pdfPTable.addCell(cell);


            paragraph.add(pdfPTable);
            document.add(paragraph);
        } catch (Exception e) {
            Log.e("creartabla", e.toString());
        }


    }

    public void creartablaimagenGRAFICA2(Bitmap bm,String name,String uri) {
        try {

            paragraph = new Paragraph();
            Image image = null;
            SaveImage(bm, name);
            image = Image.getInstance(Environment.getExternalStorageDirectory().toString() + "/recursosisp/" + name + ".png");
            PdfPTable pdfPTable = new PdfPTable(1);
            image.scaleAbsolute(380f, 720f);
            PdfPCell cell = new PdfPCell(image);
            cell.setBorderColor(BaseColor.WHITE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            // Agregar la celda a la tabla
            pdfPTable.addCell(cell);
            paragraph.add(pdfPTable);
            document.add(paragraph);
        } catch (Exception e) {
            Log.e("creartablaimagengrafica", e.toString());
        }


    }
    public void creartablaimagenGRAFICA(Bitmap bitmap, String name) {
        try {

            paragraph = new Paragraph();
            Image image = null;
            SaveImage(bitmap, name);
            image = Image.getInstance(Environment.getExternalStorageDirectory().getAbsolutePath() + "/recursosisp/" + name + ".png");
            PdfPTable pdfPTable = new PdfPTable(1);
            image.scaleAbsolute(380f, 720f);
            PdfPCell cell = new PdfPCell(image);
            //cell.setColspan(5);
            cell.setBorderColor(BaseColor.WHITE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            // Agregar la celda a la tabla
            pdfPTable.addCell(cell);
            paragraph.add(pdfPTable);
            document.add(paragraph);
        } catch (Exception e) {
            Log.e("creartabla", e.toString());
        }


    }

    public void viewmPDF() {
        Intent intent = new Intent(context, viewPDF.class);
        intent.putExtra("path", pdfFile.getAbsolutePath());
        intent.addFlags((Intent.FLAG_ACTIVITY_CLEAR_TASK));
        context.startActivity(intent);

    }

    public void SaveImage(Bitmap ImageToSave, String name) {


        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recursosisp";

        File dir = new File(file_path);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir, name + ".png");

        try {
            FileOutputStream fOut = new FileOutputStream(file);

            ImageToSave.compress(Bitmap.CompressFormat.PNG, 80, fOut);
            fOut.flush();
            fOut.close();
            MakeSureFileWasCreatedThenMakeAvabile(file);
            AbleToSave();
        } catch (FileNotFoundException e) {
            UnableToSave();
        } catch (IOException e) {
            UnableToSave();
        }

    }
    public void SaveImagejpg(Bitmap ImageToSave, String name) {


        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recursosisp";

        File dir = new File(file_path);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir, name + ".jpeg");

        try {
            FileOutputStream fOut = new FileOutputStream(file);

            ImageToSave.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.flush();
            fOut.close();
            MakeSureFileWasCreatedThenMakeAvabile(file);
            AbleToSave();
        } catch (FileNotFoundException e) {
            UnableToSave();
        } catch (IOException e) {
            UnableToSave();
        }

    }

    private void MakeSureFileWasCreatedThenMakeAvabile(File file) {
        MediaScannerConnection.scanFile(context,
                new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {

                    public void onScanCompleted(String path, Uri uri) {
                    }
                });
    }

    private String getCurrentDateAndTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-­ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    private void UnableToSave() {
        //Probar si se guarda la imagen
        //Toast.makeText(context, "¡No se ha podido guardar la imagen!", Toast.LENGTH_SHORT).show();
    }

    private void AbleToSave() {
        //Probar si no se guardo la imagen
        //Toast.makeText(context, "Imagen guardada en la galería.", Toast.LENGTH_SHORT).show();
    }
}
