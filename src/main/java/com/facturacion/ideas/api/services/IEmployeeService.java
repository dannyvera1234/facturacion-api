package com.facturacion.ideas.api.services;

import java.util.List;

import com.facturacion.ideas.api.dto.EmployeeDTO;

public interface IEmployeeService {

	EmployeeDTO save(EmployeeDTO employeeDTO, Long idSubsidiary);

	EmployeeDTO findById(Long id);
		
	
	List<EmployeeDTO> findByIdSubsidiary(Long idSubsidiary);	

	List<EmployeeDTO> findByAll();

	String deleteById(Long id);

	EmployeeDTO update(EmployeeDTO employeeDTO, Long id);

}
