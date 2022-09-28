package com.facturacion.ideas.api.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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

	@Override
	public ResponseEntity<EmployeeDTO> findById(Long id) {

		EmployeeDTO employeeDTO = employeeService.findById(id);

		return new ResponseEntity<EmployeeDTO>(employeeDTO, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<List<EmployeeDTO>> findByAll() {

		try {

			List<EmployeeDTO> employeeDTOs = employeeService.findByAll();

			return new ResponseEntity<List<EmployeeDTO>>(employeeDTOs, HttpStatus.OK);

		} catch (NotDataAccessException e) {
			throw new NotDataAccessException(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<String> deleteById(@PathVariable Long id) {

		try {
			String message = employeeService.deleteById(id);

			return new ResponseEntity<String>(message, HttpStatus.NO_CONTENT);

		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<EmployeeDTO> update(EmployeeDTO employeeDTO, Long id) {

		try {

			EmployeeDTO employeeDTOSaved = employeeService.update(employeeDTO, id);

			return new ResponseEntity<EmployeeDTO>(employeeDTOSaved, HttpStatus.OK);

		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
		}

	}

}
