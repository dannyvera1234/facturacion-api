package com.facturacion.ideas.api.services;

import java.util.List;
import java.util.Optional;

import com.facturacion.ideas.api.admin.AdminPerson;
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
    public CustomerResponseDTO save(CustomerNewDTO personNewDTO) {

        Customer customer;
        DetailsPerson detailsPerson;

        try {

            // Recuperar emisor actual
            Long idSender = senderRepository.findIdByRuc(ConstanteUtil.TOKEN_USER).orElseThrow(
                    () -> new NotFoundException(String.format("Emisor %s %s", ConstanteUtil.TOKEN_USER, ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION))
            );


            // Verificar si el numeroIdentificacion ya esta registrada
            Optional<Customer> optionalCustomer = customerRepository.findByNumberIdentification(personNewDTO.getNumberIdentification());


            // SI existe el cliente en la BD,
            // pero ahora verifico si ya esta asociado con el emisor actual
            if (optionalCustomer.isPresent()) {

                if (detailsPersonRepository.existsByPersonIdeAndAndSenderIde(optionalCustomer.get().getIde(), idSender)) {
                    throw new DuplicatedResourceException(String.format("Cliente %s %s", personNewDTO.getNumberIdentification(), ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));
                } else {
                    // Ingreso una nueva relacion, del mismo cliente pero con otro emisor
                    detailsPerson = new DetailsPerson(idSender, optionalCustomer.get().getIde());
                    detailsPersonRepository.save(detailsPerson);
                    return personMapper.mapperToDTO(optionalCustomer.get());

                    /*// Asociar el cliente encontrado con un nuevo emisor
                    personNewDTO.setIde(optionalCustomer.get());
                    customer = personMapper.mapperToEntity(personNewDTO);
                    //
                    customer.addDetailsPerson(new DetailsPerson(idSender, personNewDTO.getIde()));
                    customerSaved = customerRepository.save(customer);

                    return personMapper.mapperToDTO(customerSaved);*/

                }
            }

            // Si el cliente no esta registrado en la bd
            // Customer a persistir
            customer = personMapper.mapperToEntity(personNewDTO);

            Customer customerSaved= customerRepository.save(customer);

            detailsPerson = new DetailsPerson(idSender, customerSaved.getIde());
            detailsPersonRepository.save(detailsPerson);

            return personMapper.mapperToDTO(customerSaved);










            /*
             *
             * Si id !=null, indica que Persona ya esta registrada en el sistema, la
             * recupero y seteo los valores que no se pueden modificar.
             */


            /*if (personNewDTO.getIde() != null) {

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
            }*/


            /*
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
            throw new DuplicatedResourceException(TypeIdentificationEnum.getTipoCompradorEnum(personNewDTO.getTypeIdentification()) + " " + personNewDTO.getNumberIdentification()
                    + ConstanteUtil.MESSAJE_DUPLICATED_RESOURCE_DEFAULT_EXCEPTION);


             */
        } catch (DataAccessException e) {
            LOGGER.error("Error guardar cliente: ", e);
            throw new NotDataAccessException("Error guardar cliente: " + e.getMessage());
        }

    }

    @Override
    @Transactional
    public CustomerResponseDTO update(CustomerNewDTO customerUpdateDTO, Long idCustomer) {


        try {

            Customer customer = customerRepository.findById(idCustomer)
                    .orElseThrow(() -> new NotFoundException(String.format("Cliente %s %s", idCustomer, ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION)));
            AdminPerson.preUpdate(customer, customerUpdateDTO);



            return personMapper.mapperToDTO(customerRepository.save(customer));

        } catch (DataAccessException e) {
            LOGGER.error("Error al actualizar cliente " + customerUpdateDTO.getIde(), e);
            throw new NotDataAccessException(String.format("No se pudo actualizar el cliente %s", customerUpdateDTO.getIde()));
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
    public void deleteById( Long idPerson) {

        // Recuperar emisor actual
        Long idSender = senderRepository.findIdByRuc(ConstanteUtil.TOKEN_USER).orElseThrow(
                () -> new NotFoundException(String.format("Emisor %s %s", ConstanteUtil.TOKEN_USER, ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION))
        );

        try {
            // Verificar si existe el emisor
            if (!senderRepository.existsById(idSender)) {
                throw new NotFoundException(
                        "Emisor id " + idSender + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION);
            }

            // Retorna el numero de registros eliminados, si es 0, indica que la Persona
            // no esta asociado con un Emisor en DetallePersona
            if (detailsPersonRepository.deleteBySenderAndPerson(idSender, idPerson) == 0) {
                throw new NotFoundException(
                        "Persona id " + idPerson + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION);
            }
        } catch (DataAccessException e) {

            LOGGER.error("Error eliminar persona", e);
            throw new NotDataAccessException("Error al eliminar persona: " + e.getMessage());
        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<DriverResponseDTO> searchDriverByCedulaOrRazonSocial(Long idSender, String filtro) {
        filtro = "%" + filtro + "%";

        try {
            List<Driver> persons = driverRepository.searchByCedulaOrRazonSocail(idSender, filtro);
            return personMapper.mapperToDTODriver(persons);
        } catch (DataAccessException e) {
            LOGGER.error("Error al filtrar los Transportistas", e);
            throw new NotFoundException("Error al filtrar los Transportistas " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponseDTO> searchCustomerByCedulaOrRazonSocial(String filtro) {

        filtro = "%" + filtro + "%";
        try {
            // Recuperar emisor actual
            Long idSender = senderRepository.findIdByRuc(ConstanteUtil.TOKEN_USER).orElseThrow(
                    () -> new NotFoundException(String.format("Emisor %s %s", ConstanteUtil.TOKEN_USER, ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION))
            );

            List<Customer> persons = customerRepository.searchByCedulaOrRazonSocail(idSender, filtro);
            return personMapper.mapperToDTOCustomer(persons);
        } catch (DataAccessException e) {
            LOGGER.error("Error al filtrar los Clientes ", e);
            throw new NotFoundException("Error al filtrar los Clientes " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponseDTO findById(Long idCustomer) {

        return personMapper.mapperToDTO(customerRepository.findById(idCustomer).orElseThrow(
                () -> new NotFoundException("Cliente identificacíon " + idCustomer + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponseDTO> findAllCustomerBySender() {

        try {


            // Recuperar emisor actual
            Long idSender = senderRepository.findIdByRuc(ConstanteUtil.TOKEN_USER).orElseThrow(
                    () -> new NotFoundException(String.format("Emisor %s %s", ConstanteUtil.TOKEN_USER, ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION))
            );

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
