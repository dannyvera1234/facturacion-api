package com.facturacion.ideas.api.controller.operation;

import java.util.List;

import com.facturacion.ideas.api.dto.ProductResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.facturacion.ideas.api.dto.ProductDTO;
import com.facturacion.ideas.api.dto.ProductInformationDTO;

public interface IProductOperation {

	@PostMapping("/subsidiarys/{id}/products")
	public ResponseEntity<ProductResponseDTO> save(@RequestBody ProductDTO productDTO,
												   @PathVariable(name = "id") Long idSubsidiary);

	@GetMapping("/products/{id}")
	public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id);

	@GetMapping("/subsidiarys/{id}/products")
	public ResponseEntity<List<ProductResponseDTO>> findAllByIdSubsidiary(@PathVariable Long id);

	@DeleteMapping("/products/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id);

	@PutMapping("/products/{id}")
	public ResponseEntity<ProductResponseDTO> update(@RequestBody ProductDTO productDTO, @PathVariable Long id);
	
	// Product Information
	@GetMapping("/products/{id}/additional-details")
	public ResponseEntity<List<ProductInformationDTO>> findProducInformation(@PathVariable Long id);

	@DeleteMapping("/products/{id}/additional-details")
	public ResponseEntity<String> deleteProductInformationAll(@PathVariable Long id);
	
	@DeleteMapping("/products/{id}/additional-details/{id-details}")
	public ResponseEntity<String> deleteProductInformation(@PathVariable Long id , @PathVariable(name = "id-details") Long idDetails);

	@PutMapping("/additional-details/{id}")
	public ResponseEntity<ProductInformationDTO> updateProInformation(@RequestBody ProductInformationDTO productInformationDTO, @PathVariable Long id);
	
	
	
}
