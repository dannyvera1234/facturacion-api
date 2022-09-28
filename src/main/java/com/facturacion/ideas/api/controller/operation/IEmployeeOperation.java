package com.facturacion.ideas.api.controller.operation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.facturacion.ideas.api.dto.EmployeeDTO;

public interface IEmployeeOperation {

	@PostMapping("/subsidiarys/{id}/employees")
	public ResponseEntity<EmployeeDTO> save(@RequestBody EmployeeDTO employeeDTO, @PathVariable Long id);

}
