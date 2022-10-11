package com.facturacion.ideas.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facturacion.ideas.api.entities.DetailsPerson;
import com.facturacion.ideas.api.entities.Person;
import com.facturacion.ideas.api.entities.Sender;

public interface IDetailsPersonRepository extends JpaRepository<DetailsPerson, Long> {

	boolean existsByPersonAndSender(Person person, Sender sender);

	// Si existe devuelve el valor entero 1
	@Query(value = "SELECT  true FROM DETALLE_PERSONAS WHERE DEP_FK_COD_EMI = :idSender AND DEP_FK_COD_PER = :idPerson", nativeQuery = true)
	Optional<Boolean> existsByPersonAndSenderNative(@Param("idPerson") Long idPerson, @Param("idSender") Long idSender);
	
}
