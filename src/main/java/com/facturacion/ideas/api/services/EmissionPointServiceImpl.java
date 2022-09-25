package com.facturacion.ideas.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facturacion.ideas.api.entities.EmissionPoint;
import com.facturacion.ideas.api.repositories.IEmissionPointRepository;

@Service
public class EmissionPointServiceImpl implements IEmissionPointService {

	@Autowired
	private IEmissionPointRepository emissionPointRepository;

	@Override
	@Transactional
	public EmissionPoint save(EmissionPoint emissionPoint) {

		return emissionPointRepository.save(emissionPoint);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		emissionPointRepository.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true)
	public List<EmissionPoint> listAll() {
		// TODO Auto-generated method stub
		return emissionPointRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<EmissionPoint> findById(Long ide) {

		return emissionPointRepository.findById(ide);
	}

}
