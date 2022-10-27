package com.facturacion.ideas.api.controllers;

import com.facturacion.ideas.api.entities.Product;
import com.facturacion.ideas.api.repositories.IProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.facturacion.ideas.api.dto.InvoiceNewDTO;
import com.facturacion.ideas.api.dto.InvoiceResposeDTO;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.services.IDocumentService;
import com.facturacion.ideas.api.util.ConstanteUtil;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = ConstanteUtil.CROOS_ORIGIN)
@RestController
@RequestMapping("/facturacion/documents")
public class DocumentRestController {

	private static final Logger LOGGER = LogManager.getLogger(DocumentRestController.class);

	@Autowired
	private IDocumentService documentService;

	@Autowired
	private IProductRepository productRepository;

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


	@GetMapping("/test")
	public List<Product> test (){


		List<Long> ids = Arrays.asList(6L, 28L, 3L);

		return productRepository.findByIdeIn(ids);
	}
	
	

}
