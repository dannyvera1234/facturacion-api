package com.facturacion.ideas.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facturacion.ideas.api.entities.InvoiceNumber;
import com.facturacion.ideas.api.entities.Person;
import com.facturacion.ideas.api.entities.Subsidiary;

public interface IInvoiceNumberRepository extends JpaRepository<InvoiceNumber, Long> {

	/**
	 * Seleccionar el numero maximo actual de un tipo documento especifico correspondiente a un establecimiento 
	 * @param idSubsidiary : id Establecimiento
	 * @param codDocument : codigo del tipo de documento
	 * @return : numero actual factura
	 */
	@Query("SELECT MAX(nb.currentSequentialNumber) FROM  InvoiceNumber nb "
			+ " WHERE  nb.typeDocument = :codDocument AND nb.subsidiary.ide = :idSubsidiary")
	Optional<Integer> findMaxCurrentSequentialNumberBySubsidiary(@Param("codDocument") String codDocument, @Param("idSubsidiary") Long idSubsidiary);
	
	/**
	 * Consulta un registo
	 * @param typeDocument : el tipo de documento, puede ser factura, guia remison, etc
	 * @param subsidiary : establecimiento al que se esta asignar el documento
	 * @return
	 */
	Optional<InvoiceNumber> findByTypeDocumentAndSubsidiary(String typeDocument,  Subsidiary subsidiary);

}
