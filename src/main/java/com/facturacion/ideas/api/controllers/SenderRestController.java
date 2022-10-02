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
import com.facturacion.ideas.api.admin.AdminSender;
import com.facturacion.ideas.api.admin.AdminSubsidiary;
import com.facturacion.ideas.api.controller.operation.ISenderOperation;
import com.facturacion.ideas.api.entities.CodeDocument;
import com.facturacion.ideas.api.entities.Count;
import com.facturacion.ideas.api.entities.Sender;
import com.facturacion.ideas.api.entities.Subsidiary;
import com.facturacion.ideas.api.exeption.DuplicatedResourceException;
import com.facturacion.ideas.api.exeption.NotDataAccessException;
import com.facturacion.ideas.api.exeption.NotFoundException;
import com.facturacion.ideas.api.services.ICodeDocumentService;
import com.facturacion.ideas.api.services.ICountService;
import com.facturacion.ideas.api.util.ConstanteUtil;
import com.facturacion.ideas.api.util.FunctionUtil;

/**
 * RestController que expone servicios web para la entidad {@link Sender}
 * 
 * @author Ronny Chamba
 *
 */
@RestController
@RequestMapping("/facturacion")
public class SenderRestController implements ISenderOperation {

	private static final Logger LOGGER = LogManager.getLogger(SenderRestController.class);

	@Autowired
	private ICountService senderService;

	@Autowired
	private ICodeDocumentService codeDocumentService;

	@Override
	public ResponseEntity<?> save(Sender sender, Long idCount) {

		LOGGER.info("Id Cuenta Emisor: " + idCount);

		try {

			// Verificar si existe la cuenta
			Optional<Count> countOptional = senderService.findCountById(idCount);

			if (!countOptional.isEmpty()) {

				Count countCurrent = countOptional.get();

				// Verificar si ya existe un emisor con el Ruc
				Optional<Boolean> senderIsExist = senderService.senderIsExiste(countCurrent.getRuc());

				LOGGER.info("Ya existe Sender?:" + senderIsExist);

				if (senderIsExist.isEmpty()) {

					Optional<Integer> numberMax = codeDocumentService.findNumberMaxByIdCount(idCount);

					Integer numberNext = AdminSubsidiary.getNumberNextSubsidiary(numberMax.orElse(null));

					AdminSender.create(sender, countCurrent);

					// Crear Establecimiento
					Subsidiary subsidiary = AdminSubsidiary.create(sender, countCurrent.getIde(), numberNext);

					// Agregar al emisor el establecimiento
					sender.addSubsidiary(subsidiary);

					// Persistir el Emisor
					Sender senderCurrent = senderService.saveSender(sender);

					// Ingresar datos numeros documentos
					CodeDocument codeDocument = AdminCodeDocument.create(countCurrent.getIde(), subsidiary.getCode(),
							numberNext, null);

					codeDocumentService.save(codeDocument);

					return FunctionUtil.getResponseEntity(HttpStatus.CREATED, senderCurrent);

				} else

					throw new DuplicatedResourceException("ruc: " + countCurrent.getRuc()
							+ ConstanteUtil.MESSAJE_DUPLICATED_RESOURCE_DEFAULT_EXCEPTION);
			} else

				throw new NotFoundException("id: " + idCount + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION);

		} catch (DataAccessException e) {

			LOGGER.error("Error guardar emisor:", e);
			throw new NotDataAccessException("Error guardar emision: " + e.getMessage());

		}

	}

	@Override
	public ResponseEntity<?> findAll() {

		try {

			List<Sender> senders = senderService.findSenderAll();

			return FunctionUtil.getResponseEntity(HttpStatus.OK, senders);

		} catch (DataAccessException e) {
			LOGGER.error("Error listar emisores:", e);
			throw new NotDataAccessException("Error listar emisores: " + e.getMessage());

		}
	}

	@Override
	public ResponseEntity<?> findById(Long id) {

		try {

			Optional<Sender> senderOptional = senderService.findSenderById(id);

			if (!senderOptional.isEmpty()) {

				return FunctionUtil.getResponseEntity(HttpStatus.OK, senderOptional.get());
			} else
				throw new NotFoundException("id: " + id + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION);

		} catch (DataAccessException e) {

			LOGGER.error("Error buscar emisor:", e);

			throw new NotDataAccessException("Error buscar emisore: " + e.getMessage());
		}

	}

	@Override
	public ResponseEntity<?> update(Sender sender, Long id) {

		LOGGER.info("Id Emisor: " + id);

		try {

			// Verificar si existe el emisor
			Optional<Sender> senderOptional = senderService.findSenderById(id);

			if (!senderOptional.isEmpty()) {

				Sender senderCurrent = senderOptional.get();

				AdminSender.update(senderCurrent, sender);

				Sender senderUpdate = senderService.saveSender(senderCurrent);

				LOGGER.info("Emisor actualizado : " + senderUpdate);

				return FunctionUtil.getResponseEntity(HttpStatus.OK, senderUpdate);

			} else
				throw new NotFoundException("id: " + id + ConstanteUtil.MESSAJE_NOT_FOUND_DEFAULT_EXCEPTION);

		} catch (DataAccessException e) {

			LOGGER.error("Error actualizar emisor:", e);

			throw new NotDataAccessException("Error actualizar emisore: " + e.getMessage());

		}
	}

}
