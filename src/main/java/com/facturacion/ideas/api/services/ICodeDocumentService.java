package com.facturacion.ideas.api.services;

import java.util.List;
import java.util.Optional;

import com.facturacion.ideas.api.entities.CodeDocument;

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
	

}
