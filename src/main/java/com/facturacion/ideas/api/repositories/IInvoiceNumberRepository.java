package com.facturacion.ideas.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facturacion.ideas.api.entities.InvoiceNumber;

public interface IInvoiceNumberRepository extends JpaRepository<InvoiceNumber, Long> {

	/**
	 * Seleccionar el numero maximo actual de un tipo documento especifico correspondiente a un punto emision
	 * @param idEmissionPoint : id Establecimiento
	 * @param codDocument : codigo del tipo de documento
	 * @return : numero actual factura
	 */
	@Query("SELECT MAX(nb.currentSequentialNumber) FROM  InvoiceNumber nb "
			+ " WHERE  nb.typeDocument = :codDocument AND nb.emissionPoint.ide = :idEmissionPoint")
	Optional<Integer> findMaxCurrentSequentialNumberByEmissionPoint(@Param("codDocument") String codDocument, @Param("idEmissionPoint") Long idEmissionPoint);
	
	/**
	 * Consulta un registo
	 * @param typeDocument : el tipo de documento, puede ser factura, guia remison, etc
	 * @param ideEmissionPoint : establecimiento al que se esta asignar el documento
	 * @return
	 */
	Optional<InvoiceNumber> findByTypeDocumentAndEmissionPointIde(String typeDocument,  Long ideEmissionPoint);

}
