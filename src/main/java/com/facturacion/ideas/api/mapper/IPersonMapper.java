package com.facturacion.ideas.api.mapper;

import java.util.List;

import com.facturacion.ideas.api.dto.CustomerNewDTO;
import com.facturacion.ideas.api.dto.CustomerResponseDTO;
import com.facturacion.ideas.api.dto.DriverNewDTO;
import com.facturacion.ideas.api.dto.DriverResponseDTO;
import com.facturacion.ideas.api.entities.Customer;
import com.facturacion.ideas.api.entities.Driver;


public interface IPersonMapper {

	Customer mapperToEntity(CustomerNewDTO customerNewDTO);
	
	Driver mapperToEntity(DriverNewDTO driverNewDTO);

	CustomerResponseDTO mapperToDTO(Customer person);
	
	DriverResponseDTO mapperToDTO(Driver person);

	List<CustomerResponseDTO> mapperToDTOCustomer(List<Customer> customers);
	
	List<DriverResponseDTO> mapperToDTODriver(List<Driver> drivers);

}
