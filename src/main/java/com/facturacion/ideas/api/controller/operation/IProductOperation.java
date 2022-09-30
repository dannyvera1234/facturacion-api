package com.facturacion.ideas.api.controller.operation;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.facturacion.ideas.api.dto.ProductDTO;

public interface IProductOperation {

	@PostMapping("/subsidiarys/{id}/products")
	public ResponseEntity<ProductDTO> save(@RequestBody ProductDTO productDTO,
			@PathVariable(name = "id") Long idSubsidiary);

	@GetMapping("/products/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable Long id);

	@GetMapping("/subsidiarys/{id}/products")
	public ResponseEntity<List<ProductDTO>> findAllByIdSubsidiary(@PathVariable Long id);

	@DeleteMapping("/products/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id);

	@PutMapping("/products/{id}")
	public ResponseEntity<ProductDTO> update(@RequestBody ProductDTO productDTO, @PathVariable Long id);
}
