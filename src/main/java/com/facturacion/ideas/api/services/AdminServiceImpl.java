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
import com.facturacion.ideas.api.dto.SenderResponseDTO;
import com.facturacion.ideas.api.dto.SubsidiaryAndEmissionPointDTO;
import com.facturacion.ideas.api.entities.Agreement;
import com.facturacion.ideas.api.entities.Count;
import com.facturacion.ideas.api.entities.DetailsAggrement;
import com.facturacion.ideas.api.entities.Sender;
import com.facturacion.ideas.api.entities.Subsidiary;
import com.facturacion.ideas.api.exeption.DuplicatedResourceException;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.exeption.NotFoundException;
import com.facturacion.ideas.api.mapper.ICountMapper;
import com.facturacion.ideas.api.mapper.ISenderMapper;
import com.facturacion.ideas.api.mapper.ISubsidiaryMapper;
import com.facturacion.ideas.api.repositories.IAgreementRepository;
import com.facturacion.ideas.api.repositories.ICodeDocumentRepository;
import com.facturacion.ideas.api.repositories.ICountRepository;
import com.facturacion.ideas.api.repositories.IDetailsAgreementRepository;
import com.facturacion.ideas.api.repositories.ISenderRepository;
import com.facturacion.ideas.api.repositories.ISubsidiaryRepository;
import com.facturacion.ideas.api.util.ConstanteUtil;

@Service
public class AdminServiceImpl implements IAdminService {

	private static final Logger LOGGER = LogManager.getLogger(AdminServiceImpl.class);

	@Autowired
	private ICountRepository countRepository;

	@Autowired
	private IAgreementRepository agreementRepository;

	@Autowired
	private IDetailsAgreementRepository detailsAgreementRepository;

	@Autowired
	private ISenderRepository senderRepository;
	
	@Autowired
	private ISubsidiaryRepository subsidiaryRepository;

	@Autowired
	private ISubsidiaryMapper subsidiaryMapper;

	@Autowired
	private ISenderMapper senderMapper;
	
	@Autowired
	private ICountMapper countMapper;
	
	@Autowired
	private ICodeDocumentRepository codeDocumentRepository;
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

	@Override
	@Transactional(readOnly = true)
	public SenderResponseDTO findByCount(Long idCount) {

		try {

			Sender sender = senderRepository.fetchByWithCount(idCount).orElseThrow(() -> new NotFoundException(
					"Cuenta id: " + idCount + " No tiene registrado un emisor"));

			return senderMapper.mapperToDTO(sender);

		} catch (DataAccessException e) {

			LOGGER.info("Error buscar emisor por cuenta", e);
			throw new NotDataAccessException("Error al busca emisor por cuenta" + e.getMessage());

		}

	}

	@Override
	public List<SubsidiaryAndEmissionPointDTO> fetchBySenderWithEmissionPoint(Long idSender) {

		try {

			List<Subsidiary> subsidiaries = subsidiaryRepository.fetchBySenderWithEmissionPoint(idSender);

			if (subsidiaries.size() < 1) {
				throw new NotFoundException(
						"Emisor id " + idSender + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION);
			}

			List<SubsidiaryAndEmissionPointDTO> subsidiaryResponseDTOs = subsidiaryMapper
					.mapperToDTOAndEmissionPoint(subsidiaries);

			return subsidiaryResponseDTOs;

		} catch (DataAccessException e) {

			LOGGER.info("Error listar sender punto emision", e);
			throw new NotDataAccessException("Error listar sender punto emision" + e.getMessage());

		}

	}

	@Override
	@Transactional
	public void deleteCountById(Long id) {
	
		try {

			Count count = countRepository.findById(id).orElseThrow(
					() -> new NotFoundException("id: " + id + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

			Long ide = count.getIde();

			countRepository.deleteById(ide);

			// Eliminar los registros en la CodeDocument, que
			// esten relacionados con la cuenta recientemente elimnada
			codeDocumentRepository.deleteByCodeCount(ide);

		} catch (DataAccessException e) {

			LOGGER.info("Erro eliminar cuenta", e);
			throw new NotDataAccessException("Erro eliminar cuenta" + e.getMessage());

		}
		
		
		
	}

}
