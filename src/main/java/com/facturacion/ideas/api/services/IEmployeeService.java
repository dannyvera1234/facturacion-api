package com.facturacion.ideas.api.services;

import java.util.List;

import com.facturacion.ideas.api.dto.EmployeeDTO;
import com.facturacion.ideas.api.dto.EmployeeResponseDTO;

public interface IEmployeeService {

	EmployeeResponseDTO save(EmployeeDTO employeeDTO, Long idSender);

	EmployeeResponseDTO findById(Long id);
		
	
	List<EmployeeResponseDTO> findByIdSenders(Long idSubsidiary);	

	List<EmployeeResponseDTO> findByAll();

	String deleteById(Long idSender, Long idEmployee);

	EmployeeResponseDTO update(EmployeeDTO employeeDTO, Long idSender, Long idEmployee);

}
