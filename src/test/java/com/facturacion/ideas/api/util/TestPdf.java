package com.facturacion.ideas.api.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestPdf {


    @Test
    void testPdfEjemplo() throws IOException, DocumentException {

        // Creacion del documento con los margenes
        Document document = new Document(PageSize.A4, 35, 30, 50, 50);

// El archivo pdf que vamos a generar
        FileOutputStream fileOutputStream = new FileOutputStream(
                "/home/ronny/Documentos/Ronny/reportePDFDatoJava.pdf");

// Obtener la instancia del PdfWriter
        PdfWriter.getInstance(document, fileOutputStream);

// Abrir el documento
        document.open();

        // Creacion del parrafo
        Paragraph paragraph = new Paragraph();

// Agregar un titulo con su respectiva fuente
        paragraph.add(new Phrase("Características:"));

        document.add(paragraph);


        document.close();


        // Abrir el archivo
        File file = new File("/home/ronny/Documentos/Ronny/reportePDFDatoJava.pdf");
        Desktop.getDesktop().open(file);


    }
}
