package com.facturacion.ideas.api.services;

import java.util.List;

import com.facturacion.ideas.api.dto.EmployeeDTO;

public interface IEmployeeService {

	EmployeeDTO save(EmployeeDTO employeeDTO, Long idSubsidiary);

	EmployeeDTO findById(Long id);	

	List<EmployeeDTO> findByAll();

	void deleteById(Long id);

	EmployeeDTO update(EmployeeDTO employeeDTO, Long id);

}
