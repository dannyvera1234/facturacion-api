package com.facturacion.ideas.api.services;

import java.util.List;
import java.util.Optional;

import com.facturacion.ideas.api.entities.Agreement;

public interface IAgreementService {

	Agreement save(Agreement agreement);
	
	void deleteById(String codigo);
	
	List<Agreement> listAll();
	
	Optional<Agreement> findById(String codigo);
}
