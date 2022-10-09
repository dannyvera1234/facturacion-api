package com.facturacion.ideas.api.mapper;

import java.util.List;

import com.facturacion.ideas.api.dto.EmployeeDTO;
import com.facturacion.ideas.api.dto.EmployeeResponseDTO;
import com.facturacion.ideas.api.entities.Employee;

public interface IEmployeeMapper {

	Employee mapperToEntity(EmployeeDTO employeeDTO);

	EmployeeResponseDTO mapperToDTO(Employee employee);
	
	List<EmployeeResponseDTO> mapperToDTO(List<Employee> employees);

}
