package com.facturacion.ideas.api.sri.responses;
import com.facturacion.ideas.api.sri.ws.recepcion.Comprobante;
import com.facturacion.ideas.api.sri.ws.recepcion.Mensaje;
import com.facturacion.ideas.api.sri.ws.recepcion.RespuestaSolicitud;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import javax.xml.soap.*;


@Component
public class ProcessResponseReception {

    //String responseAutorizacion = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:autorizacionComprobanteResponse xmlns:ns2=\"http://ec.gob.sri.ws.autorizacion\"><RespuestaAutorizacionComprobante><claveAccesoConsultada>1911202201130875419900110010010000000331234567819</claveAccesoConsultada><numeroComprobantes>1</numeroComprobantes><autorizaciones><autorizacion><estado>AUTORIZADO</estado><numeroAutorizacion>1911202201130875419900110010010000000331234567819</numeroAutorizacion><fechaAutorizacion>2022-11-19T20:37:49-05:00</fechaAutorizacion><ambiente>PRUEBAS</ambiente><comprobante>&lt;?xml version=\"1.0\" encoding=\"UTF-8\"?>&lt;factura id=\"comprobante\" version=\"1.0.0\">    &lt;infoTributaria>        &lt;ambiente>1&lt;/ambiente>        &lt;tipoEmision>1&lt;/tipoEmision>        &lt;razonSocial>QUIMIS LE�N VER�NICA PATRICIA&lt;/razonSocial>        &lt;nombreComercial>Comercializadora de Cacao&lt;/nombreComercial>        &lt;ruc>1308754199001&lt;/ruc>        &lt;claveAcceso>1911202201130875419900110010010000000331234567819&lt;/claveAcceso>        &lt;codDoc>01&lt;/codDoc>        &lt;estab>001&lt;/estab>        &lt;ptoEmi>001&lt;/ptoEmi>        &lt;secuencial>000000033&lt;/secuencial>        &lt;dirMatriz>Coop Ciudad Nueva, calle Arcencio Santa Cruz y Jacinto Santos&lt;/dirMatriz>    &lt;/infoTributaria>    &lt;infoFactura>        &lt;fechaEmision>19/11/2022&lt;/fechaEmision>        &lt;dirEstablecimiento>Coop Ciudad Nueva, calle Arcencio Santa Cruz y Jacinto Santos&lt;/dirEstablecimiento>        &lt;obligadoContabilidad>NO&lt;/obligadoContabilidad>        &lt;tipoIdentificacionComprador>05&lt;/tipoIdentificacionComprador>        &lt;razonSocialComprador>RONNY RENE CHAMBA PULLAGUARI&lt;/razonSocialComprador>        &lt;identificacionComprador>1723774640&lt;/identificacionComprador>        &lt;direccionComprador>Santo Domingo&lt;/direccionComprador>        &lt;totalSinImpuestos>35.00&lt;/totalSinImpuestos>        &lt;totalDescuento>0.00&lt;/totalDescuento>        &lt;totalConImpuestos>            &lt;totalImpuesto>                &lt;codigo>2&lt;/codigo>                &lt;codigoPorcentaje>2&lt;/codigoPorcentaje>                &lt;baseImponible>35.00&lt;/baseImponible>                &lt;tarifa>12.00&lt;/tarifa>                &lt;valor>4.20&lt;/valor>            &lt;/totalImpuesto>        &lt;/totalConImpuestos>        &lt;propina>0.00&lt;/propina>        &lt;importeTotal>39.20&lt;/importeTotal>        &lt;moneda>DOLAR&lt;/moneda>        &lt;pagos>            &lt;pago>                &lt;formaPago>01&lt;/formaPago>                &lt;total>35.00&lt;/total>            &lt;/pago>        &lt;/pagos>    &lt;/infoFactura>    &lt;detalles>        &lt;detalle>            &lt;codigoPrincipal>23242&lt;/codigoPrincipal>            &lt;descripcion>Mouse&lt;/descripcion>            &lt;cantidad>1.0&lt;/cantidad>            &lt;precioUnitario>35.0&lt;/precioUnitario>            &lt;descuento>0.00&lt;/descuento>            &lt;precioTotalSinImpuesto>35.00&lt;/precioTotalSinImpuesto>            &lt;detallesAdicionales>                &lt;detAdicional nombre=\"Marca\" valor=\"LG\"/>                &lt;detAdicional nombre=\"Distribuidor\" valor=\"LG Comporation\"/>                &lt;detAdicional nombre=\"Pais\" valor=\"EEUU\"/>            &lt;/detallesAdicionales>            &lt;impuestos>                &lt;impuesto>                    &lt;codigo>2&lt;/codigo>                    &lt;codigoPorcentaje>2&lt;/codigoPorcentaje>                    &lt;tarifa>12.00&lt;/tarifa>                    &lt;baseImponible>35.00&lt;/baseImponible>                    &lt;valor>4.20&lt;/valor>                &lt;/impuesto>            &lt;/impuestos>        &lt;/detalle>    &lt;/detalles>&lt;ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:etsi=\"http://uri.etsi.org/01903/v1.3.2#\" Id=\"Signature358926\">&lt;ds:SignedInfo Id=\"Signature-SignedInfo742932\">&lt;ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/>&lt;ds:SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"/>&lt;ds:Reference Id=\"SignedPropertiesID237877\" Type=\"http://uri.etsi.org/01903#SignedProperties\" URI=\"#Signature358926-SignedProperties256209\">&lt;ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"/>&lt;ds:DigestValue>UzTmpoJsAkDzUpKP5hgsb4hgeaA=&lt;/ds:DigestValue>&lt;/ds:Reference>&lt;ds:Reference URI=\"#Certificate1633110\">&lt;ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"/>&lt;ds:DigestValue>wSjmesHCzj4dPjNJ5baMnZgnBIQ=&lt;/ds:DigestValue>&lt;/ds:Reference>&lt;ds:Reference Id=\"Reference-ID-367170\" URI=\"#comprobante\">&lt;ds:Transforms>&lt;ds:Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"/>&lt;/ds:Transforms>&lt;ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"/>&lt;ds:DigestValue>K025bjFLXlfKiGTy01T0a8dHnf0=&lt;/ds:DigestValue>&lt;/ds:Reference>&lt;/ds:SignedInfo>&lt;ds:SignatureValue Id=\"SignatureValue933292\">qSJ2Bq0evjFrcrvh1WxrO7Ba+vaT3DxX6o12t5eis8qPEfyFX3eKNWxqwVUnpV31dj3dQV7Ase5hcWkkt8MZlGHP4IS7ptRCqV4fU43bIfEmU2woh98VrnW9kPy2iW0IEd3oqHOBuUNwnmJwbBZgCelFwLUM5hVtZG5y7ICMTjWMW57QPvtbgdbDS2n5vL+6VLpUDPoGToZD3nzg6721Ltl2c7DAlQTVYJsp1qDGfEjY9eQ+6ogxzHqUdedK8ZzaxOKjePFagaoegrmWP6bnuLTjaW2by/i6A/DE74VDVbJPdsCcsSe+IlSxQK8oDqIBXBb6ibSujtFd8JBjrUC6Ow==&lt;/ds:SignatureValue>&lt;ds:KeyInfo Id=\"Certificate1633110\">&lt;ds:X509Data>&lt;ds:X509Certificate>MIIL5TCCCc2gAwIBAgIEL8X8BDANBgkqhkiG9w0BAQsFADCBmTELMAkGA1UEBhMCRUMxHTAbBgNVBAoMFFNFQ1VSSVRZIERBVEEgUy5BLiAyMTAwLgYDVQQLDCdFTlRJREFEIERFIENFUlRJRklDQUNJT04gREUgSU5GT1JNQUNJT04xOTA3BgNVBAMMMEFVVE9SSURBRCBERSBDRVJUSUZJQ0FDSU9OIFNVQkNBLTIgU0VDVVJJVFkgREFUQTAeFw0yMjA5MTMxNTQ3MzdaFw0yMzA5MTMxNTQ3MzdaMIGdMSYwJAYDVQQDDB1WRVJPTklDQSBQQVRSSUNJQSBRVUlNSVMgTEVPTjEVMBMGA1UEBRMMMTMwOTIyMTA1NzIzMTAwLgYDVQQLDCdFTlRJREFEIERFIENFUlRJRklDQUNJT04gREUgSU5GT1JNQUNJT04xHTAbBgNVBAoMFFNFQ1VSSVRZIERBVEEgUy5BLiAyMQswCQYDVQQGEwJFQzCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBANeHOwfVs38JW1PN9fViBI/Lp0oHWRRD+wnXq0w0dZQZueWNgZphaGTpHxAvf7QpwB4G8clLUHkrdoioSD4QrPC6hcQ8Low6fSYwG3F8sV+LIZ7CAkyuXPIAA0Aj4KyJHAP7EUErBjPlDZ8/VWzz2MwG4+q8hRwza16XyU9UkxmiQqb4mLTxXOu6Z6yJQTUgm8wAt/nAl70INbS3qKoZI2LuZycGD/OFNcZSTM9xdqc4K3Ze9x5/NM2dh96z/tBxtX25pxqMvq4hSgfa3aKWltORclA0+Kb3F/n+nd9i/zJq2kHOUZzjM+wa5BpSTGyxvlanLhbTMoejQxd9b01n2jMCAwEAAaOCBy0wggcpMAwGA1UdEwEB/wQCMAAwHwYDVR0jBBgwFoAUjLrKEVd4JYAdawpLVb+NrmLdvY8wWQYIKwYBBQUHAQEETTBLMEkGCCsGAQUFBzABhj1odHRwOi8vb2NzcGd3LnNlY3VyaXR5ZGF0YS5uZXQuZWMvZWpiY2EvcHVibGljd2ViL3N0YXR1cy9vY3NwMIHPBgNVHS4EgccwgcQwgcGggb6ggbuGgbhsZGFwOi8vbGRhcHNkY2EyLnNlY3VyaXR5ZGF0YS5uZXQuZWMvQ049QVVUT1JJREFEIERFIENFUlRJRklDQUNJT04gU1VCQ0EtMiBTRUNVUklUWSBEQVRBLE9VPUVOVElEQUQgREUgQ0VSVElGSUNBQ0lPTiBERSBJTkZPUk1BQ0lPTixPPVNFQ1VSSVRZIERBVEEgUy5BLiAyLEM9RUM/ZGVsdGFSZXZvY2F0aW9uTGlzdD9iYXNlMCcGA1UdEQQgMB6BHHZlcm9uaWNhcXVpbWlzMjdAaG90bWFpbC5jb20wggEGBgNVHSAEgf4wgfswWgYKKwYBBAGCpnICBzBMMEoGCCsGAQUFBwICMD4ePABDAGUAcgB0AGkAZgBpAGMAYQBkAG8AIABkAGUAIABQAGUAcgBzAG8AbgBhACAATgBhAHQAdQByAGEAbDCBnAYKKwYBBAGCpnICATCBjTCBigYIKwYBBQUHAgEWfmh0dHBzOi8vd3d3LnNlY3VyaXR5ZGF0YS5uZXQuZWMvd3AtY29udGVudC9kb3dubG9hZHMvTm9ybWF0aXZhcy9QX2RlX0NlcnRpZmljYWRvcy9Qb2xpdGljYXMgZGUgQ2VydGlmaWNhZG8gUGVyc29uYSBOYXR1cmFsLnBkZjCCAqIGA1UdHwSCApkwggKVMIHloEGgP4Y9aHR0cDovL29jc3Bndy5zZWN1cml0eWRhdGEubmV0LmVjL2VqYmNhL3B1YmxpY3dlYi9zdGF0dXMvb2NzcKKBn6SBnDCBmTE5MDcGA1UEAwwwQVVUT1JJREFEIERFIENFUlRJRklDQUNJT04gU1VCQ0EtMiBTRUNVUklUWSBEQVRBMTAwLgYDVQQLDCdFTlRJREFEIERFIENFUlRJRklDQUNJT04gREUgSU5GT1JNQUNJT04xHTAbBgNVBAoMFFNFQ1VSSVRZIERBVEEgUy5BLiAyMQswCQYDVQQGEwJFQzCBx6CBxKCBwYaBvmxkYXA6Ly9sZGFwc2RjYTIuc2VjdXJpdHlkYXRhLm5ldC5lYy9DTj1BVVRPUklEQUQgREUgQ0VSVElGSUNBQ0lPTiBTVUJDQS0yIFNFQ1VSSVRZIERBVEEsT1U9RU5USURBRCBERSBDRVJUSUZJQ0FDSU9OIERFIElORk9STUFDSU9OLE89U0VDVVJJVFkgREFUQSBTLkEuIDIsQz1FQz9jZXJ0aWZpY2F0ZVJldm9jYXRpb25MaXN0P2Jhc2UwgeCggd2ggdqGgddodHRwczovL3BvcnRhbC1vcGVyYWRvcjIuc2VjdXJpdHlkYXRhLm5ldC5lYy9lamJjYS9wdWJsaWN3ZWIvd2ViZGlzdC9jZXJ0ZGlzdD9jbWQ9Y3JsJmlzc3Vlcj1DTj1BVVRPUklEQUQgREUgQ0VSVElGSUNBQ0lPTiBTVUJDQS0yIFNFQ1VSSVRZIERBVEEsT1U9RU5USURBRCBERSBDRVJUSUZJQ0FDSU9OIERFIElORk9STUFDSU9OLE89U0VDVVJJVFkgREFUQSBTLkEuIDIsQz1FQzAdBgNVHQ4EFgQUXx5gYUU/29NfQM40MA21PK4RX2YwKwYDVR0QBCQwIoAPMjAyMjA5MTMxNTQ3MzdagQ8yMDIzMDkxMzE1NDczN1owCwYDVR0PBAQDAgXgMBoGCisGAQQBgqZyAwEEDAwKMTMwODc1NDE5OTAdBgorBgEEAYKmcgMJBA8MDVNBTlRPIERPTUlOR08wEQYKKwYBBAGCpnIDIgQDDAEuMDcGCisGAQQBgqZyAwcEKQwnQVJDRU5JTyBTQU5UQSBDUlVaIFMgTiBZIEpBQ0lOVE8gU0FOVE9TMCEGCisGAQQBgqZyAwIEEwwRVkVST05JQ0EgUEFUUklDSUEwHwYKKwYBBAGCpnIDIAQRDA8wMDEwMDIwMDAyNTI2MTcwEQYKKwYBBAGCpnIDIwQDDAEuMBMGCisGAQQBgqZyAyEEBQwDUEZYMBcGCisGAQQBgqZyAwwECQwHRUNVQURPUjAWBgorBgEEAYKmcgMDBAgMBlFVSU1JUzARBgorBgEEAYKmcgMeBAMMAS4wHQYKKwYBBAGCpnIDCwQPDA0xMzA4NzU0MTk5MDAxMBEGCisGAQQBgqZyAx0EAwwBLjAUBgorBgEEAYKmcgMEBAYMBExFT04wGgYKKwYBBAGCpnIDCAQMDAowOTgwNzk4MDI4MA0GCSqGSIb3DQEBCwUAA4ICAQC/1+JuZqvNRIcWkzVb4QQgx21sUlwSvCezpiwlUKVjRzW6AGJtr99Anper32L3BAPA0FmqXUARUnlRhlZpfDBmIxXvJnBtormleFQ7zjlaiZNsTkhXYJppCrzmCTiYBvs8Uq8LOVe3EMSedmb7hl9DwsypbtsyuHXfVs81W4JxyE92qb5uhOIpK21bi/E01pgaYviQdoPJJp4gfAYqmCjw59M2XOBfijQ/M6cabQORIM1MaS9vblPml84NNFr/nOmlxwN5vyJobF8L7glmylxRnb6EIMgzUx8ldmhwQeyRqcm4l5KXYOZKLFEqHh+gEO4dXHFUtll73RPfGH1qLnPa42KkliBVgq3jUdlz180L7z4nH0PZM1DFwLwy0kPZZTdkJeMgvUD9ghJauTHlxoA2RWTifrNuXZXFgpr+5agUlaV4/BUTdP2QCsSVrV8xlJGSFevHAy3ozfovm1livkFpvmR8J0aMNBcn7e6CK3Pj1VEru8bTmvbTKd1t1Dk2RXcUMZMYOgb0vrRiClCh2Klz+Tk/8qN7VwJr1aq8jz4BKQi9e/lPVzv05mURb/Q2F2PmSGYs2kTC3EWt3r4RYaOeWcMngNCSeiWHhLxSF4AhjsldsBjOvid1+Y0jB1zl4pFNOwDx/7pvoaDyZCEdUnmwNRRWcbQueBfocMQ75R0M9A==&lt;/ds:X509Certificate>&lt;/ds:X509Data>&lt;ds:KeyValue>&lt;ds:RSAKeyValue>&lt;ds:Modulus>14c7B9WzfwlbU8319WIEj8unSgdZFEP7CderTDR1lBm55Y2BmmFoZOkfEC9/tCnAHgbxyUtQeSt2iKhIPhCs8LqFxDwujDp9JjAbcXyxX4shnsICTK5c8gADQCPgrIkcA/sRQSsGM+UNnz9VbPPYzAbj6ryFHDNrXpfJT1STGaJCpviYtPFc67pnrIlBNSCbzAC3+cCXvQg1tLeoqhkjYu5nJwYP84U1xlJMz3F2pzgrdl73Hn80zZ2H3rP+0HG1fbmnGoy+riFKB9rdopaW05FyUDT4pvcX+f6d32L/MmraQc5RnOMz7BrkGlJMbLG+VqcuFtMyh6NDF31vTWfaMw==&lt;/ds:Modulus>&lt;ds:Exponent>AQAB&lt;/ds:Exponent>&lt;/ds:RSAKeyValue>&lt;/ds:KeyValue>&lt;/ds:KeyInfo>&lt;ds:Object Id=\"Signature358926-Object655208\">&lt;etsi:QualifyingProperties Target=\"#Signature358926\">&lt;etsi:SignedProperties Id=\"Signature358926-SignedProperties256209\">&lt;etsi:SignedSignatureProperties>&lt;etsi:SigningTime>2022-11-19T20:37:47-05:00&lt;/etsi:SigningTime>&lt;etsi:SigningCertificate>&lt;etsi:Cert>&lt;etsi:CertDigest>&lt;ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"/>&lt;ds:DigestValue>TDjepG/XXjJokYPXFuAU+0FWwhs=&lt;/ds:DigestValue>&lt;/etsi:CertDigest>&lt;etsi:IssuerSerial>&lt;ds:X509IssuerName>CN=AUTORIDAD DE CERTIFICACION SUBCA-2 SECURITY DATA,OU=ENTIDAD DE CERTIFICACION DE INFORMACION,O=SECURITY DATA S.A. 2,C=EC&lt;/ds:X509IssuerName>&lt;ds:X509SerialNumber>801504260&lt;/ds:X509SerialNumber>&lt;/etsi:IssuerSerial>&lt;/etsi:Cert>&lt;/etsi:SigningCertificate>&lt;/etsi:SignedSignatureProperties>&lt;etsi:SignedDataObjectProperties>&lt;etsi:DataObjectFormat ObjectReference=\"#Reference-ID-367170\">&lt;etsi:Description>contenido comprobante&lt;/etsi:Description>&lt;etsi:MimeType>text/xml&lt;/etsi:MimeType>&lt;/etsi:DataObjectFormat>&lt;/etsi:SignedDataObjectProperties>&lt;/etsi:SignedProperties>&lt;/etsi:QualifyingProperties>&lt;/ds:Object>&lt;/ds:Signature>&lt;/factura></comprobante><mensajes/></autorizacion></autorizaciones></RespuestaAutorizacionComprobante></ns2:autorizacionComprobanteResponse></soap:Body></soap:Envelope>";
    //String responseWsRecepcion = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:validarComprobanteResponse xmlns:ns2=\"http://ec.gob.sri.ws.recepcion\"><RespuestaRecepcionComprobante><estado>RECIBIDA</estado><comprobantes/></RespuestaRecepcionComprobante></ns2:validarComprobanteResponse></soap:Body></soap:Envelope>";


    //String responseWsConMensajee = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:validarComprobanteResponse xmlns:ns2=\"http://ec.gob.sri.ws.recepcion\"><RespuestaRecepcionComprobante><estado>DEVUELTA</estado><comprobantes><comprobante><claveAcceso>2011202201130875419900110010010000000321234567816</claveAcceso><mensajes><mensaje><identificador>45</identificador><mensaje>ERROR SECUENCIAL REGISTRADO</mensaje><tipo>ERROR</tipo></mensaje></mensajes></comprobante></comprobantes></RespuestaRecepcionComprobante></ns2:validarComprobanteResponse></soap:Body></soap:Envelope>";
    // String responseWsRecepcion = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:validarComprobanteResponse><RespuestaRecepcionComprobante><estado>RECIBIDA</estado><comprobantes/></RespuestaRecepcionComprobante></ns2:validarComprobanteResponse></soap:Body></soap:Envelope>";
    //convertToObject(responseWsConMensajee);


    public RespuestaSolicitud processResponseRequest(SOAPBody body) {

        RespuestaSolicitud respuestaSolicitud = new RespuestaSolicitud();

        RespuestaSolicitud.Comprobantes comprobantes = new RespuestaSolicitud.Comprobantes();

        // Accedo al nodo RespuestaRecepcionComprobante
        Element nodeParent = (Element) body.getElementsByTagName("RespuestaRecepcionComprobante").item(0);

        // Obtener los hijos de RespuestaRecepcionComprobante
        NodeList nodeList = nodeParent.getChildNodes();

        // Recorre cada elemento
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node element = nodeList.item(i);
            if (element.getNodeName().equalsIgnoreCase("estado")) {
                respuestaSolicitud.setEstado(element.getTextContent());
            }
            if (element.getNodeName().equalsIgnoreCase("comprobantes") && element.hasChildNodes()) {
                Comprobante comprobante = createComprobantesRecepcion(element);
                comprobantes.getComprobante().add(comprobante);
            }
        }
        respuestaSolicitud.setComprobantes(comprobantes);
        return respuestaSolicitud;
    }

    private Comprobante createComprobantesRecepcion(Node node) {

        Comprobante comprobante = new Comprobante();

        // Obtener el nodo  comprobantes: solo hay uno siempre
        Node nodeComprobantes = node.getFirstChild();

        NodeList nodeListItem = nodeComprobantes.getChildNodes();

        for (int i = 0; i < nodeListItem.getLength(); i++) {

            Node nodoComprobante = nodeListItem.item(i);

            if (nodoComprobante.getNodeName().equalsIgnoreCase("claveAcceso")) {
                comprobante.setClaveAcceso(nodoComprobante.getTextContent());
            } else if (nodoComprobante.getNodeName().equalsIgnoreCase("mensajes") && nodoComprobante.hasChildNodes()) {

                Comprobante.Mensajes mensajes = createWrapperMessages(nodoComprobante);
                comprobante.setMensajes(mensajes);
            }
        }
        return comprobante;
    }

    private Comprobante.Mensajes createWrapperMessages(Node nodeMessages) {

        Comprobante.Mensajes mensajes = new Comprobante.Mensajes();

        // Lista de mensajes
        NodeList nodeList = nodeMessages.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {

            // Acceder a cada mensaje
            Node nodeSms = nodeList.item(i);

            // Acceder a los hijos del mensaje y crear un mensaje
            Mensaje mensaje = creatItemMessage(nodeSms.getChildNodes());

            // Agregar mensaje
            mensajes.getMensaje().add(mensaje);
        }
        return mensajes;
    }

    private Mensaje creatItemMessage(NodeList childSms) {
        Mensaje mensaje = new Mensaje();

        for (int i = 0; i < childSms.getLength(); i++) {

            Node nodeItem = childSms.item(i);

            switch (nodeItem.getNodeName().toLowerCase()) {
                case "identificador":
                    mensaje.setIdentificador(nodeItem.getTextContent());
                    break;

                case "mensaje":
                    mensaje.setMensaje(nodeItem.getTextContent());
                    break;

                case "informacionadicional":
                    mensaje.setInformacionAdicional(nodeItem.getTextContent());
                    break;

                case "tipo":
                    mensaje.setTipo(nodeItem.getTextContent());
                    break;
            }
        }
        return mensaje;
    }


}
