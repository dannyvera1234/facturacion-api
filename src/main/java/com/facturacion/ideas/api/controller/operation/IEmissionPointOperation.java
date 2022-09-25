package com.facturacion.ideas.api.controller.operation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.facturacion.ideas.api.entities.EmissionPoint;

/**
 * 
 * @author Ronny Chamba
 *
 */
@RequestMapping("/default")
public interface IEmissionPointOperation {

	@PostMapping
	public ResponseEntity<?> save(@PathVariable Long codigo, @RequestBody EmissionPoint emissionPoint);

	@GetMapping
	public ResponseEntity<?> findAll(@PathVariable Long codigo);

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long codigo, @PathVariable Long id);

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long codigo, @PathVariable Long id);

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody EmissionPoint emissionPoint, @PathVariable Long codigo,
			@PathVariable Long id);

}
