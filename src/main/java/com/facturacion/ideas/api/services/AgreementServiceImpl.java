package com.facturacion.ideas.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facturacion.ideas.api.entities.Agreement;
import com.facturacion.ideas.api.repositories.IAgreementRepository;

@Service
public class AgreementServiceImpl implements IAgreementService {

	@Autowired
	private IAgreementRepository agreementRepository;

	@Transactional
	@Override
	public Agreement save(Agreement agreement) {

		return agreementRepository.save(agreement);
	}

	@Transactional
	@Override
	public void deleteById(String codigo) {

		agreementRepository.deleteById(codigo);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Agreement> listAll() {

		return agreementRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Agreement> findById(String codigo) {

		return agreementRepository.findById(codigo);
	}

}
