package com.facturacion.ideas.api.services;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facturacion.ideas.api.admin.AdminCodeDocument;
import com.facturacion.ideas.api.admin.AdminEmissionPoint;
import com.facturacion.ideas.api.admin.AdminSubsidiary;
import com.facturacion.ideas.api.dto.SubsidiaryNewDTO;
import com.facturacion.ideas.api.dto.SubsidiaryResponseDTO;
import com.facturacion.ideas.api.entities.CodeDocument;
import com.facturacion.ideas.api.entities.Count;
import com.facturacion.ideas.api.entities.Sender;
import com.facturacion.ideas.api.entities.Subsidiary;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.exeption.NotFoundException;
import com.facturacion.ideas.api.mapper.ISubsidiaryMapper;
import com.facturacion.ideas.api.repositories.ICodeDocumentRepository;
import com.facturacion.ideas.api.repositories.ISenderRepository;
import com.facturacion.ideas.api.repositories.ISubsidiaryRepository;
import com.facturacion.ideas.api.util.ConstanteUtil;

@Service
public class SubsidiaryServiceImpl implements ISubsidiaryService {

	private static final Logger LOGGER = LogManager.getLogger(SubsidiaryServiceImpl.class);

	@Autowired
	private ISubsidiaryRepository subsidiaryRepository;

	@Autowired
	private ISenderRepository senderRepository;

	@Autowired
	private ICodeDocumentRepository codeDocumentRepository;

	@Autowired
	private ISubsidiaryMapper subsidiaryMapper;

	@Override
	@Transactional
	public SubsidiaryResponseDTO save(SubsidiaryNewDTO subsidiaryNewDTO, Long idSender) {

		try {

			Sender sender = senderRepository.findById(idSender).orElseThrow(() -> new NotFoundException(
					"ID emisor: " + idSender + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

			Count count = sender.getCount();

			// Obtener el numero establecimiento maximo que tiene un emisor: se
			// lo busca x su codigo de cuenta unico
			Optional<Integer> numberMax = codeDocumentRepository.findNumberMaxSender(count.getIde());

			Integer numberNext = AdminSubsidiary.getNumberNextSubsidiary(numberMax.orElse(null));

			// Crear Establecimiento
			AdminSubsidiary.createOther(subsidiaryNewDTO, sender, numberNext);

			Subsidiary subsidiary = subsidiaryMapper.mapperToEntity(subsidiaryNewDTO);

			// Agregar al establecimiento el emisor
			subsidiary.setSender(sender);

			/*
			 * Creara el primer Punto de emision y lo agregar al establecimiento recien
			 * creado Pasamos Null para que valide como el primer punto de emiion de este
			 * establecimiento
			 */
			subsidiary.addEmissionPoint(AdminEmissionPoint.create(null, sender.getRuc()));

			// Persistir nuevo establecimiento con su punto emision
			SubsidiaryResponseDTO subsidiarySaved = subsidiaryMapper.mapperToDTO(subsidiaryRepository.save(subsidiary));

			// Ingresar datos numeros documentos
			CodeDocument codeDocument = AdminCodeDocument.create(count.getIde(), subsidiarySaved.getCode(), numberNext,
					null);

			codeDocumentRepository.save(codeDocument);

			return subsidiarySaved;

		} catch (DataAccessException | ParseException e) {
			LOGGER.error("Error guardar establecimiento: ", e.getMessage());
			throw new NotDataAccessException("Error guardar establecimiento: " + e.getMessage());
		}
	}

	@Override
	@Transactional
	public void deleteById(Long ide) {

		try {
			Subsidiary subsidiary = subsidiaryRepository.findById(ide).orElseThrow(() -> new NotFoundException(
					"Id establecimiento: " + ide + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

			// Eliminar El establecimiento y por la cascada sus Puntos de Emision
			subsidiaryRepository.deleteById(subsidiary.getIde());

			// Eliminar El Registro en CodeDocument del Establecimiento recientiemente
			// eliminado
			Long idCount = subsidiary.getSender().getCount().getIde();
			codeDocumentRepository.deleteByIdCountAndCodeSubsidiary(idCount, subsidiary.getCode());

		} catch (DataAccessException e) {
			LOGGER.error("Error eliminar establecimiento: ", e.getMessage());
			throw new NotDataAccessException("Error eliminar establecimiento: " + e.getMessage());
		}

	}

	@Override
	@Transactional(readOnly = true)
	public List<SubsidiaryResponseDTO> findAll(Long idSender) {

		try {
			Sender sender = senderRepository.findById(idSender).orElseThrow(() -> new NotFoundException(
					"ID emisor: " + idSender + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

			List<Subsidiary> subsidiaries = sender.getSubsidiarys();

			return subsidiaryMapper.mapperToDTO(subsidiaries);

		} catch (DataAccessException e) {

			LOGGER.error("Error listar establecimientos x Emisor: ", e.getMessage());
			throw new NotDataAccessException("Error listar establecimiento x Emisor: " + e.getMessage());
		}

	}

	@Override
	@Transactional(readOnly = true)
	public SubsidiaryResponseDTO findById(Long ide) {

		try {

			Subsidiary subsidiary = subsidiaryRepository.findById(ide).orElseThrow(() -> new NotFoundException(
					"id establecimiento: " + ide + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

			return subsidiaryMapper.mapperToDTO(subsidiary);

		} catch (DataAccessException e) {

			LOGGER.error("Error buscar establecimiento: ", e.getMessage());
			throw new NotDataAccessException("Error buscar establecimiento: " + e.getMessage());
		}

	}

	@Override
	@Transactional(readOnly = true)
	public List<SubsidiaryResponseDTO> findByCodeAndSender(String code, Long idSender) {

		try {

			Sender sender = senderRepository.findById(idSender).orElseThrow(() -> new NotFoundException(
					"ID emisor: " + idSender + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

			List<Subsidiary> subsidiaries = subsidiaryRepository.findByCodeAndSender(code, sender);

			return subsidiaryMapper.mapperToDTO(subsidiaries);

		} catch (DataAccessException e) {

			LOGGER.error("Error actualizar establecimientos: ", e.getMessage());
			throw new NotDataAccessException("Error actualizar establecimiento: " + e.getMessage());
		}
	}

	@Override
	@Transactional
	public SubsidiaryResponseDTO update(SubsidiaryNewDTO subsidiaryNewDTO, Long id) {

		try {
			Subsidiary subsidiary = subsidiaryRepository.findById(id).orElseThrow(() -> new NotFoundException(
					"id establecimiento: " + id + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION));

			if (subsidiaryNewDTO.getAddress() != null) {
				subsidiary.setAddress(subsidiaryNewDTO.getAddress());

			}

			subsidiary.setStatus(subsidiaryNewDTO.isStatus());

			return subsidiaryMapper.mapperToDTO(subsidiaryRepository.save(subsidiary));
		} catch (DataAccessException e) {
			LOGGER.error("Error listar establecimientos x code: ", e.getMessage());
			throw new NotDataAccessException("Error listar establecimiento x code: " + e.getMessage());
		}
	
	}


}
