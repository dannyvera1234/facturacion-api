package com.facturacion.ideas.api.services;

import java.util.List;

import com.facturacion.ideas.api.dto.CustomerNewDTO;
import com.facturacion.ideas.api.dto.CustomerResponseDTO;
import com.facturacion.ideas.api.dto.DriverNewDTO;
import com.facturacion.ideas.api.dto.DriverResponseDTO;

public interface IPersonService {

	CustomerResponseDTO save(CustomerNewDTO customerNewDTO, Long idSender);

	DriverResponseDTO save(DriverNewDTO driverNewDTO, Long idSender);

	List<CustomerResponseDTO> findAllCustomerBySender(Long idSender);

	List<DriverResponseDTO> findAllDriverBySender(Long idSender);

	boolean existsByPersonAndSender(Long idPerson, Long IdSender);

	void deleteById(Long idSender, Long idPerson);

}
