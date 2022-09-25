package com.facturacion.ideas.api.services;

import java.util.List;
import java.util.Optional;

import com.facturacion.ideas.api.entities.EmissionPoint;

public interface IEmissionPointService {

	EmissionPoint save(EmissionPoint emissionPoint);

	void deleteById(Long id);

	List<EmissionPoint> listAll();
	
	Optional<EmissionPoint> findById(Long ide);
}
