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
import com.facturacion.ideas.api.dto.DetailsAgreementDTO;
import com.facturacion.ideas.api.dto.LoginDTO;
import com.facturacion.ideas.api.entities.Agreement;
import com.facturacion.ideas.api.entities.Count;
import com.facturacion.ideas.api.entities.DetailsAggrement;
import com.facturacion.ideas.api.entities.Login;
import com.facturacion.ideas.api.exeption.DuplicatedResourceException;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.exeption.NotFoundException;
import com.facturacion.ideas.api.mapper.ICountMapper;
import com.facturacion.ideas.api.mapper.IDetailsAgreementMapper;
import com.facturacion.ideas.api.repositories.IAgreementRepository;
import com.facturacion.ideas.api.repositories.ICodeDocumentRepository;
import com.facturacion.ideas.api.repositories.ICountRepository;
import com.facturacion.ideas.api.repositories.IDetailsAgreementRepository;
import com.facturacion.ideas.api.repositories.ILoginRepository;
import com.facturacion.ideas.api.util.ConstanteUtil;

@Service
public class CountServiceImpl implements ICountService {

	private static final Logger LOGGER = LogManager.getLogger(CountServiceImpl.class);
	@Autowired
	private ICountRepository countRepository;

	@Autowired
	private ILoginRepository loginRepository;
	
	@Autowired
	private ICodeDocumentRepository codeDocumentRepository;

	@Autowired
	private IAgreementRepository agreementRepository;

	@Autowired
	private IDetailsAgreementRepository detailsAgreementRepository;

	@Autowired
	private ICountMapper countMapper;

	@Autowired
	private IDetailsAgreementMapper detailsAgreementMapper;

	@Override
	@Transactional
	public CountResponseDTO saveCount(CountNewDTO countNewDTO) {

		try {

			Count count = countRepository.findByRuc(null).orElse(null);

			// Ya existe el ruc
			if (count != null)
				throw new DuplicatedResourceException(
						"ruc: " + countNewDTO.getRuc() + ConstanteUtil.MESSAJE_DUPLICATED_RESOURCE_DEFAULT_EXCEPTION);

			Count countSaved = countRepository.save(countMapper.mapperToEntity(countNewDTO));
			return countMapper.mapperToDTO(countSaved);
		} catch (DataAccessException e) {

			LOGGER.error("Error guardar cuenta", e);
			throw new NotDataAccessException("Error guardar cuenta: " + e.getMessage());
		}

	}

	@Override
	@Transactional(readOnly = true)
	public CountResponseDTO findCountByRuc(String ruc) {

		try {
			Count count = countRepository.findByRuc(ruc).orElseThrow(
					() -> new NotFoundException("ruc:" + ruc + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

			return countMapper.mapperToDTO(count);

		} catch (DataAccessException e) {
			LOGGER.info("Error buscar cuenta ruc", e);
			throw new NotDataAccessException("Error buscar cuenta ruc: " + e.getMessage());
		}

	}

	@Override
	@Transactional
	public CountResponseDTO findCountsById(Long id) {

		try {

			Count count = countRepository.findById(id).orElse(null);
			if (count == null)
				throw new NotFoundException("id: " + id + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION);
			return countMapper.mapperToDTO(count);

		} catch (DataAccessException e) {
			LOGGER.error("Error buscar cuenta id", e);
			throw new NotDataAccessException("Error buscar cuenta id: " + e.getMessage());

		}

	}

	@Override
	@Transactional(readOnly = true)
	public List<CountResponseDTO> findCountAll() {

		try {

			List<CountResponseDTO> countResponseDTOs = countMapper.mapperToDTO(countRepository.findAll());

			return countResponseDTOs;
		} catch (DataAccessException e) {
			LOGGER.error("Error listar cuentas", e);
			throw new NotDataAccessException("Error listar cuentas: " + e.getMessage());

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

	@Override
	@Transactional
	public CountResponseDTO updateCount(CountNewDTO countNewDTO, Long idCount) {

		try {

			Count count = countRepository.findById(idCount).orElseThrow(
					() -> new NotFoundException("id: " + idCount + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

			if (countNewDTO.getPassword() != null) {

				count.setPassword(countNewDTO.getPassword());
			}

			return countMapper.mapperToDTO(countRepository.save(count));
		} catch (DataAccessException e) {

			LOGGER.info("Erro actualizar cuenta", e);
			throw new NotDataAccessException("Error actualizar cuenta" + e.getMessage());

		}

	}

	@Override
	@Transactional
	public LoginDTO saveLoginIn(Long idCount) {

		try {

			Count count = countRepository.findById(idCount).orElseThrow(
					() -> new NotFoundException("id: " + idCount + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

			Login login = new Login();

			login.setCount(count);

			Login loginSaved = loginRepository.save(login);

			return countMapper.mapperToEntity(loginSaved);

		} catch (DataAccessException e) {
			LOGGER.info("Error Guardar login", e);
			throw new NotDataAccessException("Error guardar login" + e.getMessage());

		}

	}

	@Override
	public List<LoginDTO> findAllLogin(Long idCount) {

		try {

			Count count = countRepository.findById(idCount).orElseThrow(
					() -> new NotFoundException("id:" + idCount + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

			List<Login> logins = count.getLogins();

			return countMapper.mapperToEntity(logins);

		} catch (DataAccessException e) {

			LOGGER.info("Error listar login", e);
			throw new NotDataAccessException("Error listar login" + e.getMessage());
		}

	}

	@Override
	@Transactional
	public DetailsAgreementDTO saveDetailsAgreementDTO(Long idCount, Long codeAgreement) {

		try {

			Count count = countRepository.findById(idCount).orElseThrow(() -> new NotFoundException(
					"id count: " + idCount + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

			Agreement agreement = agreementRepository.findById(codeAgreement).orElseThrow(() -> new NotFoundException(
					"codigo plan: " + codeAgreement + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

			DetailsAggrement detailsAggrement = AdminDetailsAggrement.create(agreement.getTypeAgreement());
			detailsAggrement.setGreement(agreement);
			detailsAggrement.setCount(count);

			DetailsAggrement detailsAggrementSaved = detailsAgreementRepository.save(detailsAggrement);

			return detailsAgreementMapper.mapperToDTO(detailsAggrementSaved);

		} catch (DataAccessException e) {

			LOGGER.info("Error guardar detailsAgreement", e);
			throw new NotDataAccessException("Error guardar detailsAgreement" + e.getMessage());
		}
	}
}
