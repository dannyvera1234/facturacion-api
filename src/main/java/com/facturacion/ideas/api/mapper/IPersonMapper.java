package com.facturacion.ideas.api.mapper;

import java.util.List;

import com.facturacion.ideas.api.dto.PersonNewDTO;
import com.facturacion.ideas.api.dto.PersonResponseDTO;
import com.facturacion.ideas.api.entities.Person;


public interface IPersonMapper {

	Person mapperToEntity(PersonNewDTO personNewDTO);

	PersonResponseDTO mapperToDTO(Person person);

	List<PersonResponseDTO> mapperToDTO(List<Person> persons);

}
