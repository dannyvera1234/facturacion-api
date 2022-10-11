package com.facturacion.ideas.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.facturacion.ideas.api.entities.Driver;

public interface IDriverRepository extends JpaRepository<Driver, Long> {

	
	/**
	 * Consulta los Driver de un emisor en particular
	 * @param idSender :Id del Emisor
	 * @return : Lista de Driver 
	 */
	@Query("SELECT dr FROM Driver dr join fetch dr.detailsPersons dt WHERE dt.sender.ide = :idSender")
	List<Driver> findAllBySender(@Param("idSender") Long idSender);
}
