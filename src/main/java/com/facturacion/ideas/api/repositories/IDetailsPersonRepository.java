package com.facturacion.ideas.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
	
	// Eliminar una persona/cliente  o driver de un emisor en particular
	@Modifying
	@Query("DELETE FROM DetailsPerson dt WHERE dt.sender.ide= :idSender AND dt.person.ide = :idPerson")
	int deleteBySenderAndPerson(@Param("idSender")Long idSender,@Param("idPerson")Long idPerson);
	
}
