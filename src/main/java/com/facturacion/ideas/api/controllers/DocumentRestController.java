package com.facturacion.ideas.api.controllers;

import com.facturacion.ideas.api.dto.*;
import com.facturacion.ideas.api.entities.Product;
import com.facturacion.ideas.api.exeption.*;
import com.facturacion.ideas.api.services.IEncryptionService;
import com.facturacion.ideas.api.util.PathDocuments;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.facturacion.ideas.api.services.IDocumentService;
import com.facturacion.ideas.api.util.ConstanteUtil;

import javax.validation.Valid;
import java.io.File;
import java.util.List;

@CrossOrigin(origins = ConstanteUtil.CROOS_ORIGIN)
@RestController
@RequestMapping("/facturacion/documents")
public class DocumentRestController {

    private static final Logger LOGGER = LogManager.getLogger(DocumentRestController.class);

    @Autowired
    private IDocumentService documentService;

    @Autowired
    private IEncryptionService encryptionService;

    @PostMapping("/invoices")
    public ResponseEntity<ResponseWebServiceDTO> generarFactura(@RequestBody @Valid InvoiceNewDTO invoiceNewDTO) {

        LOGGER.info("Factura a guardar: " + invoiceNewDTO);
        try {

            ResponseWebServiceDTO responseWebServiceDTO = documentService.saveInvoice(invoiceNewDTO);

            //ResponseWebServiceDTO responseWebServiceDTO =  new ResponseWebServiceDTO();

            return new ResponseEntity<>(responseWebServiceDTO, HttpStatus.CREATED);

        } catch (NotDataAccessException e) {
            throw new NotDataAccessException(e.getMessage());
        } catch (GenerateXMLExeption e) {
            throw new GenerateXMLExeption(e.getMessage());
        } catch (SignatureException e) {
            throw new SignatureException(e.getMessage());
        } catch (ConsumeWebServiceException e) {
            throw new ConsumeWebServiceException(e.getMessage());
        } catch (EncryptedException e) {
            throw new EncryptedException(e.getMessage());
        }
    }

    @PostMapping("/calcular")
    public ResponseEntity<ValueInvoiceNewDTO> generarFactura(@RequestBody List<DeatailsInvoiceProductDTO> detailsDocument) {

        LOGGER.info("Valores Factura recibidos: " + detailsDocument);
        try {

            ValueInvoiceNewDTO ValueInvoiceNewDTO = documentService.calcularValoresDocumento(detailsDocument);
            return new ResponseEntity<>(ValueInvoiceNewDTO, HttpStatus.CREATED);

        } catch (NotDataAccessException e) {
            throw new NotDataAccessException(e.getMessage());
        }
    }

    @GetMapping("/xml")
    public ResponseEntity<PDFResponse> findXml() {

        try {

            String nombreArchivo = PathDocuments.PATH_BASE.concat("1308754199001/est_001/emi_001/autorizados/0912202201130875419900110010010000000871234567816.xml");
            File archivo = new File(nombreArchivo);
            SAXReader reader = new SAXReader();

            Document document = reader.read(archivo);

            String xmlStroing = document.asXML();

            PDFResponse pdfResponse = new PDFResponse();
            pdfResponse.setDocument(xmlStroing);

            LOGGER.info(xmlStroing);
            return  ResponseEntity.ok(pdfResponse);


         /*   DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setNamespaceAware(false);
            documentBuilderFactory.setValidating(false);
            documentBuilderFactory.setFeature("http://xml.org/sax/features/namespaces", false);
            documentBuilderFactory.setFeature("http://xml.org/sax/features/validation", false);
            documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
            documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);


            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();


            Document document = documentBuilder.parse(archivo);
            document.getDocumentElement().normalize();

            LOGGER.info("Primer nodo: " + document.getFirstChild().getNodeName());

            LOGGER.info( "XML PARSEADO: " + document.getTextContent());*/

        } catch (Exception e) {

            LOGGER.info( "error parseo: ", e);
            return ResponseEntity.ok(new PDFResponse());

        }

    }

    @GetMapping("/test")
    public List<Product> test() {


        List<Long> ids = List.of(1L);

        return documentService.searchProductsDetailsByIds(ids);


        //return productRepository.fetchTaxValueTaxByIdeIn(ids);
    }


    @GetMapping("/envio")
    public String envio(@RequestParam String texto) {


		/*
		try {
			clienteSRI.validarComprobante(null);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}*/


        //clienteSRI.receptionDocument("/home/ronny/Documentos/1911202201130875419900110010010000000281234567811_sign.xml", WSTypeEnum.WS_TEST_RECEPTION);


        //AdminDocument.generateCheckDigit("200920200117231242580011001001000397193123456781");

        //return  AdminDocument.generateCheckDigit("200920200117231242540011001001000397193123456781");

        // return  AdminDocument.generateCheckDigit("151120220113087541990011001001000000019333333371");

        //return productRepository.fetchTaxValueTaxByIdeIn(ids);
        // clienteSRI.authorizationDocument(WSTypeEnum.WS_TEST_AUTHORIZATION, "1911202201130875419900110010010000000281234567811");


        //String texto = "ronny";

        String encripted = "";
        String decripted = "";
        try {
            encripted = encryptionService.encrypt(texto);

            decripted = encryptionService.deEncrypt(encripted);

        } catch (
                EncryptedException e
        ) {

            System.out.println("Error encryotar_:  " + e.getMessage());
        }
        return "Pass: " + encripted + ": " + decripted;
    }
}
// 19112022  01 1308754199001 001 001 000000012 12345678 1 4

// 1911202201130875419900110010010000000121234567810

// 19112022  01 1308754199001