package com.facturacion.ideas.api.controllers;

import com.facturacion.ideas.api.dto.ResponseWebServiceDTO;
import com.facturacion.ideas.api.entities.Product;
import com.facturacion.ideas.api.enums.WSTypeEnum;
import com.facturacion.ideas.api.exeption.*;
import com.facturacion.ideas.api.services.IEncryptionService;
import com.facturacion.ideas.api.sri.cliente.ClientSRI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.facturacion.ideas.api.dto.InvoiceNewDTO;
import com.facturacion.ideas.api.dto.InvoiceResposeDTO;
import com.facturacion.ideas.api.services.IDocumentService;
import com.facturacion.ideas.api.util.ConstanteUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.Valid;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@CrossOrigin(origins = ConstanteUtil.CROOS_ORIGIN)
@RestController
@RequestMapping("/facturacion/documents")
public class DocumentRestController {

    private static final Logger LOGGER = LogManager.getLogger(DocumentRestController.class);

    @Autowired
    private IDocumentService documentService;


    @Autowired
    private ClientSRI clienteSRI;

    @Autowired
    private IEncryptionService encryptionService;

    @PostMapping("/invoices")
    public ResponseEntity<ResponseWebServiceDTO> saveInvoice(@RequestBody @Valid InvoiceNewDTO invoiceNewDTO) {

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