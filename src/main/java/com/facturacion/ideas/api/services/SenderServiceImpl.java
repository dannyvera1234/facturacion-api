package com.facturacion.ideas.api.services;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facturacion.ideas.api.dto.CountNewDTO;
import com.facturacion.ideas.api.dto.CountResponseDTO;
import com.facturacion.ideas.api.dto.LoginDTO;
import com.facturacion.ideas.api.entities.Count;
import com.facturacion.ideas.api.entities.Login;
import com.facturacion.ideas.api.entities.Sender;
import com.facturacion.ideas.api.exeption.DuplicatedResourceException;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.exeption.NotFoundException;
import com.facturacion.ideas.api.mapper.ICountMapper;
import com.facturacion.ideas.api.repositories.ICodeDocumentRepository;
import com.facturacion.ideas.api.repositories.ICountRepository;
import com.facturacion.ideas.api.repositories.ILoginRepository;
import com.facturacion.ideas.api.repositories.ISenderRepository;
import com.facturacion.ideas.api.util.ConstanteUtil;

@Service
public class SenderServiceImpl implements ISenderService {

	private static final Logger LOGGER = LogManager.getLogger(SenderServiceImpl.class);
	@Autowired
	private ICountRepository countRepository;

	@Autowired
	private ILoginRepository loginRepository;

	@Autowired
	private ISenderRepository senderRepository;

	@Autowired
	private ICountMapper countMapper;

	@Autowired
	private ICodeDocumentRepository codeDocumentRepository;

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

	// este se debe eliminar
	@Override
	@Transactional(readOnly = true)
	public Optional<Count> findCountById(Long id) {

		return countRepository.findById(id);
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
	public Count updateCount(Count count) {
		return countRepository.save(count);
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

		return senderRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Sender> findSenderAll() {

		return senderRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Boolean> senderIsExiste(String ruc) {

		return senderRepository.senderIsExist(ruc);
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
}
