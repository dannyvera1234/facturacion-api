package com.facturacion.ideas.api.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facturacion.ideas.api.controller.operation.IEmployeeOperation;
import com.facturacion.ideas.api.dto.EmployeeDTO;
import com.facturacion.ideas.api.dto.EmployeeResponseDTO;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.services.IEmployeeService;
import com.facturacion.ideas.api.util.ConstanteUtil;

@CrossOrigin(origins = ConstanteUtil.CROOS_ORIGIN)
@RestController
@RequestMapping("/facturacion")
public class EmployeeRestController implements IEmployeeOperation {

	private static final Logger LOGGER = LogManager.getLogger(EmployeeRestController.class);

	@Autowired
	private IEmployeeService employeeService;

	@Override
	public ResponseEntity<EmployeeResponseDTO> save(EmployeeDTO employeeDTO, Long id) {

		LOGGER.info("Empleado a guardar: " + employeeDTO);
		try {

			EmployeeResponseDTO employeeDTOSaved = employeeService.save(employeeDTO, id);

			return new ResponseEntity<EmployeeResponseDTO>(employeeDTOSaved, HttpStatus.CREATED);

		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
		}

	}

	@Override
	public ResponseEntity<EmployeeResponseDTO> findById(Long id) {

		EmployeeResponseDTO employeeDTO = employeeService.findById(id);

		return new ResponseEntity<EmployeeResponseDTO>(employeeDTO, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<List<EmployeeResponseDTO>> findByAll() {

		try {

			List<EmployeeResponseDTO> employeeDTOs = employeeService.findByAll();

			return new ResponseEntity<List<EmployeeResponseDTO>>(employeeDTOs, HttpStatus.OK);

		} catch (NotDataAccessException e) {
			throw new NotDataAccessException(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<String> deleteById(Long idSender, Long idEmployee) {

		try {
			String message = employeeService.deleteById(idSender, idEmployee);

			return new ResponseEntity<String>(message, HttpStatus.NO_CONTENT);

		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<EmployeeResponseDTO> update(EmployeeDTO employeeDTO, Long idSender, 
			Long idEmployee ) {

		try {

			EmployeeResponseDTO employeeDTOSaved = employeeService.update(employeeDTO, idSender, idEmployee);

			return new ResponseEntity<EmployeeResponseDTO>(employeeDTOSaved, HttpStatus.OK);

		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
		}

	}

	@Override
	public ResponseEntity<List<EmployeeResponseDTO>> findAllBySennders(Long id) {

		LOGGER.info("Emisor empleados buscar: " + id);
		try {

			List<EmployeeResponseDTO> employeeDTOs = employeeService.findByIdSenders(id);

			return ResponseEntity.ok(employeeDTOs);

		} catch (NotDataAccessException e) {

			throw new NotDataAccessException(e.getMessage());
		}
	}

}
