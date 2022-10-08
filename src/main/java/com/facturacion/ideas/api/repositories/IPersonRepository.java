package com.facturacion.ideas.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facturacion.ideas.api.entities.Person;

public interface IPersonRepository extends JpaRepository<Person, Long> {
	
	boolean existsByNumberIdentification(String numberIdentification);
	
	Optional<Person> findByNumberIdentification(String numberIdentification);
}
