package com.facturacion.ideas.api.mapper;

import java.util.List;

import com.facturacion.ideas.api.dto.EmployeeDTO;
import com.facturacion.ideas.api.entities.Employee;

public interface IEmployeeMapper {

	Employee mapperToEntity(EmployeeDTO employeeDTO);

	EmployeeDTO mapperToDTO(Employee employee);
	
	List<EmployeeDTO> mapperToDTO(List<Employee> employees);

}
