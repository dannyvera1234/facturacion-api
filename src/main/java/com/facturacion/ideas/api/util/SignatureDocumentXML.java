package com.facturacion.ideas.api.util;

import com.facturacion.ideas.api.exeption.NotFoundException;
import com.facturacion.ideas.api.exeption.SignatureException;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Component
public class SignatureDocumentXML {

    private String PATH_OUT;
    private String PATH_DOCUMENT_XML;

    private String PATH_CERTIFICATE;
    private String PASSWORD;

    private String KEY_ACCESS;

    private final String SUFFIX_XML_SIGNED = "_sign.xml";


    public String PATH_OUT_SIGNED;

    public SignatureDocumentXML() {
    }

    public String setDataDocumentXML(String ruc, String pathDocumentXML, String password, String
            nameCertificate, String keyAccess) {

        validateField(ruc, pathDocumentXML, password, nameCertificate, keyAccess);

        this.PATH_DOCUMENT_XML = pathDocumentXML;
        this.PASSWORD = password;
        this.KEY_ACCESS = keyAccess;
        this.PATH_CERTIFICATE = PathDocuments.PATH_BASE.concat(ruc).concat("/").concat(nameCertificate);
        this.PATH_OUT = getPathFirmados().concat("firmados");

        isExistsResourcesSignature();

        return signatureXMl();
    }


    private String getPathFirmados() {
        String[] partes = PATH_DOCUMENT_XML.split("/");

        int lastIndex = partes.length - 1;

        StringBuilder pathNew = new StringBuilder();
        for (int i = 0; i < partes.length; i++) {
            if (i != lastIndex)  {
                pathNew.append(partes[i]);
                pathNew.append("/");
            }
        }
        return pathNew.toString();
    }

    private void validateField(String ruc, String pathDocumentXML, String password,
                               String nameCertificate, String keyAccess) {

        if (ruc == null || ruc.isEmpty()) {
            throw new SignatureException("El ruc no puede ser nulo o vacio para firmar");
        }

        if (pathDocumentXML == null || pathDocumentXML.isEmpty()) {
            throw new SignatureException("La ruta de archivo a firmar no puede ser nulo o vacio");
        }

        if (password == null || password.isEmpty()) {
            throw new SignatureException("La contraseña del certificado a firmar no puede ser nuo o vacio");
        }
        if (nameCertificate == null || nameCertificate.isEmpty()) {
            throw new SignatureException("El nombre del certificado a firmar no puede ser nuo o vacio");
        }

        if (keyAccess == null || keyAccess.isEmpty()) {
            throw new SignatureException("La clave de accesso de documento no puede ser nulo o vacio");
        }

    }

    private void isExistsResourcesSignature() {

        if (!new File(PATH_DOCUMENT_XML).exists()) {
            throw new SignatureException("El documento xml no existe en el directorio " + PATH_DOCUMENT_XML);
        }
        if (!new File(PATH_CERTIFICATE).exists()) {
            throw new SignatureException("El certificado  no existe en el directorio " + PATH_CERTIFICATE);
        }

        if (!new File(PATH_OUT).exists()) {
            throw new SignatureException("El directorio para documentos firmado  no existe " + PATH_OUT);
        }

    }

    private String signatureXMl() {
        final String pathJarSignature = "/home/ronny/Documentos/firma.jar";

        String[] commandSignature = new String[8];

        commandSignature[0] = "java";
        commandSignature[1] = "-jar";
        commandSignature[2] = pathJarSignature;

        commandSignature[3] = PATH_CERTIFICATE;
        commandSignature[4] = PASSWORD;
        commandSignature[5] = PATH_DOCUMENT_XML;
        commandSignature[6] = PATH_OUT.concat("/");
        commandSignature[7] = KEY_ACCESS.concat(SUFFIX_XML_SIGNED);

        // Ruta donde se guarda los archivos firmados
        PATH_OUT_SIGNED = commandSignature[6].concat(commandSignature[7]);


        Process process = null;
        try {
            process = Runtime.getRuntime().exec(commandSignature);

        } catch (IOException e) {

            throw new SignatureException("Error al ejecutar comando para firmar el documento " + PATH_DOCUMENT_XML);
        }

        if (process != null) {

            try {
                InputStream saida = new BufferedInputStream(process.getInputStream());
                BufferedReader streamReader = new BufferedReader(new InputStreamReader(saida, StandardCharsets.UTF_8));
                String inputStr;
                while ((inputStr = streamReader.readLine()) != null) {
                    System.out.println(inputStr);
                }

            } catch (IOException e) {
                throw new SignatureException("Error al leer salida de comando firmar" + PATH_DOCUMENT_XML);

            }

        }

        return PATH_OUT_SIGNED;
    }

}
