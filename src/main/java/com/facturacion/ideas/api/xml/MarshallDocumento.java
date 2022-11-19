package com.facturacion.ideas.api.xml;

import com.facturacion.ideas.api.documents.factura.Factura;
import com.facturacion.ideas.api.services.SenderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;

public class MarshallDocumento {

    public static String marshalFactura(Factura comprobante, String pathArchivoSalida) throws JAXBException, IOException {

            String pathFileGenerate = null;
            JAXBContext context = JAXBContext.newInstance(new Class[]{Factura.class});
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.encoding", "UTF-8");
            marshaller.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            OutputStreamWriter out = new OutputStreamWriter(byteArrayOutputStream, "UTF-8");
            marshaller.marshal(comprobante, out);
            OutputStream outputStream = new FileOutputStream(pathArchivoSalida);
            byteArrayOutputStream.writeTo(outputStream);
            byteArrayOutputStream.close();
            outputStream.close();

            pathFileGenerate = pathArchivoSalida;

            return  pathFileGenerate;
    }


}
