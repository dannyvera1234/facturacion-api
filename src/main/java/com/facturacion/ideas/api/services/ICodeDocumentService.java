package com.facturacion.ideas.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.facturacion.ideas.api.controllers.SubsidiaryRestController;
import com.facturacion.ideas.api.entities.CodeDocument;
import com.facturacion.ideas.api.entities.Count;
import com.facturacion.ideas.api.entities.Subsidiary;

public interface ICodeDocumentService {
	
	CodeDocument save(CodeDocument codeDocument);
	
	Optional<CodeDocument> findById(Long id);
	
	List<CodeDocument> findAll();
	
	/**
	 * Busca el valor numerico maximo  del establecimiento de un emisor.
	 * @param idSender : codigo del emisor
	 * @return
	 */
	Optional<Integer> findNumberMaxByIdCount(Long idCount);
	
	/**
	 * Eliminar registro acorde al codgo de una cuenta
	 * @param idCount
	 */
	void deleteByIdCount(Long idCount);
	
	Optional<CodeDocument> findByIdSenderAndCodSubsidiary(Long idSender, String codSubsidiary);
	
	/**
	 * Elimina un registro en {@link CodeDocument} . Este metodo es llamado desde {@link SubsidiaryRestController} 
	 * cuando elimina un {@link Subsidiary}
	 * 
	 * @param idCount : Id de la {@link Count} a la que pertenece el CodeDocument
	 * @param codeSender : Codigo del {@link Subsidiary} a eliminar
	 */
	void deleteByIdCountAndCodeSubsidiary(Long idCount , 
			 String codeSender	);
	
}
