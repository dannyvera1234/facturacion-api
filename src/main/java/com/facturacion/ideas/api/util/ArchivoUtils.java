package com.facturacion.ideas.api.util;


import com.facturacion.ideas.api.documents.factura.Factura;
import com.facturacion.ideas.api.exeption.GenerateXMLExeption;
import com.facturacion.ideas.api.xml.MarshallDocumento;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class ArchivoUtils {

    private static final Logger LOGGER = LogManager.getLogger(ArchivoUtils.class);

    public static String realizarMarshall(Object comprobate, String pathArchivoSalida){

        try {

            if (comprobate instanceof Factura) {
                return MarshallDocumento.marshalFactura((Factura) comprobate, pathArchivoSalida);
            }
        } catch (JAXBException e) {

           LOGGER.error("Error de formato de comprobate XML Factura", e);
            throw new GenerateXMLExeption("Ocurrio un error con el formato XML de Factura: " + e.getMessage());

        } catch (IOException e) {
            LOGGER.error("Error de escritura para el comprobate XML Factura", e);
            throw new GenerateXMLExeption("Error de escritura para comprobate XML: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Error inesperado en el comprobate XML Factura", e);
            throw new GenerateXMLExeption("Ocurrio un error inesperado al generar el XML: " + e.getMessage());
        }

        return null;
    }
}
