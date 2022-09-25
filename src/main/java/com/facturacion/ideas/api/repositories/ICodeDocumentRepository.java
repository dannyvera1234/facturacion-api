package com.facturacion.ideas.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facturacion.ideas.api.controllers.CountRestController;
import com.facturacion.ideas.api.entities.CodeDocument;
import com.facturacion.ideas.api.entities.Count;
import com.facturacion.ideas.api.entities.Subsidiary;
import com.facturacion.ideas.api.services.CodeDocumentServiceImpl;

public interface ICodeDocumentRepository extends JpaRepository<CodeDocument, Long> {

	@Query("SELECT MAX(c.numSubsidiary) FROM CodeDocument c  where c.codeCount = :codeCount GROUP BY c.codeCount")
	Optional<Integer> findNumberMaxSender(@Param("codeCount") Long codeCount);
	
	/**
	 * Consulta  un registro en {@link CodeDocument}
	 * @param codeCount : Id de la cuenta 
	 * @param codeSubsidiary : Codigo del establecimiento
	 * @return
	 */
	Optional<CodeDocument> findByCodeCountAndCodeSubsidiary (Long codeCount, String codeSubsidiary);
	
	@Query("SELECT MAX(c.numEmissionPoint) FROM CodeDocument c  where c.codeCount = :codeCount GROUP BY c.codeCount")
	Optional<Integer> findByNumberMaxEmission(@Param("codeCount") Long codeCount);


	/**
	 * Elimina todos los registros asociados a una cuenta. Este metodo es llamado desde {@link CountRestController} despues
	 * de eliminar una {@link Count}
	 * @param codeCount : Id de la Count
	 */
	@Modifying
	@Query("DELETE FROM CodeDocument c WHERE c.codeCount = :codeCount")
	void deleteByCodeCount( @Param("codeCount") Long codeCount);
	
	
	/**
	 * Elimina un registro en {@link CodeDocument} . Este metodo es llamado desde {@link CodeDocumentServiceImpl} 
	 * cuando elimina un {@link Subsidiary}
	 * 
	 * @param idCount : Id de la {@link Count} a la que pertenece el CodeDocument
	 * @param codeSender : Codigo del {@link Subsidiary} a eliminar
	 */
	@Modifying
	@Query("DELETE FROM CodeDocument c WHERE c.codeCount = :idCount and c.codeSubsidiary = :codeSender")
	void  deleteByIdCountAndCodeSubsidiary( @Param(value = "idCount") Long idCount , 
			@Param("codeSender") String codeSender);

}
