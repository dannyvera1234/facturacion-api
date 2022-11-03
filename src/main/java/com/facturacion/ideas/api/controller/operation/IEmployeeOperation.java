package com.facturacion.ideas.api.controller.operation;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.facturacion.ideas.api.dto.EmployeeDTO;
import com.facturacion.ideas.api.dto.EmployeeResponseDTO;

public interface IEmployeeOperation {

	@PostMapping("/senders/{id}/employees")
	public ResponseEntity<EmployeeResponseDTO> save(@RequestBody EmployeeDTO employeeDTO, @PathVariable Long id);

	@GetMapping("/senders/{id}/employees")
	public ResponseEntity<List<EmployeeResponseDTO>> findAllBySennders(@PathVariable Long id);
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<EmployeeResponseDTO> findById(@PathVariable Long id);

	@GetMapping("/employees")
	public ResponseEntity<List<EmployeeResponseDTO>> findByAll();

	@GetMapping("/senders/{id}/subsidiarys/{id-sub}/employees/search")
	public ResponseEntity<List<EmployeeResponseDTO>>
	findByAllBySenderAndSubsidiary(@PathVariable("id") Long idSender,
								   @PathVariable("id-sub") Long idSub,
								   @RequestParam(value = "filtro", required = false) String filtro);

	@DeleteMapping("/senders/{id}/employees/{id-employee}")
	public ResponseEntity<String> deleteById(@PathVariable("id") Long idSender,
			@PathVariable("id-employee") Long idEmployee);

	@PutMapping("/senders/{id}/employees/{id-employee}")
	public ResponseEntity<EmployeeResponseDTO> update(@RequestBody EmployeeDTO employeeDTO, 
			@PathVariable("id") Long idSender,
			@PathVariable("id-employee") Long idEmployee);

}
