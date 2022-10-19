package com.facturacion.ideas.api.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.facturacion.ideas.api.dto.InvoiceNewDTO;
import com.facturacion.ideas.api.dto.InvoiceResposeDTO;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.services.IDocumentService;
import com.facturacion.ideas.api.util.ConstanteUtil;

@CrossOrigin(origins = ConstanteUtil.CROOS_ORIGIN)
@RestController
@RequestMapping("/facturacion/documents")
public class DocumentRestController {

	private static final Logger LOGGER = LogManager.getLogger(DocumentRestController.class);

	@Autowired
	private IDocumentService documentService;

	@PostMapping("/invoices")
	public ResponseEntity<InvoiceResposeDTO> saveInvoice(@RequestBody InvoiceNewDTO invoiceNewDTO) {

		LOGGER.info("Factura a guardar: " +  invoiceNewDTO);
		try {

			InvoiceResposeDTO invoiceResposeDTO = documentService.saveInvoice(invoiceNewDTO);

			return ResponseEntity.ok(invoiceResposeDTO);
			
		} catch (NotDataAccessException e) {
			
			throw new NotDataAccessException(e.getMessage());
		}
	}
	
	

}
