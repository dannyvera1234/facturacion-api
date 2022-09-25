package com.facturacion.ideas.api.controllers;

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
import com.facturacion.ideas.api.admin.AdminSubsidiary;
import com.facturacion.ideas.api.controller.operation.ISubsidiaryOperation;
import com.facturacion.ideas.api.entities.CodeDocument;
import com.facturacion.ideas.api.entities.Count;
import com.facturacion.ideas.api.entities.Sender;
import com.facturacion.ideas.api.entities.Subsidiary;
import com.facturacion.ideas.api.services.ICodeDocumentService;
import com.facturacion.ideas.api.services.ISenderService;
import com.facturacion.ideas.api.services.ISubsidiaryService;
import com.facturacion.ideas.api.util.FunctionUtil;

@RestController
@RequestMapping("/facturacion/senders/{id}")
public class SubsidiaryRestController implements ISubsidiaryOperation {

	private static final Logger LOGGER = LogManager.getLogger(SubsidiaryRestController.class);

	@Autowired
	private ISubsidiaryService subsidiaryService;

	@Autowired
	private ISenderService senderService;

	@Autowired
	private ICodeDocumentService codeDocumentService;

	@Override
	public ResponseEntity<?> save(Subsidiary subsidiary, Long idSender) {

		LOGGER.info("Id  Emisor: " + idSender);

		ResponseEntity<Map<String, Object>> responseEntity = null;

		try {

			Optional<Sender> senderOptional = senderService.findSenderById(idSender);

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

				responseEntity = FunctionUtil.getResponseEntity(HttpStatus.CREATED, subsidiarySave.getCode(), null);

			} else
				responseEntity = FunctionUtil.getResponseEntity(HttpStatus.NOT_FOUND, null,
						"Emisor con Id " + idSender + " no esta registrado en la Base de Datos");

		} catch (DataAccessException e) {
			LOGGER.error("Error al guardar establecimiento:", e);

			responseEntity = FunctionUtil.getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " : " + e.getMostSpecificCause());
		}

		return responseEntity;
	}

	@Override
	public ResponseEntity<?> findAll(Long idSender) {

		LOGGER.info("Id  Emisor: " + idSender);

		ResponseEntity<Map<String, Object>> responseEntity = null;

		try {
			Optional<Sender> senderOptional = senderService.findSenderById(idSender);

			if (!senderOptional.isEmpty()) {

				Sender senderCurrent = senderOptional.get();

				List<Subsidiary> subsidiaries = senderCurrent.getSubsidiarys();

				responseEntity = FunctionUtil.getResponseEntity(HttpStatus.OK, subsidiaries, null);

			} else
				responseEntity = FunctionUtil.getResponseEntity(HttpStatus.NOT_FOUND, null,
						"Emisor con Id " + idSender + " no esta registrado en la Base de Datos");

		} catch (DataAccessException e) {
			LOGGER.error("Error al listar establecimientoas", e);

			responseEntity = FunctionUtil.getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " : " + e.getMostSpecificCause());
		}

		return responseEntity;
	}

	@Override
	public ResponseEntity<?> findById(Long idSender, Long codigo) {

		LOGGER.info("Id  Emisor: " + idSender);

		ResponseEntity<Map<String, Object>> responseEntity = null;

		try {
			Optional<Sender> senderOptional = senderService.findSenderById(idSender);

			if (!senderOptional.isEmpty()) {

				// Este metodo tambien sirve,
				// Optional<Subsidiary> subsidiaryOptional = subsidiaryService.findById(codigo);

				Optional<Subsidiary> subsidiaryOptional = subsidiaryService.findByIdAndSender(codigo,
						senderOptional.get());

				if (!subsidiaryOptional.isEmpty()) {

					responseEntity = FunctionUtil.getResponseEntity(HttpStatus.OK, subsidiaryOptional.get(), null);

				} else
					responseEntity = FunctionUtil.getResponseEntity(HttpStatus.NOT_FOUND, null,
							"Establecimiento con Id " + codigo + " no esta registrado en la Base de Datos");

			} else
				responseEntity = FunctionUtil.getResponseEntity(HttpStatus.NOT_FOUND, null,
						"Emisor con Id " + idSender + " no esta registrado en la Base de Datos");

		} catch (DataAccessException e) {
			LOGGER.error("Error al buscar un establecimientoas", e);

			responseEntity = FunctionUtil.getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " : " + e.getMostSpecificCause());
		}

		return responseEntity;
	}

	@Override
	public ResponseEntity<?> update(Subsidiary subsidiary, Long idSender, Long codigo) {

		LOGGER.info("Id  Emisor: " + idSender);

		ResponseEntity<Map<String, Object>> responseEntity = null;

		try {
			Optional<Sender> senderOptional = senderService.findSenderById(idSender);

			if (!senderOptional.isEmpty()) {

				Optional<Subsidiary> subsidiaryOptional = subsidiaryService.findByIdAndSender(codigo,
						senderOptional.get());

				if (!subsidiaryOptional.isEmpty()) {

					Subsidiary subsidiaryCurrent = subsidiaryOptional.get();

					// Cambiar solo la direccion del establecimiento
					subsidiaryCurrent.setAddress(
							subsidiary.getAddress() == null ? subsidiaryCurrent.getAddress() : subsidiary.getAddress());

					subsidiaryCurrent = subsidiaryService.save(subsidiaryCurrent);

					responseEntity = FunctionUtil.getResponseEntity(HttpStatus.OK, subsidiaryCurrent, null);

				} else
					responseEntity = FunctionUtil.getResponseEntity(HttpStatus.NOT_FOUND, null,
							"Establecimiento con Id " + codigo + " no esta registrado en la Base de Datos");

			} else
				responseEntity = FunctionUtil.getResponseEntity(HttpStatus.NOT_FOUND, null,
						"Emisor con Id " + idSender + " no esta registrado en la Base de Datos");

		} catch (DataAccessException e) {
			LOGGER.error("Error al actualizar un establecimiento", e);

			responseEntity = FunctionUtil.getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " : " + e.getMostSpecificCause());
		}

		return responseEntity;
	}

	@Override
	public ResponseEntity<?> delete(Long idSender, Long codigo) {

		LOGGER.info("Id  Emisor: " + idSender);

		ResponseEntity<Map<String, Object>> responseEntity = null;

		try {
			Optional<Sender> senderOptional = senderService.findSenderById(idSender);

			if (!senderOptional.isEmpty()) {

				Sender senderCurrent = senderOptional.get();

				Optional<Subsidiary> subsidiaryOptional = subsidiaryService.findByIdAndSender(codigo, senderCurrent);

				if (!subsidiaryOptional.isEmpty()) {

					Subsidiary subsidiaryCurrent = subsidiaryOptional.get();

					String codSubsidiary = subsidiaryCurrent.getCode();

					// Eliminar El establecimiento y pro la cascada sus Puntos de Emision
					subsidiaryService.deleteById(subsidiaryCurrent.getIde());

					responseEntity = FunctionUtil.getResponseEntity(HttpStatus.OK,
							"Establecimiento " + subsidiaryCurrent.getIde() + "  eliminado con exito", null);

					// Eliminar El Registro en CodeDocument del Establecimiento recientiemente
					// eliminado
					codeDocumentService.deleteByIdCountAndCodeSubsidiary(senderCurrent.getIde(), codSubsidiary);

				} else
					responseEntity = FunctionUtil.getResponseEntity(HttpStatus.NOT_FOUND, null,
							"Establecimiento con Id " + codigo + " no esta registrado en la Base de Datos");

			} else
				responseEntity = FunctionUtil.getResponseEntity(HttpStatus.NOT_FOUND, null,
						"Emisor con Id " + idSender + " no esta registrado en la Base de Datos");

		} catch (DataAccessException e) {
			LOGGER.error("Error al eliminar un establecimientoas", e);

			responseEntity = FunctionUtil.getResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, null,
					e.getMessage() + " : " + e.getMostSpecificCause());
		}

		return responseEntity;
	}

}
