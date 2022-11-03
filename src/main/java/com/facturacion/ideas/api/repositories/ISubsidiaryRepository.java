package com.facturacion.ideas.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facturacion.ideas.api.entities.Sender;
import com.facturacion.ideas.api.entities.Subsidiary;

public interface ISubsidiaryRepository extends JpaRepository<Subsidiary, Long> {

	// Optional<Subsidiary> findByIdeAndSender(Long ide, Sender sender);

	List<Subsidiary> findByCodeAndSender(String code, Sender sender);

	/*
	 * @Query("SELECT  sb.id, sb.code, sb.socialReason, sb.address, sb.status from Subsidiary sb "
	 * ) List<Subsidiary> findByCodeAndSenderPrivate(String code, Long ideSender);
	 */

	
	/**
	 * Busca los establecimiento  y punto emision de un emisor en particular
	 * @return
	 */
	
	@Query("select distinct s from Subsidiary s left join fetch s.emissionPoints e where s.sender.ide= :idSender")
	List<Subsidiary> fetchBySenderWithEmissionPoint(@Param("idSender") Long idSender);
	
	/**
	 * Obtiene un establecimiento de un emisor en particular
	 * @param ide
	 * @param ideSubs
	 * @return
	 */
	Optional<Subsidiary> findByIdeAndSenderIde(Long ide, Long ideSubs);

	@Query("select sub from Subsidiary sub left  join  fetch  sub.emissionPoints where  sub.sender.ruc = :ruc")
	List<Subsidiary> fetchSubsidiaryAndEmissionPointsByRuc(@Param("ruc") String ruc);

	Boolean existsByCodeAndSenderIde(String code, Long ide);

}
