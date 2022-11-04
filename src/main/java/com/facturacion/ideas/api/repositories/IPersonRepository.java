package com.facturacion.ideas.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facturacion.ideas.api.entities.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IPersonRepository extends JpaRepository<Person, Long> {
	
	boolean existsByNumberIdentification(String numberIdentification);
	
	Optional<Person> findByNumberIdentification(String numberIdentification);

	@Query("select pe.ide from Person pe where pe.ide = :idPerson")
	Optional<Long>  selectIdeByIde( @Param("idPerson") Long ide);
}
