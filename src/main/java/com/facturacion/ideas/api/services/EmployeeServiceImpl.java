package com.facturacion.ideas.api.services;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facturacion.ideas.api.dto.EmployeeDTO;
import com.facturacion.ideas.api.dto.EmployeeResponseDTO;
import com.facturacion.ideas.api.entities.Employee;
import com.facturacion.ideas.api.entities.Sender;
import com.facturacion.ideas.api.entities.Subsidiary;
import com.facturacion.ideas.api.enums.RolEnum;
import com.facturacion.ideas.api.exeption.DuplicatedResourceException;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.exeption.NotFoundException;
import com.facturacion.ideas.api.mapper.IEmployeeMapper;
import com.facturacion.ideas.api.repositories.IEmployeeRepository;
import com.facturacion.ideas.api.repositories.ISenderRepository;
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

	@Autowired
	private ISenderRepository senderRepository;

	@Override
	public EmployeeResponseDTO save(EmployeeDTO employeeDTO, Long idSennder) {

		try {

			// Obtener el emisor del etablecimiento
			Sender sender = senderRepository.findById(idSennder).orElseThrow(() -> new NotFoundException(
					"Emisor id: " + idSennder + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

			// Verificar si el empleado ya pertene a la empresa | emisor
			if (employeeRepository.existsByCedulaAndSender(employeeDTO.getCedula(), sender)) {

				throw new DuplicatedResourceException("Empleado con cedula: " + employeeDTO.getCedula()
						+ " ya pertenece al emisor " + sender.getSocialReason());

			}

			Employee employee = employeeMapper.mapperToEntity(employeeDTO);

			Long idSubsidiary = employeeDTO.getSubsidiary();

			// Si al empleado se le asigno un establecimiento
			if (idSubsidiary != null) {

				// Validar que existe el establecimiento, ademas que este asociado con el
				// emisor.
				// para asegurarse que establecimiento sea del mismo emisor
				Subsidiary subsidiary = subsidiaryRepository.findByIdeAndSender(idSubsidiary, sender)
						.orElseThrow(() -> new NotFoundException("Emisor " + sender.getSocialReason()
								+ " no tiene registrado un establecimiento con id :" + idSubsidiary));

				// Asingnar establecimiento al empleado
				employee.setSubsidiary(subsidiary);

			}

			employee.setSender(sender);

			// Persistir empleado
			Employee employeeSaved = employeeRepository.save(employee);

			return employeeMapper.mapperToDTO(employeeSaved);

		} catch (DataAccessException e) {

			LOGGER.error("Error guardar empleado", e);

			throw new NotDataAccessException("Error guardar empleado: " + e.getMessage());

		}
	}

	@Override
	public EmployeeResponseDTO findById(Long id) {
		try {

			EmployeeResponseDTO employeeDTO = employeeMapper.mapperToDTO(findByIdPrivate(id));

			return employeeDTO;

		} catch (DataAccessException e) {
			LOGGER.error("Error buscar empleado", e);

			throw new NotDataAccessException("Error buscar empleado: " + e.getMessage());
		}

	}

	@Override
	@Transactional(readOnly = true)
	public List<EmployeeResponseDTO> findByAll() {

		try {

			List<EmployeeResponseDTO> employeeDTOs = employeeRepository.findAll().stream()
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
	public EmployeeResponseDTO update(EmployeeDTO employeeDTO, Long id) {

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

			// Si se asigna un establecimiento
			if (employeeDTO.getSubsidiary() != null) {
				
			
				// Obtener el establecimiento que se desea asigna al empleado, esté debe corresponder al mismo emisor del empleado a actualizar	
				Subsidiary subsidiary = subsidiaryRepository.findByIdeAndSender(employeeDTO.getSubsidiary(), employee.getSender())
						.orElseThrow(() -> new NotFoundException("Establecimiento " + employeeDTO.getSubsidiary()
								+ ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));
	
				// Asignar establecimiento al empleado
				employee.setSubsidiary(subsidiary);

			}
			// Dejar si un establecimiento al empleado
			else
				employee.setSubsidiary(null);

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
	public List<EmployeeResponseDTO> findByIdSenders(Long idSender) {

		try {

			Sender emisor = senderRepository.findById(idSender).orElseThrow(() -> new NotFoundException(
					"Emisor: " + idSender + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

			List<Employee> employees = emisor.getEmployees();

			return employeeMapper.mapperToDTO(employees);

		} catch (DataAccessException e) {
			LOGGER.error("Error listar empleado emisor", e);

			throw new NotDataAccessException("Error listar empleado emisor: " + e.getMessage());
		}

	}

}
