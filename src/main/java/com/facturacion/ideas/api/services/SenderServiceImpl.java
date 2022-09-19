package com.facturacion.ideas.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facturacion.ideas.api.entities.Count;
import com.facturacion.ideas.api.repositories.ICountRepository;

@Service
public class SenderServiceImpl implements ISenderService {

	@Autowired
	private ICountRepository countRepository;

	@Override
	@Transactional
	public Count saveCount(Count count) {

		return countRepository.save(count);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Count> findCountByRuc(String ruc) {

		Count count = countRepository.findByRuc(ruc);

		return Optional.ofNullable(count);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Count> findCountById(Long id) {
	
		return  countRepository.findById(id);
	}

	@Override
	@Transactional
	public void deleteCountById(Long id) {
	
		countRepository.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<Count> findCountAll() {
		
		return  countRepository.findAll();
	}

}
