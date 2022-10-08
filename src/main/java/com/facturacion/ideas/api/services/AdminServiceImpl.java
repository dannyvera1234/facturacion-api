package com.facturacion.ideas.api.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facturacion.ideas.api.admin.AdminDetailsAggrement;
import com.facturacion.ideas.api.dto.CountNewDTO;
import com.facturacion.ideas.api.dto.CountResponseDTO;
import com.facturacion.ideas.api.entities.Agreement;
import com.facturacion.ideas.api.entities.Count;
import com.facturacion.ideas.api.entities.DetailsAggrement;
import com.facturacion.ideas.api.exeption.DuplicatedResourceException;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.exeption.NotFoundException;
import com.facturacion.ideas.api.mapper.ICountMapper;
import com.facturacion.ideas.api.repositories.IAgreementRepository;
import com.facturacion.ideas.api.repositories.ICountRepository;
import com.facturacion.ideas.api.repositories.IDetailsAgreementRepository;
import com.facturacion.ideas.api.util.ConstanteUtil;

@Service
public class AdminServiceImpl implements IAdminService {

	private static final Logger LOGGER = LogManager.getLogger(AdminServiceImpl.class);

	@Autowired
	private ICountRepository countRepository;

	@Autowired
	private ICountMapper countMapper;

	@Autowired
	private IAgreementRepository agreementRepository;

	@Autowired
	private IDetailsAgreementRepository detailsAgreementRepository;

	@Override
	@Transactional
	public CountResponseDTO saveCount(CountNewDTO countNewDTO) {
		try {

			// Ya existe el ruc
			if (countRepository.existsByRuc(countNewDTO.getRuc()))
				throw new DuplicatedResourceException(
						"ruc: " + countNewDTO.getRuc() + ConstanteUtil.MESSAJE_DUPLICATED_RESOURCE_DEFAULT_EXCEPTION);

			// Buscar el plan
			Agreement agreement = agreementRepository.findById(countNewDTO.getIdAgreement())
					.orElseThrow(() -> new NotFoundException("Plan id: " + countNewDTO.getIdAgreement()
							+ ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

			Count countSaved = countRepository.save(countMapper.mapperToEntity(countNewDTO));

			// Asignar Plan a cuenta
			DetailsAggrement detailsAggrement = AdminDetailsAggrement.create(agreement.getTypeAgreement(),
					countNewDTO.getAmount());
			detailsAggrement.setGreement(agreement);
			detailsAggrement.setCount(countSaved);

			detailsAgreementRepository.save(detailsAggrement);

			return countMapper.mapperToDTO(countSaved);
		} catch (DataAccessException e) {

			LOGGER.error("Error guardar cuenta", e);
			throw new NotDataAccessException("Error guardar cuenta: " + e.getMessage());
		}

	}

	@Override
	@Transactional(readOnly = true)
	public List<CountResponseDTO> fetchByWithAgreement() {

		try {

			List<Count> counts = countRepository.fetchByWithAgreement();

			List<CountResponseDTO> countResponseDTOs = countMapper.mapperToDTO(counts);
			return countResponseDTOs;
		} catch (DataAccessException e) {

			LOGGER.info("Error listar cuentas", e);
			throw new NotDataAccessException("Error listar cuentas" + e.getMessage());

		}

	}

	@Override
	@Transactional
	public CountResponseDTO updateCountStatus(Long ide) {

		try {

			Count count = countRepository.findById(ide).orElseThrow(() -> new NotFoundException(
					"Cuenta id :" + ide + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

			count.setEstado(!count.isEstado());

			Count countUpdate = countRepository.save(count);

			return countMapper.mapperToDTO(countUpdate);

		} catch (DataAccessException e) {

			LOGGER.info("Error actualizar estado cuenta", e);
			throw new NotDataAccessException("Error actualizar estado cuenta" + e.getMessage());

		}
	}

}
