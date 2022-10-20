package com.facturacion.ideas.api.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facturacion.ideas.api.dto.TaxValueResponseDTO;
import com.facturacion.ideas.api.entities.TaxValue;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.mapper.ITaxValueMapper;
import com.facturacion.ideas.api.repositories.ITaxValueRepository;

@Service
public class TaxValueServiceImpl implements ITaxValueService {

	private static final Logger LOGGER = LogManager.getLogger(TaxValueServiceImpl.class);

	@Autowired
	private ITaxValueRepository taxValueRepository;

	@Autowired
	private ITaxValueMapper taxValueMapper;

	private SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	@Transactional(readOnly = true)
	public List<TaxValueResponseDTO> findAllIVA() {

		Calendar calendar = Calendar.getInstance();
		

		String fechaDeEmision = formateador.format(calendar.getTime());

		try {

			List<TaxValue> taxValues = taxValueRepository.findAllIVA(fechaDeEmision);

			taxValues = taxValues.stream().map(item -> {

				if (item.getPorcentage() > 0.0D)
					item.setDescription("GRAVA IVA");

				return item;
			}).collect(Collectors.toList());

			return taxValueMapper.mapperToDTO(taxValues);

		} catch (DataAccessException e) {
			LOGGER.error("Error al listar impuestos IVA", e);
			throw new NotDataAccessException("Error al listar impuestos IVA: " + e.getMessage());
		}

	}

	@Override
	@Transactional(readOnly = true)
	public List<TaxValueResponseDTO> findAllICE() {
	
		try {

			List<TaxValue> taxValues = taxValueRepository.findAllICE();
			return taxValueMapper.mapperToDTO(taxValues);

		} catch (DataAccessException e) {
			LOGGER.error("Error al listar impuestos ICE", e);
			throw new NotDataAccessException("Error al listar impuestos ICE: " + e.getMessage());
		}
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<TaxValueResponseDTO> findAllIRBPNR() {
		try {

			List<TaxValue> taxValues = taxValueRepository.findAllIRBPNR();
			return taxValueMapper.mapperToDTO(taxValues);

		} catch (DataAccessException e) {
			LOGGER.error("Error al listar impuestos IRBPNR", e);
			throw new NotDataAccessException("Error al listar impuestos IRBPNR: " + e.getMessage());
		}
	}

}
