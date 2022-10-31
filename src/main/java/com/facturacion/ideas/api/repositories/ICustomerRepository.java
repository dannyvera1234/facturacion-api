package com.facturacion.ideas.api.repositories;

import java.util.List;

import com.facturacion.ideas.api.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facturacion.ideas.api.entities.Customer;

public interface ICustomerRepository extends JpaRepository<Customer, Long>{

	
	/**
	 * Consulta los Customer de un emisor en particular
	 * @param idSender :Id del Emisor
	 * @return : Lista de Customer 
	 */
	@Query("SELECT cm FROM Customer cm join fetch cm.detailsPersons dt WHERE dt.sender.ide = :idSender")
	List<Customer> findAllBySender(@Param("idSender") Long idSender);

	@Query("select cu from  Customer  cu  join   fetch  cu.detailsPersons dtp where dtp.sender.ide= :param1 and (cu.numberIdentification like %:param2% or cu.socialReason like %:param2%   )")
	List<Customer> searchByCedulaOrRazonSocail(@Param("param1") Long idSender, @Param("param2") String filtro );
}
