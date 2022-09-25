package com.facturacion.ideas.api.services;

import java.util.List;
import java.util.Optional;

import com.facturacion.ideas.api.entities.Sender;
import com.facturacion.ideas.api.entities.Subsidiary;

public interface ISubsidiaryService {

	Subsidiary save(Subsidiary subsidiary);

	void deleteById(Long ide);

	List<Subsidiary> listAll();

	Optional<Subsidiary> findById(Long ide);
	
	Optional<Subsidiary> findByIdAndSender(Long ide, Sender sender);

}
