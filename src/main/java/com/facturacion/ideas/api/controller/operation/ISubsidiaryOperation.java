package com.facturacion.ideas.api.controller.operation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.facturacion.ideas.api.entities.Subsidiary;

public interface ISubsidiaryOperation {

	@PostMapping("/subsidiarys")
	public ResponseEntity<?> save(@RequestBody Subsidiary subsidiary, @PathVariable("id") Long idSender);

	@GetMapping("/subsidiarys")
	public ResponseEntity<?> findAll(@PathVariable("id") Long idSender);

	@GetMapping("/subsidiarys/{codigo}")
	public ResponseEntity<?> findById(@PathVariable(required = false, name="id") Long idSender, @PathVariable Long codigo);

	@PutMapping("/subsidiarys/{codigo}")
	public ResponseEntity<?> update(@RequestBody Subsidiary subsidiary,
			@PathVariable(required = false, name="id") Long idSender, @PathVariable  Long codigo);

	@DeleteMapping("/subsidiarys/{codigo}")
	public ResponseEntity<?> delete(@PathVariable(required = false, name="id") Long idSender,  @PathVariable Long codigo);

}
