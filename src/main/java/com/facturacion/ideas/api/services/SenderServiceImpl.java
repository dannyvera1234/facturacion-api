package com.facturacion.ideas.api.services;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.facturacion.ideas.api.dto.SenderNewDTO;
import com.facturacion.ideas.api.dto.SenderResponseDTO;


@Component
public class SenderServiceImpl implements ISenderService {

	@Override
	public SenderResponseDTO save(SenderNewDTO senderNewDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SenderResponseDTO findByRuc(String ruc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SenderResponseDTO findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Boolean> senderIsExiste(String ruc) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	
	

}
