package com.facturacion.ideas.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facturacion.ideas.api.entities.Count;
import com.facturacion.ideas.api.entities.Login;
import com.facturacion.ideas.api.entities.Sender;
import com.facturacion.ideas.api.repositories.ICountRepository;
import com.facturacion.ideas.api.repositories.ILoginRepository;
import com.facturacion.ideas.api.repositories.ISenderRepository;

@Service
public class SenderServiceImpl implements ISenderService {

	@Autowired
	private ICountRepository countRepository;
	
	@Autowired
	private ILoginRepository loginRepository;

	
	@Autowired
	private ISenderRepository senderRepository;
	
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

	@Override
	@Transactional
	public Login saveLoginIn(Login login) {
	
		return loginRepository.save(login);
	}

	@Override
	@Transactional
	public Count updateCount(Count count) {
		return countRepository.save(count);
	}

	@Override
	@Transactional
	public Sender saveSender(Sender sender) {
		
		return senderRepository.save(sender);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Sender> findSenderByRuc(String ruc) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Sender> findSenderById(Long id) {
		
		return  senderRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Sender> findSenderAll() {
		
		return senderRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Boolean> senderIsExiste(String ruc) {
		
		return  senderRepository.senderIsExist(ruc);
	}

}
