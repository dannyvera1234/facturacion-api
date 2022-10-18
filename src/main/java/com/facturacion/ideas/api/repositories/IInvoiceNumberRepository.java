package com.facturacion.ideas.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facturacion.ideas.api.entities.InvoiceNumber;

public interface IInvoiceNumberRepository extends JpaRepository<InvoiceNumber, Long> {

	/**
	 * Seleccionar el numero maximo actual de las facturas correspondiente a un establecimiento 
	 * @param idSubsidiary : id Establecimiento
	 * @return : numero actual factura
	 */
	@Query("SELECT MAX(nb.currentSequentialNumber) FROM  InvoiceNumber nb "
			+ " WHERE nb.subsidiary.ide = :idSubsidiary")
	Optional<Integer> findMaxCurrentSequentialNumberBySubsidiary(@Param("idSubsidiary") Long idSubsidiary);

}
