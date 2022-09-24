package com.facturacion.ideas.api.controllers;

import java.util.Date;
import java.util.List;
import java.util.Map;
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
import com.facturacion.ideas.api.entities.EmissionPoint;
import com.facturacion.ideas.api.entities.Sender;
import com.facturacion.ideas.api.entities.Subsidiary;
import com.facturacion.ideas.api.services.ICodeDocumentService;
import com.facturacion.ideas.api.services.ISenderService;
import com.facturacion.ideas.api.util.FunctionUtil;

/**
 * RestController que expone servicios web para la entidad {@link Sender}
 * 
 * @author Ronny Chamba
 *
 */
@RestController
@RequestMapping("/facturacion/senders")
public class SenderRestController implements ISenderOperation {

	private static final Logger LOGGER = LogManager.getLogger(SenderRestController.class);

	@Autowired
	private ISenderService senderService;

	@Autowired
	private ICodeDocumentService codeDocumentService;
	
	@Override
	public ResponseEntity<?> save(Sender sender, Long idCount) {
		LOGGER.info("Id Cuenta Emisor: " + idCount);

		ResponseEntity<Map<String, Object>> responseEntity = null;

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

					//Crear Establecimiento
					Subsidiary subsidiary = AdminSubsidiary.create(sender, countCurrent.getIde(), numberNext);
		
					// Agregar al emisor el establecimiento
					sender.addSubsidiary(subsidiary);

					// Persistir el Emisor
					Sender senderCurrent = senderService.saveSender(sender);

					// Ingresar datos numeros documentos
					CodeDocument codeDocument = AdminCodeDocument.create(countCurrent.getIde(), subsidiary.getCode(),
							numberNext);

					codeDocumentService.save(codeDocument);

					responseEntity = FunctionUtil.getResponseEntity(HttpStatus.CREATED, senderCurrent, null);

				} else
					responseEntity = FunctionUtil.getResponseEntity(HttpStatus.BAD_REQUEST, null,
							"Emisor con ruc " + countCurrent.getRuc() + " ya esta registrado");

			} else
				responseEntity = FunctionUtil.getResponseEntity(HttpStatus.NOT_FOUND, null,
						"Cuenta con id " + idCount + " no esta registrada");

		} catch (DataAccessException e) {
			LOGGER.error("Error al guardar emisor:", e);

			responseEntity = FunctionUtil.getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " : " + e.getMostSpecificCause());

		}

		return responseEntity;
	}

	@Override
	public ResponseEntity<?> findAll() {
		ResponseEntity<Map<String, Object>> responseEntity = null;

		try {

			List<Sender> senders = senderService.findSenderAll();

			responseEntity = FunctionUtil.getResponseEntity(HttpStatus.OK, senders, null);

		} catch (DataAccessException e) {
			LOGGER.error("Error al listar todos emisor:", e);

			responseEntity = FunctionUtil.getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " : " + e.getMostSpecificCause());

		}

		return responseEntity;
	}

	@Override
	public ResponseEntity<?> findById(Long id) {
		ResponseEntity<Map<String, Object>> responseEntity = null;

		try {

			Optional<Sender> senderOptional = senderService.findSenderById(id);

			if (!senderOptional.isEmpty()) {

				responseEntity = FunctionUtil.getResponseEntity(HttpStatus.OK, senderOptional.get(), null);
			} else
				responseEntity = FunctionUtil.getResponseEntity(HttpStatus.NOT_FOUND, null,
						"El emisor con id " + id + " no esta registrado");

		} catch (DataAccessException e) {
			LOGGER.error("Error al buscar emisor:", e);

			responseEntity = FunctionUtil.getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " : " + e.getMostSpecificCause());

		}

		return responseEntity;

	}

	@Override
	public ResponseEntity<?> update(Sender sender, Long id) {
		
		LOGGER.info("Id Emisor: " + id);

		ResponseEntity<Map<String, Object>> responseEntity = null;

		try {

			// Verificar si existe el emisor
			Optional<Sender> senderOptional = senderService.findSenderById(id);

			if (!senderOptional.isEmpty()) {

				Sender senderCurrent = senderOptional.get();

				AdminSender.update(senderCurrent, sender);

				Sender senderUpdate = senderService.saveSender(senderCurrent);

				LOGGER.info("Emisor actualizado : " + senderUpdate);
				responseEntity = FunctionUtil.getResponseEntity(HttpStatus.OK, senderUpdate, null);

			} else
				responseEntity = FunctionUtil.getResponseEntity(HttpStatus.NOT_FOUND, null,
						"Emisor con id " + id + " no esta registrado");

		} catch (DataAccessException e) {
			LOGGER.error("Error al actualizar emisor:", e);

			responseEntity = FunctionUtil.getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " : " + e.getMostSpecificCause());

		}

		return responseEntity;
	}

}
