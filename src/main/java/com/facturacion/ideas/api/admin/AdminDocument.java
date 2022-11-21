package com.facturacion.ideas.api.admin;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.facturacion.ideas.api.documents.InfoTributaria;
import com.facturacion.ideas.api.documents.factura.Factura;
import com.facturacion.ideas.api.documents.factura.InfoFactura;
import com.facturacion.ideas.api.entities.*;
import com.facturacion.ideas.api.enums.*;
import com.facturacion.ideas.api.exeption.KeyAccessException;
import com.facturacion.ideas.api.exeption.NotFoundException;
import com.facturacion.ideas.api.util.ConstanteUtil;

public class AdminDocument {

    public static String generateKeyAcces(Invoice invoiceXMl) {

        Subsidiary subsidiary = invoiceXMl.getEmissionPoint().getSubsidiary();
        Sender sender = subsidiary.getSender();

        // * 1) Fecha Actual
        String today = new SimpleDateFormat(ConstanteUtil.DATE_FORMAT_KEY_ACCESS)
                .format(Calendar.getInstance().getTime());

        // Tipo Comprobante
        TypeDocumentEnum typeDocument = TypeDocumentEnum.getTypeDocumentEnum(invoiceXMl.getTypeDocument());

        if (typeDocument == null) {
            throw new NotFoundException("Tipo Documento " + invoiceXMl.getTypeDocument() + " no es valido");
        }

        // * 2) Codigo del tipo documento
        String codeTypeDocument = typeDocument.getCode();

        // * 3) Ruc del emisor
        String ruc = sender.getRuc();

        // * 4) Tipo Ambiente
        String codeTypeEnviroment = sender.getTypeEnvironment();

        // * 5 Serie : conformada por codigo de establecimiento y codigo punto emision
        String serie = subsidiary.getCode() + invoiceXMl.getEmissionPoint().getCodePoint();

        // * 6 Numero de comprobante secuencial
        String squentialNumberDocument = invoiceXMl.getNumberSecuencial();


        // * 7 Codigo numerico
        String codeNumeric = ConstanteUtil.CODE_NUMERIC_KEY_ACCESS;

        // * 8 Tipo emission
        String codeTypeEmission = invoiceXMl.getTypoEmision();

        String keyAccess = today + codeTypeDocument + ruc + codeTypeEnviroment + serie + squentialNumberDocument
                + codeNumeric + codeTypeEmission;
        return generateCheckDigit(keyAccess);

    }

    /**
     * Geneara el digito verificador aplicando el Modulo 11.
     *
     * @param keyAccess : clave de acceso de 48 digitos
     * @return
     */
    public static String generateCheckDigit(String keyAccess) {

        Pattern pat = Pattern.compile("[0-9]{48}");
        Matcher mat = pat.matcher(keyAccess);

        if (!mat.matches()) {
            throw new KeyAccessException("La clave de acceso debe tener solo 48 digitos: " + keyAccess.length());
        }

        // Convertir en array los digitos
        String digits[] = keyAccess.split("");

        int suma = 0;
        int factor = 7;
        for (int index = 0; index < digits.length; index++) {
            suma += Integer.parseInt(digits[index]) * factor;
            factor -= 1;

            if (factor == 1) {
                factor = 7;
            }

        }

        int checkDigit = (suma % 11);

        checkDigit = 11 - checkDigit;

        if (checkDigit == 10)
            checkDigit = 1;

        if (checkDigit == 11)
            checkDigit = 0;

        return keyAccess + checkDigit;
    }

    /**
     * Genera el siguiente numero de documento
     *
     * @param currentNumber : el numero actual del documento
     * @return
     */
    public static String nextSquentialNumberDocument(int currentNumber) {

        // Numero de digitos que permitira saber cuantos ceros(0) se debe eliminar
        int amountDigits = (currentNumber + "").length();

        String numberInvoiceDefault = "000000000".trim();

        // Cantidad de espacios a recortar
        int endLong = numberInvoiceDefault.length() - amountDigits;

        // Quitar cantidad segun el numero de digitos
        numberInvoiceDefault = numberInvoiceDefault.substring(0, endLong) + currentNumber;

        return numberInvoiceDefault;

    }

    public static InvoiceNumber createInvoiceNumber(Subsidiary subsidiary, int curreSequencial, String typeDocument) {
        InvoiceNumber invoiceNumber = new InvoiceNumber();
        invoiceNumber.setSubsidiary(subsidiary);
        invoiceNumber.setCurrentSequentialNumber(curreSequencial);
        invoiceNumber.setTypeDocument(TypeDocumentEnum.getTypeDocumentEnum(typeDocument));

        return invoiceNumber;

    }


    public static void deleteDocument(String pathFile) {
        File file = new File(pathFile);
        if (file.exists()) {
            file.delete();
        }
    }

}
