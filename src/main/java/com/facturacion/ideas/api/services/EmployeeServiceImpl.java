package com.facturacion.ideas.api.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facturacion.ideas.api.dto.EmployeeDTO;
import com.facturacion.ideas.api.entities.Employee;
import com.facturacion.ideas.api.entities.Sender;
import com.facturacion.ideas.api.entities.Subsidiary;
import com.facturacion.ideas.api.enums.RolEnum;
import com.facturacion.ideas.api.exeption.DuplicatedResourceException;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.exeption.NotFoundException;
import com.facturacion.ideas.api.mapper.IEmployeeMapper;
import com.facturacion.ideas.api.repositories.IEmployeeRepository;
import com.facturacion.ideas.api.repositories.ISubsidiaryRepository;
import com.facturacion.ideas.api.util.ConstanteUtil;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

	private static final Logger LOGGER = LogManager.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private IEmployeeRepository employeeRepository;

	@Autowired
	private IEmployeeMapper employeeMapper;

	@Autowired
	private ISubsidiaryRepository subsidiaryRepository;

	@Override
	public EmployeeDTO save(EmployeeDTO employeeDTO, Long idSubsidiary) {

		try {

			Subsidiary subsidiary = subsidiaryRepository.findById(idSubsidiary).orElseThrow(() -> new NotFoundException(
					"id:" + idSubsidiary + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

			// Obtener el emisor del etablecimiento
			Sender sender = subsidiary.getSender();

			
			// Verificar si el empleado ya pertene a la empresa o emisor
			if (employeeRepository.existsBySender(sender)) {

				throw new DuplicatedResourceException("Empleado con cedula: " + employeeDTO.getCedula()
						+ ConstanteUtil.MESSAJE_DUPLICATED_RESOURCE_DEFAULT_EXCEPTION + " , mismo emisor");
				
			}

			
			/*
			// Verificar si la cedula ya esta registrada en el establecimiento
			if (employeeRepository.existsByCedulaAndSubsidiary(employeeDTO.getCedula(), subsidiary)) {

				throw new DuplicatedResourceException("Cedula: " + employeeDTO.getCedula()
						+ ConstanteUtil.MESSAJE_DUPLICATED_RESOURCE_DEFAULT_EXCEPTION
						+ "  empleado esta registrado en un establecimiento");

			}*/

			Employee employee = employeeMapper.mapperToEntity(employeeDTO);
			employee.setSender(sender);
			employee.setSubsidiary(subsidiary);

			// Persistir empleado
			Employee employeeSaved = employeeRepository.save(employee);

			return employeeMapper.mapperToDTO(employeeSaved);

		} catch (DataAccessException e) {

			LOGGER.error("Error guardar empleado", e);

			throw new NotDataAccessException("Error guardar empleado: " + e.getMessage());

		}
	}

	@Override
	public EmployeeDTO findById(Long id) {

		EmployeeDTO employeeDTO = employeeMapper.mapperToDTO(findByIdPrivate(id));

		return employeeDTO;
	}

	@Override
	@Transactional(readOnly = true)
	public List<EmployeeDTO> findByAll() {

		try {

			List<EmployeeDTO> employeeDTOs = employeeRepository.findAll().stream()
					.map(item -> employeeMapper.mapperToDTO(item)).collect(Collectors.toList());

			return employeeDTOs;

		} catch (DataAccessException e) {
			LOGGER.error("Error listar empleados", e);

			throw new NotDataAccessException("Error listar empleados: " + e.getMessage());

		}

	}

	@Override
	@Transactional
	public String deleteById(Long id) {

		Employee employee = findByIdPrivate(id);

		try {

			employeeRepository.delete(employee);

			return "Empleado id " + id + " eliminado con exito";

		} catch (DataAccessException e) {

			LOGGER.error("Error eliminar empleado", e);

			throw new NotDataAccessException("Error eliminar empleado: " + e.getMessage());
		}

	}

	@Override
	@Transactional
	public EmployeeDTO update(EmployeeDTO employeeDTO, Long id) {

		Employee employee = findByIdPrivate(id);

		try {

			employee.setTelephone(
					employeeDTO.getTelephone() == null ? employee.getTelephone() : employeeDTO.getTelephone());

			if (employeeDTO.getRol() != null && !employeeDTO.getRol().isEmpty()) {

				if (RolEnum.ADMIN.name().equals(employeeDTO.getRol())
						|| RolEnum.USSER.name().equals(employeeDTO.getRol())) {

					employee.setRol(RolEnum.getRolEnum(employeeDTO.getRol()));
				}

			}

			Employee employeeSaved = employeeRepository.save(employee);

			return employeeMapper.mapperToDTO(employeeSaved);

		} catch (DataAccessException e) {
			LOGGER.error("Error actualizar empleado", e);

			throw new NotDataAccessException("Error actualizar empleado: " + e.getMessage());

		}

	}

	@Transactional(readOnly = true)
	private Employee findByIdPrivate(Long ide) {
		try {

			Employee employee = employeeRepository.findById(ide).orElseThrow(
					() -> new NotFoundException("ide: " + ide + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

			return employee;

		} catch (DataAccessException e) {

			LOGGER.error("Error buscar empleado ide", e);

			throw new NotDataAccessException("Error buscar empleado: " + e.getMessage());

		}

	}

	@Override
	@Transactional(readOnly = true)
	public List<EmployeeDTO> findByIdSubsidiary(Long idSubsidiary) {

		try {

			Subsidiary subsidiary = subsidiaryRepository.findById(idSubsidiary).orElseThrow(() -> new NotFoundException(
					"establecimiento: " + idSubsidiary + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

			List<Employee> employees = subsidiary.getEmployees();

			return employeeMapper.mapperToDTO(employees);

		} catch (DataAccessException e) {
			LOGGER.error("Error listar empleado establecimiento", e);

			throw new NotDataAccessException("Error listar empleado establecimiento: " + e.getMessage());
		}

	}

}
