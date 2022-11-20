package com.facturacion.ideas.api.util;

import com.facturacion.ideas.api.exeption.SignatureException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Component
public class SignatureDocumentXML {

    private static final Logger LOGGER = LogManager.getLogger(SignatureDocumentXML.class);

    private String PATH_OUT;
    private String PATH_DOCUMENT_XML;

    private String PATH_CERTIFICATE;
    private String PASSWORD;

    private String KEY_ACCESS;

    private final String SUFFIX_XML_SIGNED = "_sign.xml";

    private final String PATH_JAR_SIGNATURE = "src/main/resources/lib/jar_firma.jar";


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

        // Debe ser llamado antes de verificar los directorios
        createDirectorioFirmados();

        isExistsResourcesSignature();

        return signatureXMl();
    }


    /**
     * Crear el directorio para los documentos firmados
     */
    private void createDirectorioFirmados() {


        File fileToSigned = new File(this.PATH_OUT);

        if (!fileToSigned.exists()) {

            if (!fileToSigned.mkdirs()) {
                throw new SignatureException("EL directorio " + PATH_OUT + "  para los documentos firmados no pudo ser creado");
            }
        }
    }

    private String getPathFirmados() {
        String[] partes = PATH_DOCUMENT_XML.split("/");

        int lastIndex = partes.length - 1;

        StringBuilder pathNew = new StringBuilder();
        for (int i = 0; i < partes.length; i++) {
            if (i != lastIndex) {
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

        if (!new File(PATH_JAR_SIGNATURE).exists()) {
            throw new SignatureException("El jar para firmar los documentos no existe " + PATH_JAR_SIGNATURE);
        }

    }

    private String signatureXMl() {

        String[] commandSignature = createCommandsToSignature();


        Process process;
        try {
            process = Runtime.getRuntime().exec(commandSignature);

        } catch (IOException e) {

            throw new SignatureException("Error al ejecutar comando para firmar el documento " + PATH_DOCUMENT_XML);
        }
        if (process != null) processDataProcess(process);
        else throw new SignatureException("No se firmo el documento ubicado en " + PATH_DOCUMENT_XML);

        return PATH_OUT_SIGNED;
    }

    /**
     * Méthod que se encarga de leer la información de salida del jar utilizar para firmar.
     * EL jar para firma imprime un Map en consola con información sobre la firma del documento.
     * @param process : La informacion a Leer
     */
    private void processDataProcess(Process process) {

        try {
            InputStream saida = new BufferedInputStream(process.getInputStream());
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(saida, StandardCharsets.UTF_8));
            String inputStr;

            Map<String, String> dataOutputSigned = new HashMap<>();
            int columnKey = 0;
            int columnData = 1;

            while ((inputStr = streamReader.readLine()) != null) {
                String[] dataRead = dataOutReadSigned(inputStr);
                // Reecreamos la representacion del map de salida de jar para acceder a las propiedades
                dataOutputSigned.put(dataRead[columnKey], dataRead[columnData]);
            }

            String statusSigned = dataOutputSigned.get("ESTADO");

            if (statusSigned != null && statusSigned.equalsIgnoreCase("FIRMADO")) {
                LOGGER.info("Documento firmado: " + statusSigned + " PATH: "
                        + dataOutputSigned.get("PATH_OUT") + dataOutputSigned.get("NAME_FILE_OUT"));

            }else throw  new SignatureException("El documento  " +  dataOutputSigned.get("PATH_XML_ENTRADA") + " no pudo ser firmado");

        } catch (IOException e) {
            throw new SignatureException("Error al verificar la salida del comando firmar" + PATH_DOCUMENT_XML);
        }


    }

    /**
     * Separar en dos pártes cada linea leida
     * Ejemplo del formado: dato1?dato2
     * Donde valor1 respresenta a la clave del Map y valor2 a su valor
     * @param lineRead
     * @return
     */
    private String[] dataOutReadSigned(String lineRead) {
        return lineRead.split("\\?");

    }

    private String[] createCommandsToSignature() {

        String[] commandSignature = new String[8];

        commandSignature[0] = "java";
        commandSignature[1] = "-jar";
        commandSignature[2] = PATH_JAR_SIGNATURE;

        commandSignature[3] = PATH_CERTIFICATE;
        commandSignature[4] = PASSWORD;
        commandSignature[5] = PATH_DOCUMENT_XML;
        commandSignature[6] = PATH_OUT.concat("/");
        commandSignature[7] = KEY_ACCESS.concat(SUFFIX_XML_SIGNED);

        // Ruta completa (path + nombre) donde se guardará el archivo firmado
        PATH_OUT_SIGNED = commandSignature[6].concat(commandSignature[7]);

        return commandSignature;


    }
}
