package com.facturacion.ideas.api.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.facturacion.ideas.api.dto.CustomerNewDTO;
import com.facturacion.ideas.api.dto.CustomerResponseDTO;
import com.facturacion.ideas.api.dto.DriverNewDTO;
import com.facturacion.ideas.api.dto.DriverResponseDTO;
import com.facturacion.ideas.api.dto.PersonResponseDTO;
import com.facturacion.ideas.api.entities.Customer;
import com.facturacion.ideas.api.entities.Driver;
import com.facturacion.ideas.api.entities.Person;
import com.facturacion.ideas.api.enums.TypeCustomerEnum;
import com.facturacion.ideas.api.enums.TypeIdentificationEnum;

@Component
public class PersonMapperImpl implements IPersonMapper {

	@Override
	public Customer mapperToEntity(CustomerNewDTO personNewDTO) {

		Customer person = new Customer();

		person.setIde(personNewDTO.getIde());
		person.setTipoIdentificacion(
				TypeIdentificationEnum.getTipoCompradorEnum(personNewDTO.getNumberIdentification()));
		person.setEmail(personNewDTO.getEmail());
		person.setNumeroIdentificacion(personNewDTO.getNumberIdentification());
		person.setRazonSocial(personNewDTO.getSocialReason());
		person.setAddress(personNewDTO.getAddress());

		// Customer
		person.setCellPhone(personNewDTO.getCellPhone());
		person.setTlfConvencional(personNewDTO.getTlfConvencional());
		person.setExtTlfConvencional(personNewDTO.getExtTlfConvencional());
		person.setTypeCustomer(TypeCustomerEnum.getTypeCustomerEnum(personNewDTO.getTypeCustomer()));

		return person;
	}

	@Override
	public CustomerResponseDTO mapperToDTO(Customer person) {

		CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();

		customerResponseDTO.setIde(person.getIde());
		customerResponseDTO.setEmail(person.getEmail());
		customerResponseDTO.setNumberIdentification(person.getNumeroIdentificacion());
		customerResponseDTO.setSocialReason(person.getRazonSocial());
		customerResponseDTO.setAddress(person.getAddress());
		customerResponseDTO.setCellPhone(person.getCellPhone());
		customerResponseDTO.setTlfConvencional(person.getTlfConvencional());
		return customerResponseDTO;
	}

	@Override
	public Driver mapperToEntity(DriverNewDTO personNewDTO) {

		Driver person = new Driver();

		person.setIde(personNewDTO.getIde());
		person.setTipoIdentificacion(
				TypeIdentificationEnum.getTipoCompradorEnum(personNewDTO.getNumberIdentification()));
		person.setEmail(personNewDTO.getEmail());
		person.setNumeroIdentificacion(personNewDTO.getNumberIdentification());
		person.setRazonSocial(personNewDTO.getSocialReason());
		person.setAddress(personNewDTO.getAddress());

		// Driver
		person.setPlaca(personNewDTO.getPlaca());

		return person;

	}

	@Override
	public DriverResponseDTO mapperToDTO(Driver person) {

		DriverResponseDTO driverResponseDTO = new DriverResponseDTO();

		driverResponseDTO.setIde(person.getIde());
		driverResponseDTO.setEmail(person.getEmail());
		driverResponseDTO.setNumberIdentification(person.getNumeroIdentificacion());
		driverResponseDTO.setSocialReason(person.getRazonSocial());
		driverResponseDTO.setAddress(person.getAddress());
		driverResponseDTO.setPlaca(person.getPlaca());
		return driverResponseDTO;
	}

	@Override
	public List<CustomerResponseDTO> mapperToDTOCustomer(List<Customer> customers) {

		List<CustomerResponseDTO> customerResponseDTOs = new ArrayList<>();

		if (customers.size() > 0) {
			customerResponseDTOs = customers.stream().map(item -> mapperToDTO(item)).collect(Collectors.toList());
		}

		return customerResponseDTOs;

	}

	@Override
	public List<DriverResponseDTO> mapperToDTODriver(List<Driver> drivers) {

		List<DriverResponseDTO> driverResponseDTOs = new ArrayList<>();

		if (drivers.size() > 0) {
			driverResponseDTOs = drivers.stream().map(item -> mapperToDTO(item)).collect(Collectors.toList());
		}

		return driverResponseDTOs;
	}

}
