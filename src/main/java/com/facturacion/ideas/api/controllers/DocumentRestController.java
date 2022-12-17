package com.facturacion.ideas.api.controllers;

import com.facturacion.ideas.api.dto.*;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<ResponseWebServiceDTO> saveDocument(@RequestBody @Valid InvoiceNewDTO invoiceNewDTO) {

        LOGGER.info("Factura a guardar: " + invoiceNewDTO);
        try {

            ResponseWebServiceDTO responseWebServiceDTO = documentService.saveInvoice(invoiceNewDTO);
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
    public ResponseEntity<ValueInvoiceNewDTO> calcularValores(@RequestBody List<DeatailsInvoiceProductDTO> detailsDocument) {

        LOGGER.info("Detalle Productos  a calcular Factura recibidos" + detailsDocument);
        try {

            ValueInvoiceNewDTO ValueInvoiceNewDTO = documentService.calcularValoresDocumento(detailsDocument);
            return new ResponseEntity<>(ValueInvoiceNewDTO, HttpStatus.CREATED);

        } catch (NotDataAccessException e) {
            throw new NotDataAccessException(e.getMessage());
        }
    }

    @GetMapping("/documents")
    public ResponseEntity<Map<String, Object>> findAllDocumentsBySender() {

        try {
            Map<String, Object> mapData = new HashMap<>();
            List<ComprobantesResponseDTO> lista = documentService.findAllDocumentsBySender();
            mapData.put("data", lista);
            mapData.put("size", lista.size());
            return  ResponseEntity.ok(mapData);
        } catch (NotDataAccessException e) {
            throw new NotDataAccessException(e.getMessage());
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<ResponseWebServiceDTO> download(@PathVariable Long id) {

        LOGGER.info("Documento a descargar: " + id);
        try{

            ResponseWebServiceDTO responseWebServiceDTO = documentService.download(id);

            return  ResponseEntity.ok(responseWebServiceDTO);
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


}
// 19112022  01 1308754199001 001 001 000000012 12345678 1 4

// 1911202201130875419900110010010000000121234567810

// 19112022  01 1308754199001