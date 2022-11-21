package com.facturacion.ideas.api.sri.responses;


import com.facturacion.ideas.api.admin.AdminInvoice;
import com.facturacion.ideas.api.documents.factura.Factura;
import com.facturacion.ideas.api.enums.StatusDocumentsEnum;
import com.facturacion.ideas.api.sri.ws.autorizacion.Autorizacion;
import com.facturacion.ideas.api.sri.ws.autorizacion.Mensaje;
import com.facturacion.ideas.api.sri.ws.autorizacion.RespuestaComprobante;
import com.facturacion.ideas.api.util.FunctionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPMessage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;

@Component
public class ProcessResponseAuthorization {

    private static final Logger LOGGER = LogManager.getLogger(ProcessResponseAuthorization.class);

    private String claveAccesoConsultantada;


 /*   public static void main(String... agrs) {

        String cadenaRespuesta = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:autorizacionComprobanteResponse xmlns:ns2=\"http://ec.gob.sri.ws.autorizacion\"><RespuestaAutorizacionComprobante><claveAccesoConsultada>2011202201130875419900110010010000000521234567815</claveAccesoConsultada><numeroComprobantes>1</numeroComprobantes><autorizaciones><autorizacion><estado>AUTORIZADO</estado><numeroAutorizacion>2011202201130875419900110010010000000521234567815</numeroAutorizacion><fechaAutorizacion>2022-11-20T23:48:43-05:00</fechaAutorizacion><ambiente>PRUEBAS</ambiente><comprobante>&lt;?xml version=\"1.0\" encoding=\"UTF-8\"?>&lt;factura id=\"comprobante\" version=\"1.0.0\">    &lt;infoTributaria>        &lt;ambiente>1&lt;/ambiente>        &lt;tipoEmision>1&lt;/tipoEmision>        &lt;razonSocial>QUIMIS LE�N VER�NICA PATRICIA&lt;/razonSocial>        &lt;nombreComercial>Comercializadora de Cacao&lt;/nombreComercial>        &lt;ruc>1308754199001&lt;/ruc>        &lt;claveAcceso>2011202201130875419900110010010000000521234567815&lt;/claveAcceso>        &lt;codDoc>01&lt;/codDoc>        &lt;estab>001&lt;/estab>        &lt;ptoEmi>001&lt;/ptoEmi>        &lt;secuencial>000000052&lt;/secuencial>        &lt;dirMatriz>Coop Ciudad Nueva, calle Arcencio Santa Cruz y Jacinto Santos&lt;/dirMatriz>    &lt;/infoTributaria>    &lt;infoFactura>        &lt;fechaEmision>20/11/2022&lt;/fechaEmision>        &lt;dirEstablecimiento>Coop Ciudad Nueva, calle Arcencio Santa Cruz y Jacinto Santos&lt;/dirEstablecimiento>        &lt;obligadoContabilidad>NO&lt;/obligadoContabilidad>        &lt;tipoIdentificacionComprador>07&lt;/tipoIdentificacionComprador>        &lt;razonSocialComprador>CONSUMIDOR FINAL&lt;/razonSocialComprador>        &lt;identificacionComprador>9999999999999&lt;/identificacionComprador>        &lt;totalSinImpuestos>35.00&lt;/totalSinImpuestos>        &lt;totalDescuento>0.00&lt;/totalDescuento>        &lt;totalConImpuestos>            &lt;totalImpuesto>                &lt;codigo>2&lt;/codigo>                &lt;codigoPorcentaje>2&lt;/codigoPorcentaje>                &lt;baseImponible>35.00&lt;/baseImponible>                &lt;tarifa>12.00&lt;/tarifa>                &lt;valor>4.20&lt;/valor>            &lt;/totalImpuesto>        &lt;/totalConImpuestos>        &lt;propina>0.00&lt;/propina>        &lt;importeTotal>39.20&lt;/importeTotal>        &lt;moneda>DOLAR&lt;/moneda>        &lt;pagos>            &lt;pago>                &lt;formaPago>01&lt;/formaPago>                &lt;total>35.00&lt;/total>            &lt;/pago>        &lt;/pagos>    &lt;/infoFactura>    &lt;detalles>        &lt;detalle>            &lt;codigoPrincipal>23242&lt;/codigoPrincipal>            &lt;descripcion>Mouse&lt;/descripcion>            &lt;cantidad>1.0&lt;/cantidad>            &lt;precioUnitario>35.0&lt;/precioUnitario>            &lt;descuento>0.00&lt;/descuento>            &lt;precioTotalSinImpuesto>35.00&lt;/precioTotalSinImpuesto>            &lt;detallesAdicionales>                &lt;detAdicional nombre=\"Marca\" valor=\"LG\"/>                &lt;detAdicional nombre=\"Distribuidor\" valor=\"LG Comporation\"/>                &lt;detAdicional nombre=\"Pais\" valor=\"EEUU\"/>            &lt;/detallesAdicionales>            &lt;impuestos>                &lt;impuesto>                    &lt;codigo>2&lt;/codigo>                    &lt;codigoPorcentaje>2&lt;/codigoPorcentaje>                    &lt;tarifa>12.00&lt;/tarifa>                    &lt;baseImponible>35.00&lt;/baseImponible>                    &lt;valor>4.20&lt;/valor>                &lt;/impuesto>            &lt;/impuestos>        &lt;/detalle>    &lt;/detalles>&lt;ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:etsi=\"http://uri.etsi.org/01903/v1.3.2#\" Id=\"Signature118226\">&lt;ds:SignedInfo Id=\"Signature-SignedInfo81759\">&lt;ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/>&lt;ds:SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"/>&lt;ds:Reference Id=\"SignedPropertiesID1017246\" Type=\"http://uri.etsi.org/01903#SignedProperties\" URI=\"#Signature118226-SignedProperties831455\">&lt;ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"/>&lt;ds:DigestValue>smsNHKG8TSh5a+hKruXHh9dFAvQ=&lt;/ds:DigestValue>&lt;/ds:Reference>&lt;ds:Reference URI=\"#Certificate1137265\">&lt;ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"/>&lt;ds:DigestValue>UFtePxigZBlpxcyOVSQkFCSm2Tc=&lt;/ds:DigestValue>&lt;/ds:Reference>&lt;ds:Reference Id=\"Reference-ID-198197\" URI=\"#comprobante\">&lt;ds:Transforms>&lt;ds:Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"/>&lt;/ds:Transforms>&lt;ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"/>&lt;ds:DigestValue>1SIO24pWwbElbwAneOWlNdFtsJc=&lt;/ds:DigestValue>&lt;/ds:Reference>&lt;/ds:SignedInfo>&lt;ds:SignatureValue Id=\"SignatureValue124413\">wnluNJ5KVF2A/h2q2UQ6eJQyoVrDYbBTvoC/X1ncX5znjROWBDtVFqvcsHaLZZqzvOruXdcPP40sLCWLofhXIVx1I2KCEsxx/yHX2N7FHOj2MRkMOefXu//Nclet/Zlskpn72Kyvjmjy/oJawgJNfGPK2oatoIdyh6g6MfLCqMcDCGrmbfILzKwnBt2R1tQMbxN2nYwBG3ZXQ95kCh/ySK8yfa/72+zsACzOKpf5Bj1cWbIc5dIwXyA0e7Mfo7E3TlIU5u5Opl3CLpZEHzXopX/pb+Rb/8Ele8dxSx2vP4Q44kdc5esomSHz3Y+Xi2ZFgB07tPcAXX4SYx8rIhtreQ==&lt;/ds:SignatureValue>&lt;ds:KeyInfo Id=\"Certificate1137265\">&lt;ds:X509Data>&lt;ds:X509Certificate>MIIL5TCCCc2gAwIBAgIEL8X8BDANBgkqhkiG9w0BAQsFADCBmTELMAkGA1UEBhMCRUMxHTAbBgNVBAoMFFNFQ1VSSVRZIERBVEEgUy5BLiAyMTAwLgYDVQQLDCdFTlRJREFEIERFIENFUlRJRklDQUNJT04gREUgSU5GT1JNQUNJT04xOTA3BgNVBAMMMEFVVE9SSURBRCBERSBDRVJUSUZJQ0FDSU9OIFNVQkNBLTIgU0VDVVJJVFkgREFUQTAeFw0yMjA5MTMxNTQ3MzdaFw0yMzA5MTMxNTQ3MzdaMIGdMSYwJAYDVQQDDB1WRVJPTklDQSBQQVRSSUNJQSBRVUlNSVMgTEVPTjEVMBMGA1UEBRMMMTMwOTIyMTA1NzIzMTAwLgYDVQQLDCdFTlRJREFEIERFIENFUlRJRklDQUNJT04gREUgSU5GT1JNQUNJT04xHTAbBgNVBAoMFFNFQ1VSSVRZIERBVEEgUy5BLiAyMQswCQYDVQQGEwJFQzCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBANeHOwfVs38JW1PN9fViBI/Lp0oHWRRD+wnXq0w0dZQZueWNgZphaGTpHxAvf7QpwB4G8clLUHkrdoioSD4QrPC6hcQ8Low6fSYwG3F8sV+LIZ7CAkyuXPIAA0Aj4KyJHAP7EUErBjPlDZ8/VWzz2MwG4+q8hRwza16XyU9UkxmiQqb4mLTxXOu6Z6yJQTUgm8wAt/nAl70INbS3qKoZI2LuZycGD/OFNcZSTM9xdqc4K3Ze9x5/NM2dh96z/tBxtX25pxqMvq4hSgfa3aKWltORclA0+Kb3F/n+nd9i/zJq2kHOUZzjM+wa5BpSTGyxvlanLhbTMoejQxd9b01n2jMCAwEAAaOCBy0wggcpMAwGA1UdEwEB/wQCMAAwHwYDVR0jBBgwFoAUjLrKEVd4JYAdawpLVb+NrmLdvY8wWQYIKwYBBQUHAQEETTBLMEkGCCsGAQUFBzABhj1odHRwOi8vb2NzcGd3LnNlY3VyaXR5ZGF0YS5uZXQuZWMvZWpiY2EvcHVibGljd2ViL3N0YXR1cy9vY3NwMIHPBgNVHS4EgccwgcQwgcGggb6ggbuGgbhsZGFwOi8vbGRhcHNkY2EyLnNlY3VyaXR5ZGF0YS5uZXQuZWMvQ049QVVUT1JJREFEIERFIENFUlRJRklDQUNJT04gU1VCQ0EtMiBTRUNVUklUWSBEQVRBLE9VPUVOVElEQUQgREUgQ0VSVElGSUNBQ0lPTiBERSBJTkZPUk1BQ0lPTixPPVNFQ1VSSVRZIERBVEEgUy5BLiAyLEM9RUM/ZGVsdGFSZXZvY2F0aW9uTGlzdD9iYXNlMCcGA1UdEQQgMB6BHHZlcm9uaWNhcXVpbWlzMjdAaG90bWFpbC5jb20wggEGBgNVHSAEgf4wgfswWgYKKwYBBAGCpnICBzBMMEoGCCsGAQUFBwICMD4ePABDAGUAcgB0AGkAZgBpAGMAYQBkAG8AIABkAGUAIABQAGUAcgBzAG8AbgBhACAATgBhAHQAdQByAGEAbDCBnAYKKwYBBAGCpnICATCBjTCBigYIKwYBBQUHAgEWfmh0dHBzOi8vd3d3LnNlY3VyaXR5ZGF0YS5uZXQuZWMvd3AtY29udGVudC9kb3dubG9hZHMvTm9ybWF0aXZhcy9QX2RlX0NlcnRpZmljYWRvcy9Qb2xpdGljYXMgZGUgQ2VydGlmaWNhZG8gUGVyc29uYSBOYXR1cmFsLnBkZjCCAqIGA1UdHwSCApkwggKVMIHloEGgP4Y9aHR0cDovL29jc3Bndy5zZWN1cml0eWRhdGEubmV0LmVjL2VqYmNhL3B1YmxpY3dlYi9zdGF0dXMvb2NzcKKBn6SBnDCBmTE5MDcGA1UEAwwwQVVUT1JJREFEIERFIENFUlRJRklDQUNJT04gU1VCQ0EtMiBTRUNVUklUWSBEQVRBMTAwLgYDVQQLDCdFTlRJREFEIERFIENFUlRJRklDQUNJT04gREUgSU5GT1JNQUNJT04xHTAbBgNVBAoMFFNFQ1VSSVRZIERBVEEgUy5BLiAyMQswCQYDVQQGEwJFQzCBx6CBxKCBwYaBvmxkYXA6Ly9sZGFwc2RjYTIuc2VjdXJpdHlkYXRhLm5ldC5lYy9DTj1BVVRPUklEQUQgREUgQ0VSVElGSUNBQ0lPTiBTVUJDQS0yIFNFQ1VSSVRZIERBVEEsT1U9RU5USURBRCBERSBDRVJUSUZJQ0FDSU9OIERFIElORk9STUFDSU9OLE89U0VDVVJJVFkgREFUQSBTLkEuIDIsQz1FQz9jZXJ0aWZpY2F0ZVJldm9jYXRpb25MaXN0P2Jhc2UwgeCggd2ggdqGgddodHRwczovL3BvcnRhbC1vcGVyYWRvcjIuc2VjdXJpdHlkYXRhLm5ldC5lYy9lamJjYS9wdWJsaWN3ZWIvd2ViZGlzdC9jZXJ0ZGlzdD9jbWQ9Y3JsJmlzc3Vlcj1DTj1BVVRPUklEQUQgREUgQ0VSVElGSUNBQ0lPTiBTVUJDQS0yIFNFQ1VSSVRZIERBVEEsT1U9RU5USURBRCBERSBDRVJUSUZJQ0FDSU9OIERFIElORk9STUFDSU9OLE89U0VDVVJJVFkgREFUQSBTLkEuIDIsQz1FQzAdBgNVHQ4EFgQUXx5gYUU/29NfQM40MA21PK4RX2YwKwYDVR0QBCQwIoAPMjAyMjA5MTMxNTQ3MzdagQ8yMDIzMDkxMzE1NDczN1owCwYDVR0PBAQDAgXgMBoGCisGAQQBgqZyAwEEDAwKMTMwODc1NDE5OTAdBgorBgEEAYKmcgMJBA8MDVNBTlRPIERPTUlOR08wEQYKKwYBBAGCpnIDIgQDDAEuMDcGCisGAQQBgqZyAwcEKQwnQVJDRU5JTyBTQU5UQSBDUlVaIFMgTiBZIEpBQ0lOVE8gU0FOVE9TMCEGCisGAQQBgqZyAwIEEwwRVkVST05JQ0EgUEFUUklDSUEwHwYKKwYBBAGCpnIDIAQRDA8wMDEwMDIwMDAyNTI2MTcwEQYKKwYBBAGCpnIDIwQDDAEuMBMGCisGAQQBgqZyAyEEBQwDUEZYMBcGCisGAQQBgqZyAwwECQwHRUNVQURPUjAWBgorBgEEAYKmcgMDBAgMBlFVSU1JUzARBgorBgEEAYKmcgMeBAMMAS4wHQYKKwYBBAGCpnIDCwQPDA0xMzA4NzU0MTk5MDAxMBEGCisGAQQBgqZyAx0EAwwBLjAUBgorBgEEAYKmcgMEBAYMBExFT04wGgYKKwYBBAGCpnIDCAQMDAowOTgwNzk4MDI4MA0GCSqGSIb3DQEBCwUAA4ICAQC/1+JuZqvNRIcWkzVb4QQgx21sUlwSvCezpiwlUKVjRzW6AGJtr99Anper32L3BAPA0FmqXUARUnlRhlZpfDBmIxXvJnBtormleFQ7zjlaiZNsTkhXYJppCrzmCTiYBvs8Uq8LOVe3EMSedmb7hl9DwsypbtsyuHXfVs81W4JxyE92qb5uhOIpK21bi/E01pgaYviQdoPJJp4gfAYqmCjw59M2XOBfijQ/M6cabQORIM1MaS9vblPml84NNFr/nOmlxwN5vyJobF8L7glmylxRnb6EIMgzUx8ldmhwQeyRqcm4l5KXYOZKLFEqHh+gEO4dXHFUtll73RPfGH1qLnPa42KkliBVgq3jUdlz180L7z4nH0PZM1DFwLwy0kPZZTdkJeMgvUD9ghJauTHlxoA2RWTifrNuXZXFgpr+5agUlaV4/BUTdP2QCsSVrV8xlJGSFevHAy3ozfovm1livkFpvmR8J0aMNBcn7e6CK3Pj1VEru8bTmvbTKd1t1Dk2RXcUMZMYOgb0vrRiClCh2Klz+Tk/8qN7VwJr1aq8jz4BKQi9e/lPVzv05mURb/Q2F2PmSGYs2kTC3EWt3r4RYaOeWcMngNCSeiWHhLxSF4AhjsldsBjOvid1+Y0jB1zl4pFNOwDx/7pvoaDyZCEdUnmwNRRWcbQueBfocMQ75R0M9A==&lt;/ds:X509Certificate>&lt;/ds:X509Data>&lt;ds:KeyValue>&lt;ds:RSAKeyValue>&lt;ds:Modulus>14c7B9WzfwlbU8319WIEj8unSgdZFEP7CderTDR1lBm55Y2BmmFoZOkfEC9/tCnAHgbxyUtQeSt2iKhIPhCs8LqFxDwujDp9JjAbcXyxX4shnsICTK5c8gADQCPgrIkcA/sRQSsGM+UNnz9VbPPYzAbj6ryFHDNrXpfJT1STGaJCpviYtPFc67pnrIlBNSCbzAC3+cCXvQg1tLeoqhkjYu5nJwYP84U1xlJMz3F2pzgrdl73Hn80zZ2H3rP+0HG1fbmnGoy+riFKB9rdopaW05FyUDT4pvcX+f6d32L/MmraQc5RnOMz7BrkGlJMbLG+VqcuFtMyh6NDF31vTWfaMw==&lt;/ds:Modulus>&lt;ds:Exponent>AQAB&lt;/ds:Exponent>&lt;/ds:RSAKeyValue>&lt;/ds:KeyValue>&lt;/ds:KeyInfo>&lt;ds:Object Id=\"Signature118226-Object225393\">&lt;etsi:QualifyingProperties Target=\"#Signature118226\">&lt;etsi:SignedProperties Id=\"Signature118226-SignedProperties831455\">&lt;etsi:SignedSignatureProperties>&lt;etsi:SigningTime>2022-11-20T23:48:42-05:00&lt;/etsi:SigningTime>&lt;etsi:SigningCertificate>&lt;etsi:Cert>&lt;etsi:CertDigest>&lt;ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"/>&lt;ds:DigestValue>TDjepG/XXjJokYPXFuAU+0FWwhs=&lt;/ds:DigestValue>&lt;/etsi:CertDigest>&lt;etsi:IssuerSerial>&lt;ds:X509IssuerName>CN=AUTORIDAD DE CERTIFICACION SUBCA-2 SECURITY DATA,OU=ENTIDAD DE CERTIFICACION DE INFORMACION,O=SECURITY DATA S.A. 2,C=EC&lt;/ds:X509IssuerName>&lt;ds:X509SerialNumber>801504260&lt;/ds:X509SerialNumber>&lt;/etsi:IssuerSerial>&lt;/etsi:Cert>&lt;/etsi:SigningCertificate>&lt;/etsi:SignedSignatureProperties>&lt;etsi:SignedDataObjectProperties>&lt;etsi:DataObjectFormat ObjectReference=\"#Reference-ID-198197\">&lt;etsi:Description>contenido comprobante&lt;/etsi:Description>&lt;etsi:MimeType>text/xml&lt;/etsi:MimeType>&lt;/etsi:DataObjectFormat>&lt;/etsi:SignedDataObjectProperties>&lt;/etsi:SignedProperties>&lt;/etsi:QualifyingProperties>&lt;/ds:Object>&lt;/ds:Signature>&lt;/factura></comprobante><mensajes/></autorizacion></autorizaciones></RespuestaAutorizacionComprobante></ns2:autorizacionComprobanteResponse></soap:Body></soap:Envelope>";
        new ProcessResponseAuthorization().crearSoap(cadenaRespuesta);
    }*/

    public void crearSoap(String data) {
        try {

            MessageFactory factory;
            factory = MessageFactory.newInstance();
            SOAPMessage message = factory.createMessage(new MimeHeaders(), new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)));

            SOAPBody soapBody = message.getSOAPBody();

            RespuestaComprobante respuestaComprobante = processResponseRequest(soapBody);

            System.out.println("Clave acceso consultada: " + respuestaComprobante.getClaveAccesoConsultada());

            System.out.println("Numero de comprobante: " + respuestaComprobante.getNumeroComprobantes());
            RespuestaComprobante.Autorizaciones autorizaciones = respuestaComprobante.getAutorizaciones();

            System.out.println("Numero de autorizaciones: " + autorizaciones.getAutorizacion().size());


        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());

        }

    }


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

                claveAccesoConsultantada = element.getTextContent();

                respuestaComprobante.setClaveAccesoConsultada(element.getTextContent());
            }

            if (element.getNodeName().equalsIgnoreCase("numeroComprobantes")) {
                respuestaComprobante.setNumeroComprobantes(element.getTextContent());
            }

            if (element.getNodeName().equalsIgnoreCase("autorizaciones") && element.hasChildNodes()) {
                respuestaComprobante.setAutorizaciones(createWrapperAuthorizationes(element));
            }

        }
        return respuestaComprobante;
    }

    /**
     * @param node : nodo autorizaciones
     * @return
     */
    private RespuestaComprobante.Autorizaciones createWrapperAuthorizationes(Node node) {


        RespuestaComprobante.Autorizaciones autorizaciones = new RespuestaComprobante.Autorizaciones();

        // Nodos hijos autorizacion:  de autorizaciones
        NodeList nodeListAutorizacion = node.getChildNodes();

        for (int i = 0; i < nodeListAutorizacion.getLength(); i++) {

            // Nodo autorizacion
            Node nodeAutorizathion = nodeListAutorizacion.item(i);

            // Autorizacion generada
            Autorizacion autorizacion = createItemAuthorization(nodeAutorizathion);

            autorizaciones.getAutorizacion().add(autorizacion);

        }

        return autorizaciones;

    }

    private Autorizacion createItemAuthorization(Node nodeAutorization) {

        Autorizacion autorizacion = new Autorizacion();

        // Los hijos de autorizacion
        NodeList nodeListChild = nodeAutorization.getChildNodes();

        for (int i = 0; i < nodeListChild.getLength(); i++) {

            // Cada nodo hijo
            Node nodeChild = nodeListChild.item(i);

            switch (nodeChild.getNodeName()) {

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


                    String dataRead = writeComprobanteAuthorization(nodeAutorization.getTextContent(), autorizacion.getEstado());

                    // No es necesario cargar el texto, ya que el xml que regreso el ws ya fue escrito en el directorio
                    // autorizados, por ahora lo deje asi
                    autorizacion.setComprobante(dataRead);
                    break;

                case "mensajes":

                    autorizacion.setMensajes(createWrapperMessages(nodeChild));
                    break;


            }
        }

        return autorizacion;
    }

    /**
     * Escribe  el comprobante  devuelto, pueder ser autorizado o no autorizado
     *
     * @param dataComprobante : representa la informacion recibida por el webservice
     * @param status          : el estado
     */
    private String writeComprobanteAuthorization(String dataComprobante, String status) {

        // AdminInvoice.getPathDirectory(); Este path fue seteado cuando se genero el xml, antes de firmar, contiene la ruta
        // hasta el  directorio  de punto de emision

        String pathDirectoryAut = AdminInvoice.getPathDirectory();


        pathDirectoryAut += status.trim().equalsIgnoreCase(StatusDocumentsEnum.AUTORIZADO.getName()) ?
                "/autorizados" : "/no_autorizados";

        Writer out = null;


        try {

            // Cortar caracters del inicio
            int indexRegex = dataComprobante.lastIndexOf("<?xml");

            dataComprobante = dataComprobante.substring(indexRegex);

            dataComprobante = dataComprobante.replaceAll("\\p{Cntrl}", "\n").replaceAll("\t\r", "");

            String nameFileOuput = pathDirectoryAut.concat("/").concat(claveAccesoConsultantada).concat(".xml");

            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(nameFileOuput), StandardCharsets.UTF_8));
            //  out.write(body.getElementsByTagName("autorizacion").item(0).getTextContent().replaceAll("\\p{Cntrl}", "\n").replaceAll("\t\r", ""));
            out.write(dataComprobante);
        } catch (FileNotFoundException e) {
            LOGGER.error("error al leer salida autotizacion : " + e.getMessage());
            //throw new RuntimeException(e);

        } catch (IOException e) {
            LOGGER.error("error al leer salida autorizacion : " + e.getMessage());
            //throw new RuntimeException(e);
        } finally {

            if (out != null) {

                try {
                    out.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }

        return dataComprobante;
    }

    private Autorizacion.Mensajes createWrapperMessages(Node nodeWrapperMessage) {

        Autorizacion.Mensajes messagesWrapper = new Autorizacion.Mensajes();

        // Hijos de mensajes
        NodeList nodeListMessage = nodeWrapperMessage.getChildNodes();

        for (int i = 0; i < nodeListMessage.getLength(); i++) {

            // Acceder a cada mensaje
            Mensaje mensaje = createItemMessage(nodeListMessage.item(i));

            messagesWrapper.getMensaje().add(mensaje);
        }

        return messagesWrapper;
    }

    private Mensaje createItemMessage(Node nodeMessage) {

        Mensaje mensaje = new Mensaje();

        // Acceder a cada hijo de mensaje,, devuelve 3 nodos hijos
        NodeList nodeItemMessage = nodeMessage.getChildNodes();

        for (int i = 0; i < nodeItemMessage.getLength(); i++) {

            Node nodeChild = nodeItemMessage.item(i);

            switch (nodeChild.getNodeName()) {

                case "identificador":
                    mensaje.setIdentificador(nodeChild.getTextContent());
                    break;

                case "mensaje":
                    mensaje.setMensaje(nodeChild.getTextContent());
                    break;

                case "informacionAdicional":
                    mensaje.setInformacionAdicional(nodeChild.getTextContent());
                    break;

                case "tipo":
                    mensaje.setTipo(nodeChild.getTextContent());
                    break;


            }

        }

        return mensaje;
    }

}
