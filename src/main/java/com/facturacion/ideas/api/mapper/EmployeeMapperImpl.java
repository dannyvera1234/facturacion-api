package com.facturacion.ideas.api.mapper;

import org.springframework.stereotype.Component;

import com.facturacion.ideas.api.dto.EmployeeDTO;
import com.facturacion.ideas.api.entities.Employee;
import com.facturacion.ideas.api.enums.RolEnum;

@Component
public class EmployeeMapperImpl implements IEmployeeMapper {

	@Override
	public Employee mapperToEntity(EmployeeDTO employeeDTO) {

		Employee employee = new Employee();

		employee.setIde(employeeDTO.getIde());

		employee.setCedula(employeeDTO.getCedula());

		employee.setTelephone(employeeDTO.getTelephone());

		employee.setName(employeeDTO.getName());

		employee.setRol(RolEnum.getRolEnum(employeeDTO.getRol()));

		return employee;
	}

	@Override
	public EmployeeDTO mapperToDTO(Employee employee) {

		EmployeeDTO employeeDTO = new EmployeeDTO();

		employeeDTO.setIde(employee.getIde());
		employeeDTO.setCedula(employee.getCedula());
		employeeDTO.setName(employee.getName());
		employeeDTO.setTelephone(employee.getTelephone());
		employeeDTO.setRol(employee.getRol().name());
		employeeDTO.setSubsidiary(employee.getSubsidiary().getSocialReason());

		return employeeDTO;
	}

}
