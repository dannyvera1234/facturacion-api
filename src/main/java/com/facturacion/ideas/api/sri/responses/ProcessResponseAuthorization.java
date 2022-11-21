package com.facturacion.ideas.api.sri.responses;


import com.facturacion.ideas.api.controllers.DocumentRestController;
import com.facturacion.ideas.api.documents.factura.Factura;
import com.facturacion.ideas.api.enums.StatusDocumentsEnum;
import com.facturacion.ideas.api.sri.ws.autorizacion.Autorizacion;
import com.facturacion.ideas.api.sri.ws.autorizacion.RespuestaComprobante;
import com.facturacion.ideas.api.util.ConstanteUtil;
import com.facturacion.ideas.api.util.FunctionUtil;
import com.facturacion.ideas.api.util.PathDocuments;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.soap.SOAPBody;
import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;

@Component
public class ProcessResponseAuthorization {

    private static final Logger LOGGER = LogManager.getLogger(ProcessResponseAuthorization.class);

    private Factura factura;


    public RespuestaComprobante processResponseRequest(SOAPBody body) {

        RespuestaComprobante respuestaComprobante = new RespuestaComprobante();

        // Acceso al nodo RespuestaAutorizacionComprobante
        Element nodeParent = (Element) body.getElementsByTagName("RespuestaAutorizacionComprobante").item(0);


        // Obtener los hijos de RespuestaAutorizacionComprobante
        NodeList nodeList = nodeParent.getChildNodes();

        // Recorre cada hijo elemento
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node element = nodeList.item(i);

            if (element.getNodeName().equalsIgnoreCase("claveAccesoConsultada")) {
                respuestaComprobante.setClaveAccesoConsultada(element.getTextContent());
            }

            if (element.getNodeName().equalsIgnoreCase("numeroComprobantes")) {
                respuestaComprobante.setNumeroComprobantes(element.getTextContent());
            }

            if (element.getNodeName().equalsIgnoreCase("autorizaciones") && element.hasChildNodes()) {
                respuestaComprobante.setAutorizaciones(createWrapperAuthorizationes(element));
            }

        }
        return  respuestaComprobante;
    }

    /**
     *
     * @param node : nodo autorizaciones
     * @return
     */
    private  RespuestaComprobante.Autorizaciones createWrapperAuthorizationes(Node node){


        RespuestaComprobante.Autorizaciones autorizaciones = new RespuestaComprobante.Autorizaciones();

        // Nodo autorizaciones: solo hay uno siempre
        Node nodeAutorizaciones = node.getFirstChild();

        // Nodos autorizacion
        NodeList listNodeAuthorization = nodeAutorizaciones.getChildNodes();

        for (int i = 0;i< listNodeAuthorization.getLength();i++){

            // Nodo autorizacion
            Node nodeAutorizathion = listNodeAuthorization.item(i);

            // Autorizacion generada
            Autorizacion autorizacion = createItemAuthorization(nodeAutorizaciones);

            autorizaciones.getAutorizacion().add(autorizacion);

        }

        return  autorizaciones;

    }

    private Autorizacion createItemAuthorization(Node nodeAutorization){

        Autorizacion autorizacion = new Autorizacion();

        // Los hijos de autorizacion
        NodeList nodeListChild = nodeAutorization.getChildNodes();

        for (int i =0; i< nodeListChild.getLength();i++){

            // Cada nodo hijo
            Node nodeChild = nodeListChild.item(i);

            switch (nodeChild.getNodeName()){

                case "estado":
                    autorizacion.setEstado(nodeChild.getTextContent());
                    break;

                case "numeroAutorizacion":
                    autorizacion.setNumeroAutorizacion(nodeChild.getTextContent());
                    break;

                case "fechaAutorizacion":

                    try {
                        Date date = FunctionUtil.convertStringToDateNormal(nodeChild.getTextContent());

                        GregorianCalendar gc = new GregorianCalendar();

                        gc.setTime(date);
                        autorizacion.setFechaAutorizacion(DatatypeFactory.newInstance().newXMLGregorianCalendar(gc));

                    } catch (ParseException e) {
                        //throw new RuntimeException(e);
                        LOGGER.error("Error al convertir fecha xml: ", e);

                    } catch (DatatypeConfigurationException e) {
                        LOGGER.error("Error al convertir fecha xml: ", e);
                    }

                    break;

                case "ambiente":
                    autorizacion.setAmbiente(nodeChild.getTextContent());
                    break;

                case "comprobante":

                    writeComprobanteAuthorization( nodeAutorization.getTextContent(),  autorizacion.getEstado());

                    break;
            }
        }

        return autorizacion;
    }

    /**
     * Aqui vamos a esscribir el comprobante autorizado o no autorizado
     * @param dataComprobante : representa la informacion recibida por el webservice
     * @param status : el estado
     */
    private  void writeComprobanteAuthorization(String dataComprobante, String status){

        String pathBase = PathDocuments.PATH_BASE;

        if (status.trim().equalsIgnoreCase(StatusDocumentsEnum.AUTORIZADO.getName())){

            // Eliminar el comprobante  firmado


        }

    }
}
