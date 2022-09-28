package com.facturacion.ideas.api.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facturacion.ideas.api.controller.operation.IEmployeeOperation;
import com.facturacion.ideas.api.dto.EmployeeDTO;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.services.IEmployeeService;

@RestController
@RequestMapping("/facturacion")
public class EmployeeRestController implements IEmployeeOperation {

	private static final Logger LOGGER = LogManager.getLogger(EmployeeRestController.class);

	@Autowired
	private IEmployeeService employeeService;

	@Override
	public ResponseEntity<EmployeeDTO> save(EmployeeDTO employeeDTO, Long id) {

		LOGGER.info("Empleado a guardar: " + employeeDTO);
		try {

			EmployeeDTO employeeDTOSaved = employeeService.save(employeeDTO, id);

			return new ResponseEntity<EmployeeDTO>(employeeDTOSaved, HttpStatus.CREATED);

		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
		}

	}

}
