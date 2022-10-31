package com.facturacion.ideas.api.services;

import java.util.List;

import com.facturacion.ideas.api.dto.DetailsAgreementDTO;
import com.facturacion.ideas.api.entities.DetailsAggrement;
import com.facturacion.ideas.api.mapper.IDetailsAgreementMapper;
import com.facturacion.ideas.api.repositories.ICountRepository;
import com.facturacion.ideas.api.repositories.IDetailsAgreementRepository;
import com.facturacion.ideas.api.repositories.ISenderRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facturacion.ideas.api.dto.AgreementDTO;
import com.facturacion.ideas.api.entities.Agreement;
import com.facturacion.ideas.api.enums.TypeAgreementEnum;
import com.facturacion.ideas.api.exeption.BadRequestException;
import com.facturacion.ideas.api.exeption.DuplicatedResourceException;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.exeption.NotFoundException;
import com.facturacion.ideas.api.mapper.IAgreementMapper;
import com.facturacion.ideas.api.repositories.IAgreementRepository;
import com.facturacion.ideas.api.util.ConstanteUtil;

@Service
public class AgreementServiceImpl implements IAgreementService {

	private static final Logger LOGGER = LogManager.getLogger(AgreementServiceImpl.class);

	@Autowired
	private IAgreementRepository agreementRepository;

	@Autowired
	private IAgreementMapper agreementMapper;

	@Autowired
	private ICountRepository countRepository;

	@Autowired
	private IDetailsAgreementRepository detailsAgreementRepository;

	@Autowired
	private IDetailsAgreementMapper detailsAgreementMapper;



	@Override
	@Transactional
	public AgreementDTO save(AgreementDTO agreementDTO) {

		try {

			// Validar si el nombre del plan esta correcto
			if (TypeAgreementEnum.getTypeAgreementEnum(agreementDTO.getTypeAgreement())== null) {

				throw new BadRequestException("Nombre Plan: " +  agreementDTO.getTypeAgreement() + 
						" es incorrecto");
			}

			Agreement agreementSear = agreementRepository
					.findByTypeAgreement(TypeAgreementEnum.getTypeAgreementEnum(agreementDTO.getTypeAgreement()))
					.orElse(null);

			if (agreementSear != null) {

				throw new DuplicatedResourceException("Plan : " + agreementSear.getTypeAgreement()
						+ ConstanteUtil.MESSAJE_DUPLICATED_RESOURCE_DEFAULT_EXCEPTION);
			}

			Agreement agreementSaved = agreementMapper.mapperToEntity(agreementDTO);

			agreementSaved = agreementRepository.save(agreementSaved);

			return agreementMapper.mapperToDTO(agreementSaved);

		} catch (DataAccessException e) {
			LOGGER.error("Error guardar plan ", e);
			throw new NotDataAccessException("Errror guardar plan: " + e.getMessage());
		}

	}

	@Override
	@Transactional(readOnly = true)
	public List<AgreementDTO> listAll() {

		try {

			List<Agreement> agreementDTOs = agreementRepository.findAll();

			return agreementMapper.mapperToDTO(agreementDTOs);

		} catch (DataAccessException e) {
			LOGGER.error("Error listar planes ", e);
			throw new NotDataAccessException("Errror listar planes: " + e.getMessage());
		}
	}

	@Override
	@Transactional(readOnly = true)
	public AgreementDTO findById(Long codigo) {

		try {
			Agreement agreement = agreementRepository.findById(codigo).orElseThrow(() -> new NotFoundException(
					"codigo: " + codigo + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

			return agreementMapper.mapperToDTO(agreement);

		} catch (DataAccessException e) {
			LOGGER.error("Error buscar plan id ", e);
			throw new NotDataAccessException("Errror buscar plan id: " + e.getMessage());
		}

	}

	@Override
	public AgreementDTO findByType(String type) {
		return null;
	}

	@Override
	@Transactional
	public void deleteById(Long codigo) {

		try {
			Agreement agreement = agreementRepository.findById(codigo).orElseThrow(() -> new NotFoundException(
					"codigo: " + codigo + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

			agreementRepository.deleteById(agreement.getCodigo());

		} catch (DataAccessException e) {
			LOGGER.error("Error eiminar plan ", e);
			throw new NotDataAccessException("Errror eliminar plan id: " + e.getMessage());
		}

	}

	@Override
	public AgreementDTO update(AgreementDTO agreementDTO, Long codigo) {
		try {
			Agreement agreement = agreementRepository.findById(codigo).orElseThrow(() -> new NotFoundException(
					"codigo: " + codigo + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

			agreement.setValue(agreementDTO.getValue());

			Agreement agreementSaved = agreementRepository.save(agreement);

			return agreementMapper.mapperToDTO(agreementSaved);

		} catch (DataAccessException e) {
			LOGGER.error("Error actualizar plan ", e);
			throw new NotDataAccessException("Errror actualizar plan: " + e.getMessage());
		}
	}

}
