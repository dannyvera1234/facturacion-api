package com.facturacion.ideas.api.controller.operation;

import java.util.List;

import com.facturacion.ideas.api.dto.ProductResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.facturacion.ideas.api.dto.ProductDTO;
import com.facturacion.ideas.api.dto.ProductInformationDTO;

import javax.validation.Valid;

public interface IProductOperation {

	@PostMapping("/subsidiarys/{id}/products")
	ResponseEntity<ProductResponseDTO> save(@RequestBody @Valid  ProductDTO productDTO,
											@PathVariable(name = "id") Long idSubsidiary);

	@GetMapping("/products/{id}")
	ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id);

	@GetMapping("/products/search")
	ResponseEntity<ProductResponseDTO> findByCodePrivate(@RequestParam("codigo") String  codigo);

	@GetMapping("/subsidiarys/{id}/products")
	ResponseEntity<List<ProductResponseDTO>> findAllByIdSubsidiary(@PathVariable Long id);

	@DeleteMapping("/products/{id}")
	ResponseEntity<String> deleteById(@PathVariable Long id);

	@PutMapping("/products/{id}")
	ResponseEntity<ProductResponseDTO> update(@RequestBody ProductDTO productDTO, @PathVariable Long id);
	
	// Product Information
	@GetMapping("/products/{id}/additional-details")
	ResponseEntity<List<ProductInformationDTO>> findProducInformation(@PathVariable Long id);

	@DeleteMapping("/products/{id}/additional-details")
	ResponseEntity<String> deleteProductInformationAll(@PathVariable Long id);
	
	@DeleteMapping("/products/{id}/additional-details/{id-details}")
	ResponseEntity<String> deleteProductInformation(@PathVariable Long id , @PathVariable(name = "id-details") Long idDetails);

	@PutMapping("/additional-details/{id}")
	ResponseEntity<ProductInformationDTO> updateProInformation(@RequestBody ProductInformationDTO productInformationDTO, @PathVariable Long id);

	@GetMapping("/subsidiarys/{id}/products/search")
	ResponseEntity<List<ProductResponseDTO>> searchByCodeAndName(@PathVariable Long id, @RequestParam(name = "filtro", required = false, defaultValue = "") String filtro);


}
