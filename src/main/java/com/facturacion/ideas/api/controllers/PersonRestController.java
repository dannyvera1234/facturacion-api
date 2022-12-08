package com.facturacion.ideas.api.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.facturacion.ideas.api.dto.CustomerNewDTO;
import com.facturacion.ideas.api.dto.CustomerResponseDTO;
import com.facturacion.ideas.api.dto.DriverNewDTO;
import com.facturacion.ideas.api.dto.DriverResponseDTO;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.services.IPersonService;
import com.facturacion.ideas.api.util.ConstanteUtil;

import javax.validation.Valid;

@CrossOrigin(origins = ConstanteUtil.CROOS_ORIGIN)
@RestController
@RequestMapping("/facturacion")
public class PersonRestController {

    private static final Logger LOGGER = LogManager.getLogger(PersonRestController.class);
    @Autowired
    private IPersonService personService;

    @PostMapping("/customers")
    public ResponseEntity<CustomerResponseDTO> saveCustomer(@RequestBody @Valid CustomerNewDTO customerNewDTO) {

        try {

            CustomerResponseDTO customerResponseDTO = personService.save(customerNewDTO);

            return new ResponseEntity<>(customerResponseDTO, HttpStatus.CREATED);
        } catch (NotDataAccessException e) {

            throw new NotDataAccessException(e.getMessage());
        }

    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@RequestBody @Valid CustomerNewDTO customerNewDTO,
                                                              @PathVariable  Long id) {
        LOGGER.info("Customer update: " + customerNewDTO);
        try {

            CustomerResponseDTO customerResponseDTO = personService.update(customerNewDTO, id);

            return new ResponseEntity<>(customerResponseDTO, HttpStatus.CREATED);
        } catch (NotDataAccessException e) {
            throw new NotDataAccessException(e.getMessage());
        }

    }


    @GetMapping("/customers")
    public ResponseEntity<List<CustomerResponseDTO>> findAllCustomers() {

        try {

            List<CustomerResponseDTO> customerResponseDTOs = personService.findAllCustomerBySender();

            return ResponseEntity.ok(customerResponseDTOs);
        } catch (NotDataAccessException e) {

            throw new NotDataAccessException(e.getMessage());
        }

    }

    /**
     * El id del emisor no lo tomo en cuenta
     *
     * @param id
     * @return
     */
    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerResponseDTO> findCustomerById(@PathVariable Long id) {

        try {

            LOGGER.info("Cliente buscar : " + id);
            CustomerResponseDTO customerResponseDTO = personService.findById(id);

            return ResponseEntity.ok(customerResponseDTO);
        } catch (NotDataAccessException e) {

            throw new NotDataAccessException(e.getMessage());
        }

    }

    @PostMapping("/senders/{id}/drivers")
    public ResponseEntity<DriverResponseDTO> saveDriver(@RequestBody @Valid DriverNewDTO driverNewDTO,
                                                        @PathVariable(name = "id") Long idSender) {

        try {

            DriverResponseDTO driverResponseDTO = personService.save(driverNewDTO, idSender);

            return new ResponseEntity<>(driverResponseDTO, HttpStatus.CREATED);
        } catch (NotDataAccessException e) {

            throw new NotDataAccessException(e.getMessage());
        }

    }

    @GetMapping("/senders/{id}/drivers")
    public ResponseEntity<List<DriverResponseDTO>> findAllDrivers(@PathVariable(name = "id") Long idSender) {

        try {

            List<DriverResponseDTO> customerResponseDTOs = personService.findAllDriverBySender(idSender);

            return ResponseEntity.ok(customerResponseDTOs);
        } catch (NotDataAccessException e) {

            throw new NotDataAccessException(e.getMessage());
        }

    }


    /**
     * En si, solo se elimina el registro de la relaacion emisor/personas en
     * DetailsPerson, pero la persona sigue registrada en el sistema, solo que ahora
     * ya no esta realacion con un emisor
     *
     * @param id : El id del Customer
     * @return
     */
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<String> deleteCustomersById(
            @PathVariable Long id) {

        try {
            personService.deleteById(id);

            return ResponseEntity.noContent().build();

        } catch (NotDataAccessException e) {

            throw new NotDataAccessException(e.getMessage());
        }

    }

    /**
     * En si, solo se elimina el registro de la relaacion emisor/persona en
     * DetailsPerson, pero la persona sigue registrada en el sistema, solo que ahora
     * ya no esta realacion con un emisor
     *
     * @param id : id del Transportistas
     * @return
     */
    @DeleteMapping("/drivers/{id}")
    public ResponseEntity<String> deleteDriversById(
            @PathVariable Long id) {

        try {
            personService.deleteById(id);
            return ResponseEntity.noContent().build();

        } catch (NotDataAccessException e) {

            throw new NotDataAccessException(e.getMessage());
        }
    }

    @GetMapping("/customers/search")
    public ResponseEntity<List<CustomerResponseDTO>> searchCustomerByCedulaOrRazonSocial(
            @RequestParam(name = "filtro", required = false, defaultValue = "") String filtro
    ) {
        try {
            List<CustomerResponseDTO> persons = personService.searchCustomerByCedulaOrRazonSocial(filtro);
            return ResponseEntity.ok(persons);

        } catch (NotDataAccessException e) {

            throw new NotDataAccessException(e.getMessage());
        }
    }

    @GetMapping("/senders/{id}/drivers/search")
    public ResponseEntity<List<DriverResponseDTO>> searchDriverByCedulaOrRazonSocial(
            @PathVariable(name = "id") Long idSender,
            @RequestParam(name = "filtro", required = false, defaultValue = "") String filtro
    ) {
        try {
            List<DriverResponseDTO> persons = personService.searchDriverByCedulaOrRazonSocial(idSender, filtro);
            return ResponseEntity.ok(persons);

        } catch (NotDataAccessException e) {

            throw new NotDataAccessException(e.getMessage());
        }
    }

}
