package com.facturacion.ideas.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facturacion.ideas.api.entities.Sender;
import com.facturacion.ideas.api.entities.Subsidiary;

public interface ISubsidiaryRepository extends JpaRepository<Subsidiary, Long> {

	// Optional<Subsidiary> findByIdeAndSender(Long ide, Sender sender);

	List<Subsidiary> findByCodeAndSender(String code, Sender sender);

	/*
	 * @Query("SELECT  sb.id, sb.code, sb.socialReason, sb.address, sb.status from Subsidiary sb "
	 * ) List<Subsidiary> findByCodeAndSenderPrivate(String code, Long ideSender);
	 */

}
