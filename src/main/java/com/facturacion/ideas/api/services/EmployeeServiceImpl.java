package com.facturacion.ideas.api.services;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facturacion.ideas.api.dto.EmployeeDTO;
import com.facturacion.ideas.api.entities.Employee;
import com.facturacion.ideas.api.entities.Subsidiary;
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

			if (employeExist(employeeDTO.getCedula()).isPresent()) {

				throw new DuplicatedResourceException("Cedula: " + employeeDTO.getCedula()
						+ ConstanteUtil.MESSAJE_DUPLICATED_RESOURCE_DEFAULT_EXCEPTION);
			}

			Employee employee = employeeMapper.mapperToEntity(employeeDTO);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmployeeDTO> findByAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public EmployeeDTO update(EmployeeDTO employeeDTO, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly = true)
	private Employee findByCedula(String cedula) {
		try {

			Employee employee = employeeRepository.findByCedula(cedula).orElseThrow(() -> new NotFoundException(
					"cedula: " + cedula + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

			return employee;

		} catch (DataAccessException e) {

			LOGGER.error("Error buscar empleado cedula", e);

			throw new NotDataAccessException("Error buscar empleado: " + e.getMessage());

		}

	}

	@Transactional(readOnly = true)
	private Optional<Employee> employeExist(String cedula) {
		try {

			return employeeRepository.findByCedula(cedula);

		} catch (DataAccessException e) {

			LOGGER.error("Error buscar empleado cedula", e);

			throw new NotDataAccessException("Error buscar empleado: " + e.getMessage());

		}

	}

}
