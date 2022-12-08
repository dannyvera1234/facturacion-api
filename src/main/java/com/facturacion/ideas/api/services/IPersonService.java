package com.facturacion.ideas.api.services;

import java.util.List;

import com.facturacion.ideas.api.dto.CustomerNewDTO;
import com.facturacion.ideas.api.dto.CustomerResponseDTO;
import com.facturacion.ideas.api.dto.DriverNewDTO;
import com.facturacion.ideas.api.dto.DriverResponseDTO;

public interface IPersonService {

	CustomerResponseDTO save(CustomerNewDTO customerNewDTO);

	CustomerResponseDTO update(CustomerNewDTO customerUpdateDTO, Long idCustomer);

	DriverResponseDTO save(DriverNewDTO driverNewDTO, Long idSender);

	List<CustomerResponseDTO> findAllCustomerBySender();

	List<DriverResponseDTO> findAllDriverBySender(Long idSender);

	boolean existsByPersonAndSender(Long idPerson, Long IdSender);

	void deleteById(Long idPerson);

	List<DriverResponseDTO> searchDriverByCedulaOrRazonSocial(Long idSender, String filtro);

	List<CustomerResponseDTO> searchCustomerByCedulaOrRazonSocial(String filtro);

	CustomerResponseDTO findById(Long idCustomer);
}
