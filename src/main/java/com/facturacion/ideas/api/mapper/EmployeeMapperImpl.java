package com.facturacion.ideas.api.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.facturacion.ideas.api.dto.EmployeeDTO;
import com.facturacion.ideas.api.dto.EmployeeResponseDTO;
import com.facturacion.ideas.api.entities.Employee;
import com.facturacion.ideas.api.entities.Subsidiary;
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
	public EmployeeResponseDTO mapperToDTO(Employee employee) {

		EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO();

		employeeResponseDTO.setIde(employee.getIde());
		employeeResponseDTO.setCedula(employee.getCedula());
		employeeResponseDTO.setName(employee.getName());
		employeeResponseDTO.setTelephone(employee.getTelephone());
		employeeResponseDTO.setRol(employee.getRol().name());
		
		
		Subsidiary subsidiary = employee.getSubsidiary();
		employeeResponseDTO.setSubsidiary( subsidiary == null ? "No Asignado" : subsidiary.getSocialReason());

		return employeeResponseDTO;
	}

	@Override
	public List<EmployeeResponseDTO> mapperToDTO(List<Employee> employees) {

		List<EmployeeResponseDTO> employeeDTOs = new ArrayList<>();

		if (employees.size() > 0) {

			employeeDTOs = employees.stream().map(item -> mapperToDTO(item)).collect(Collectors.toList());
		}

		return employeeDTOs;
	}

}
