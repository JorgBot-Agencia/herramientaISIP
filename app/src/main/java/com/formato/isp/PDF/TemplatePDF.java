package com.formato.isp.PDF;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.formato.isp.R;
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


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TemplatePDF {

    private Context context;
    private File pdfFile;
    private Document document;
    private PdfWriter pdfwriter;
    private Paragraph paragraph;// este es el parrafoo
    private Font letratitulo=new Font(Font.FontFamily.TIMES_ROMAN, 20,Font.BOLD);
    private Font letraroja=new Font(Font.FontFamily.TIMES_ROMAN, 20,Font.BOLD, BaseColor.RED);
    private Font letrasubtitulo=new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
    private Font letratexto=new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD);
    private Font letratexto2=new Font(Font.FontFamily.TIMES_ROMAN, 20,Font.BOLD);
    private Font letraresaltado=new Font(Font.FontFamily.TIMES_ROMAN, 15,Font.BOLD, BaseColor.RED);

    public TemplatePDF(Context context) {
        this.context=context;
    }

    private void crearFile(){
        File folder = new File(Environment.getExternalStorageDirectory().toString(),"PDF");
        if(!folder.exists())
            folder.mkdirs();
            pdfFile = new File(folder, "TemplatePDF.pdf");


    }

    public void OpenDocument(){
        crearFile();
        try{
            document = new Document(PageSize.A4);
            pdfwriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();
        }catch (Exception e){
            Log.e("openDocument", e.toString());
        }
    }

    public void closeDocument(){
        document.close();
    }

    public void addMetadata(String titulo, String tema, String autor){
        document.addTitle(titulo);
        document.addSubject(tema);
        document.addAuthor(autor);
    }

    public void addTitulos(String titulo, String subtitulo, String fecha){
        try {


            paragraph = new Paragraph();
            addparafoHijocentrado(new Paragraph(titulo, letratitulo));
            addparafoHijocentrado(new Paragraph(subtitulo, letrasubtitulo));
            addparafoHijocentrado(new Paragraph(fecha, letraresaltado));
            paragraph.setSpacingAfter(30);//espacio entre parrafo padre y parrafos hijos
            document.add(paragraph);
        }catch (Exception e){
            Log.e("addTutulos", e.toString());
        }
    }

    public void addTitulosizq(String nombre, String ubicacion, String telefono, String correo){
        try {


            paragraph = new Paragraph();
            addparafoHijoizquierda(new Paragraph(nombre, letratitulo));
            addparafoHijoizquierda(new Paragraph(ubicacion, letrasubtitulo));
            addparafoHijoizquierda(new Paragraph(telefono, letrasubtitulo));
            addparafoHijoizquierda(new Paragraph(correo, letrasubtitulo));
            paragraph.setSpacingAfter(0);//espacio entre parrafo padre y parrafos hijos
            document.add(paragraph);
        }catch (Exception e){
            Log.e("addTutulos", e.toString());
        }
    }
    public void addletraroja(String nombre){
        try {


            paragraph = new Paragraph();
            addparafoHijoizquierda(new Paragraph(nombre, letraroja));
            paragraph.setSpacingAfter(5);//espacio entre parrafo padre y parrafos hijos
            document.add(paragraph);
        }catch (Exception e){
            Log.e("addTutulos", e.toString());
        }
    }

    private void addparafoHijocentrado(Paragraph parafoHijo){
        parafoHijo.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(parafoHijo);
    }

    private void addparafoHijoizquierda(Paragraph parafoHijo){
        parafoHijo.setAlignment(Element.ALIGN_LEFT);
        paragraph.add(parafoHijo);
    }
    public void addparrafo(String text){
        try {

            paragraph = new Paragraph(text, letratexto);
            paragraph.setSpacingAfter(5);
            paragraph.setSpacingBefore(5);
            document.add(paragraph);
        }catch (Exception e){
            Log.e("addparrafo", e.toString());
        }
    }


    public void addtitulo(String text){
        try {


            paragraph = new Paragraph(text, letratitulo);
            paragraph.setSpacingAfter(5);
            paragraph.setSpacingBefore(5);
            document.add(paragraph);
        }catch (Exception e){
            Log.e("addparrafo", e.toString());
        }
    }



    public void creartabla(String[]encabezado, ArrayList<String[]>clients){
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
        }catch (Exception e){
            Log.e("creartabla", e.toString());
        }


    }

    public void creartablaimagencentra(Bitmap bitmap ,String name){
        try {


            //Uri path = Uri.parse("android.resource://com.segf4ult.test/" + R.drawable.isip);
            //String pat = path.toString();
            //String imageUrl =   getURLForResource(R.drawable.isip);
            paragraph = new Paragraph();
            paragraph.setSpacingBefore(10);
            paragraph.setSpacingAfter(10);
            paragraph.setFont(letratexto);
            Image image = null;
            Image image2 = null;

            // Obtenemos el logo de datojava
            //Environment.getExternalStorageDirectory().toString() es el directorio raiz
            //image = Image.getInstance(Environment.getExternalStorageDirectory().toString()+"/sdcard/isip.png");
            //image = Image.getInstance(Environment.getExternalStorageDirectory().toString()+"/sdcard/isip.png");

            //image = Image.getInstance("data/data/com.formato.isp//sdcard/isip.png");

            SaveImage(bitmap, name);
            //SaveImage(bitmap2, name);
            image = Image.getInstance(Environment.getExternalStorageDirectory().toString()+"/recursosisp/"+name+".png");
            //image2 = Image.getInstance(Environment.getExternalStorageDirectory().toString()+"/recursosisp/"+name2+".png");
            image.scaleAbsolute(320f, 80f);
            PdfPTable pdfPTable = new PdfPTable(1);
            PdfPCell cell = new PdfPCell(image);
            //PdfPCell cell2 = new PdfPCell(image2);

            // Propiedades de la celda
            cell.setColspan(5);
            cell.setBorderColor(BaseColor.WHITE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);

            // Agregar la celda a la tabla
            pdfPTable.addCell(cell);


            //paragraph.add(pdfPTable);
            document.add(pdfPTable);
        }catch (Exception e){
            Log.e("creartabla", e.toString());
        }


    }

    public void viewmPDF(){
        Intent intent= new Intent(context,viewPDF.class);
        intent.putExtra("path",pdfFile.getAbsolutePath());
        intent.addFlags((Intent.FLAG_ACTIVITY_CLEAR_TASK));
        context.startActivity(intent);

    }

    public void SaveImage( Bitmap ImageToSave, String name) {


        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recursosisp";

        File dir = new File(file_path);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir, name + ".png");

        try {
            FileOutputStream fOut = new FileOutputStream(file);

            ImageToSave.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
            MakeSureFileWasCreatedThenMakeAvabile(file);
            AbleToSave();
        }

        catch(FileNotFoundException e) {
            UnableToSave();
        }
        catch(IOException e) {
            UnableToSave();
        }

    }
    private void MakeSureFileWasCreatedThenMakeAvabile(File file){
        MediaScannerConnection.scanFile(context,
                new String[] { file.toString() } , null,
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
        Toast.makeText(context, "¡No se ha podido guardar la imagen!", Toast.LENGTH_SHORT).show();
    }

    private void AbleToSave() {
        Toast.makeText(context, "Imagen guardada en la galería.", Toast.LENGTH_SHORT).show();
    }
}
