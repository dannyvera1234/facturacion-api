package com.facturacion.ideas.api.mapper;

import com.facturacion.ideas.api.dto.EmployeeDTO;
import com.facturacion.ideas.api.entities.Employee;

public interface IEmployeeMapper {

	Employee mapperToEntity(EmployeeDTO employeeDTO);

	EmployeeDTO mapperToDTO(Employee employee);

}
