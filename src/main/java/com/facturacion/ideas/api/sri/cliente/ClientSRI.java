package com.facturacion.ideas.api.sri.cliente;


import com.facturacion.ideas.api.enums.WSTypeEnum;
import com.facturacion.ideas.api.exeption.NotFoundException;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.w3c.dom.NodeList;

import javax.xml.soap.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Component
public class ClientSRI {

    private static final Logger LOGGER = LogManager.getLogger(ClientSRI.class);
    private HttpURLConnection conn = null;

    private WSTypeEnum wsTypeEnum = null;

    private URL uriLogin = null;

    // FALTA CERRAR LA CONEXION PLZ

    public void receptionDocument(final String pathXMLSigned, WSTypeEnum wsTypeEnum) {

        try {
            this.wsTypeEnum = wsTypeEnum;
            File file = getFileXml(pathXMLSigned);

            FileInputStream inp = new FileInputStream(file);
            byte[] xmlByte = inp.readAllBytes();
            String xmlBase64String = Base64.getEncoder().encodeToString(xmlByte);

            String params = getRequestReception(xmlBase64String);

            // Open connection
            openConnection();

            //Sender POST
            sendRequestSOAP(params);

            // Verifier request connexion
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                responseOKRecepcion();
            } else {
                System.err.println("ERROR HTTP : " + conn.getResponseMessage());
            }

        } catch (MalformedURLException e) {

            System.out.println("URL MAL FORMADO " + e.getMessage());
        } catch (NotFoundException e) {

            System.out.println("FILE NO encontrado: " + e.getMessage());
        } catch (IOException e) {

            System.out.println("ERROR DE CONEXION: " + e.getMessage());
            //throw new RuntimeException(e);
        }
    }

    public void authorizationDocument(WSTypeEnum wsTypeEnum, final String keyAccess) {

        try {
            this.wsTypeEnum = wsTypeEnum;
            String params = getRequestAuthorization(keyAccess);

            // Open connection
            openConnection();

            // Sender POST
            sendRequestSOAP(params);

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                responseOkAuthorization();
            } else {
                System.err.println("HTTP ERROR: " + conn.getResponseMessage());
            }


        } catch (MalformedURLException e) {

            System.out.println("URL MAL FORMADO " + e.getMessage());
        } catch (IOException e) {

            System.out.println("ERROR DE CONEXION: " + e.getMessage());
            //throw new RuntimeException(e);
        }
    }

    private File getFileXml(String pathXMLSigned) {

        File file = new File(pathXMLSigned);

        if (!file.exists()) {
            throw new NotFoundException("La factura a firmar' " + pathXMLSigned + " ' no se encuentra guardada");
        }
        return file;

    }

    private String getRequestReception(String xmlBase64) {
        return "<x:Envelope xmlns:x=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ec=\"http://ec.gob.sri.ws.recepcion\">" +
                "    <x:Header/>" +
                "    <x:Body>" +
                "        <ec:validarComprobante>" +
                "            <xml>" + xmlBase64 + "</xml>" +
                "        </ec:validarComprobante>" +
                "    </x:Body>" +
                "</x:Envelope>";
    }

    private String getRequestAuthorization(final String keyAccess) {

        return "<x:Envelope xmlns:x=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ec=\"http://ec.gob.sri.ws.autorizacion\">" +
                "    <x:Header/>" +
                "    <x:Body>" +
                "        <ec:autorizacionComprobante>" +
                "            <claveAccesoComprobante>"+keyAccess+"</claveAccesoComprobante>" +
                "        </ec:autorizacionComprobante>" +
                "    </x:Body>" +
                "</x:Envelope>";
    }


    private void openConnection() throws IOException {

        conn = null;
        uriLogin = null;

        uriLogin = new URL(wsTypeEnum.getWsdl());
        conn = (HttpURLConnection) uriLogin.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setReadTimeout(15000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("POST");
    }


    private void sendRequestSOAP(String params) throws IOException {

        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(params);
        wr.flush();
        wr.close();
    }

    private void responseOKRecepcion() {

        try {
            // Read response as String
            StringBuilder responseStrBuilder = getDataReadResponse();

            // Converter to SOAP BODY la response leida anteriorment como String
            SOAPBody body = getSOAPBodyResponse(responseStrBuilder);

            //NodeList returnList = body.getElementsByTagName("web:RES");
            NodeList list = body.getElementsByTagName("autorizaciones");

                        /*Writer out = new BufferedWriter(new OutputStreamWriter( new FileOutputStream("comprobante_"+numero+".xml"), "UTF-8"));
                        try {
                            out.write( body.getElementsByTagName("autorizacion").item(0).getTextContent().replaceAll("\\p{Cntrl}", "\n").replaceAll("\t\r", "")  );
                        } finally {
                            out.close();
                        }*/

        } catch (IOException e) {
            System.out.println("Error al leer la informacion : " + e.getMessage());


        } catch (SOAPException e) {

            System.out.println("Erorr SOAP: " + e.getMessage());
        }
    }

    private void responseOkAuthorization() {

        try {

            StringBuilder responseStrBuilder = getDataReadResponse();

            SOAPBody body = getSOAPBodyResponse(responseStrBuilder);

            //NodeList returnList = body.getElementsByTagName("web:RES");
            NodeList list = body.getElementsByTagName("autorizaciones");


                        /*Writer out = new BufferedWriter(new OutputStreamWriter( new FileOutputStream("comprobante_"+numero+".xml"), "UTF-8"));
                        try {
                            out.write( body.getElementsByTagName("autorizacion").item(0).getTextContent().replaceAll("\\p{Cntrl}", "\n").replaceAll("\t\r", "")  );
                        } finally {
                            out.close();
                        }*/

        } catch (IOException e) {
            System.out.println("Error al leer la informacion : " + e.getMessage());


        } catch (SOAPException e) {

            System.out.println("Error SOAP: " + e.getMessage());
        }
    }

    private StringBuilder getDataReadResponse() throws IOException {

        StringBuilder responseStrBuilder = new StringBuilder();
        InputStream in = new BufferedInputStream(conn.getInputStream());
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String inputStr;
        while ((inputStr = streamReader.readLine()) != null) {
            responseStrBuilder.append(inputStr);
        }

        System.out.println(
                "Respuesta " + wsTypeEnum.getNameService() + " " + wsTypeEnum.getEnvironmentEnum().getCode() + " " + responseStrBuilder.toString()
        );

        return responseStrBuilder;
    }


    private SOAPBody getSOAPBodyResponse(StringBuilder dataResponseRead) throws SOAPException, IOException {
        String xml = dataResponseRead.toString();

        MessageFactory factory;
        factory = MessageFactory.newInstance();
        SOAPMessage message = factory.createMessage(new MimeHeaders(), new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));

        return message.getSOAPBody();
    }

    private byte[] archivoToByte(File file) throws IOException {
        String archivo = FileUtils.readFileToString(file, "UTF-8");
        return archivo.getBytes(Charset.forName("UTF-8"));
    }


}
