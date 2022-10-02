package com.facturacion.ideas.api.controllers;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facturacion.ideas.api.admin.AdminCodeDocument;
import com.facturacion.ideas.api.admin.AdminSubsidiary;
import com.facturacion.ideas.api.controller.operation.ISubsidiaryOperation;
import com.facturacion.ideas.api.entities.CodeDocument;
import com.facturacion.ideas.api.entities.Count;
import com.facturacion.ideas.api.entities.Sender;
import com.facturacion.ideas.api.entities.Subsidiary;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.exeption.NotFoundException;
import com.facturacion.ideas.api.services.ICodeDocumentService;
import com.facturacion.ideas.api.services.ICountService;
import com.facturacion.ideas.api.services.ISubsidiaryService;
import com.facturacion.ideas.api.util.ConstanteUtil;
import com.facturacion.ideas.api.util.FunctionUtil;

/**
 * RestController que expone servicios web para la entidad {@link Subsidiary}
 * 
 * @author Ronny Chamba
 *
 */
@RestController
@RequestMapping("/facturacion/senders/{id}/subsidiarys")
public class SubsidiaryRestController implements ISubsidiaryOperation {

	private static final Logger LOGGER = LogManager.getLogger(SubsidiaryRestController.class);

	@Autowired
	private ISubsidiaryService subsidiaryService;

	@Autowired
	private ICountService senderService;

	@Autowired
	private ICodeDocumentService codeDocumentService;

	@Override
	public ResponseEntity<?> save(Subsidiary subsidiary, Long idSender) {

		LOGGER.info("Id  Emisor: " + idSender);

		try {

			// Optional<Sender> senderOptional = senderService.findSenderById(idSender);
			Optional<Sender> senderOptional = null;

			if (!senderOptional.isEmpty()) {

				Sender senderCurrent = senderOptional.get();

				Count countSender = senderCurrent.getCount();

				Optional<Integer> numberMax = codeDocumentService.findNumberMaxByIdCount(countSender.getIde());

				Integer numberNext = AdminSubsidiary.getNumberNextSubsidiary(numberMax.orElse(null));

				// Crear Establecimiento
				AdminSubsidiary.createOther(subsidiary, senderCurrent, countSender.getIde(), numberNext);

				Subsidiary subsidiarySave = subsidiaryService.save(subsidiary);

				// Ingresar datos numeros documentos
				CodeDocument codeDocument = AdminCodeDocument.create(countSender.getIde(), subsidiarySave.getCode(),
						numberNext, null);

				codeDocumentService.save(codeDocument);

				return FunctionUtil.getResponseEntity(HttpStatus.CREATED, subsidiarySave);

			} else
				throw new NotFoundException("id: " + idSender + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION);

		} catch (DataAccessException e) {

			LOGGER.error("Error guardar establecimiento", e);

			throw new NotDataAccessException("Error guardar establecimiento: " + e.getMessage());
		}
	}

	@Override
	public ResponseEntity<?> findAll(Long idSender) {

		LOGGER.info("Id  Emisor: " + idSender);

		try {
			// Optional<Sender> senderOptional = senderService.findSenderById(idSender);
			Optional<Sender> senderOptional = null;

			if (!senderOptional.isEmpty()) {

				Sender senderCurrent = senderOptional.get();

				List<Subsidiary> subsidiaries = senderCurrent.getSubsidiarys();

				return FunctionUtil.getResponseEntity(HttpStatus.OK, subsidiaries);

			} else

				throw new NotFoundException("id: " + idSender + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION);

		} catch (DataAccessException e) {
			LOGGER.error("Error listar establecimientoas", e);

			throw new NotDataAccessException("Error listar  establecimientos: " + e.getMessage());

		}

	}

	@Override
	public ResponseEntity<?> findById(Long idSender, Long codigo) {

		LOGGER.info("Id  Emisor: " + idSender);

		try {
			// Optional<Sender> senderOptional = senderService.findSenderById(idSender);
			Optional<Sender> senderOptional = null;

			if (!senderOptional.isEmpty()) {

				// Este metodo tambien sirve,
				// Optional<Subsidiary> subsidiaryOptional = subsidiaryService.findById(codigo);

				Optional<Subsidiary> subsidiaryOptional = subsidiaryService.findByIdAndSender(codigo,
						senderOptional.get());

				if (!subsidiaryOptional.isEmpty()) {

					return FunctionUtil.getResponseEntity(HttpStatus.OK, subsidiaryOptional.get());

				} else

					throw new NotFoundException(
							"establecimiento: " + codigo + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION);

			} else
				throw new NotFoundException("emisor: " + idSender + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION);

		} catch (DataAccessException e) {
			LOGGER.error("Error buscar establecimiento", e);

			throw new NotDataAccessException("Error buscar establecimiento: " + e.getMessage());
		}

	}

	@Override
	public ResponseEntity<?> update(Subsidiary subsidiary, Long idSender, Long codigo) {

		LOGGER.info("Id  Emisor: " + idSender);

		try {
			// Optional<Sender> senderOptional = senderService.findSenderById(idSender);
			Optional<Sender> senderOptional = null;

			if (!senderOptional.isEmpty()) {

				Optional<Subsidiary> subsidiaryOptional = subsidiaryService.findByIdAndSender(codigo,
						senderOptional.get());

				if (!subsidiaryOptional.isEmpty()) {

					Subsidiary subsidiaryCurrent = subsidiaryOptional.get();

					// Cambiar solo la direccion del establecimiento
					subsidiaryCurrent.setAddress(
							subsidiary.getAddress() == null ? subsidiaryCurrent.getAddress() : subsidiary.getAddress());

					subsidiaryCurrent = subsidiaryService.save(subsidiaryCurrent);

					return FunctionUtil.getResponseEntity(HttpStatus.OK, subsidiaryCurrent);

				} else

					throw new NotFoundException(
							"establecimiento: " + codigo + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION);

			} else
				throw new NotFoundException("emisor: " + idSender + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION);

		} catch (DataAccessException e) {
			LOGGER.error("Error actualizar  establecimiento", e);

			throw new NotDataAccessException("Error actualizar  establecimiento: " + e.getMessage());
		}
	}

	@Override
	public ResponseEntity<?> delete(Long idSender, Long codigo) {

		LOGGER.info("Id  Emisor: " + idSender);

		try {
			// Optional<Sender> senderOptional = senderService.findSenderById(idSender);

			Optional<Sender> senderOptional = null;

			if (!senderOptional.isEmpty()) {

				Sender senderCurrent = senderOptional.get();

				Optional<Subsidiary> subsidiaryOptional = subsidiaryService.findByIdAndSender(codigo, senderCurrent);

				if (!subsidiaryOptional.isEmpty()) {

					Subsidiary subsidiaryCurrent = subsidiaryOptional.get();

					String codSubsidiary = subsidiaryCurrent.getCode();

					// Eliminar El establecimiento y por la cascada sus Puntos de Emision
					subsidiaryService.deleteById(subsidiaryCurrent.getIde());

					// Eliminar El Registro en CodeDocument del Establecimiento recientiemente
					// eliminado
					codeDocumentService.deleteByIdCountAndCodeSubsidiary(senderCurrent.getIde(), codSubsidiary);

					return FunctionUtil.getResponseEntity(HttpStatus.NO_CONTENT, "");

				} else
					throw new NotFoundException(
							"establecimiento: " + codigo + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION);

			} else
				throw new NotFoundException("emisor: " + idSender + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION);

		} catch (DataAccessException e) {
			LOGGER.error("Error eliminar establecimientoas", e);

			throw new NotDataAccessException("Error eliminar establecimiento: " + e.getMessage());
		}

	}

}
