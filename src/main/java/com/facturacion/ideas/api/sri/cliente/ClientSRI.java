package com.facturacion.ideas.api.sri.cliente;


import com.facturacion.ideas.api.enums.WSTypeEnum;
import com.facturacion.ideas.api.exeption.ConnectionWSException;
import com.facturacion.ideas.api.exeption.ConsumeWebServiceException;
import com.facturacion.ideas.api.exeption.NotFoundException;
import com.facturacion.ideas.api.sri.responses.ProcessResponseAuthorization;
import com.facturacion.ideas.api.sri.responses.ProcessResponseReception;
import com.facturacion.ideas.api.sri.ws.autorizacion.RespuestaComprobante;
import com.facturacion.ideas.api.sri.ws.recepcion.RespuestaSolicitud;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

@Component
public class ClientSRI {

    private static final Logger LOGGER = LogManager.getLogger(ClientSRI.class);
    private HttpURLConnection conn = null;

    private WSTypeEnum wsTypeEnum = null;

    private URL uriLogin = null;

    // FALTA CERRAR LA CONEXION PLZ

    @Autowired
    private ProcessResponseReception marshallResponseReception;

    @Autowired
    private ProcessResponseAuthorization processResponseAuthorization;


    public RespuestaSolicitud receptionDocument(final String pathXMLSigned, WSTypeEnum wsTypeEnum) {

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
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
                return responseOKRecepcion();
            else
                throw new ConsumeWebServiceException("ERROR HTTP: " + conn.getResponseMessage());

        } catch (MalformedURLException e) {
            throw new ConsumeWebServiceException("URL MAL FORMADO: " + e.getMessage());

            // El mensaje viene desde sus metodos  metodo
        } catch (NotFoundException | ConnectionWSException e) {

            throw new ConsumeWebServiceException(e.getMessage());

        } catch (IOException e) {
            throw new ConsumeWebServiceException("ERROR DE LECTURA RESPUESTA: " + e.getMessage());

        } catch (SOAPException e) {
            throw new ConsumeWebServiceException("ERROR AL ACCCEDER AL BODY DE SOAP" + e.getMessage());
        }

    }

    public RespuestaComprobante authorizationDocument(WSTypeEnum wsTypeEnum, final String keyAccess) {

        try {
            this.wsTypeEnum = wsTypeEnum;
            String params = getRequestAuthorization(keyAccess);

            // Open connection
            openConnection();

            // Sender POST
            sendRequestSOAP(params);

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
                return responseOkAuthorization();
            else throw new ConsumeWebServiceException("ERROR HTTP: " + conn.getResponseMessage());

        } catch (MalformedURLException e) {
            throw new ConsumeWebServiceException("URL MAL FORMADO: " + e.getMessage());

            // El mensaje viene desde sus metodos  metodo
        } catch (ConnectionWSException e) {
            throw new ConsumeWebServiceException(e.getMessage());
        } catch (IOException e) {
            throw new ConsumeWebServiceException("ERROR DE LECTURA RESPUESTA: " + e.getMessage());

        } catch (SOAPException e) {
            throw new ConsumeWebServiceException("ERROR AL ACCCEDER AL BODY DE SOAP" + e.getMessage());
        }

    }

    private File getFileXml(String pathXMLSigned) {

        File file = new File(pathXMLSigned);

        if (!file.exists()) {
            throw new NotFoundException("El comprobante a enviar' " + pathXMLSigned + " ' no existe");
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
                "            <claveAccesoComprobante>" + keyAccess + "</claveAccesoComprobante>" +
                "        </ec:autorizacionComprobante>" +
                "    </x:Body>" +
                "</x:Envelope>";
    }


    private void openConnection() {

        conn = null;
        uriLogin = null;

        try {

            uriLogin = new URL(wsTypeEnum.getWsdl());
            conn = (HttpURLConnection) uriLogin.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
        } catch (IOException e) {
            throw new ConnectionWSException("Error al abrir coneccion con Web Service: " + e.getMessage());

        }


    }

    private void sendRequestSOAP(String params) throws IOException {

        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(params);
        wr.flush();
        wr.close();
    }

    private RespuestaSolicitud responseOKRecepcion() throws SOAPException, IOException {
        // Read response as String
        StringBuilder responseStrBuilder = getDataReadResponse();

        // Converter to SOAP BODY la response leida anteriorment como String
        SOAPBody body = getSOAPBodyResponse(responseStrBuilder);

        //NodeList returnList = body.getElementsByTagName("web:RES");
        //NodeList list = body.getElementsByTagName("autorizaciones");

        return marshallResponseReception.processResponseRequest(body);
    }

    private RespuestaComprobante responseOkAuthorization() throws IOException, SOAPException {


        StringBuilder responseStrBuilder = getDataReadResponse();

        SOAPBody body = getSOAPBodyResponse(responseStrBuilder);

        //NodeList returnList = body.getElementsByTagName("web:RES");
        //NodeList list = body.getElementsByTagName("autorizaciones");


        return processResponseAuthorization.processResponseRequest(body);
           /* String respuesta = "/home/ronny/Documentos/respuestaxml";
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(respuesta + ".xml"), StandardCharsets.UTF_8));
            try {
                out.write(body.getElementsByTagName("autorizacion").item(0).getTextContent().replaceAll("\\p{Cntrl}", "\n").replaceAll("\t\r", ""));
            } finally {
                out.close();
            }*/


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
