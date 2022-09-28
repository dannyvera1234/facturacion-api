package com.facturacion.ideas.api.controller.operation;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.facturacion.ideas.api.dto.EmployeeDTO;

public interface IEmployeeOperation {

	@PostMapping("/subsidiarys/{id}/employees")
	public ResponseEntity<EmployeeDTO> save(@RequestBody EmployeeDTO employeeDTO, @PathVariable Long id);

	@GetMapping("/employees/{id}")
	public ResponseEntity<EmployeeDTO> findById(@PathVariable Long id);

	@GetMapping("/employees")
	public ResponseEntity<List<EmployeeDTO>> findByAll();

	@DeleteMapping("/employees/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id);

	@PutMapping("/employees/{id}")
	public ResponseEntity<EmployeeDTO> update(@RequestBody EmployeeDTO employeeDTO, @PathVariable Long id);

}
