package com.facturacion.ideas.api.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facturacion.ideas.api.controller.operation.IProductOperation;
import com.facturacion.ideas.api.dto.ProductDTO;
import com.facturacion.ideas.api.dto.ProductInformationDTO;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.services.IProductService;

@RestController
@RequestMapping("/facturacion")
public class ProductRestController implements IProductOperation {

	private static final Logger LOGGER = LogManager.getLogger(ProductRestController.class);

	@Autowired
	private IProductService productService;

	@Override
	public ResponseEntity<ProductDTO> save(ProductDTO productDTO, Long idSubsidiary) {

		LOGGER.info("Producto guardar: " + productDTO.getProductInformationDTOs().size());
		try {

			// return ResponseEntity.ok(productDTO);
			ProductDTO productDTOSaved = productService.save(productDTO, idSubsidiary);

			return new ResponseEntity<ProductDTO>(productDTOSaved, HttpStatus.CREATED);

		} catch (NotDataAccessException e) {
			throw new NotDataAccessException(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<ProductDTO> findById(Long id) {

		ProductDTO productDTOSaved = productService.findById(id);

		return new ResponseEntity<ProductDTO>(productDTOSaved, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<ProductDTO>> findAllByIdSubsidiary(Long id) {

		try {
			List<ProductDTO> productDTOS = productService.findAll(id);

			return new ResponseEntity<List<ProductDTO>>(productDTOS, HttpStatus.OK);

		} catch (NotDataAccessException e) {
			throw new NotDataAccessException(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<String> deleteById(Long id) {

		LOGGER.info("Id producto eliminar: " + id);

		try {

			String smsResponse = productService.deleteById(id);

			return new ResponseEntity<String>(smsResponse, HttpStatus.NO_CONTENT);

		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
		}

	}

	@Override
	public ResponseEntity<ProductDTO> update(ProductDTO productDTO, Long id) {
		LOGGER.info("Id producto actualizar: " + id);

		try {

			ProductDTO productDTOUpdated = productService.update(productDTO, id);

			return ResponseEntity.ok(productDTOUpdated);

		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<List<ProductInformationDTO>> findProducInformation(Long id) {

		try {

			List<ProductInformationDTO> productInformationDTOs = productService.findProductInforAll(id);

			return ResponseEntity.ok(productInformationDTOs);

		} catch (NotDataAccessException e) {
			throw new NotDataAccessException(e.getMessage());

		}
	}

	@Override
	public ResponseEntity<ProductInformationDTO> findProducInformationById(Long id, Long idDetails) {
		try {

			ProductInformationDTO productInformationDTO = productService.findProductInforById(id, idDetails);
			return ResponseEntity.ok(productInformationDTO);

		} catch (NotDataAccessException e) {
			throw new NotDataAccessException(e.getMessage());

		}
	}

	@Override
	public ResponseEntity<String> deleteProductInformation(Long id, Long idDetails) {

		try {

			String response = productService.deleteProductInfoById(id, idDetails);

			return new ResponseEntity<String>(response, HttpStatus.NO_CONTENT);

		} catch (NotDataAccessException e) {
			throw new NotDataAccessException(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<String> deleteProductInformationAll(Long id) {
		try {

			String response = productService.deleteProductInfoAllById(id);

			return new ResponseEntity<String>(response, HttpStatus.NO_CONTENT);

		} catch (NotDataAccessException e) {
			throw new NotDataAccessException(e.getMessage());
		}
	}

}
