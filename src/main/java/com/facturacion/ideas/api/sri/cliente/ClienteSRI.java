package com.facturacion.ideas.api.sri.cliente;


import com.facturacion.ideas.api.documents.factura.Factura;
import com.facturacion.ideas.api.exeption.NotFoundException;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.w3c.dom.NodeList;

import javax.xml.soap.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Base64;

@Component
public class ClienteSRI {

    private static final Logger LOGGER = LogManager.getLogger(ClienteSRI.class);

    public void validarComprobante(Factura factura) throws IOException {

        /*

        // Crear instancia de la implementacion del servicio
        RecepcionComprobantesOfflineService service = new RecepcionComprobantesOfflineService();

        // Obtener una instancia del servicio
        RecepcionComprobantesOffline recepcionWs = service.getRecepcionComprobantesOfflinePort();

        // leer el archivo xml generado anteriormente
        File file = getFileXml(factura);

        // Hacer la petición
        RespuestaSolicitud respuestaSolicitud = recepcionWs.validarComprobante(archivoToByte(file));

        // System.out.println(respuestaSolicitud.getComprobantes().getComprobante().get(0).getMensajes().getMensaje().get(0).getMensaje());

        System.out.println("Estado: " + respuestaSolicitud.getEstado());

        RespuestaSolicitud.Comprobantes comprobante = respuestaSolicitud.getComprobantes();

        List<Comprobante> comprobanteList = comprobante.getComprobante();

        for (Comprobante item : comprobanteList) {


            System.out.println("Clave de acceso: " + item.getClaveAcceso());

            Comprobante.Mensajes mensajes = item.getMensajes();

            List<Mensaje> mensajeList = mensajes.getMensaje();

            for (Mensaje sms : mensajeList) {

                System.out.println("identificador: " + sms.getIdentificador());
                System.out.println("mensaje: " + sms.getMensaje());
                System.out.println("info adicioanal: " + sms.getInformacionAdicional());
                System.out.println("Tipo: " + sms.getTipo());

            }


        }*/


    }


    public void recepcionComprobante() {

        final String WSDL = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl";

        try {
            File file = getFileXml(null);

            FileInputStream inp = new FileInputStream(file);
            byte[] todo = inp.readAllBytes();


            String xmlString = Base64.getEncoder().encodeToString(todo);

            //byte[] xmlBase64 = archivoToByte(file);

            String params = "<x:Envelope xmlns:x=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ec=\"http://ec.gob.sri.ws.recepcion\">" +
                    "    <x:Header/>" +
                    "    <x:Body>" +
                    "        <ec:validarComprobante>" +
                    "            <xml>" + xmlString + "</xml>" +
                    "        </ec:validarComprobante>" +
                    "    </x:Body>" +
                    "</x:Envelope>";


            HttpURLConnection conn = null;
            URL uriLogin;

            try {

                uriLogin = new URL(WSDL);
                conn = (HttpURLConnection) uriLogin.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");

                //Sender POST
                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(params);
                wr.flush();
                wr.close();


                if (conn.getResponseCode() == conn.HTTP_OK) {
                    StringBuilder responseStrBuilder = new StringBuilder();
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

                    String inputStr;
                    while ((inputStr = streamReader.readLine()) != null) {
                        responseStrBuilder.append(inputStr);
                    }
                    System.out.println(
                            "Respuesta: " + responseStrBuilder.toString()
                    );

                    String xml = responseStrBuilder.toString();

                    MessageFactory factory;
                    try {
                        factory = MessageFactory.newInstance();
                        SOAPMessage message = factory.createMessage(new MimeHeaders(), new ByteArrayInputStream(xml.getBytes(Charset.forName("UTF-8"))));
                        SOAPBody body = message.getSOAPBody();
                        //NodeList returnList = body.getElementsByTagName("web:RES");
                        NodeList list = body.getElementsByTagName("autorizaciones");


                        /*Writer out = new BufferedWriter(new OutputStreamWriter( new FileOutputStream("comprobante_"+numero+".xml"), "UTF-8"));
                        try {
                            out.write( body.getElementsByTagName("autorizacion").item(0).getTextContent().replaceAll("\\p{Cntrl}", "\n").replaceAll("\t\r", "")  );
                        } finally {
                            out.close();
                        }*/

                    } catch (SOAPException ex) {

                        System.out.println("Erorr: " + ex.getMessage());
                    }


                } else {
                    System.err.println("HTTP: " + conn.getResponseMessage());
                }


            } catch (Exception e) {


                System.out.println("Erro principal: " + e.getMessage());
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void autorizacion() {

        final String WSDL = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl";

        try {
            File file = getFileXml(null);

            FileInputStream inp = new FileInputStream(file);
            byte[] todo = inp.readAllBytes();

            String numero = "1511202201130875419900110010020000000203333333717";
            //String xmlString = Base64.getEncoder().encodeToString(todo);

            //byte[] xmlBase64 = archivoToByte(file);

            String params = "<x:Envelope xmlns:x=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ec=\"http://ec.gob.sri.ws.autorizacion\">" +
                    "    <x:Header/>" +
                    "    <x:Body>" +
                    "        <ec:autorizacionComprobante>" +
                    "            <claveAccesoComprobante>" + numero + "</claveAccesoComprobante>" +
                    "        </ec:autorizacionComprobante>" +
                    "    </x:Body>" +
                    "</x:Envelope>";


            HttpURLConnection conn = null;
            URL uriLogin;

            try {

                uriLogin = new URL(WSDL);
                conn = (HttpURLConnection) uriLogin.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");

                //Sender POST
                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(params);
                wr.flush();
                wr.close();


                if (conn.getResponseCode() == conn.HTTP_OK) {
                    StringBuilder responseStrBuilder = new StringBuilder();
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

                    String inputStr;
                    while ((inputStr = streamReader.readLine()) != null) {
                        responseStrBuilder.append(inputStr);
                    }
                    System.out.println(
                            "Respuesta: " + responseStrBuilder.toString()
                    );

                    String xml = responseStrBuilder.toString();

                    MessageFactory factory;
                    try {
                        factory = MessageFactory.newInstance();
                        SOAPMessage message = factory.createMessage(new MimeHeaders(), new ByteArrayInputStream(xml.getBytes(Charset.forName("UTF-8"))));
                        SOAPBody body = message.getSOAPBody();
                        //NodeList returnList = body.getElementsByTagName("web:RES");
                        NodeList list = body.getElementsByTagName("autorizaciones");


                        /*Writer out = new BufferedWriter(new OutputStreamWriter( new FileOutputStream("comprobante_"+numero+".xml"), "UTF-8"));
                        try {
                            out.write( body.getElementsByTagName("autorizacion").item(0).getTextContent().replaceAll("\\p{Cntrl}", "\n").replaceAll("\t\r", "")  );
                        } finally {
                            out.close();
                        }*/

                    } catch (SOAPException ex) {

                        System.out.println("Erorr: " + ex.getMessage());
                    }


                } else {
                    System.err.println("HTTP: " + conn.getResponseMessage());
                }


            } catch (Exception e) {


                System.out.println("Erro principal: " + e.getMessage());
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File getFileXml(Factura factura) {


      /*  final String pathArchivoXml = PathDocuments.PATH_BASE + "/" +
                factura.getInfoTributaria().getRuc() + "/" + "est_" + factura.getInfoTributaria().getEstab()
                + "/emi_" + factura.getInfoTributaria().getPtoEmi() + "/" + factura.getInfoTributaria().getClaveAcceso()
                + ".xml";
        */


        String paht = "/home/ronny/Documentos/Ronny/proyecto_factura/archivos_sri/firmados/1511202201130875419900110010020000000203333333717_sign.xml";
        //String path = "/home/ronny/Documentos/Ronny/factura_bd/generados/1311202201130875419900110010010000000111234567811.xml";
        File file = new File(paht);

        if (!file.exists()) {
            throw new NotFoundException("La factura " + factura.getInfoTributaria().getClaveAcceso() + " no se encuentra en "
                    + "");
        }
        return file;


    }

    private void firmarXml() {

        String xmlPath = "/home/ronny/Documentos/Ronny/escribirXML/1308754199001/est_001/emi_001/1311202201130875419900110010010000000152204061210.xml";
        String pathSignature = "/home/ronny/Documentos/Ronny/proyecto_factura/archivos_sri/VERONICA PATRICIA QUIMIS LEON 130922105723.p12";
        String passSignature = "justin06";

        String pathOut = "/home/ronny/Documentos/Ronny/proyecto_factura/archivos_sri/firmados";
        String nameFileOut = "1311202201130875419900110010010000000152204061210_sign.xml";

        System.out.println("Ruta del XML de entrada: " + xmlPath);
        System.out.println("Ruta Certificado: " + pathSignature);
        System.out.println("Clave del Certificado: " + passSignature);
        System.out.println("Ruta de salida del XML: " + pathOut);
        System.out.println("Nombre del archivo salido: " + nameFileOut);

        try{
          //XAdESBESSignature.firmar(xmlPath, pathSignature, passSignature, pathOut, nameFileOut);
        }catch(Exception e){
            System.out.println("Error: " + e);
        }

    }

    private byte[] archivoToByte(File file) throws IOException {
        String archivo = FileUtils.readFileToString(file, "UTF-8");
        return archivo.getBytes(Charset.forName("UTF-8"));
    }


}
