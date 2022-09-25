package com.facturacion.ideas.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facturacion.ideas.api.entities.Sender;
import com.facturacion.ideas.api.entities.Subsidiary;
import com.facturacion.ideas.api.repositories.ISubsidiaryRepository;

@Service
public class SubsidiaryServiceImpl implements ISubsidiaryService {

	@Autowired
	private ISubsidiaryRepository subsidiaryRepository;

	@Override
	@Transactional
	public Subsidiary save(Subsidiary subsidiary) {

		return subsidiaryRepository.save(subsidiary);
	}

	@Override
	@Transactional
	public void deleteById(Long ide) {
		subsidiaryRepository.deleteById(ide);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Subsidiary> listAll() {

		return subsidiaryRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Subsidiary> findById(Long ide) {

		return subsidiaryRepository.findById(ide);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Subsidiary> findByIdAndSender(Long ide, Sender sender) {
	
		return  subsidiaryRepository.findByIdeAndSender(ide, sender);
	}

}
