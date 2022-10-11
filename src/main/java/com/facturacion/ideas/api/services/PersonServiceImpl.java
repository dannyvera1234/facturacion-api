package com.facturacion.ideas.api.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facturacion.ideas.api.dto.CustomerNewDTO;
import com.facturacion.ideas.api.dto.CustomerResponseDTO;
import com.facturacion.ideas.api.dto.DriverNewDTO;
import com.facturacion.ideas.api.dto.DriverResponseDTO;
import com.facturacion.ideas.api.entities.Customer;
import com.facturacion.ideas.api.entities.DetailsPerson;
import com.facturacion.ideas.api.entities.Driver;
import com.facturacion.ideas.api.entities.Sender;
import com.facturacion.ideas.api.exeption.DuplicatedResourceException;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.exeption.NotFoundException;
import com.facturacion.ideas.api.mapper.IPersonMapper;
import com.facturacion.ideas.api.repositories.ICustomerRepository;
import com.facturacion.ideas.api.repositories.IDetailsPersonRepository;
import com.facturacion.ideas.api.repositories.IDriverRepository;
import com.facturacion.ideas.api.repositories.IPersonRepository;
import com.facturacion.ideas.api.repositories.ISenderRepository;
import com.facturacion.ideas.api.util.ConstanteUtil;

@Service
public class PersonServiceImpl implements IPersonService {

	private static final Logger LOGGER = LogManager.getLogger(PersonServiceImpl.class);

	@Autowired
	private ICustomerRepository customerRepository;

	@Autowired
	private ISenderRepository senderRepository;

	@Autowired
	private IDriverRepository driverRepository;

	@Autowired
	private IPersonMapper personMapper;

	@Autowired
	private IPersonRepository personRepository;

	@Autowired
	private IDetailsPersonRepository detailsPersonRepository;

	@Override
	@Transactional
	public CustomerResponseDTO save(CustomerNewDTO personNewDTO, Long idSender) {

		Sender sender = null;
		Customer customer = null;
		DetailsPerson detailsPerson = null;
		Customer customerSaved = null;

		try {

			// Recuperar emisor actual
			sender = senderRepository.findById(idSender).orElseThrow(() -> new NotFoundException(
					"Emisor con identitificacion : " + idSender + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

			/*
			 * 
			 * Si id !=null, indica que Persona ya esta registrada en el sistema, la
			 * recupero y seteo los valores que no se pueden modificar.
			 */
			if (personNewDTO.getIde() != null) {

				// Recuperar la persona actual
				customer = customerRepository.findById(personNewDTO.getIde()).get();

				// Verifica si la persona actual ya pertence al emisor actual
				if (existsByPersonAndSender(customer.getIde(), idSender)) {
					throw new DuplicatedResourceException("Cliente " + customer.getNumeroIdentificacion()
							+ " ya pertenece al emisor : " + sender.getSocialReason());
				}

				// setear valores que no se pueden modificar
				personNewDTO.setIde(customer.getIde());
				personNewDTO.setNumberIdentification(customer.getNumeroIdentificacion());
				// Convertir DTO a entidad
				customer = personMapper.mapperToEntity(personNewDTO);

				// Actualizada datos del customer
				customerSaved = customerRepository.save(customer);

				// Asociar persona a un emisor
				detailsPerson = new DetailsPerson();
				detailsPerson.setSender(sender);
				detailsPerson.setPerson(customer);

				// Persistir el DetailsPerson
				detailsPersonRepository.save(detailsPerson);

				return personMapper.mapperToDTO(customerSaved);
			}

			// Cedula que no esta registra en Persona, x si acaso valido
			if (!personRepository.existsByNumberIdentification(personNewDTO.getNumberIdentification())) {

				// Customer a persistir
				customer = personMapper.mapperToEntity(personNewDTO);

				// Asociar persona a un emisor
				detailsPerson = new DetailsPerson();
				detailsPerson.setSender(sender);
				detailsPerson.setPerson(customer);

				// Asociar persona con un emisor
				customer.addDetailsPerson(detailsPerson);

				// Persistir persona
				customerSaved = customerRepository.save(customer);
				return personMapper.mapperToDTO(customerSaved);

			}

			// La nueva cedula ya esta registrada
			throw new DuplicatedResourceException("Cedula " + personNewDTO.getNumberIdentification()
					+ ConstanteUtil.MESSAJE_DUPLICATED_RESOURCE_DEFAULT_EXCEPTION);

		} catch (DataAccessException e) {
			LOGGER.error("Error guardar cliente: ", e);
			throw new NotDataAccessException("Error guardar cliente: " + e.getMessage());
		}

	}

	@Override
	@Transactional
	public DriverResponseDTO save(DriverNewDTO driverNewDTO, Long idSender) {

		Sender sender = null;
		Driver driver = null;
		DetailsPerson detailsPerson = null;
		Driver driverSaved = null;

		try {

			// Recuperar emisor actual
			sender = senderRepository.findById(idSender).orElseThrow(() -> new NotFoundException(
					"Emisor con identitificacion : " + idSender + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

			/*
			 * 
			 * Si id !=null, indica que Persona ya esta registrada en el sistema, la
			 * recupero y seteo los valores que no se pueden modificar.
			 */
			if (driverNewDTO.getIde() != null) {

				// Recuperar la persona actual
				driver = driverRepository.findById(driverNewDTO.getIde()).get();

				// Verifica si la persona actual ya pertence al emisor actual
				if (existsByPersonAndSender(driver.getIde(), idSender)) {
					throw new DuplicatedResourceException("Transportista " + driver.getNumeroIdentificacion()
							+ " ya pertenece al emisor : " + sender.getSocialReason());
				}

				// setear valores que no se pueden modificar
				driverNewDTO.setIde(driver.getIde());
				driverNewDTO.setNumberIdentification(driver.getNumeroIdentificacion());
				// Convertir DTO a entidad
				driver = personMapper.mapperToEntity(driverNewDTO);

				// Actualizada datos del druver
				driverSaved = driverRepository.save(driver);

				// Asociar persona a un emisor
				detailsPerson = new DetailsPerson();
				detailsPerson.setSender(sender);
				detailsPerson.setPerson(driver);

				// Persistir el DetailsPerson
				detailsPersonRepository.save(detailsPerson);

				return personMapper.mapperToDTO(driverSaved);
			}

			// Cedula que no esta registra en Persona, x si acaso valido
			if (!personRepository.existsByNumberIdentification(driverNewDTO.getNumberIdentification())) {

				// Customer a persistir
				driver = personMapper.mapperToEntity(driverNewDTO);

				// Asociar persona a un emisor
				detailsPerson = new DetailsPerson();
				detailsPerson.setSender(sender);
				detailsPerson.setPerson(driver);

				// Asociar persona con un emisor
				driver.addDetailsPerson(detailsPerson);

				// Persistir persona
				driverSaved = driverRepository.save(driver);
				return personMapper.mapperToDTO(driverSaved);

			}

			// La nueva cedula ya esta registrada
			throw new DuplicatedResourceException("Cedula " + driverNewDTO.getNumberIdentification()
					+ ConstanteUtil.MESSAJE_DUPLICATED_RESOURCE_DEFAULT_EXCEPTION);

		} catch (DataAccessException e) {
			LOGGER.error("Error guardar transportista: ", e);
			throw new NotDataAccessException("Error guardar tranportista: " + e.getMessage());
		}

	}

	/**
	 * Verifica si una persona ya esta asociada con un emisor
	 */
	@Override
	@Transactional(readOnly = true)
	public boolean existsByPersonAndSender(Long idPerson, Long idSender) {
		return detailsPersonRepository.existsByPersonAndSenderNative(idPerson, idSender).isPresent();
	}

	@Override
	@Transactional
	public void deleteById(Long idDetailsPerson) {

		try {

			DetailsPerson detailsPerson = detailsPersonRepository.findById(idDetailsPerson)
					.orElseThrow(() -> new NotFoundException(
							"Detalle Persona " + idDetailsPerson + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

			detailsPersonRepository.deleteById(detailsPerson.getIde());
		} catch (DataAccessException e) {

			LOGGER.error("Error eliminar detailsPersona", e);
			throw new NotDataAccessException("Error al eliminar persona: " + e.getMessage());
		}

	}

	@Override
	@Transactional(readOnly = true)
	public List<CustomerResponseDTO> findAllCustomerBySender(Long idSender) {

		try {

			// Verificar si existe el emisor
			if (!senderRepository.existsById(idSender)) {

				throw new NotFoundException("Emisor: " + idSender + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION);
			}

			List<Customer> customers = customerRepository.findAllBySender(idSender);

			return personMapper.mapperToDTOCustomer(customers);

		} catch (DataAccessException e) {

			LOGGER.error("Error listar clientes", e);
			throw new NotDataAccessException("Error listar clientes: " + e.getMessage());

		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<DriverResponseDTO> findAllDriverBySender(Long idSender) {

		try {

			// Verificar si existe el emisor
			if (!senderRepository.existsById(idSender)) {

				throw new NotFoundException("Emisor: " + idSender + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION);
			}

			List<Driver> drivers = driverRepository.findAllBySender(idSender);

			return personMapper.mapperToDTODriver(drivers);

		} catch (DataAccessException e) {

			LOGGER.error("Error listar transportistas", e);
			throw new NotDataAccessException("Error listar transportistas: " + e.getMessage());

		}
	}

}
