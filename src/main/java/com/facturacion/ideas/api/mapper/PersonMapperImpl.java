package com.facturacion.ideas.api.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.facturacion.ideas.api.dto.CustomerNewDTO;
import com.facturacion.ideas.api.dto.DriverNewDTO;
import com.facturacion.ideas.api.dto.PersonNewDTO;
import com.facturacion.ideas.api.dto.PersonResponseDTO;
import com.facturacion.ideas.api.entities.Customer;
import com.facturacion.ideas.api.entities.Driver;
import com.facturacion.ideas.api.entities.Person;
import com.facturacion.ideas.api.enums.TypeCustomerEnum;
import com.facturacion.ideas.api.enums.TypeIdentificationEnum;

@Component
public class PersonMapperImpl implements IPersonMapper {

	@Override
	public Person mapperToEntity(PersonNewDTO personNewDTO) {

		Person person = new Person();

		person.setIde(personNewDTO.getIde());
		person.setTipoIdentificacion(
				TypeIdentificationEnum.getTipoCompradorEnum(personNewDTO.getNumberIdentification()));
		person.setEmail(personNewDTO.getEmail());

		person.setNumeroIdentificacion(personNewDTO.getNumberIdentification());
		person.setRazonSocial(personNewDTO.getSocialReason());

		if (personNewDTO instanceof CustomerNewDTO) {

			CustomerNewDTO customerNewDTO = (CustomerNewDTO) personNewDTO;

			Customer customer = (Customer) person;
			customer.setAddress(customerNewDTO.getAddress());
			customer.setCellPhone(customerNewDTO.getCellPhone());
			customer.setTlfConvencional(customerNewDTO.getTlfConvencional());
			customer.setExtTlfConvencional(customerNewDTO.getExtTlfConvencional());
			customer.setTypeCustomer(TypeCustomerEnum.getTypeCustomerEnum(customerNewDTO.getTypeCustomer()));

			person = customer;

		} else if (personNewDTO instanceof DriverNewDTO) {

			DriverNewDTO driverNewDTO = (DriverNewDTO) personNewDTO;

			Driver driver = (Driver) person;

			driver.setPlaca(driverNewDTO.getPlaca());
			person = driver;
		}

		return person;
	}

	@Override
	public PersonResponseDTO mapperToDTO(Person person) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PersonResponseDTO> mapperToDTO(List<Person> persons) {
		// TODO Auto-generated method stub
		return null;
	}

}
